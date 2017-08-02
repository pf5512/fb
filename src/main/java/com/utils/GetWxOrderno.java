package com.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.utils.http.HttpClientConnectionManager;

@SuppressWarnings("deprecation")
public class GetWxOrderno {
	public static DefaultHttpClient httpclient;

	static {
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager
				.getSSLInstance(httpclient);
	}
	
	public static void main(String[] args) {
		String xml = "<xml><appid><![CDATA[wxa20ba5602fae14ee]]></appid><attach>支付测试</attach><mch_id>1308641501</mch_id><nonce_str><![CDATA[1738453509]]></nonce_str><sign><![CDATA[2FE9EBE324B1F5DF1B5EF825565F4104]]></sign><body><![CDATA[踢球者数字化足球公园-订场费用]]></body><out_trade_no><![CDATA[wxa20ba5602fae14ee1473586725]]></out_trade_no><total_fee>1</total_fee><spbill_create_ip><![CDATA[115.29.204.12]]></spbill_create_ip><notify_url><![CDATA[http://dev.footballer.cc/footballer/mobile]]></notify_url><trade_type><![CDATA[JSAPI]]></trade_type><openid><![CDATA[oIpEZs0Klqj854K1It1X2A2gTKYo]]></openid></xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		try {
			 String id = getPayNo(createOrderURL, xml);
			 System.out.print(id);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	public static String getDynamicPayNumber(String mch_id, String nonce_str,
			String out_trade_no, String openid, String price, com.footballer.rest.api.v2.vo.WechatConfig wechatConfig) {
		String appid = wechatConfig.getAppid();
		
		String body = wechatConfig.getBody();
		String spbill_create_ip = wechatConfig.getSpbillCreateIp();//WechatConfig.spbill_create_ip;
		String notify_url = wechatConfig.getNotifyUrl();//WechatConfig.notify_url;
		String trade_type = wechatConfig.getTradeType();//WechatConfig.trade_type;
		
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("attach", wechatConfig.getAttach());
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);		
		packageParams.put("out_trade_no", out_trade_no);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", price);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openid);

		String sign = Signature.getSign(packageParams, wechatConfig.getPartnerAppSecret());

		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		String xml = "<xml>" + "<appid><![CDATA[" + appid + "]]></appid>"
				+ "<attach>" + wechatConfig.getAttach() + "</attach>" + "<mch_id>" + mch_id + "</mch_id>"
				+ "<nonce_str><![CDATA[" + nonce_str + "]]></nonce_str>"
				+ "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<body><![CDATA["
				+ body
				+ "]]></body>"
				+ "<out_trade_no><![CDATA["
				+ out_trade_no
				+ "]]></out_trade_no>"
				+
				// 金额，这里写的1 分到时修改
				"<total_fee>" + price + "</total_fee>"
				+ "<spbill_create_ip><![CDATA[" + spbill_create_ip
				+ "]]></spbill_create_ip>" + "<notify_url><![CDATA["
				+ notify_url + "]]></notify_url>" + "<trade_type><![CDATA["
				+ trade_type + "]]></trade_type>" + "<openid><![CDATA["
				+ openid + "]]></openid>" + "</xml>";
		
		try {
			 return getPayNo(createOrderURL, xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static JSONObject getWechatUserInfo(String code, String appid, String appsecret) {
		JSONObject openIdAndToken = getWechatOpenIdAndToken(code, appid, appsecret);
		
		
		String openId = openIdAndToken.getString("openid");
		String token = openIdAndToken.getString("access_token");
		
		JSONObject user = getWechatUserInfo(token, openId);
		
		user.put("user", user);
		
		return user;
	}
	
	private static JSONObject getWechatUserInfo(String token, String openid) {
		String url = "https://api.weixin.qq.com/sns/userinfo?" + 
					 	"access_token=" + token + "&openid="+ "openid" + "&lang=zh_CN";
		return CommonUtil.httpsRequest(url, "GET", null);
	}
	
	/**
	 * 通过code换取网页授权access_token
	 * 
	 * 获取code后，请求以下链接获取access_token： 
	 * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	 * 
	 * 正确时返回的JSON数据包如下：
		{
		   "access_token":"ACCESS_TOKEN",
		   "expires_in":7200,
		   "refresh_token":"REFRESH_TOKEN",
		   "openid":"OPENID",
		   "scope":"SCOPE",
		   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
		}
	 * @param code
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static JSONObject getWechatOpenIdAndToken(String code, String appid, String appsecret) {
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid + "&secret=" + appsecret + "&code=" + code
				+ "&grant_type=authorization_code";
		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		return jsonObject;		
	}

	@SuppressWarnings({ "resource", "unused" })
	private static String getPayNo(String url, String xmlParam) throws Exception {
		System.out.println("xml是:" + xmlParam);
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
				true);
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		String prepay_id = "";
		// try {
		httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		HttpResponse response = httpclient.execute(httpost);
		String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		
		System.out.println("调用prepayid api返回结果字符串:" + jsonStr);

		if (jsonStr.indexOf("FAIL") != -1) {
			return prepay_id;
		}
		
		Map<String, String> map = doXMLParse(jsonStr);
		String return_code = (String) map.get("return_code");
		prepay_id = (String) map.get("prepay_id");
		return prepay_id;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map<String, String> m = new HashMap<String, String>();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<?> list = root.getChildren();
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List<?> children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List<?> children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator<?> it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List<?> list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

}