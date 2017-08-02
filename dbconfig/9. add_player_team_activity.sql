CREATE TABLE `player_team_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `team_player_id` int(11) NOT NULL,
  `event_id` varchar(20) NOT NULL,
  `type` varchar(20) NOT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_player_team_activity` (`team_player_id`),
  CONSTRAINT `fk_team_player_player_team_activity` FOREIGN KEY (`team_player_id`) REFERENCES `team_player` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;