-- 区分是否审核通过
alter table tournament add status VARCHAR(20);

-- 行政区域
alter table tournament add address VARCHAR(200);

-- 行政区域id
alter table tournament add addressId VARCHAR(200);

-- 报名开始时间
alter table tournament add register_start_dt DATETIME;

-- 报名结束时间
alter table tournament add register_end_dt DATETIME;

-- 比赛类型：5人制等
alter table tournament add match_type VARCHAR(20);

-- 创建者
alter table tournament add create_by VARCHAR(45);

-- 创建时间
alter table tournament add create_dt DATETIME;
