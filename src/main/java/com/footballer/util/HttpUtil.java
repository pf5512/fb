package com.footballer.util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpUtil {

	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	public final static int socketTimeout = 5000;
	public final static int connectTimeout = 5000;
	public final static String REQUEST_STATUS = "requestStatus";
	public final static String RESPONSE_STATUS = "responseStatus";
	public final static String RESPONSE_BODY = "responseBody";

	public static void main(String args[]){
		String APP_ID = "wxa549b28c24cf341e";
	    String SECRET = "78d8a8cd7a4fa700142d06b96bf44a37";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ APP_ID + "&secret=" + SECRET;
		try {
			
			Map<String, Object> jsonMap = JacksonUtil.jsonToMap(httpClientGet(url));
			System.out.println(jsonMap.get("expires_in"));
			System.out.println(jsonMap.get("access_token").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String httpClientPost(String url,
			Map<String, Object> paramsMap) throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(url);

		List<NameValuePair> nvps = buildParams(paramsMap);
															
		post.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));

		return execute(url, post);
	}
	
	

	private static String execute(String url, HttpPost post)
			throws IOException, ClientProtocolException {
		CloseableHttpClient httpClient = buildHttpClient();
		CloseableHttpResponse response = httpClient.execute(post);

		String digest = digest(response,  url);
		httpClient.close();

		return digest;
	}

	private static String digest(CloseableHttpResponse resp,
			String url) throws ParseException, IOException {
		int status = resp.getStatusLine().getStatusCode();
		//String returnContent = EntityUtils.toString(resp.getEntity());
		String returnContent = EntityUtils.toString(resp.getEntity() , "UTF-8").trim();//中文乱码问题解决 （justin 2017.01.20）
		
		if (status == HttpStatus.SC_OK) {
			log.debug("http 响应结果　:{}", returnContent);
		} else {
			log.error("http 执行错误， status code: {}, url: {},returnContent:{}", status,url,returnContent);
			returnContent = null; // 不将错误信息返回给业务程序
		}

		return returnContent;
	}

	private static List<NameValuePair> buildParams(Map<String, Object> paramsMap) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();

		if (paramsMap != null) {
			for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
				String value = null;
				String name = entry.getKey();

				if (entry.getValue() != null) {
					value = entry.getValue().toString();
				}

				BasicNameValuePair param = new BasicNameValuePair(name, value);
				list.add(param);
			}
		}

		return list;
	}

	
	public static String httpPost(String url, Map<String, Object> params)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		try {
			httpclient = HttpClients.custom()
					.setDefaultRequestConfig(requestConfig).build();
			HttpPost httppost = new HttpPost(url);
			List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) { 
				if (params.get(key) != null) {
					valueList.add(new BasicNameValuePair(key, String
							.valueOf(params.get(key))));
				}
			}
			httppost.setEntity(new UrlEncodedFormEntity(valueList,Consts.UTF_8));
			response = httpclient.execute(httppost);
			log.debug("httpPost���󣺽����Ӧ" + response);
			String content = StringUtil.streamToString(response.getEntity().getContent()); 
			return content;
		} finally {
			if (response != null) {
				response.close();
			}
			if (httpclient != null) {
				httpclient.close();
			}
		}
	}

	public static String httpClientGet(String url) throws Exception {
		HttpGet get = new HttpGet(url);

		return execute(url, get);
	}

	private static String execute(String url, HttpGet get) throws IOException,
			ClientProtocolException {
		CloseableHttpClient httpClient = buildHttpClient();
		CloseableHttpResponse response = httpClient.execute(get);

		String digest = digest(response,  url);
		httpClient.close();

		return digest;
	}

	private static CloseableHttpClient buildHttpClient() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig).build();
		return httpclient;
	}

	public static String httpPostByJson(String url, String json)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);

		StringEntity se = new StringEntity(json, ContentType.APPLICATION_JSON);
		post.setEntity(se);

		CloseableHttpClient httpClient = buildHttpClient();
		CloseableHttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		String digest = digest(response,  url);
		httpClient.close();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(RESPONSE_STATUS, statusCode);
		return digest;

	}
	
	
	public static String httpPutByJson(String url, String json)
			throws ClientProtocolException, IOException {
		HttpPut post = new HttpPut(url);

		StringEntity se = new StringEntity(json, ContentType.APPLICATION_JSON);
		post.setEntity(se);
		CloseableHttpClient httpClient = buildHttpClient();
		CloseableHttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		String digest = digest(response,  url);
		httpClient.close();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(RESPONSE_STATUS, statusCode);
		return digest;

	}

	public static String httpPostByXml(String url, String xml)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);

		StringEntity se = new StringEntity(xml, ContentType.create("application/xml","UTF-8"));
		post.setEntity(se);

		CloseableHttpClient httpClient = buildHttpClient();
		CloseableHttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		String digest = digest(response,  url);
		httpClient.close();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(RESPONSE_STATUS, statusCode);
		return digest;

	}

}