var FService = (function() {

	var Service = function() {
		this.openid = 'NON-OPENID';
	}

	// 存取单个字符串到某个参数（虽然对存储的内容大小没有限制，但是存入的东西都被转换成了字符串，也就是说无法存入数组或者对象，就算存入了也会被转化为字符串。）
	Service.cache = function() {
		if (window.localStorage) {
			var length = arguments.length, cache = window.localStorage;

			if (length > 1) {
				cache.setItem(arguments[0], arguments[1]);
				return Service;
			} else if (1 == length) {
				return cache.getItem(arguments[0]);
			}
		} else {
			return Service;
		}
	};

	// 存储_读取json对象
	Service.cacheObj = function() {
		if (window.localStorage) {
			var length = arguments.length, cache = window.localStorage;
			if (1 == length) {
				return JSON.parse(cache.getItem(arguments[0]));
			} else if (length == 2) {
				cache.setItem(arguments[0], JSON.stringify(arguments[1]));
				return Service;
			}
		} else {
			return Service;
		}
	};

	// 有过期时间的缓存 毫秒级
	Service.expCache = function() {
		var saveTime = 'saveTime'
		var expTime = 'expTime'
		if (window.localStorage) {
			var length = arguments.length, expCache = window.localStorage;

			if (1 == length) {
				var currentTime = (new Date).getTime()
				var saveTime = Number(expCache.getItem(arguments[0] + saveTime))
				var expTime = Number(expCache.getItem(arguments[0] + expTime))

				if (currentTime - saveTime < expTime) {// 还未过期
					return expCache.getItem(arguments[0]);
				} else { // 过期
					expCache.setItem(arguments[0], null);
					return expCache.getItem(arguments[0]);
				}
			}
			if (length == 2) { // 两个参数 就表示 无过期时间
				expCache.setItem(arguments[0], arguments[1]);
				expCache.setItem(arguments[0] + saveTime, (new Date).getTime());
				expCache.setItem(arguments[0] + expTime, 999999999999999999);
				return Service;
			}
			if (length == 3) {
				expCache.setItem(arguments[0], arguments[1]);
				expCache.setItem(arguments[0] + saveTime, (new Date).getTime());
				expCache.setItem(arguments[0] + expTime, arguments[2]);
				return Service;
			}
		} else {
			return Service;
		}
	};

	var domain = Service.cache('domain')
	var suffix = Service.cache('suffix')

	var v2 = 'footballer/api/mobile/v2', v1 = 'footballer/api/mobile/v1', checkPermissionUrl = domain
			+ v2
			+ '/player-service/getArenaOwnerByIdAndOpenId/{arenaId}/{openId}/'
			+ suffix, fieldServiceUrl = domain
			+ v2
			+ '/reservation-service/getArenaReservationByDate/{arenaId}/{date}/'
			+ suffix, reserveFieldUrl = domain + v2
			+ '/reservation-service/reserveField/' + suffix, getPlayersServiceUrl = domain
			+ v2 + '/player-service/getArenaPlayerList/{arenaId}/' + suffix, cancelReservationURL = domain
			+ v2 + '/reservation-service/adminCancelReservation/' + suffix, acceptBattleURL = domain
			+ v2 + '/reservation-service/acceptBattle/' + suffix,

	weixinPayUrl = domain + v2 + '/payment-service/payWithWechat/' + suffix, ordersUrl = domain
			+ v2 + '/reservation-service/getPlayerReservationList/' + suffix, updateReserveURL = domain
			+ v2 + '/reservation-service/updatePaidReserveField/' + suffix, receiveBattleURL = domain
			+ v2 + '/reservation-service/acceptBattle/' + suffix, updatePhoneURL = domain
			+ v1 + '/user-service/player/updateAccountCellPhone/' + suffix, createTeamURL = domain
			+ v1 + '/team-service/createTeamByNamePlayerId/' + suffix, removeBattleURL = domain
			+ v2 + '/reservation-service/removeWechatGuestBattle/' + suffix, battleWishesURL = domain
			+ v2 + '/battle-service/findWeChatBattleWishs/{arenaId}/' + suffix, addBattleWishesURL = domain
			+ v2 + '/battle-service/addWeChatBattleWish/' + suffix, removeBattleWishesURL = domain
			+ v2 + '/battle-service/removeWeChatBattleWish/' + suffix, soloWishesURL = domain
			+ v2 + '/solo-service/findWeChatSoloWishes/{arenaId}/' + suffix, addSoloWishesURL = domain
			+ v2 + '/solo-service/addWeChatSoloWish/' + suffix, removeSoloWishesURL = domain
			+ v2 + '/solo-service/removeWeChatSoloWish/' + suffix,

	getWXAccessTokenURL = domain + v2
			+ '/wechat-service/getWXAccessToken/{arenaId}/' + suffix, 
    getWXjsApiTicketURL = domain
			+ v2 + '/wechat-service/getWXjsApiTicket/{accessToken}/' + suffix, 
	getWXSignatureURL = domain
			+ v2 + '/wechat-service/getWXSignature/' + suffix;
	fetchWeixinImageToQiqiuURL = domain + v2
			+ '/wechat-service/fetchWeixinImageToQiqiu/' + suffix;
	fetchWeixinImageToAliOSSURL = domain + v2
			+ '/wechat-service/fetchWeixinImageToAliOSS/' + suffix;

	// temp =
	// 'e30ea6d0-5273-4ba2-9cf7-155af789418c/100000/e7afdd55291a3e3796a6d3d83e70d6dd',
	// tempdomain = 'footballer.cc/';

	// 赛事
	// loadMatchListURL = GLOBAL_VAR_DOMAIN + v2 +
	// '/tournament-service/getAllTournamentList/'+ suffix,
	// getTournamentVideoListURL = GLOBAL_VAR_DOMAIN + v2 +
	// '/tournament-service/getTournamentVideoList/'+ suffix,

	// 微信相关

	Service.removeCache = function(key) {
		if (window.localStorage) {
			window.localStorage.removeItem(key);
		}
		return Service;
	};

	// 4 wx user access
	Service.getWXUserAccessToken = function(code) {
		var getWXUserAccessTokenURL = GLOBAL_VAR_DOMAIN + v2
				+ '/wechat-service/getWechatOAuthAccessToken/{code}/';
		var url = getWXUserAccessTokenURL.replace('{code}', code)
		// alert(url)
		$.ajax({
			url : url,
			type : 'get',
			contentType : 'application/json;charset=utf8',
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					f$.cache('playerId', data.playerId)
					f$.cache('playerName', data.nickname)
					f$.cache('playerLogo', data.headimgurl)

					currentPlayer = {
						id : data.playerId,
						name : data.nickname,
						logo : data.headimgurl
					}
					f$.cacheObj('currentPlayer', currentPlayer)
					return currentPlayer
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR.status);
			}
		})
	};

	// 4 wx sharing
	Service.getWXAccessToken = function(arenaId) {
		//$accessToken = f$.expCache('accessToken') // 获取缓存的值
		//if ($accessToken == 'null' || $accessToken == 'undefined') {
			var url = getWXAccessTokenURL.replace('{arenaId}', arenaId);
			// alert('GetAccessTokenURL: '+ url)
			$.ajax({
				url : url,
				type : 'get',
				contentType : 'application/json;charset=utf8',
				async : false,
				dataType : 'json',
				success : function(data) {
					if (data.accessToken) {
						$token = data.accessToken
						// 注意：这里需要将获取到的token缓存起来（或写到数据库中）
						// 不能频繁的访问https://api.weixin.qq.com/cgi-bin/token，每日有次数限制
						// 通过此接口返回的token的有效期目前为2小时。令牌失效后，JS-SDK也就不能用了。
						// 因此，这里将token值缓存:5400000
						// (1小时30分钟，比2小时小)。缓存失效后，再从接口获取新的token，这样
						// 就可以避免token失效。
						// alert('ajax-accessToken: '+ data.accessToken)
						//服务器缓存，不在客户端缓存
						//f$.expCache('accessToken', data.accessToken, 5400000)
					}
				},
				error : function(e) {
					// alert('GetAccessToken Error' +e);
				}
			})
			return $token;
		//} else {
		//	return $accessToken
		//}
	};

	Service.getWXjsApiTicket = function(arenaId) {
		$ticket = f$.expCache('wx_ticket')
		if ($ticket == 'null' || $ticket == void 0) {
			//$token = f$.expCache('accessToken')
			//if ($token == 'null' || $token == 'undefined') {
			//	$token = f$.getWXAccessToken(arenaId)
			//}
			
			$token = f$.getWXAccessToken(arenaId);
			
			// alert('get accessToken: '+ $token)
			var url = getWXjsApiTicketURL.replace('{accessToken}', $token);
			$.ajax({
				url : url,
				type : 'get',
				contentType : 'application/json;charset=utf8',
				async : false,
				dataType : 'json',
				success : function(data) {
					if (data.jsAPITicket && data.errcode == 0) {
						// 注意：这里需要将获取到的ticket缓存起来（或写到数据库中）
						// ticket和token一样，不能频繁的访问接口来获取，在每次获取后，我们把它保存起来。
						// 因此，这里将ticket值:5400000
						// (1小时30分钟，比2小时小)。缓存失效后，再从接口获取新的ticket，这样
						// 就可以避免ticket失效。
						f$.expCache('wx_ticket', data.jsAPITicket, 5400000)
						// alert('data.jsAPITicket: '+ data.jsAPITicket)
						return $ticket;
					}
				}
			})
		} else {
			return $ticket;
		}
	};

	Service.getWXSignature = function(params) {
		return $.ajax({
			url : getWXSignatureURL,
			data : JSON.stringify(params),
			async : false,
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	// TODO 目前如果是跨场馆 就会直接从微信端去获取 accessToken 和 JSSDKticket.未按照不同得arenaID 来作缓存。
	// (改进建议) 如果以后跨场地使用过多 需要根据不同得arenaId 来缓存对应的 accessToken 和 JSSDKticket.
	Service.getNewWXjsApiTicket = function(newArenaId) {

		var url = getWXAccessTokenURL.replace('{arenaId}', newArenaId);
		$.ajax({
			url : url,
			type : 'get',
			contentType : 'application/json;charset=utf8',
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data.accessToken) {
					$token = data.accessToken
					// 注意：这里需要将获取到的token缓存起来（或写到数据库中）
					// 不能频繁的访问https://api.weixin.qq.com/cgi-bin/token，每日有次数限制
					// 通过此接口返回的token的有效期目前为2小时。令牌失效后，JS-SDK也就不能用了。
					// 因此，这里将token值缓存:5400000
					// (1小时30分钟，比2小时小)。缓存失效后，再从接口获取新的token，这样
					// 就可以避免token失效。
					f$.expCache('accessToken', data.accessToken, 5400000)

					ticketUrl = getWXjsApiTicketURL.replace('{accessToken}',
							$token)

					$.ajax({
						url : ticketUrl,
						type : 'get',
						contentType : 'application/json;charset=utf8',
						async : false,
						dataType : 'json',
						success : function(data) {
							if (data.jsAPITicket && data.errcode == 0) {
								// 注意：这里需要将获取到的ticket缓存起来（或写到数据库中）
								// ticket和token一样，不能频繁的访问接口来获取，在每次获取后，我们把它保存起来。
								// 因此，这里将ticket值:5400000
								// (1小时30分钟，比2小时小)。缓存失效后，再从接口获取新的ticket，这样
								// 就可以避免ticket失效。
								f$.expCache('wx_ticket', data.jsAPITicket,
										5400000)
							}
						}
					})
				}
			}
		})
	};

	Service.checkPermission = function(arenaId, openId) {
		f$.cache('isArenaOwner', false)
		// alert(openId)
		var url = checkPermissionUrl.replace('{arenaId}', arenaId).replace(
				'{openId}', openId)
		$.ajax({
			url : url,
			type : 'GET',
			contentType : 'application/json;charset=utf8',
			async : true,
			dataType : 'json',
			success : function(data) {
				if (data.arenaOwner) {
					f$.cache('isArenaOwner', data.arenaOwner)
				}
				// alert('checkPermission: '+ f$.cache('isArenaOwner'))
			},
			error : function(e) {
				f$.cache('isArenaOwner', false)
				// alert(e);
			}
		})
	}

	Service.battleWishes = function(params) {
		var url = battleWishesURL.replace('{arenaId}', params.arenaId);
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.getTournamentVideoList = function(domain, suffix, params) {
		var url = domain + v2 + '/tournament-service/getTournamentVideoList/'
				+ suffix;
		return $.ajax({
			url : url,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.getTeamTournamentVideoList = function(domain, suffix, teamId,
			tournamentId) {
		var url = domain + v2
				+ '/tournament-service/getTeamTournamentVideoList/' + teamId
				+ "/" + tournamentId + "/" + suffix;
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.addBattleWish = function(params) {
		return $.ajax({
			url : addBattleWishesURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.removeBattleWish = function(params) {
		return $.ajax({
			url : removeBattleWishesURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.soloWishes = function(params) {
		var url = soloWishesURL.replace('{arenaId}', params.arenaId);
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.addSoloWish = function(params) {
		return $.ajax({
			url : addSoloWishesURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.removeSoloWish = function(params) {
		return $.ajax({
			url : removeSoloWishesURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.removeBattle = function(params) {
		return $.ajax({
			url : removeBattleURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.createTeam = function(params) {
		return $.ajax({
			url : createTeamURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.updateAccountPhone = function(params) {
		return $.ajax({
			url : updatePhoneURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.receiveBattle = function(params) {
		return $.ajax({
			url : receiveBattleURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	};

	Service.updateReserve = function(params) {
		return $.ajax({
			url : updateReserveURL,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.orders = function(params) {
		return $.ajax({
			url : ordersUrl,
			data : JSON.stringify(params),
			type : 'post',
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.fields = function(params) {
		var url = fieldServiceUrl.replace('{date}', params.date);
		url = url.replace('{arenaId}', params.arenaId);
		// url = url.replace('{arenaId}', 52);
		return $.ajax({
			url : url,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			// method : 'post',
			dataType : 'json'
		})
	}

	Service.getPlayers = function(params) {
		var url = getPlayersServiceUrl.replace('{arenaId}', params.arenaId);
		return $.ajax({
			url : url,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.cancelReservation = function(params) {
		return $.ajax({
			url : cancelReservationURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.acceptBattle = function(params) {
		return $.ajax({
			url : acceptBattleURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	// 赛事部分

	Service.login = function(domain, username, pwd, suffix) {
		loginURL = domain + v1 + '/user-service/login/' + username + "/" + pwd
				+ "/" + suffix;
		return $.ajax({
			url : loginURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.updateInfo = function(domain, params, sessionToken, suffix) {
		updateUserURL = domain + v1 + '/user-service/player/update/'
				+ sessionToken + "/" + suffix;
		return $.ajax({
			url : updateUserURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.updatePlayerAndTeamInfo = function(domain, suffix, params) {
		updatePlayerAndTeamInfoURL = domain + v2
				+ '/player-service/updatePlayerAndTeamInfo/' + suffix;
		return $.ajax({
			url : updatePlayerAndTeamInfoURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.getPlayerAndBasketBallTeamsInfo = function(domain, suffix, playerId) {
		loadPlayerAndTeamsInfoUrl = domain + v2
				+ '/player-service/getPlayerAndBasketBallTeamsInfo/' + playerId
				+ "/" + suffix;
		return $.ajax({
			url : loadPlayerAndTeamsInfoUrl,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.getPlayerAndFootballTeamsInfo = function(domain, suffix, playerId) {
		loadPlayerAndTeamsInfoUrl = domain + v2
				+ '/player-service/getPlayerAndFootballTeamsInfo/' + playerId
				+ "/" + suffix;
		return $.ajax({
			url : loadPlayerAndTeamsInfoUrl,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadMatchPlayerData = function(domain, matchId, sessionToken,
			suffix) {
		matchPlayerDataUrl = domain + v2
				+ '/tournament-service/getTournamentMatchPlayerInfo/' + matchId
				+ "/" + sessionToken + "/" + suffix;
		return $.ajax({
			url : matchPlayerDataUrl,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketBallMatchPlayerData = function(domain, matchId, suffix) {
		loadBasketBallMatchPlayerDataUrl = domain + v2
				+ '/tournament-service/getBasketBallTournamentMatchPlayerInfo/'
				+ matchId + "/" + suffix;
		return $.ajax({
			url : loadBasketBallMatchPlayerDataUrl,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadMatchData = function(domain, matchId, sessionToken, suffix) {
		matchDataUrl = domain + v2
				+ '/tournament-service/getTournamentMatchTeamInfo/' + matchId
				+ "/" + sessionToken + "/" + suffix;
		return $.ajax({
			url : matchDataUrl,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}
	
	Service.loadMatchTeamResultData = function(domain, matchId, sessionToken, suffix) {
		matchDataUrl = domain + v2
				+ '/tournament-service/getTournamentMatchTeamResultInfo/' + matchId
				+ "/" + sessionToken + "/" + suffix;
		return $.ajax({
			url : matchDataUrl,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}
	
	
	Service.loadTournamentDataStats = function(domain, tournamentId, sessionToken, suffix) {
		url = domain + v2
				+ '/tournament-service/getTournamentStatsContent/' + tournamentId
				+ "/" + sessionToken + "/" + suffix;
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketBallMatchData = function(domain, matchId, suffix) {
		loadBasketBallMatchDataUrl = domain + v2
				+ '/tournament-service/getBasketBallTournamentMatchTeamInfo/'
				+ matchId + "/" + suffix;
		return $.ajax({
			url : loadBasketBallMatchDataUrl,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadMatchVideoData = function(domain, suffix, params) {
		loadMatchVideoDataUrl = domain + v2
				+ '/tournament-service/getTournamentMatchVideoRelatedInfo/'
				+ suffix;
		return $.ajax({
			url : loadMatchVideoDataUrl,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.loadVideoData = function(videoId, domain, sessionToken, suffix) {
		var url = domain + v2 + '/tournament-service/getVideoRelatedInfo/'
				+ videoId + "/" + sessionToken + "/" + suffix;
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}
	
	Service.loadMomentData = function(domain, suffix, momentId) {
		var url = domain + v2 + '/tournament-service/getMomentRelatedInfo/'
				+ momentId + "/" + suffix;
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadMatchList = function(domain, suffix, playerId) {
		loadMatchListURL = domain + v2
				+ '/tournament-service/getAllTournamentList/' + playerId + '/' + suffix;
		return $.ajax({
			url : loadMatchListURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}
	
	Service.findTeamsByCaptainUserId = function(domain, suffix, id) {
		var url = domain + v1 + '/team-service/findTeamsByCaptainUserId/' + suffix,
		    param = {"captainUserId" : id}
		return $.ajax({
			url : url,
			data : JSON.stringify(param),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}
	
	Service.registerTournementByTeam = function(domain, suffix, param) {
		var  url = domain + v2 + '/tournament-service/registerTournementByTeam/' + suffix
		return $.ajax({
			url : url,
			data : JSON.stringify(param),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}
	
	Service.deleteTournamentTeamById = function(domain, suffix, param) {
		var  url = domain + v2 + '/tournament-service/deleteTournamentTeamById/' + suffix
		return $.ajax({
			url : url,
			data : JSON.stringify(param),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}
	
	Service.createTournamentSchedule = function(domain, suffix, param) {
		var  url = domain + v2 + '/tournament-service/createTournamentSchedule/' + suffix
		return $.ajax({
			url : url,
			data : JSON.stringify(param),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}
	
	Service.findMatchScheduleByTournamentId = function(domain, suffix, param) {
		var  url = domain + v2 + '/tournament-service/findMatchScheduleByTournamentId/' + suffix
		return $.ajax({
			url : url,
			data : JSON.stringify(param),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}
	
	Service.getTournamentMatchListByMatchScheduleId = function(domain, suffix, param) {
		var  url = domain + v2 + '/tournament-service/getTournamentMatchListByMatchScheduleId/' + suffix
		return $.ajax({
			url : url,
			data : JSON.stringify(param),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.checkPlayerisTournamentRegisteredPlayer = function(domain, suffix,
			tournmentId, playerId) {
		url = domain
				+ v2
				+ '/tournament-service/checkPlayerisTournamentRegisteredPlayer/'
				+ tournmentId + '/' + playerId + '/' + suffix;
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadTeamInfo = function(domain, suffix, tournmentId, teamId) {
		loadTeamInfoURL = domain + v2
				+ '/tournament-service/getTeamTournamentPlayerInfos/'
				+ tournmentId + '/' + teamId + '/' + suffix;
		return $.ajax({
			url : loadTeamInfoURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketballTeamInfo = function(domain, suffix, tournmentId,
			teamId) {
		loadTeamInfoURL = domain + v2
				+ '/tournament-service/getBasketballTeamTournamentPlayerInfos/'
				+ tournmentId + '/' + teamId + '/' + suffix;
		return $.ajax({
			url : loadTeamInfoURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.playerApplyToJoinTeam = function(domain, suffix, params) {
		playerApplyToJoinTeamURL = domain + v1
				+ '/invitation-service/createPlayerToTeam/' + suffix;
		return $.ajax({
			url : playerApplyToJoinTeamURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.captainApprovePlayerToTeam = function(domain, suffix, params) {
		captainApprovePlayerToTeamURL = domain + v1
				+ '/invitation-service/approvePlayerToTeam/' + suffix;
		return $.ajax({
			url : captainApprovePlayerToTeamURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.captainRefusePlayerToTeam = function(domain, suffix, params) {
		captainRefusePlayerToTeamURL = domain + v1
				+ '/invitation-service/refusePlayerToTeam/' + suffix;
		return $.ajax({
			url : captainRefusePlayerToTeamURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.captainRemovePlayerFromTeam = function(domain, suffix, teamId,
			playerId) {
		url = domain + v1 + '/team-service/leaveTeam/' + teamId + '/'
				+ playerId + '/' + suffix;
		return $.ajax({
			url : url,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.loadTeamTournamentStats = function(domain, suffix, tournmentId,
			teamId) {
		loadTeamTournamentStatsURL = domain + v2
				+ '/tournament-service/getTeamTournamentStats/' + tournmentId
				+ '/' + teamId + '/' + suffix;
		return $.ajax({
			url : loadTeamTournamentStatsURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketBallTeamTournamentStats = function(domain, suffix,
			tournmentId, teamId) {
		loadTeamTournamentStatsURL = domain + v2
				+ '/tournament-service/loadBasketBallTeamTournamentStats/'
				+ tournmentId + '/' + teamId + '/' + suffix;
		return $.ajax({
			url : loadTeamTournamentStatsURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadPlayerInfo = function(domain, suffix, tournmentId, playerId) {
		loadPlayerInfoURL = domain + v2 + '/tournament-service/  /'
				+ tournmentId + '/' + playerId + '/' + suffix;
		return $.ajax({
			url : loadPlayerInfoURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadPlayerTournamentStats = function(domain, suffix, tournmentId,
			playerId) {
		loadPlayerTournamentStatsURL = domain + v2
				+ '/tournament-service/getPlayerTournamentStats/' + tournmentId
				+ '/' + playerId + '/' + suffix;
		return $.ajax({
			url : loadPlayerTournamentStatsURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketBallPlayerTournamentStats = function(domain, suffix,
			tournmentId, playerId) {
		loadPlayerTournamentStatsURL = domain + v2
				+ '/tournament-service/loadBasketBallPlayerTournamentStats/'
				+ tournmentId + '/' + playerId + '/' + suffix;
		return $.ajax({
			url : loadPlayerTournamentStatsURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadPlayerTournamentBaseInfo = function(domain, suffix,
			tournmentId, playerId) {
		loadPlayerTournamentBaseInfoURL = domain + v2
				+ '/tournament-service/getPlayerTournamentBaseInfo/'
				+ tournmentId + '/' + playerId + '/' + suffix;
		return $.ajax({
			url : loadPlayerTournamentBaseInfoURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.getTeamTournamentCalendar = function(domain, tournmentId, teamId,
			suffix) {
		var url = domain + v2
				+ '/tournament-service/getTeamTournamentCalendar/'
				+ tournmentId + '/' + teamId + '/' + suffix;
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.getBasketBallTeamTournamentCalendar = function(domain, tournmentId,
			teamId, suffix) {
		var url = domain + v2
				+ '/tournament-service/getBasketBallTeamTournamentCalendar/'
				+ tournmentId + '/' + teamId + '/' + suffix;
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}
	
	Service.loadTournamentPagTimeLines = function(domain, suffix, params) {
		url = domain + v2 + '/tournament-service/getTournamentPagingTimeLines/' + suffix;
		return $.ajax({
			url : url,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}
	
	Service.loadPlayerTournamentPagTimeLines = function(domain, suffix, params) {
		url = domain + v2 + '/tournament-service/getPlayerTournamentPagingTimeLines/' + suffix;
		return $.ajax({
			url : url,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.loadTournamentBaseInfo = function(domain, suffix, tournamentId) {
		loadTournamentBaseInfoURL = domain + v2
				+ '/tournament-service/getTournamentBaseInfo/' + tournamentId
				+ '/' + suffix;
		return $.ajax({
			url : loadTournamentBaseInfoURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadTournamentSponsors = function(domain, suffix, tournamentId) {
		loadTournamentSponsorsURL = domain + v2
				+ '/sponsor-service/getAllSponsorsBytournamentId/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : loadTournamentSponsorsURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadTournamentTeamList = function(domain, suffix, tournamentId) {
		loadTournamentTeamListURL = domain + v2
				+ '/tournament-service/getTournamentTeamsInfo/' + tournamentId
				+ '/' + suffix;
		return $.ajax({
			url : loadTournamentTeamListURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadTournamentGourpStandings = function(domain, suffix,
			tournamentId) {
		loadTournamentGourpStandingsURL = domain + v2
				+ '/tournament-service/getTournamentGourpStandings/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : loadTournamentGourpStandingsURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketBallTournamentGourpStandings = function(domain, suffix,
			tournamentId) {
		loadBasketBallTournamentGourpStandingsURL = domain + v2
				+ '/tournament-service/getBasketBallTournamentGourpStandings/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : loadBasketBallTournamentGourpStandingsURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.getTournamentMatchWinLoseList = function(domain, suffix,
			tournamentId) {
		url = domain + v2
				+ '/tournament-service/getTournamentMatchWinLoseList/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : url,
			async : false,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadTournamentMatchList = function(domain, suffix, tournamentId) {
		loadTournamentMatchListURL = domain + v2
				+ '/tournament-service/getTournamentFullCalendarInfo/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : loadTournamentMatchListURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketBallTournamentMatchList = function(domain, suffix,
			tournamentId) {
		url = domain
				+ v2
				+ '/tournament-service/getBasketBallTournamentFullCalendarInfo/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : url,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadPlayerMatchResultList = function(domain, suffix, tournamentId) {
		loadPlayerMatchResultListURL = domain + v2
				+ '/tournament-service/getTournamentPlayerStatisticRanking/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : loadPlayerMatchResultListURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketBallPlayerMatchResultList = function(domain, suffix,
			tournamentId) {
		loadPlayerMatchResultListURL = domain
				+ v2
				+ '/tournament-service/getBasketBallTournamentPlayerStatisticRanking/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : loadPlayerMatchResultListURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadTeamMatchResultList = function(domain, suffix, tournamentId) {
		loadTeamMatchResultListURL = domain + v2
				+ '/tournament-service/getTournamentTeamStatisticRanking/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : loadTeamMatchResultListURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.loadBasketBallTeamMatchResultList = function(domain, suffix,
			tournamentId) {
		loadTeamMatchResultListURL = domain
				+ v2
				+ '/tournament-service/getBasketBallTournamentTeamStatisticRanking/'
				+ tournamentId + '/' + suffix;
		return $.ajax({
			url : loadTeamMatchResultListURL,
			contentType : 'application/json;charset=utf8',
			dataType : 'json'
		})
	}

	Service.addCommentsToVideo = function(domain, sessionToken, suffix, params) {
		addCommentsToVideoURL = domain + v2
				+ '/userComments-service/saveUserCommentsToVideoItem/'
				+ sessionToken + '/' + suffix;
		return $.ajax({
			url : addCommentsToVideoURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.addSupportToVideoItem = function(domain, sessionToken, suffix,
			params) {
		addSupportToVideoItemURL = domain + v2
				+ '/userSupports-service/addSupportToVideoItem/' + sessionToken
				+ '/' + suffix;
		return $.ajax({
			url : addSupportToVideoItemURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}
	
	Service.addCommentsToMoment = function(domain, sessionToken, suffix, params) {
		addCommentsToVideoURL = domain + v2
				+ '/userComments-service/saveUserCommentsToMomentItem/'
				+ sessionToken + '/' + suffix;
		return $.ajax({
			url : addCommentsToVideoURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.addSupportToMomentItem = function(domain, sessionToken, suffix,
			params) {
		addSupportToVideoItemURL = domain + v2
				+ '/userSupports-service/addSupportToMomentItem/' + sessionToken
				+ '/' + suffix;
		return $.ajax({
			url : addSupportToVideoItemURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.saveShareRecord = function(domain, suffix, params) {
		saveShareRecordURL = domain + v2
				+ '/userShare-service/saveUserShareRecord/' + suffix;
		return $.ajax({
			url : saveShareRecordURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.removeUserSupportFromVideoItem = function(domain, sessionToken,
			suffix, params) {
		removeUserSupportFromVideoItemURL = domain + v2
				+ '/userSupports-service/removeUserSupportFromVideoItem/'
				+ sessionToken + '/' + suffix;
		return $.ajax({
			url : removeUserSupportFromVideoItemURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.removeUserSupportFromMomentItem = function(domain, sessionToken,
			suffix, params) {
		removeUserSupportFromVideoItemURL = domain + v2
				+ '/userSupports-service/removeUserSupportFromMomentItem/'
				+ sessionToken + '/' + suffix;
		return $.ajax({
			url : removeUserSupportFromVideoItemURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}	

	Service.saveAccessRecord = function(domain, suffix, params) {
		url = domain + v2 + '/system-service/saveAccessRecord/' + suffix;
		return $.ajax({
			url : url,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.domain = domain;

	Service.reservField = function(params) {
		return $.ajax({
			url : reserveFieldUrl,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	Service.urlParam = function(key) {
		return getUrlVars()[key];
	}

	Service.wechatPay = function(params) {
		console.log('params:', params)

		return $.ajax({
			url : weixinPayUrl,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	function getUrlVars() {
		var vars = [], hash;
		var hashes = window.location.href.slice(
				window.location.href.indexOf('?') + 1).split('&');
		for (var i = 0; i < hashes.length; i++) {
			hash = hashes[i].split('=')
			vars.push(hash[0])
			vars[hash[0]] = hash[1]
		}
		return vars
	}

	Service.fetchWeixinImageToQiqiu = function(domain, suffix, params) {
		fetchWeixinImageToQiqiuURL = domain + v2
				+ '/wechat-service/fetchWeixinImageToQiqiu/' + suffix;
		return $.ajax({
			url : fetchWeixinImageToQiqiuURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}
	
	Service.fetchWeixinImageToAliOSS = function(domain, suffix, params) {
		fetchWeixinImageToAliOSSURL = domain + v2
				+ '/wechat-service/fetchWeixinImageToAliOSS/' + suffix;
		
		return $.ajax({
			url : fetchWeixinImageToAliOSSURL,
			data : JSON.stringify(params),
			contentType : 'application/json;charset=utf8',
			type : 'post',
			dataType : 'json'
		})
	}

	return Service
})()

window.FService = FService
window.f$ === undefined && (window.f$ = FService)