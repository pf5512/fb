-- table: player_deposit_account: 球员资金账户 --
CREATE TABLE `player_deposit_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `balances` NUMERIC(6, 2) NOT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

ALTER TABLE player_deposit_account
ADD CONSTRAINT uc_pid_tid UNIQUE (player_id, team_id)

--- table: player_balance_lines: 球员资金明细 --
CREATE TABLE `player_balance_lines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_deposit_account_id` int(11) NOT NULL,
  `fee_type` varchar(20) NOT NULL,
  `pay_method` varchar(20) NOT NULL,
  `fee` NUMERIC(6, 2) NOT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_player_deposit_account_lines` FOREIGN KEY (`player_deposit_account_id`) REFERENCES `player_deposit_account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

