<%@ page language="java" 
		 import="java.util.*" 
		 pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String appId = (String)session.getAttribute("appid"); //request.getParameter("appid");
String timeStamp = (String)session.getAttribute("timeStamp");
String nonceStr = (String)session.getAttribute("nonceStr");
String packageValue = (String)session.getAttribute("package");
String paySign = (String)session.getAttribute("sign");
String openid = (String)session.getAttribute("openid");
String playerid = (String)session.getAttribute("playerid");
String fieldReservationId = (String)session.getAttribute("fieldReservationId");

String id = (String)session.getAttribute("id");
String order = (String)session.getAttribute("order");
String date = (String)session.getAttribute("date");
String startTime = (String)session.getAttribute("startTime");
String endTime = (String)session.getAttribute("endTime");
String fieldName = (String)session.getAttribute("fieldName");

String currentdate = (String)session.getAttribute("currentdate");
Long cellphone = (Long)session.getAttribute("cellphone");
String teamName = (String)session.getAttribute("teamName");

String arenaHeaderTitle = (String)session.getAttribute("arenaHeaderTitle");
String battleComments = (String)session.getAttribute("battleComments");
String type = (String)session.getAttribute("type");

String isReceiveBattle = (String)session.getAttribute("isReceiveBattle");

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
        .ui-header-positive, .ui-footer-positive {
			    background-color: green;
			    color: #fff;
			}
    </style>
    <script type="text/javascript">
    	var order = JSON.parse('<%=order%>'),
    		cellphone = <%=cellphone%>,
    		teamName = '<%=teamName%>',
    		isReceiveBattle = '<%=isReceiveBattle%>';
    </script>

  </head>
  
  <body>
	<div id="app">
        <header class="ui-header ui-header-positive ui-border-b">
            <h1>{{arenaHeaderTitle}} 球场预定</h1>
        </header>
        <footer class="ui-footer ui-footer-btn">
            <ul class="ui-tiled ui-border-t">
                <li data-href="..<%=path%>/mobile/field/orderField.html"><i style="color:green">预定球场</i></li>
                <li data-href="..<%=path%>/mobile/order/orders.html"><i style="color:green">我的订单</i></li>
                <li data-href="..<%=path%>/mobile/order/battlePool2.html"><i style="color:green">本周约战</i></li>
            </ul>
        </footer>
        <section class="ui-container">
        	{{#if headimgurl}}
        	<ul class="ui-tiled" style="margin-top: 5%">
		        <li><div></div><i></i></li>
		        <li><div><div class="ui-avatar-one">
		            <span style="background-image:url({{headimgurl}})"></span>
		        </div></div>
		        <i v-if="!teamName">{{nickname}}</i>
		        <i v-else>{{teamName}}</i>
		        </li>
		        <li><div></div><i></i></li>
		    </ul>
		    {{/if}}
		    
            <br>
            <section class="ui-container ui-whitespace">
            <%if(type.equals("battle")) {%>
	            <div class="ui-flex ui-flex-pack-center">
	            	<div><h1><%=battleComments%><h1></div>
	            </div>
            <%}%>                     	
            <div class="ui-flex ui-flex-pack-center">
			    <div><h1>{{field.date}} {{field.time}}</h1></div>
			</div>
            <br>
            <div v-if="!cellphone || !teamName">
	        	<div class="ui-form ui-border-t">
				    <form action="#">
				        <div v-if="!cellphone" class="ui-form-item ui-form-item-l ui-border-b">
				            <label class="ui-border-r">
				                中国 +86
				            </label>
				            <input v-model="phone" type="text" placeholder="请输入手机号码, 只需一次填写">
				        </div>
				        
				        <div v-if="!teamName" class="ui-form-item ui-form-item-l ui-border-b">
				            <label class="ui-border-r">
				                球队名称:
				            </label>
				            <input v-model="postTeamName" type="text" placeholder="请输入球队名称, 只需一次填写">
				        </div>
				    </form>
				</div>
			</div>
			<br v-if="!cellphone || !teamName">
			
            <ul class="ui-list ui-list-text ui-border-tb">
                <li class="ui-border-t">
                    <div class="ui-list-info">
                        <h4 class="ui-nowrap">场地名称：</h4>
                    </div>
                    <div>{{field.fieldName}}</div>
                </li>
								
                <li class="ui-border-t" v-for="orderLine in order.orderLines">
                    <div class="ui-list-info">
                        <h4 class="ui-nowrap">{{orderLine.description}}：</h4>
                    </div>
                    <div>
                    	<span v-show="orderLine.type == 'CREDIT'" style="color:red">-</span> 
                    	<span v-show="orderLine.type == 'CREDIT'" style="color:red">￥{{orderLine.fee}}</span>
                    	<span v-show="orderLine.type == 'DEBIT'" class="ui-txt-highlight">￥{{orderLine.fee}}</span>
                    </div>
                </li>
                
                <li class="ui-border-t">
                    <div class="ui-list-info">
                        <h4 class="ui-nowrap">总计：</h4>
                    </div>
                    <div class="ui-txt-highlight">¥ {{order.finalPrice}}</div>
                </li>
                
            </ul>    
            <br>
            <div class="ui-btn-wrap">
                <button v-on:click="wechatPay" class="ui-btn-lg ui-btn-primary">
                    微信支付
                </button>
            </div>       
            <br>     
            
        </section>
        
        </section>
    </div>		
  <script src="<%=path%>/mobile/js/lib/zeptojs/zepto.min.js"></script>
  <script src="<%=path%>/mobile/js/frozen.js"></script>
  <script src="<%=path%>/mobile/js/server/service.js"></script>
  <script src="<%=path%>/mobile/js/lib/vue.min.js"></script>
  <script src="<%=path%>/mobile/js/lib/underscore-min.js"></script>
  </body>
  
  <script type="text/javascript">
  	(function() {
  		
  		var updatedPhone,
  		    updatedTeam;
  		
  		function valdateMobile(s) {
  			var pattern = /^1\d{10}$/;
  			return pattern.exec(s);
  		}
  		
  		function removeGuestBattle(fn) {
  			var orderType = order.orderType;
  			if ('GuestOrder' == orderType) {
  				f$.removeBattle({
  	            	fieldReservationId :  '<%=fieldReservationId%>',
  	                guestPlayerId      :  '<%=playerid%>'
  	            }).done(function(data) {
  	            	console.log('removeGuestBattle done!')
  	            	fn.call();
  	            }).fail(function(data) {
  	            	console.log('removeGuestBattle failed!')
  	            });
  			} else {
  				fn.call();
  			}	
  		}
  		
  		function callpay(){
  			 var params = {
  		  	  		 "appId" : "<%=appId%>","timeStamp" : "<%=timeStamp%>", "nonceStr" : "<%=nonceStr%>", "package" : "<%=packageValue%>","signType" : "MD5", "paySign" : "<%=paySign%>" 
	   			},
	   			openid = '<%=openid%>',
	   			playerid = '<%=playerid%>',
	   			date = '<%=currentdate%>'
	   		
	   	  if (openid == null) {alert('openid is null')}
	   	  if (playerid == null) {alert('playerid is null')}
	   	  if (date == null) {alert('date is null')}
	   	   	
	   	  if (!cellphone) {
  	   	   	if (!valdateMobile(vm.$data.phone)) {
  		   	   	alert("请填写真实号码，否则无法退款！");
  	   	   		return;
  	   	   	}
  	   	   	
  	   		!updatedPhone && f$.updateAccountPhone({
    	   	 		phone  : vm.$data.phone,
    	   	 		openid : openid
    	   	 	}).done(function(data) {
    	   	 		updatedPhone = true;
    	   	 		console.log('update phone successfully:', data);
    	   	 	}).fail(function(data) {
    	   	 		console.log('update phone failed:', data);
    	   	 	});
           }
	   	  
	   	   if (!teamName) {
	   	       if (!vm.$data.postTeamName) {
	   	    	alert("请填写球队名称，否则无法退款！");
  	   	   		return;
	   	       }
	   	       
	   	    !updatedTeam && f$.createTeam({
	   	    	teamName : vm.$data.postTeamName,
	   	    	playerId : '<%=playerid%>'
	   	 	}).done(function(data) {
	   	 		updatedTeam = true;
	   	 		console.log('create team successfully:', data);
	   	 	}).fail(function(data) {
	   	 		console.log('create team failed:', data);
	   	 	});
       		
	   	   }
	   			
  			 WeixinJSBridge.invoke('getBrandWCPayRequest', params, function(res){
  					WeixinJSBridge.log(res.err_msg);
  		            if(res.err_msg == "get_brand_wcpay_request:ok"){
  		            	var fieldReservationId;
  		            	
  		            	if ("true" == isReceiveBattle) {
  		            		var params = f$.cache("receiveBattleParams");
  		            		f$.receiveBattle(params).done(function(data) {
	                    		if (data.status == 'error') {
	                    			alert(data.message)
	                    		} else {	                    		
	                    			fieldReservationId = data.fieldReservationId
	                    			f$.updateReserve({
	      	  		                	fieldReservationId : fieldReservationId
	      	  		                }).done(function(data) {
	      	  		                	alert(data.message)
	      	  		                	location.href="..<%=path%>/mobile/order/orders.html?openid=<%=openid%>&playerid=<%=playerid%>&date=<%=currentdate%>"
	      	  		                }).fail(function(message) {
	      	  		                })
	                    		}
	                    	}).fail(function() {
	                    		alert("服务出错")
	                    	})
  		            	} else {
  		            		fieldReservationId = <%=fieldReservationId%>;
  	  		                f$.updateReserve({
  	  		                	fieldReservationId : fieldReservationId
  	  		                }).done(function(data) {
  	  		                	alert(data.message)
  	  		                	location.href="..<%=path%>/mobile/order/orders.html?openid=<%=openid%>&playerid=<%=playerid%>&date=<%=currentdate%>"
  	  		                }).fail(function(message) {
  	  		                })	
  		            	}
  		            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){   
	 		              alert("用户取消支付!");
  		            }else{
  		               alert("支付失败!");
  		            }  
  				})
  			}
  	  	
  	 // vue
  	    var id = '<%=id%>',
  	    	date = '<%=date%>',
  	    	startTime = '<%=startTime%>',
  	    	endTime = '<%=endTime%>',
  	    	fieldName = '<%=fieldName%>',
  	        field = {
  	    		id        : id,
  	    		fieldName : fieldName,
  	    		date      : date,
  	    		time      : startTime + "-" + endTime
  	    	},
  	        vm
  	        
  	        console.log(field)

  	        vm = new Vue({
  	          el: '#app',
  	          data: {
  	            field : field,
  	          	order : order,
  	          	phone : null,
  	            cellphone : cellphone,
  	          	postTeamName : null,
  	          	arenaHeaderTitle : '<%=arenaHeaderTitle%>',
  	          	teamName : teamName,
  	            headimgurl : f$.cache('headimgurl'),
  	          	nickname : f$.cache('nickname')
  	          },
  	          methods: {
  	        	  wechatPay : function() {  	        		  
  	        		callpay();
  	        	  }
  	          }
  	        })

  	        vm.$log()

  	    // navigation
  	    $('.ui-list li,.ui-tiled li').click(function(){
  	        if($(this).data('href')){
  	        	var that = this;
  	        	removeGuestBattle(function() {
	        		console.log('after remove battle, navigate out');
	        		location.href= $(that).data('href');		
	        	});
  	        }
  	    });
  	})()
  	
  </script>
  
</html>
