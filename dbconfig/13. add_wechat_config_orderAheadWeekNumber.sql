alter table wechatConfig add orderAheadWeekNumber SMALLINT;
alter table wechatConfig add battleComments varchar(200);

update wechatConfig set orderAheadWeekNumber = 1 where arenaId = 52;
update wechatConfig set orderAheadWeekNumber = 2 where arenaId = 56;

update wechatConfig set battleComments = '提前24小时,未达成,场馆有权取消场次' where arenaId = 52;
update wechatConfig set battleComments = '提前24小时,未达成,场馆有权取消场次' where arenaId = 56;

