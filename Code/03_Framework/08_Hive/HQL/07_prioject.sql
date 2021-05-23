// ===================  Hive项目实战  =================

--统计硅谷影音视频网站的常规指标，各种TopN指标：
--统计视频观看数Top10
--统计视频类别热度Top10
--统计视频观看数Top20所属类别
--统计视频观看数Top50所关联视频的所属类别Rank
--统计每个类别中的视频热度Top10
--统计每个类别视频观看数Top10

// ===================  数据准备  =================
--video表
create external table video_ori(
    videoId string,
    uploader string,
    age int,
    category array<string>,
    length int,
    views int,
    rate float,
    ratings int,
    comments int,
    relatedId array<string>)
row format delimited fields terminated by "\t"
collection items terminated by "&"
location '/user/hive/database/gulivideo/video_ori';

--user表
create external table user_ori(
    uploader string,
    videos int,
    friends int)
row format delimited fields terminated by "\t"
location '/user/hive/database/gulivideo/user_ori';

--video_orc表
create table video_orc(
    videoId string,
    uploader string,
    age int,
    category array<string>,
    length int,
    views int,
    rate float,
    ratings int,
    comments int,
    relatedId array<string>)
stored as orc
tblproperties("orc.compress"="SNAPPY");

--user_orc表
create table user_orc(
    uploader string,
    videos int,
    friends int)
stored as orc
tblproperties("orc.compress"="SNAPPY");

// 查看文件导入的数据
select * from user_ori;
select * from video_ori;

--从外部表中插入数据
insert into table video_orc select * from video_ori;
insert into table user_orc select * from user_ori;