<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String appId = (String)session.getAttribute("appId"); //request.getParameter("appid");
String timeStamp = (String)session.getAttribute("timeStamp");
String nonceStr = (String)session.getAttribute("nonceStr");
String packageValue = (String)session.getAttribute("package");
String paySign = (String)session.getAttribute("sign");

String openid = (String)session.getAttribute("openid");
String code = (String)session.getAttribute("code");
String playerid = (String)session.getAttribute("playerid");
String nickname = (String)session.getAttribute("nickname");
String headimgurl = (String)session.getAttribute("headimgurl");
String currentdate = (String)session.getAttribute("currentdate");
String teamId = (String)session.getAttribute("teamId");
String teamName = (String)session.getAttribute("teamName");
String cellphone = (String)session.getAttribute("cellphone");
String arenaId = (String)session.getAttribute("arenaId");

String arenaHeaderTitle = (String)session.getAttribute("arenaHeaderTitle");
String arenaLogoUrl = (String)session.getAttribute("arenaLogoUrl");
String arenaFooterTitle = (String)session.getAttribute("arenaFooterTitle");

String battleComments = (String)session.getAttribute("battleComments");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="<%=path%>/mobile/css/frozen.css">
    <style type="text/css">
        body {
            font-family: "Microsoft Yahei",Helvetica,Arial,sans-serif;
        }
        h6 {
            font-size: 12px;
        }
        .ui-footer {
            bottom: 0;
            height: 70px;
        }
        .fdsp {
        	width: 192px;
		    height: 192px;
		    background-image: url(<%=path%>/mobile/image/fdsp-icon-small.jpg);
        }
        .ui-header-positive, .ui-footer-positive {
		    background-color: green;
		    color: #fff;
		}
    </style>

  </head>
  
  <body ontouchstart="">
	<header class="ui-header ui-header-positive ui-border-b">
    	<h1><%=arenaHeaderTitle%> 微信订场</h1>
    </header>  
<section class="ui-container">

    <ul class="ui-tiled" style="margin-top: 10%">
        <li><div></div><i></i></li>
        <li><div><div class="ui-avatar-one">
            <span style="background-image:url(<%=headimgurl%>)"></span>
        </div></div><i><%=nickname%></i></li>
        <li><div></div><i></i></li>
    </ul>

    <ul class="ui-tiled">
        <li>
        	<!-- <div class="fdsp"></div> -->
        	<div style="margin-top: 10%">
        		<img src="<%=arenaLogoUrl%>" style="width: 192px;height: 192px;">
        	</div>
        </li>
    </ul>
    
    <ul class="ui-tiled">
    	<div class="ui-flex ui-flex-pack-center">
		    <div style="margin-top:20px; margin-bottom:10px">
		    	<h1>
		    		<span style="color:green; font-weight:bold"><%=arenaFooterTitle%></span>
		    	</h1>
		    </div>
		</div>
    </ul>
    
    <br>
    
</section>

<script src="<%=path%>/mobile/js/server/service.js"></script>
<script src="js/jquery-1.11.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.i18n.properties-min-1.0.9.js"></script>   
</body>

<script>
	(function() {
		var arenaId = '<%=arenaId%>' 
		var openId = '<%=openid%>'
		//var arenaId = 52, openId = 'oVDi1uDLi0og4kFSni9melITLiUI'
		var domain,suffix
		$.i18n.properties({// 加载properties文件  
		    name:'env', // properties文件名称  
		    path:'i18n/', // properties文件路径  
		    mode:'map', // 用 Map 的方式使用资源文件中的值  
		    callback: function() {// 加载成功后设置显示内容  
		        domain =  $.i18n.prop('domain');
		        suffix = $.i18n.prop('suffix');
		    }  
		}); 
		
		//检测是否与之前进入的是同一个场馆
		if(f$.cache('arenaId') == null || f$.cache('arenaId') == 'null' || arenaId == f$.cache('arenaId')){
			//进入同一个场馆
			f$.getWXjsApiTicket()		
		}else{
			//进入了不同得场馆，需要清空之前浏览器缓存的arenaId, accessToken, api_ticket
			f$.getNewWXjsApiTicket(arenaId)
		}
		
		f$.cache('headimgurl', '<%=headimgurl%>')
		  .cache('openid', '<%=openid%>')
		  .cache('date', '<%=currentdate%>')
		  .cache('playerid', '<%=playerid%>')
		  .cache('nickname', '<%=nickname%>')
		  .cache('teamId', '<%=teamId%>')
		  .cache('teamName', '<%=teamName%>')
		  .cache('arenaId', arenaId)
		  .cache('appId', '<%=appId%>')
		  .cache('arenaHeaderTitle', '<%=arenaHeaderTitle%>')
		  .cache('arenaLogoUrl', '<%=arenaLogoUrl%>')
		  .cache('battleComments', '<%=battleComments%>')
		  .cache('cellphone', '<%=cellphone%>')
		  .cache('domain', domain)
		  .cache('suffix', suffix);

		f$.checkPermission(arenaId,openId)
		
		setTimeout(function() {
			location.href = '<%=path%>/mobile/field/orderField.html'+'?arenaId='+arenaId;
		}, 1000); 
		
	})()
</script>  
</html>
