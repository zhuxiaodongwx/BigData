--用查询结果建表
create table stu_par4
    as select * from stu_par;


--将查询结果，插入到已有的表中
select * from stu_par2;
insert into stu_par2
    select id,name,class from stu_par where class = '01';

-- 建立一张分区表
create external table stu_par3
(id int,name string)
row format delimited fields terminated by '\t'
location '/user/hive/database/xiaodong.db/stu_par3';

select * from stu_par3;

drop table stu_par3;

--将数据insert导出
insert overwrite  directory '/user/hive/export/student'
row format delimited fields terminated by '\t'
    select * from stu_par;

-- 数据导出
export table stu_par to '/user/hive/export/stu_par';
-- 数据导入
import table stu_par4 from '/user/hive/export/stu_par';

truncate table stu_par4;
