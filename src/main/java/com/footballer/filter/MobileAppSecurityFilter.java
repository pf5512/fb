package com.footballer.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.footballer.filter.context.ApplicationContext;
import com.footballer.rest.api.v1.SecurityService;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.AppSecurity;
import com.footballer.rest.api.v1.vo.enumType.AppTokenType;
import com.footballer.util.SecurityUtils;

/**
 * Created by ian on 6/21/14.
 */
public class MobileAppSecurityFilter implements javax.servlet.Filter {
	
	private static final String WHITLIST_KEY = "whitelist";
    private static final String STATUS_KEY = "status";
    private static final String MESSAGE_KEY = "message";
    private static final String ERROR_STATUS = "error";

    
    private static final String ERROR_NO_IDENTITY = "There is no identity";
    private static final String INVALID_IDENTITY = "the identity is not valid";
    private static final String ERROR_NO_APP_ID = "There is no app id";
    private static final String ERROR_NO_APP_TOKEN = "There is no app token";
    private static final String INVALID_APP_TOKEN = "The app token is not valid";
    

    private Logger logger = LoggerFactory.getLogger(MobileAppSecurityFilter.class);

    private Map<String, AppSecurity> appTokens = new HashMap<String, AppSecurity>();
    
    private Set<String> whitelist;

    @Autowired
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	String whilteListString = filterConfig.getInitParameter(WHITLIST_KEY);
    	
    	if (!StringUtils.isEmpty(whilteListString)) {
    		String[] list = whilteListString.split(",");
    		whitelist = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(list)));

            // log
            for (String passByUrl : whitelist) {
                //System.out.println("init filter whitelist:" + passByUrl);
            }
    	}
    }

    private void init() {
        if (appTokens.isEmpty()) {
            logger.debug("start to init mobile security filter");

            List<AppSecurity> appSecurityList = securityService.findAllAppSecurity();

            if (!CollectionUtils.isEmpty(appSecurityList)) {
                for (AppSecurity appSecurityItem : appSecurityList) {
                    String appId = String.valueOf(appSecurityItem.getAppId());
                    if (!appTokens.containsKey(appId)) {
                        appTokens.put(appId, appSecurityItem);
                    }
                }
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        init();

        if (!validateAppToken((HttpServletRequest) servletRequest, servletResponse)) return;

        ApplicationContext context = loadUserContext((HttpServletRequest) servletRequest);
        
        try {
            AppContextHolder.setContext(context);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            AppContextHolder.clearContext();
        }  
      
    }

    private ApplicationContext loadUserContext(HttpServletRequest request) {
        String url = request.getRequestURI();
        boolean isPassbyUrl = isPassByUrl(url); 
        String identify = null;
        ApplicationContext context = null;
        
        if (StringUtils.hasText(url)) {
            String[] params = url.split("/");
            int length = params.length;

            if (3 < length) {
                identify = params[length - 3];
            }

            Account account = securityService.findUserByIdentify(identify);
            
            /*
            if (account != null || isPassbyUrl) {
            	context = new ApplicationContext(account);
            	context.setAppTokenType(getAppTokenType(request));
            } else {
                //log
                System.out.println("url:" + url);
            	throw new RuntimeException(INVALID_IDENTITY);
            }
            */
        }

        return context;
    }
    
    private AppTokenType getAppTokenType(HttpServletRequest servletRequest) {
    	String url = servletRequest.getRequestURI();
    	String appId = null;
    	
    	if (StringUtils.hasText(url)) {
            String[] params = url.split("/");
            int length = params.length;
            if (3 < length) {
                appId = params[length - 2];
            }
        }
    	
    	AppSecurity appSecurity = appTokens.get(appId);
    	
    	if (null != appSecurity) {
    		return AppTokenType.valueOf(appSecurity.getType());
    	}
    	
    	return null;
    }
    
    private boolean isPassByUrl(String url) {
    	if (!CollectionUtils.isEmpty(whitelist)) {
    		for (String whiteListUrl : whitelist) {
                //System.out.println("whiteListUrl:" + whiteListUrl);
                //System.out.println("current url:" + url);
                //System.out.println("url.indexOf(whiteListUrl) > -1:" + (url.indexOf(whiteListUrl) > -1));
    			if (url.indexOf(whiteListUrl) > -1) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * By default, the identify should be the last third two.
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws IOException
     */
    private boolean validateAppToken(HttpServletRequest servletRequest,
                                     ServletResponse servletResponse)
            throws IOException {
        String url = servletRequest.getRequestURI();
        boolean isPassbyUrl = isPassByUrl(url);
        String identity = null;
        String appId = null;
        String appToken = null;

        if (isPassbyUrl) {
            return true;
        }
        
        if (StringUtils.hasText(url)) {
            String[] params = url.split("/");
            int length = params.length;
            if (3 < length) {
            	identity = params[length -3];
                appId = params[length - 2];
                appToken = params[length -1];
            }
        }

        if (!StringUtils.hasText(identity)) {
            servletResponse.setContentType(MediaType.APPLICATION_JSON);
            servletResponse.getWriter().write(getJSONMessage(ERROR_STATUS, ERROR_NO_IDENTITY));
            return false;
        }else if(identity.contains("null")){
        	servletResponse.setContentType(MediaType.APPLICATION_JSON);
            servletResponse.getWriter().write(getJSONMessage(ERROR_STATUS, INVALID_IDENTITY));
            return false;
        }
        
        if (!StringUtils.hasText(appId)) {
            servletResponse.setContentType(MediaType.APPLICATION_JSON);
            servletResponse.getWriter().write(getJSONMessage(ERROR_STATUS, ERROR_NO_APP_ID));
            return false;
        }

        if (!StringUtils.hasText(appToken)) {
            servletResponse.setContentType(MediaType.APPLICATION_JSON);
            servletResponse.getWriter().write(getJSONMessage(ERROR_STATUS, ERROR_NO_APP_TOKEN));
            return false;
        }

        AppSecurity appSecurity = appTokens.get(appId);
        if (null == appSecurity || !appToken.equals(SecurityUtils.md5(appId + appSecurity.getAppSecurity()))) {
            servletResponse.setContentType(MediaType.APPLICATION_JSON);
            servletResponse.getWriter().write(getJSONMessage(ERROR_STATUS, INVALID_APP_TOKEN));
            return false;
        }
        return true;
    }

    private String getJSONMessage(String status, String message) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(STATUS_KEY, status);
            jsonObject.put(MESSAGE_KEY, message);
        } catch (JSONException e) {
            logger.error(e.getMessage());
        }
        return jsonObject.toString();
    }

    @Override
    public void destroy() {
        appTokens.clear();
        appTokens = null;
    }
}
