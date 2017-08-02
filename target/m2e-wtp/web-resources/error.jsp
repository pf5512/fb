<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

String appid = request.getParameter("appid");
String mch_id = request.getParameter("mch_id");
String body = request.getParameter("body");
String nonce_str = request.getParameter("nonce_str");
String attach = request.getParameter("attach");
String out_trade_no = request.getParameter("out_trade_no");
String spbill_create_ip = request.getParameter("spbill_create_ip");
String notify_url = request.getParameter("notify_url");
String trade_type = request.getParameter("trade_type");
String openid = request.getParameter("openid");
String sign = request.getParameter("sign");
String responseJson = request.getParameter("responseJson");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>微信支付</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
		错误页面！！
		<div>
			responseJson: "<%=responseJson%>"
		</div>
		<div>
			appid："<%=appid%>"
		</div>
		<div>
			mch_id："<%=mch_id%>"
		</div>
		<div>
			body："<%=body%>"
		</div>
		<div>
			nonce_str："<%=nonce_str%>"
		</div>
		<div>
			attach："<%=attach%>"
		</div>
		<div>
			out_trade_no："<%=out_trade_no%>"
		</div>
		<div>
			spbill_create_ip："<%=spbill_create_ip%>"
		</div>
		<div>
			notify_url："<%=notify_url%>"
		</div>
		<div>
			trade_type："<%=trade_type%>"
		</div>
		<div>
			openid："<%=openid%>"
		</div>
		<div>
			sign："<%=sign%>"
		</div>
  </body>
  
</html>
