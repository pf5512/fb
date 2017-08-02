package com.footballer.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.footballer.order.Order;
import com.footballer.order.OrderManager;
import com.footballer.rest.api.v2.client.ReserveServiceClient;
import com.footballer.rest.api.v2.vo.FieldChargeStandard;
import com.footballer.rest.api.v2.vo.WechatConfig;
import com.footballer.util.DateUtil;
import com.google.gson.Gson;
import com.utils.GetWxOrderno;
import com.utils.Sha1Util;
import com.utils.Signature;
import com.utils.TenpayUtil;

public class TopayServlet extends HttpServlet {
	
	private static final long serialVersionUID = 9001718386663510709L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String arenaId = request.getParameter("arenaId");
		//获取微信配置根据场地id
		com.footballer.rest.api.v2.vo.WechatConfig wechatConfig = null;
		wechatConfig = ReserveServiceClient.getWechatConfigByArenaId(arenaId);
		
		if (wechatConfig == null) {
			throw new RuntimeException("can not get wechat config by arenaId:" + arenaId);
		}
		
		//网页授权后获取传递的参数
		//String userId = request.getParameter("userId");
		String orderNo = wechatConfig.getAppid() + Sha1Util.getTimeStamp();
		String money = request.getParameter("money");
		String code = request.getParameter("code");
		String openId = request.getParameter("openid");
		String type = request.getParameter("type");
		
		if (StringUtils.isNotBlank(money)) {
			//金额转化为分为单位
			float sessionmoney = Float.parseFloat(money);
			String finalmoney = String.format("%.2f", sessionmoney);
			finalmoney = finalmoney.replace(".", "");
		}
		
		if (StringUtils.isBlank(openId)) {
			if (StringUtils.isNotBlank(code)) {
				accessWithoutAccount(request, response, code, wechatConfig);
			} else {
				throw new RuntimeException("There is no code with wechat api.");
			}			
		} else {
			doWechatPay(request, response, orderNo, openId, type, wechatConfig);
		}
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

	private void accessWithoutAccount(HttpServletRequest request,
			HttpServletResponse response, String code, WechatConfig wechatConfig)
			throws IOException {
		WechatUser wechatUser = fetchWechatUser(code, wechatConfig);
		HttpSession session = request.getSession();
		
		session.setAttribute("nickname", wechatUser.nickName);
		session.setAttribute("headimgurl", wechatUser.headerImgUrl);
		
		session.setAttribute("code", code);
		session.setAttribute("openid", wechatUser.openId);
		
		String playerId = ReserveServiceClient
				.createPlayerAccountByOpenIdArenaId(wechatUser.openId,
						wechatUser.nickName, wechatUser.headerImgUrl,
						String.valueOf(wechatConfig.getArenaId()));
		session.setAttribute("playerid", playerId);
		
		String currentDate = DateUtil.getCurrentDateString();
		session.setAttribute("currentdate", currentDate);
		
		String teamId = ReserveServiceClient.findTeamIdByPlayerId(Long.valueOf(playerId));
		session.setAttribute("teamId", teamId);
		
		String teamName = ReserveServiceClient.findTeamNameByPlayerId(Long.valueOf(playerId));
		session.setAttribute("teamName", teamName);
		
		com.footballer.rest.api.v2.vo.Account account = ReserveServiceClient.getPlayerAccountByOpenid(wechatUser.openId);
		session.setAttribute("cellphone", account.getCellphone() == null ? "" : String.valueOf(account.getCellphone()));
		
		session.setAttribute("arenaId", String.valueOf(wechatConfig.getArenaId()));
		session.setAttribute("battleComments", String.valueOf(wechatConfig.getBattleComments()));
		
		session.setAttribute("appId", String.valueOf(wechatConfig.getAppid()));
		session.setAttribute("appSecret", String.valueOf(wechatConfig.getWechatAppSecret()));
		session.setAttribute("arenaHeaderTitle", String.valueOf(wechatConfig.getArenaHeaderTitle()));
		session.setAttribute("arenaLogoUrl", String.valueOf(wechatConfig.getArenaLogoUrl()));
		session.setAttribute("arenaFooterTitle", String.valueOf(wechatConfig.getArenaFooterTitle()));

		response.sendRedirect("mobile/index.jsp");
	}
	
	private Order commitOrder(BigDecimal systemPrice, String type, String isReceiveBattle) {
		Order order = null;
		if ("battle".equals(type)) {
			if ("true".equals(isReceiveBattle)) {
				order = OrderManager.createBattleOrder(systemPrice);
			} else {
				order = OrderManager.createPositiveOrder(systemPrice);
			}
		} else {
			order = OrderManager.createFullFieldOrder(systemPrice);
		}
		OrderManager.priceOrder(order);
		return order;
	}
	
	private Boolean isTodayInWeekend(String usedDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(usedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int today = date.getDay();
		return today == 0 || today == 6;
	}
	
	private BigDecimal getPrice(FieldChargeStandard detail, String usedDate) {
		boolean isTodayInWeekend = isTodayInWeekend(usedDate);
		
		if (isTodayInWeekend && detail.getWeekendPrice() != null) {
			return detail.getWeekendPrice();
		} else {
			return detail.getPrice();
		}
	}
		
	public void doWechatPay(HttpServletRequest request,
			HttpServletResponse response, String orderNo, String openId,
			String type, WechatConfig wechatConfig) throws IOException {		
		// 订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		String strReq = buildRandomNumber();
		
		String fieldReservationId = request.getParameter("fieldReservationId");
		String fieldStandardId = request.getParameter("orderno");
		String usedDate = request.getParameter("useddate");
		String playerid = request.getParameter("playerid");
		String isReceiveBattle = request.getParameter("isReceiveBattle");
		
		FieldChargeStandard detail = ReserveServiceClient.getFieldById(fieldStandardId);
		
		//String price = caculatePrice(detail, type, isReceiveBattle);
		Order order = commitOrder(getPrice(detail, usedDate), type, isReceiveBattle);
		
		String prePayId = buildPrepayId(orderNo, openId, spbill_create_ip, strReq, order.getWechatFinalPrice(), wechatConfig);
				
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		
		String appid2 = wechatConfig.getAppid();
		String timestamp = Sha1Util.getTimeStamp();
		String nonceStr2 = strReq;
		String prepay_id2 = "prepay_id=" + prePayId;
		String packages = prepay_id2;
		
		finalpackage.put("appId", appid2);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonceStr2);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		String finalsign = Signature.getSign(finalpackage, wechatConfig.getPartnerAppSecret());

		if (prePayId != null) {
			HttpSession session = request.getSession();			
			String currentDate = DateUtil.getCurrentDateString();
			com.footballer.rest.api.v2.vo.Account account = ReserveServiceClient.getPlayerAccountByOpenid(openId);
			String teamName = ReserveServiceClient.findTeamNameByPlayerId(Long.valueOf(playerid));
			
			session.setAttribute("currentdate", currentDate);			
			session.setAttribute("appid", appid2);
			session.setAttribute("timeStamp", timestamp);
			session.setAttribute("nonceStr", nonceStr2);
			session.setAttribute("package", packages);
			session.setAttribute("sign", finalsign);
			
			//session.setAttribute("nickname", wechatUser.nickName);
			//session.setAttribute("headimgurl", wechatUser.headerImgUrl);
			
			//session.setAttribute("code", code);
			session.setAttribute("openid", openId);			
			session.setAttribute("playerid", playerid);
			session.setAttribute("fieldReservationId", fieldReservationId);
			session.setAttribute("cellphone", account.getCellphone());
			session.setAttribute("teamName", teamName);
			
			session.setAttribute("arenaHeaderTitle", String.valueOf(wechatConfig.getArenaHeaderTitle()));
			session.setAttribute("arenaLogoUrl", String.valueOf(wechatConfig.getArenaLogoUrl()));
			session.setAttribute("arenaFooterTitle", String.valueOf(wechatConfig.getArenaFooterTitle()));
			
			session.setAttribute("type", type);
			session.setAttribute("battleComments", wechatConfig.getBattleComments());
			
			
			session.setAttribute("isReceiveBattle", isReceiveBattle);
			
			buildFieldSummary(fieldStandardId, detail, order, usedDate, session);

			//response.sendRedirect("pay.jsp");
			response.sendRedirect("mobile/field/orderSummary.jsp");
			//response.sendRedirect("mobile/index.jsp");
		}
	}
	
	private void buildFieldSummary(String fieldStandardId,
			FieldChargeStandard detail, Order order, String usedDate, HttpSession session) {
		//FieldReservationDetail detail = ReserveServiceClient.getReservationById(fieldReserveId);
		//FieldChargeStandard detail = ReserveServiceClient.getFieldById(fieldStandardId);
		
		if (null == detail) {
			throw new RuntimeException("can not find field by id");
		}
		
		Gson gson = new Gson();
		String orderJson = gson.toJson(order);
		
		session.setAttribute("id", fieldStandardId);
		session.setAttribute("order", orderJson);
		session.setAttribute("date", usedDate);
		session.setAttribute("startTime", detail.getDisPlayStartTime());
		session.setAttribute("endTime", detail.getDisPlayEndTime());
		session.setAttribute("fieldName", detail.getName());
	}
		
	private String buildPrepayId(String orderNo, String openid,
			String spbill_create_ip, String nonce_str, String price, WechatConfig wechatConfig) {
		// 商户号
		String mch_id = wechatConfig.getPartnerId();
		// 商户订单号
		String out_trade_no = orderNo;

		return GetWxOrderno.getDynamicPayNumber(mch_id, nonce_str,
				out_trade_no, openid, price, wechatConfig);
	}
	
	private JSONObject doFetchWechatUser(String code, WechatConfig webchatConfig) {
		String appid = webchatConfig.getAppid();
		String appsecret = webchatConfig.getWechatAppSecret();
		JSONObject userInfo = GetWxOrderno.getWechatUserInfo(code, appid, appsecret);
		
		return userInfo;
	}
	
	private WechatUser fetchWechatUser(String code, WechatConfig webchatConfig) {
		JSONObject userInfo = doFetchWechatUser(code, webchatConfig);
		String openId = userInfo.getString("openid");
		
		WechatUser wechatUser = new WechatUser(openId,
				userInfo.getString("nickname"),
				userInfo.getString("headimgurl"));
		
		return wechatUser;
	}
	
	private static final class WechatUser {
		public String openId;
		public String nickName;
		public String headerImgUrl;
		
		public WechatUser(String openId, String nickName, String headerImgUrl) {
			this.openId = openId;
			this.nickName = nickName;
			this.headerImgUrl = headerImgUrl;
		}
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

