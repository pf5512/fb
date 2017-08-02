-- add create, update info into team_player table --

alter table team_player add 'create_at' date;
alter table team_player add 'create_by' VARCHAR(45);

alter table team_player add 'update_at' date;
alter table team_player add 'update_by' VARCHAR(45);