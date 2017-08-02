drop table wechatConfig;

CREATE TABLE `wechatConfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,  
  `arenaId` int(11) NOT NULL,
  `appid` varchar(200) NOT NULL,
  `accessBackUrl` varchar(200) NOT NULL,
  `wechatAppSecret` varchar(200) NOT NULL,
  `spbillCreateIp` varchar(200) NOT NULL,
  `notifyUrl` varchar(200) NOT NULL,
  `tradeType` varchar(200) NOT NULL, 
  `body` varchar(200) NOT NULL,
  `attach` varchar(200) NOT NULL,
  `partnerId` varchar(200) NOT NULL,
  `partnerAppSecret` varchar(200) NOT NULL,
  `arenaHeaderTitle` varchar(200) NOT NULL,
  `arenalogUrl` varchar(200) NOT NULL,
  `arenaFooterTitle` varchar(200) NOT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
