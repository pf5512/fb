CREATE TABLE `footballer`.`event` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`type` VARCHAR(45) NOT NULL,
`start_date` DATETIME NOT NULL,
`end_date` DATETIME NOT NULL,
`field_id` BIGINT NOT NULL,
`player_count` INT NOT NULL,
`reply_end_date` DATETIME NOT NULL,
`owner_id` BIGINT NOT NULL,
PRIMARY KEY (`id`));

CREATE TABLE `footballer`.`event_invitation` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`event_id` BIGINT NOT NULL,
`player_id` BIGINT NOT NULL,
`status` VARCHAR(45) NOT NULL,
PRIMARY KEY (`id`),
CONSTRAINT `event_player_id` UNIQUE (`event_id`,`player_id`)
);