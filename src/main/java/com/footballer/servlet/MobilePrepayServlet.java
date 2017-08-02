package com.footballer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.utils.GetWxOrderno;
import com.utils.Sha1Util;
import com.utils.TenpayUtil;

public class MobilePrepayServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static final String APP_ID = null; //WechatConfig.APP_ID;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String spbill_create_ip = request.getRemoteAddr();
		String strReq = buildRandomNumber();
		//oIpEZs0Klqj854K1It1X2A2gTKYo
		String openId = request.getParameter("openid");
		String price = request.getParameter("price");
		String orderNo = APP_ID + Sha1Util.getTimeStamp();
		
		String prepayId = buildPrepayId(orderNo, openId, spbill_create_ip, strReq, price);
		
		JsonObject json = new JsonObject();
		json.addProperty("prepayId", prepayId);
		
		response.getWriter().print(json);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 
	 * @param orderNo: 产生的随机订单号
	 * @param openid: 
	 * @param spbill_create_ip: 
	 * @param nonce_str: 
	 * @param price: 
	 * @return
	 */
	private String buildPrepayId(String orderNo, String openid,
			String spbill_create_ip, String nonce_str, String price) {
		// 商户号
		String mch_id = null;//WechatConfig.PARTNER_ID;
		// 商户订单号
		String out_trade_no = orderNo;

		return GetWxOrderno.getDynamicPayNumber(mch_id, nonce_str,
				out_trade_no, openid, price, null);
	}
	
	private String buildRandomNumber() {
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		return strReq;
	}
}
