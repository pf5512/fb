-- 增加一个赛事和赛程的关系表 --
-- 1. 一个赛事有多个赛程，但同一种赛程只有一个 --
-- 2. 未开赛前，指定赛程可以重新生成 --
-- 3. 未开赛前，以新生成的赛程为准 --

--赛程设置  --
DROP TABLE IF EXISTS `tournament_match_schedule`;

CREATE TABLE `tournament_match_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tournament_id` int(11) NOT NULL,
  `match_schedule_type` varchar(11) NOT NULL  COMMENT '赛事类型(联赛或者杯赛)',
  `league_setting` varchar(45) COMMENT '联赛设置(单循环或双循环)',
  `match_cup_setting` varchar(45) COMMENT '杯赛设置',
  `name` varchar(1000) NOT NULL,
  `status` tinyint(2) DEFAULT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 增加 match_schedule_id 到 tournament_match --
alter table tournament_match add match_schedule_id int(11);

-- 增加 round 到 tournament_match --
alter table tournament_match add round int(4);

