$.GetJSON = function(url, data, callback) {
    return $.ajax({
    headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
    },
    'type': 'GET',
    'url': url,
    //'url': '/'+url,
    'data': JSON.stringify(data),
    'dataType': 'json',
    'success': callback
    });
};


$.postJSON = function(url, data, callback) {
    return jQuery.ajax({
    headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
    },
    'type': 'POST',
    'url': url,
    //'url': '/'+url,
    'data': JSON.stringify(data),
    'dataType': 'json',
    'success': callback
    });
};

$.putJSON = function(url, parameter, otherdata, callback) {
    return jQuery.ajax({
    headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
    },
    'type': 'PUT',
    'url': url,
    //'url': '/'+url,
    'data': JSON.stringify(parameter),
    'otherdata': JSON.stringify(otherdata),
    'dataType': 'json',
    'success': callback
    });
};

$.deleteJSON = function(url, data, callback) {
    return jQuery.ajax({
    headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
    },
    'type': 'DELETE',
    'url': url,
    //'url': '/'+url,
    'data': JSON.stringify(data),
    'dataType': 'json',
    'success': callback
    });
};


//-------------------------------------------------------------时间日期处理-----------------------------------------------------------------------

//根据生日 计算年龄 （1987-05-06）
function getAgeByBirth(strBirthday)
{       
	var returnAge;
	var strBirthdayArr=strBirthday.split("-");
	var birthYear = strBirthdayArr[0];
	var birthMonth = strBirthdayArr[1];
	var birthDay = strBirthdayArr[2];
	
	d = new Date();
	var nowYear = d.getFullYear();
	var nowMonth = d.getMonth() + 1;
	var nowDay = d.getDate();
	
	if(nowYear == birthYear)
	{
		returnAge = 0;//同年 则为0岁
	}
	else
	{
		var ageDiff = nowYear - birthYear ; //年之差
		if(ageDiff > 0)
		{
			if(nowMonth == birthMonth){
				var dayDiff = nowDay - birthDay;//日之差
				if(dayDiff < 0)
				{returnAge = ageDiff - 1;}
				else
				{returnAge = ageDiff ;}
			}else{
				var monthDiff = nowMonth - birthMonth;//月之差
				if(monthDiff < 0)
				{returnAge = ageDiff - 1;}
				else
				{returnAge = ageDiff ;}
			}
		}
		else
		{	
			returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
		}
	}
	return returnAge;//返回周岁年龄
}


function CompareDate(d1,d2)
{
  if(d1!=null && d1.indexOf('.')>0){
	 d1= d1.substring(0,d1.length-2);
  }
  if(d2!=null &&d2.indexOf('.')>0){
	 d2= d2.substring(0,d2.length-2);
  }
	
  return (new Date(String(d1).replace(/-/g,"\/"))) - (new Date(String(d2).replace(/-/g,"\/")))
}




function unix_to_datetime(unix) {
    var now = new Date(parseInt(unix) * 1000);
    return now.toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
}

//2016年01月08日 => 1459440000000
function StringYMDToDateNumber(str){
	var commonStringDate = str.replace('年','-').replace('月','-').replace('日','');
	return Date.parse(commonStringDate);
}

//10:20 => Date
//参数为: 固定格式： hh：mm
function StringHMTimeToDateNumber(timestr){
	timestr = timestr.trim()+':00'
	var date = new Date, time = timestr.split(/\:|\-/g);
	date.setHours(time[0]);
	date.setMinutes(time[1]);
	date.setSeconds(time[2]);
	return date;
}

//2017-04-09 10:20:30 => Date()
function convertYMDHMSFromString(dateString) { 
	if (dateString) { 
		var arr1 = dateString.split(" "); 
		var sdate = arr1[0].split('-'); 
		var date = new Date(sdate[0], sdate[1]-1, sdate[2]); 
		return date;
	} 
}

//Date() =>  2016-02-01
function formatToDateString(date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    return y + '-' + m + '-' + d;  
};

//Date() =>  2016-02-01 10:22
function formatToDateTimeString(date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;
    var hh = date.getHours();
    hh = hh < 10 ? '0' + hh : hh;
    var mm = date.getMinutes();
    mm = mm < 10 ? '0' + mm : mm
    //var ss = date.getSeconds;
    return y + '-' + m + '-' + d + ' '+ hh+ ':' +mm;  
};

//Date() =>  2016-02-01 星期三
function formatToAllDateString(date) { 
	var dayNames = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" ];
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    
    return y + '-' + m + '-' + d + ' '+ dayNames[date.getDay()];  
};

// date= '2016-02-12'
function is_weekend(date){
    var dt = new Date(date);
    if(dt.getDay() == 6 || dt.getDay() == 0)
    {
        return true;
    }else{
    	return false;
    } 
}

function timeBetween(date1,date2){
	var date3=date2.getTime()-date1.getTime()  //时间差的毫秒数
	 
	//计算出相差天数
	//var days=Math.floor(date3/(24*3600*1000))
	 
	//计算出小时数
	var leave1=date3%(24*3600*1000)    //计算天数后剩余的毫秒数
	var hours=Math.floor(leave1/(3600*1000))
	//计算相差分钟数
	var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
	var minutes=Math.floor(leave2/(60*1000))

	//计算相差秒数
	var leave3=leave2%(60*1000)      //计算分钟数后剩余的毫秒数
	var seconds=Math.round(leave3/1000)
	
	if(hours > 0){
		return hours+":"+minutes+"'"+seconds+"''";
	}else{
		return minutes+"'"+seconds+"''";
	}
	//alert(" 相差 "+days+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒")
}

//计算日期相差天数    (String, String)
function getsDateDiff(startDate,endDate)  
{  
    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();     
    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
    return  dates;    
}


//计算日期相差天数  (Date(), Date())
function getDateDiff(startDate,endDate)  
{  
    var startTime = startDate.getTime();     
    var endTime = endDate.getTime();     
    return ((startTime - endTime))/(1000*60*60*24);    
}

// 检查 时间 是否处于 某一个时间段之中
// 参数 （Date ,Date ,Date ）
function timeInTimeShiftCheck(currentTime, startShiftTime, endShiftTime){
	currentTime = Date.parse(currentTime)
	startShiftTime = Date.parse(startShiftTime)
	endShiftTime = Date.parse(endShiftTime)
	
	if(currentTime - startShiftTime >0  && currentTime - endShiftTime <0)
	{
		return true;
	}else{
		return false;
	}
}


function nextday(){
	var monthNames = [ "1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" ];
	var dayNames = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" ];

	var currentDate = $('#Date').text().substring(0,12).trim();
	
	var newDate = StringYMDToDateNumber(currentDate);
	newDate = newDate + 1 * 24 * 60 * 60 * 1000
	newDate = new Date(newDate)
	//vm.changeDate(formatToDateString(newDate));
	$('#Date').html(newDate.getFullYear() + '年' + monthNames[newDate.getMonth()] +'月'+ newDate.getDate() +'日   '+ dayNames[newDate.getDay()] );
}
function yesterday(){
	var monthNames = [ "1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" ];
	var dayNames = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" ];

	var currentDate = $('#Date').text().substring(0,12).trim();
	
	var newDate = StringYMDToDateNumber(currentDate);
	newDate = newDate - 1 * 24 * 60 * 60 * 1000;
	newDate = new Date(newDate);
	//vm.changeDate(formatToDateString(newDate));
	$('#Date').html(newDate.getFullYear() + '年' + monthNames[newDate.getMonth()] +'月'+ newDate.getDate() +'日   '+ dayNames[newDate.getDay()] );
	
}

//digital Clock.js code import in
function prepareTime() {
	var monthNames = [ "1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" ];
	var dayNames = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" ];

	var newDate = new Date();
	newDate.setDate(newDate.getDate());
	$('#Date').html(
			newDate.getFullYear() + '年' + monthNames[newDate.getMonth()] +'月'+ newDate.getDate() +'日   '+ dayNames[newDate.getDay()] );

	setInterval(function() {
		var seconds = new Date().getSeconds();
		$("#sec").html((seconds < 10 ? "0" : "") + seconds);
	}, 1000);

	setInterval(function() {
		var minutes = new Date().getMinutes();
		$("#min").html((minutes < 10 ? "0" : "") + minutes);
	}, 1000);

	setInterval(function() {
		var hours = new Date().getHours();
		$("#hours").html((hours < 10 ? "0" : "") + hours);
	}, 1000);

}

//digital Clock.js code import in
function prepareWeekTime() {
	var monthNames = [ "1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" ];
	var dayNames = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" ];

	var newDate = new Date();
	newDate.setDate(newDate.getDate());
	$('#Date').html(dayNames[newDate.getDay()] );

	setInterval(function() {
		var seconds = new Date().getSeconds();
		$("#sec").html((seconds < 10 ? "0" : "") + seconds);
	}, 1000);

	setInterval(function() {
		var minutes = new Date().getMinutes();
		$("#min").html((minutes < 10 ? "0" : "") + minutes);
	}, 1000);

	setInterval(function() {
		var hours = new Date().getHours();
		$("#hours").html((hours < 10 ? "0" : "") + hours);
	}, 1000);

}

//------------------------------------------------------------------加载------------------------------------------------------------------

function loadingInit(){
	$("#fakeLoader").begin({
        timeToHide:1000000,
        bgColor:"#a2c2c3",
        spinner:"spinner6"
    });
	htmlHeaderInit();
}

function loading(){
	$("#fakeLoader").fakeLoader_show();
}

function hideLoading(){
	$("#fakeLoader").fakeLoader_hide();
}

//隐藏延迟加载部分
function hidesomeloadingpart(target){
	setTimeout( function() {$("#"+target).hide() }, 200)
}

//------------------------------------------------------------------微信端登录功能------------------------------------------------------------------

function getCurrentDomain()
{
	url = window.location.href
	//'http://dev.footballer.cc/footballer/mobile/order/battlePool2.html'
	start_index = url.indexOf('//')+2   
	end_index = url.substr(start_index).indexOf('/')
	return url.substring(0,start_index+end_index)
}


//获取页面url 及 参数
function getParameter(url,param)  
{  
    var iLen = param.length;//获取你的参数名称长度  
    var iStart = url.indexOf(param);//获取你该参数名称的其实索引  
    if (iStart == -1)//-1为没有该参数  
        return "";  
    iStart += iLen + 1;  
    var iEnd = url.indexOf("&", iStart);//获取第二个参数的其实索引  
    if (iEnd == -1)//只有一个参数  
        return url.substring(iStart);//获取单个参数的参数值  
    return url.substring(iStart, iEnd);//获取第二个参数的值  
}  

function weChatLogin(){
	var currentDomain = getCurrentDomain() 
	var url_arenaId = getParameter(window.location.href ,"arenaId")  
	loginUrl =  currentDomain + '/footballer/wechataccess?arenaId='+ url_arenaId
//	alert(currentDomain)
//	alert(url_arenaId)
//	alert(loginUrl)
	location.href= loginUrl
}

function checkArenaChangeAndUpdateCaches(){
	 	
//	if(url_arenaId == null || url_arenaId == '' || url_arenaId == 'null'){
//		alert('当前缓存的 arenaID:'+f$.cache('arenaId'));
//	}else{
//		f$.cache('arenaId', url_arenaId)
//		alert('当前缓存的 arenaID:'+f$.cache('arenaId'));
//	}
	
	url_arenaId = getParameter(window.location.href ,"arenaId")
	if(f$.cache('arenaId') == url_arenaId){
		//Do Nothing
		//alert('同场地- 当前缓存的 arenaID:'+f$.cache('arenaId'));
	}else{
		//alert('场地变化-当前缓存的 arenaID:'+url_arenaId);
		//场地变化 更新缓存
		getWechatConfigUrl = f$.cache('domain') + 'footballer/api/mobile/v2/wechat-service/getWechatConfig/'+url_arenaId+'/' + f$.cache('suffix'),
		//alert(getWechatConfigUrl)
		$.ajax({
			url : getWechatConfigUrl,
			type : 'get',
			contentType : 'application/json;charset=utf8',
			async: false,
			dataType : 'json',
			success : function(data) {
					//alert(data.wechatConfig.arenaId+'=|='+data.wechatConfig.arenaHeaderTitle)
				
					f$.cache('arenaId', data.wechatConfig.arenaId)
					f$.cache('appId', data.wechatConfig.appid)
					f$.cache('arenaHeaderTitle', data.wechatConfig.arenaHeaderTitle)
					f$.cache('arenaLogoUrl', data.wechatConfig.arenaLogoUrl)
					//f$.cache('orderAheadWeekNumber', data.orderAheadWeekNumber)
					f$.cache('battleComments', data.wechatConfig.battleComments) 
			}
		})
	}
}

//------------------------------------------------------------------后台登录功能------------------------------------------------------------------

function loginStatusCheck(callback){
	var storage = window.localStorage;    
//    var getUserName = storage["userName"];    
//    var getPwd = storage["password"];    
//    var isStroePwd = storage["isStroePwd"];    
    var isAutoLogin = storage["isAutoLogin"]  
    
    
    if(isAutoLogin == 'true'){  //已经存在用户的本地存储中
    	//免登陆进入
    	callback()
    }else{ //未登陆过
    	loginPopup(callback)
    }
}

function loginPopup(callback)
{
	var returnCallBack = callback
	
	dialog = $( "#dialog-form" ).dialog({
		  closeOnEscape: false,
		  open: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog | ui).hide(); },//前2行 隐去右侧自带得关闭按钮
	      autoOpen: true,
	      height: 250,
	      width: 350,
	      modal: true,
	      buttons: {
	        "清空": function() {
	        	$("#name").val('')
	            $("#password").val('')
	            var tips = $(".validateTips");
		      	tips.text('').hide()
	        },
	        "登陆": function(e){
	        	$(e.target.parentElement.parentElement).find('button').hide()
	        	$(e.target.parentElement.parentElement).append('<img style="margin: 0px 0px 0px 5px;width: 25px;height: 25px;" src="../img/gif/soccer_loading.gif">')
	        	login(returnCallBack,e)
	        }
	      },
	       close: function() {
	        form[ 0 ].reset();
//	        var tips = $(".validateTips");
//	      	tips.text('请输入用户名和密码').addClass( "ui-state-highlight" );
	      }
	     });	
	
	form = dialog.find( "form" ).on( "submit", function( event ) {
	      event.preventDefault();
	      //login(returnCallBack);
	 });
	
	dialog.dialog( "open" );
}  

function login(callback,e) 
{
	var name = $("#name").val().trim();
    var pwd = $("#password").val().trim();
    
    if(pwd == '' || name == ''){
    	var tips = $(".validateTips");
      	tips.text('请输入用户名和密码').addClass( "ui-state-highlight" );
      	setTimeout( function() {tips.hide();}, 3000);
      	form[ 0 ].reset();
      	$(e.target.parentElement.parentElement).find('button').show()
    	$(e.target.parentElement.parentElement).find('img').hide()
    	return
    }
    
    var Url = '../../footballer/api/mobile/v2/player-service/arenaOwnerLogin/100000/e7afdd55291a3e3796a6d3d83e70d6dd';
    var parameters = {};
	parameters.accountName = name
	parameters.pwd = pwd
    $.postJSON(Url, parameters,function(data) {
	 	if (data && data.status == 'success') {
	 		localStorage.setItem("playerAccountId", data.playerAccountId)
	 		localStorage.setItem("isAutoLogin",true)
	 		localStorage.setItem("adminName", name)
	 		localStorage.setItem("urlPre", data.urlPre)
	 		localStorage.setItem("iaa", data.iaa)

	 		var arenas = data.arenas
	 		
	 		if(arenas.length== 1){
	 			localStorage.setItem("arenaId",arenas[0].id)
	 			localStorage.setItem("arenaName",arenas[0].name)
	 			localStorage.setItem("arenaLogo",arenas[0].pic)
	 			form[ 0 ].reset();
	 			$(e.target.parentElement.parentElement).find('button').show()
	 	    	$(e.target.parentElement.parentElement).find('img').hide()
	 			dialog.dialog( "close" );
	 			callback()
	 		}else{
	 			
	 			var html = "<div class='HBox'>" +
	 					"<label for='arenaSelect' style='margin-top: 5px;margin-right: 5px;'>场馆</label>" +
	 					"<select id='arenaSelect' style='width: 155px;margin-right:5px;'></select>" +
	 					"<button id='enterArena' class='button button-primary button-rounded button-tiny' style='margin-top:3px;'>进入</button>" +
	 					"</div>"
	 			
	 			$("#dialog-form").append(html);
	 			$(e.target.parentElement.parentElement).find('img').hide()
	 			$('#enterArena').click(function(e){
	 				selectArenaId = $("#arenaSelect").find('option:selected').val()
	 				localStorage.setItem("arenaId",selectArenaId)
	 				
	 				$.each(arenas,function(i,v) {
	 					if(v.id == selectArenaId){
	 						localStorage.setItem("arenaName",v.name)
	 			 			localStorage.setItem("arenaLogo",v.pic)
	 			 			return
	 					}
	 				});
	 				//localStorage.setItem("arenaName",$("#arenaSelect").find('option:selected').text())
	 				form[ 0 ].reset();
	 				dialog.dialog( "close" );
	 				callback()
	 			});

	 			$(arenas).each(function() {
	 				 $('#arenaSelect').append($("<option>").attr('value',this.id).text(this.name));
	 			});
	 			
	 			console.log(1);
	 			
	 		}
	 		
	 	}else if(data && data.status == 'noData'){
	 		var tips = $(".validateTips");
	 	   	tips.text('用户名/密码不正确/不存在场地管理权限').addClass( "ui-state-error" ).show();
	 	   	$(e.target.parentElement.parentElement).find('button').show()
	    	$(e.target.parentElement.parentElement).find('img').hide()
	 	}else if(data && data.status == 'error'){
	 		var tips = $(".validateTips");
	 	   	tips.text('用户名/密码 不能为空').addClass( "ui-state-error" ).show();
	 	   	$(e.target.parentElement.parentElement).find('button').show()
	    	$(e.target.parentElement.parentElement).find('img').hide()
	 	}
	});
     
} 

function htmlHeaderInit(){
	var storage = window.localStorage;
	var adminName = storage["adminName"]
	var arenaName = storage["arenaName"] 
	var arenaLogo = storage["arenaLogo"]
	
	$('a.logo').text(arenaName)
	$('#arenaLogo').attr('src', 'http://static.footballer.cc/'+arenaLogo).attr('display','block');
	$('body#reservation').css('background-image', 'url(http://static.footballer.cc/'+arenaLogo+')');
	$('body#reservation').css('background-repeat', 'space');
	//$('body#reservation').css('background-blend-mode','overlay');
	$('body#reservation').css('background-blend-mode','screen');
	
	$('body#reservation').css('background-size', 'cover');
	$('#adminName').text(adminName)
}

function loginOut(){
	
	cleanLocalStorage();
}

function cleanLocalStorage(){
	
	localStorage.setItem("isAutoLogin", false)
	localStorage.setItem("adminName", '')
	localStorage.setItem("arenaName", '')
	localStorage.setItem("arenaLogo", '')
	localStorage.setItem("adminName", '')
	localStorage.setItem("urlPre", '')
	localStorage.setItem("iaa", '')
	
	$('a.logo').text('')
	$('#arenaLogo').attr('src', ''); 
	$('#adminName').text('')
}

function updatePwdPopup(){
	
	dialog = $( "#dialog-updatePwd" ).dialog({
		  closeOnEscape: false,
		  open: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog | ui).hide(); },//前2行 隐去右侧自带得关闭按钮
	      autoOpen: true,
	      height: 250,
	      width: 350,
	      modal: true,
	      buttons: {
	        "清空": function() {
	        	$("#newPwd").val('')
	            $("#confirmPwd").val('')
	            $(".validateTips").text('').hide()
	        },
	        "更新": function(e){
	        	$(e.target.parentElement.parentElement).find('button').hide()
	        	$(e.target.parentElement.parentElement).append('<img style="margin: 0px 0px 0px 5px;width: 25px;height: 25px;" src="../img/gif/soccer_loading.gif">')
	        	updatePwd(e)
	        }
	      },
	       close: function() {
	        form[ 0 ].reset();
	        var tips = $(".validateTips");
	      	//tips.text('请输入一致的新密码').addClass( "ui-state-highlight" );
	      }
	     });	
	
	form = dialog.find( "form" ).on( "submit", function( event ) {
	      event.preventDefault();
	 });
	
	dialog.dialog( "open" );
}

function updatePwd(e){
	var newPwd = $("#newPwd").val().trim()
	var confirmPwd = $("#confirmPwd").val().trim();
    var playerAccountId = window.localStorage["playerAccountId"]
    
    if(newPwd != confirmPwd){
    	var tips = $(".validateTips");
 	   	tips.text('输入的密码不一致').addClass( "ui-state-error" ).show();
 	   	setTimeout( function() {tips.hide();}, 3000);
 	   	$(e.target.parentElement.parentElement).find('button').show()
 	   	$(e.target.parentElement.parentElement).find('img').hide()
 	   return
    }
    
    if(playerAccountId == '' || playerAccountId == void 0 || newPwd == ''){
    	var tips = $(".validateTips");
 	   	tips.text('输入密码不能为空').addClass( "ui-state-error" ).show();
 	   	setTimeout( function() {
 	   		tips.removeClass( "ui-state-error" )
 	   		tips.hide();}, 3000);
 	   	$(e.target.parentElement.parentElement).find('button').show()
 	   	$(e.target.parentElement.parentElement).find('img').hide()
    	return
    }
    
    var Url = '../../footballer/api/mobile/v2/player-service/updateAccountPWD'+window.localStorage["iaa"]
    var parameters = {};
	parameters.playerAccountId = playerAccountId
	parameters.newPwd = newPwd
	
    $.postJSON(Url, parameters,function(data) {
	 	if (data && data.status == 'success') {
	 		
	 		var tips = $(".validateTips");
	 	   	tips.text('密码修改成功').addClass("ui-state-active").show();
	 	   setTimeout( function() {
	 		  $(e.target.parentElement.parentElement).find('button').show()
	 	 	  $(e.target.parentElement.parentElement).find('img').hide()
	 		  dialog.dialog( "close" ) 
	 		  tips.hide();
	 	   }, 2000);
	 	}else {
	 		var tips = $(".validateTips");
	 	   	tips.text('用户名不存在 / 参数不能为空').addClass( "ui-state-error" ).show();
	 	   	$(e.target.parentElement.parentElement).find('button').show()
	 	 	$(e.target.parentElement.parentElement).find('img').hide()
	 	}
	});
     
}


// -------------------------------------------------------------------字符数组处理-----------------------------------------------------------------


//根据参数只去字符串参数个字节
function textLengthFormat(str, number){
	str = str.toString().trim()
	var bytelength = getBytesLength(str)
	if(bytelength<= number){
		return str
	}else{
		return str.substr(0,number)
	}
}

//获取字符串字节长度
//把双字节的替换成两个单字节的然后再获得长度
function getBytesLength(str) { 
return str.replace(/[^\x00-\xff]/gi, "01").length; 
} 

//获取中文和英文不同得字符长度
function getChineseAndEnglishChatLength(str) {  
    ///<summary>获得字符串实际长度，中文2，英文1</summary>  
    ///<param name="str">要获得长度的字符串</param>  
    var realLength = 0, len = str.length, charCode = -1;  
    for (var i = 0; i < len; i++) {  
        charCode = str.charCodeAt(i);  
        if (charCode >= 0 && charCode <= 128) realLength += 1;  
        else realLength += 2;  
    }  
    return realLength;  
};  

//数组去重
function uniquearr(arr)
{
    arr = arr || [];
    var a = {};
    for (var i=0; i<arr.length; i++)
    {
        var v = arr[i];
        if (typeof(a[v]) == 'undefined')
    {
            a[v] = 1;
        }
    }
    arr.length = 0;
    for (var i in a )
    {
        arr[arr.length] = i;
    }
    return arr;
}

// HTML控制文本框只能输入数字和小数点，并且只能保留小数点后两位
function clearNoNum(obj) {  
    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符  
        obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是  
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数  
      
}

//set all property to null
function setAllpropToNull(Obj){
	for (var prop in Obj) {
	    if (Obj.hasOwnProperty(prop)) {
	    	Obj[prop]=null
	    }
	}
}

//-------------------------------------------------------------------微信分享预处理-----------------------------------------------------------------
function shareUrlhandle(url){
	var newUrl = url
	//移除微信分享后加入的参数
	if(url.indexOf('&from=singlemessage') >0){
		newUrl = url.replace('&from=singlemessage', '')
	}
	if(url.indexOf('&from=timeline') >0){
		newUrl = url.replace('&from=timeline', '')
	}
	if(url.indexOf('&isappinstalled=0') >0){
		newUrl = url.replace('&isappinstalled=0', '')
	}
	
	//移除不同类型得访问参数
	if(url.indexOf('type=web&') >0){
		newUrl = url.replace('type=web&', '')
	}
	if(url.indexOf('type=app&') >0){
		newUrl = url.replace('type=app&', '')
	}
	
	//alert(newUrl)
	return newUrl
}

function wx_get_nonceStr(len){
	len = len || 32;
	var $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890';    
	var maxPos = $chars.length;
	var pwd = '';
	for (i = 0; i < len; i++) {
		pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return pwd;
}

function wx_get_timestamp(){
	return ((new Date).getTime()/1000).toFixed(0);
}

function wxJSSDKInit(arenaId, appid, url, type, desc, shareImgUrl,shareContent){
	f$.cache('domain',getCurrentDomain()+'/')
	f$.cache('suffix', GLOBAL_VAR_SUFFIX)

	if(f$.expCache('wx_ticket') ==null || f$.expCache('wx_ticket') == 'null'){
		f$.getWXjsApiTicket(arenaId)
	}
	
	var timestamp = wx_get_timestamp()
	var noncestr =  wx_get_nonceStr(20)

//	alert('appid: '+f$.cache('appId'))
//	alert('accessToken: '+f$.expCache('accessToken'))
//	alert('ticket: '+f$.expCache('wx_ticket'))
	
	var string1 = 'jsapi_ticket='+f$.expCache('wx_ticket')+
			      '&noncestr='+noncestr+
			      '&timestamp='+timestamp+
			      '&url='+ url
    
//  alert('string1: '+string1)
			      
	//sha1 version1		      
//	var shaObj = new jsSHA("SHA-1", "TEXT");
//	shaObj.update(string1);
//	signature = shaObj.getHash("HEX") 
			      
	//sha1 version2
	signature = hex_sha1(string1)
	
	callWXConfig(appid, timestamp ,noncestr, signature, url, type, desc, shareImgUrl, shareContent,arenaId)

	//sha1 version3 从服务器去生成签名，（出于安全考虑，开发者必须在服务器端实现签名的逻辑。但是亲测可以在浏览器端完成）
	/*		      
	var params = {
		jsApiTicket:f$.expCache('wx_ticket'),
		noncestr:noncestr,
		timestamp:timestamp,
		url:url
	} 	
	
	f$.getWXSignature(params).done(function(data) {
		alert('signature: '+ data.signature)
		callWXConfig(appid, timestamp ,noncestr, data.signature, url, type, desc, shareImgUrl)
	});		      
	*/
}	

function callWXConfig(appid, timestamp, noncestr, signature, url, type, desc, shareImgUrl, shareContent, arenaId){
	wx.config({    
	    debug: false,
	    //debug: true,
	    appId: appid,    
	    timestamp: timestamp,    
	    nonceStr: noncestr,    
	    signature: signature,    
	    jsApiList: [    
	        //'checkJsApi',
	    	"chooseImage", 
	    	"previewImage", 
	    	"uploadImage", 
	    	"getLocalImgData",
	    	"downloadImage",
	        'onMenuShareTimeline',    //分享到朋友圈
	        'onMenuShareAppMessage',  //分享给朋友
	        'onMenuShareQQ'           //分享到QQ 
	    ]
	});
	
	wx.ready(function () {    
	    /*  <%--公共方法--%> */    
	    var shareData = {    
	        //title: f$.cache('arenaHeaderTitle') + type,
	        title: type,
	        desc: desc,    
	        link: url,    
	        //imgUrl: f$.cache('arenaLogoUrl'),
	        imgUrl: shareImgUrl,
	        success: function (res) {    
	        	//alert('[success sharing] playerId: ' + f$.cacheObj('currentPlayer').id + '   url: ' + url + '  content: ' + type)
	        	
	        	//这里可以加上 已经分享的记录已经分享的情况
	        	params = {
	        		playerId: f$.cacheObj('currentPlayer').id,
	        		url: url,
	        		content: shareContent
	        	}
	        	f$.saveShareRecord(f$.cache('domain'), f$.cache('suffix'), params).done(function(data) {
	        		//alert('save Shared Record')
	        	});  
	        },    
	        cancel: function (res) {
	        	//alert('cancel Shared Record')
	        },
	        fail: function (res) {   
	            //alert(JSON.stringify(res));
	            //alert('playerID: '+ f$.cacheObj('currentPlayer').id + ' url: '+ url + '  content: ' + type)
	        }   
	    };    
	       
	    //详细定义处理方式
//	    /* <%--分享给朋友接口--%> */    
//		wx.onMenuShareAppMessage({    
//	        title: shareData.title,    
//	        desc: shareData.desc,    
//	        link: shareData.link,    
//	        imgUrl: shareData.imgUrl,    
//	        trigger: function (res) {    
//	           //alert('用户点击发送给朋友');    
//	        },    
//	        success: function (res) {    
//	            //alert('处理失败成功-已分享');    
//	        },    
//	        cancel: function (res) {    
//	            //alert('已取消');    
//	        },    
//	        fail: function (res) {   
//	        	//alert('分享朋友圈失败');
//	            //alert(JSON.stringify(res));    
//	        }    
//	    });    
	    
	    
	    //统一处理方式
	    /* <%--分享给朋友接口--%> */    
		wx.onMenuShareAppMessage(shareData);
	    
	        /* <%--分享到朋友圈接口--%> */    
	    wx.onMenuShareTimeline(shareData);  
	    
	    /* 分享到qq */
	    wx.onMenuShareQQ({
	    	title: shareData.title,    
	        desc: shareData.desc,    
	        link: shareData.link,    
	        imgUrl: shareData.imgUrl,    
	        trigger: function (res) {    
	           //alert('用户点击发送给朋友');    
	        },    
	        success: function (res) {    
	            //alert('处理失败成功-已分享');    
	        },    
	        cancel: function (res) {    
	            //alert('已取消');    
	        },    
	        fail: function (res) {   
	        	//alert('分享朋友圈失败');
	            //alert(JSON.stringify(res));    
	        }    
	    });
	    	    
	});    
	 /*  <%--处理失败验证--%> */    
	wx.error(function (res) {    
	    //alert("处理失败验证-error: " + res.errMsg);
		console.log(res);
	});
}

function wxChooseImage(tournamentId,matchId) {
		
    wx.chooseImage({
        count: 1,
        sizeType: ['compressed'],
        sourceType: ['album', 'camera'],
        success: function(res) {
            var localIds = res.localIds; //存储localId供本地预览
            wx.uploadImage({
                localId: res.localIds[0],
                isShowProgressTips: 1,
                success: function(res) {
                    var mediaId = res.serverId; //图片上传成功后保存serverId然后发给后台，让后台根据serverId去微信服务器下载对应的图片
                                    	
                	$token = f$.getWXAccessToken(0);
                    
                    params = {
        	        		playerId: f$.cacheObj('currentPlayer').id,
        	        		mediaId: mediaId,
        	        		accessToken: $token,
        	        		matchId:matchId,
        	        		tournamentId:tournamentId
        	        }
                    
                    if(playerId == 1 || playerId == 3 || playerId == 1344  || playerId == 1316 || playerId == 1317 || playerId == 1318  || playerId == 1319 || playerId == 1320)
                    {
	                   	 f$.fetchWeixinImageToAliOSSURL(f$.cache('domain'), f$.cache('suffix'), params).done(function(data) {
	          	        	alert('upload image to alioss success!');
	          	         });
                    } else {
                    	 f$.fetchWeixinImageToQiqiu(f$.cache('domain'), f$.cache('suffix'), params).done(function(data) {
             	        	alert('upload image to qiniu success!');
             	         });
                    }
                }
            });
        }
    });
}

function wxChooseImage2(tournamentId,matchId,callback) {
//	callback('11','11');
//	return
	
	wx.chooseImage({
        count: 1,
        sizeType: ['compressed'],
        sourceType: ['album', 'camera'],
        success: function(res) {
            var localIds = res.localIds; //存储localId供本地预览
            wx.uploadImage({
                localId: res.localIds[0],
                isShowProgressTips: 1,
                success: function(res) {
                    var mediaId = res.serverId; //图片上传成功后保存serverId然后发给后台，让后台根据serverId去微信服务器下载对应的图片
                    
                    callback(mediaId,localIds[0]);                   
                }
            });
        }
    });
}

//-------------------------------------------------------------------赛事h5部分 手机和web端共同使用的-----------------------------------------------------------------
function wxOauth2Redirect(){
	code = getParameter(window.location.href ,"code")
	if(code == '' || code==null){
		//var currentDomain = getCurrentDomain() 
		var currentUrl = location.href.split('#')[0]
		var appid = f$.cache('appId')
		
		url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
			  "appid=" + appid +
			  "&redirect_uri=" + encodeURIComponent(currentUrl) +
			  "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		
		//alert(url)
		
		location.href = url
		return
	}else{
		return f$.getWXUserAccessToken(code)
	}
}

function h5Init(){
	f$.cache('appId', 'wx8fcb7dc481af1d3c')
	if(f$.cache('accessType') == null || f$.cache('accessType') == '' || f$.cache('accessType') == void 0){
		url_type = getParameter(window.location.href ,"type");
		url_type = (url_type && url_type.length > 0) ? url_type:'web';//默认web
		f$.cache('accessType', url_type);
	}
	if(f$.cache('lang') == null || f$.cache('lang') == '' || f$.cache('lang') == void 0){
		url_lang = getParameter(window.location.href ,"lang");
		url_lang = (url_lang && url_lang.length > 0) ? url_lang:'en';//默认英语
		f$.cache('lang', url_lang);
	}
	getCurrentH5PlayerAccount()
}

function getCurrentH5PlayerAccount(){
	
	if(f$.cache('accessType') == 'app'){
		playerId = f$.cache('uesr_current_playerId')
		if(!playerId || playerId <= 0) {
        	location.href = GLOBAL_VAR_DOMAIN + 'footballer/mobile/match/football/login.html';
        	return
    	}else{
    		player ={}
    		player.id = f$.cache('uesr_current_playerId');
    		player.name = f$.cache('uesr_current_playerNickName');
    		player.logo = f$.cache('uesr_current_peopleImgUrl');
    		player.identity = f$.cache('uesr_current_accountIdentity');
    		f$.cacheObj('currentPlayer',player)

    		return player
    	}
		
	}else{
		//alert('playerId: ' + f$.cache('playerId'))
		
		if(f$.cache('playerId') == 'null' || f$.cache('playerId') == null || f$.cache('playerId') == ''){
			//return wxOauth2Redirect()
		}else{
			//alert('已缓存player' + f$.cache('playerLogo'))
			player ={}
    		player.id = f$.cache('playerId');
    		player.name = f$.cache('playerName');
    		player.logo = f$.cache('playerLogo');
    		f$.cacheObj('currentPlayer',player)
    		return player
		}
	}
}

function accessStats(){

	params = {
		url:window.location.href,
		page:getPageName(),
		date: formatToDateString(new Date())
	}
	
	
	//f$.saveAccessRecord(f$.cache('domain'), f$.cache('suffix'), params).done(function(data) {
	f$.saveAccessRecord(getCurrentDomain()+'/', GLOBAL_VAR_SUFFIX, params).done(function(data) {
		//alert('save Shared Record')
	});	
}

function getPageName()
{
	url = window.location.href
	//'http://dev.footballer.cc/footballer/mobile/order/battlePool2.html'
	start_index = url.lastIndexOf('/')+1  
	end_index = url.indexOf('?')
	return url.substring(start_index, end_index)
}

//-------------------------------------------------------------------赛事h5部分 跳转-----------------------------------------------------------------
function aufFooterRedirect(){
	//location.href = 'http://footballer.cc';
	//location.href = 'http://aufsports.sxl.cn/';
}

function historyBack(type){
	if(document.referrer == null || document.referrer == '' || document.referrer == void 0){
		if(type=='football'){
			location.href = GLOBAL_VAR_DOMAIN +'/footballer/mobile/match/football/matchListDashboard.html?lang=en&type=web';	
		}
		if(type=='basketball'){
			location.href = GLOBAL_VAR_DOMAIN +'/footballer/mobile/match/basketball/bbTournamentDashboard.html?lang=en&type=web&tournamentId=7';
		}
	}else{
		history.back();
	}
}
