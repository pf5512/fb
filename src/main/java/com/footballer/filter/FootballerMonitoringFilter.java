package com.footballer.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.bull.javamelody.MonitoringFilter;

import com.alibaba.druid.support.http.ResourceServlet;

public class FootballerMonitoringFilter extends MonitoringFilter {
    public boolean containsUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        return session.getAttribute(ResourceServlet.SESSION_USER_KEY) != null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.equals(getMonitoringUrl(httpRequest)) && !containsUser(httpRequest)) {
            if (contextPath == null || contextPath.equals("") || contextPath.equals("/")) {
                httpResponse.sendRedirect("/druid/login.html");
            } else {
                httpResponse.sendRedirect("login.html");
            }
            return;
        }
        super.doFilter(httpRequest, httpResponse, chain);
    }
}
