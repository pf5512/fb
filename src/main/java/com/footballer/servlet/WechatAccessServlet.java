package com.footballer.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.footballer.rest.api.v2.client.ReserveServiceClient;
import com.utils.Sha1Util;

public class WechatAccessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2265702255878563663L;

	/**
	 * 网页授权获取用户信息. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String arenaId = request.getParameter("arenaId");
		
		//获取微信配置根据场地id
		com.footballer.rest.api.v2.vo.WechatConfig wechatConfig = null;
		
		wechatConfig = ReserveServiceClient.getWechatConfigByArenaId(arenaId);
				
		//共账号及商户相关参数
		//String appid = WechatConfig.APP_ID;
		//String backUri = WechatConfig.ACCESS_BACK_URL;
		
		String appid = wechatConfig.getAppid();
		String backUri = wechatConfig.getAccessBackUrl();
		
		
		//授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
		//最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
		//比如 Sign = %3D%2F%CS% 
		String orderNo = appid + Sha1Util.getTimeStamp();
		
		backUri = backUri + "?userId=b88001&orderNo=" + orderNo
				+ "&describe=test&money=1780.00&arenaId=" + arenaId;
		//URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
		backUri = URLEncoder.encode(backUri);
		
		//scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
		//scope=snsapi_userinfo 表示应用授权作用域为请求用户信息
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=" + appid +
				"&redirect_uri=" +
				 backUri +
				"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		response.sendRedirect(url);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
