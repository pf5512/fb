//------------------------------------------------------------------后台登录功能------------------------------------------------------------------

function tournamentMgmtLoginStatusCheck(callback){
	var storage = window.localStorage;    
    var isAutoLogin = storage["isAutoLogin"]  

    if(isAutoLogin == 'true'){  //已经存在用户的本地存储中
    	//免登陆进入
    	callback()
    }else{ //未登陆过
    	tournamentMgmtLoginPopup(callback)
    }
}

function tournamentMgmtLoginPopup(callback)
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
	        	tournamentMgmtLoginFunction(returnCallBack,e)
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

function tournamentMgmtLoginFunction(callback,e) 
{
	var name = $("#name").val().trim();
    var pwd = $("#password").val().trim();
    
    if(pwd == '' || name == ''){
    	var tips = $(".validateTips");
      	tips.text('请输入用户名和密码').addClass( "ui-state-highlight" ).show();
      	setTimeout( function() {tips.hide();}, 3000);
      	form[ 0 ].reset();
      	$(e.target.parentElement.parentElement).find('button').show()
    	$(e.target.parentElement.parentElement).find('img').hide()
    	return
    }
    
    var Url = '../../footballer/api/mobile/v2/player-service/tournamentOwnerLogin/100000/e7afdd55291a3e3796a6d3d83e70d6dd';
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

	 		var tournaments = data.tournaments
	 		
	 		if(tournaments.length== 1){
	 			localStorage.setItem("tournamentId",tournaments[0].id)
	 			localStorage.setItem("tournamentName",tournaments[0].name)
	 			localStorage.setItem("tournamentsLogo",tournaments[0].logo)
	 			form[ 0 ].reset();
	 			$(e.target.parentElement.parentElement).find('button').show()
	 	    	$(e.target.parentElement.parentElement).find('img').hide()
	 			dialog.dialog( "close" );
	 			callback()
	 		}else{
	 			
	 			var html = "<div class='HBox'>" +
	 					"<label for='tournamentSelect' style='margin-top: 5px;margin-right: 5px;'>赛事</label>" +
	 					"<select id='tournamentSelect' style='width: 155px;margin-right:5px;'></select>" +
	 					"<button id='enterTournament' class='button button-primary button-rounded button-tiny' style='margin-top:3px;'>进入</button>" +
	 					"</div>"
	 			
	 			$("#dialog-form").append(html);
	 			$(e.target.parentElement.parentElement).find('img').hide()
	 			$('#enterTournament').click(function(e){
	 				selectTournamentId = $("#tournamentSelect").find('option:selected').val()
	 				localStorage.setItem("tournamentId",selectTournamentId)
	 				
	 				$.each(tournaments,function(i,v) {
	 					if(v.id == selectTournamentId){
	 						localStorage.setItem("tournamentName",v.name)
	 			 			localStorage.setItem("tournamentLogo",v.logo)
	 			 			return
	 					}
	 				});
	 				//localStorage.setItem("tournamentName",$("#tournamentSelect").find('option:selected').text())
	 				form[ 0 ].reset();
	 				dialog.dialog( "close" );
	 				callback()
	 			});

	 			$(tournaments).each(function() {
	 				 $('#tournamentSelect').append($("<option>").attr('value',this.id).text(this.name));
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

function loginOut(){
	
	cleanLocalStorage();
}

function cleanLocalStorage(){
	
	localStorage.setItem("isAutoLogin", false)
	localStorage.setItem("adminName", '')
	localStorage.setItem("tournamentName", '')
	localStorage.setItem("tournamentLogo", '')
	localStorage.setItem("adminName", '')
	localStorage.setItem("urlPre", '')
	localStorage.setItem("iaa", '')
	
	$('a.logo').text('')
	$('#tournamentLogo').attr('src', ''); 
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
