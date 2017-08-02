CREATE TABLE `player_app_tokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `token` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

ALTER TABLE player_app_tokens
ADD CONSTRAINT uc_player_app_tokens_p_t_t UNIQUE (player_id, type)
