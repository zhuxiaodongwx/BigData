// ---------------------------Part 1 、数据库的定义-----------------------------------------

 -- 创建数据库小东
create database if not exists xiaodong
comment "hive databse of xiaodoong"
location "/user/hive/database/xiaodong.db"
with dbproperties ("aaa"="bbb");

--显示所有数据库
show databases ;

-- 查看数据库详情
desc database xiaodong;
-- 查看数据库详情 可查看数据库parameter属性
desc database extended xiaodong;

-- 数据库切换
use default;
use xiaodong;

--数据库删除
drop database if exists xiaodong cascade ;

-- 修改数据库的元数据信息
alter database xiaodong set dbproperties ("createTime"="20210512");

// ---------------------------Part 2、表的定义-----------------------------------------

-- 表创建
create table test
(id int comment "ID",name string comment "StudentName") -- 列定义、列备注
comment "TestTable" -- 表备注
row format delimited fields terminated by '\t' --每一行的分隔符
location "/user/hive/database/xiaodong.db/test" -- 数据存储位置
tblproperties ("aaa"="bbb"); -- 表属性

-- 查看所有表
show tables ;
-- 查看表属性
desc test;
-- 查看表详细属性
desc formatted test;

-- 查询表数据
select * from test;
select * from xiaodong.test;

--表重命名
alter table test rename to student;

-- 更新列
alter  table  student change id stuid string comment "student id";
desc student;
-- 增加列
alter table student add columns (age int comment "student age");

-- 表删除
drop table student;

-- 将表修改为外部表
alter table test set tblproperties ("EXTERNAL"="TRUE");
-- 查看内部表外部表
desc formatted test;
