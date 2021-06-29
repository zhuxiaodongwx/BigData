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


-- 建立一张分区表
create table stu_par
(id int,name string)
partitioned by (class string)
row format delimited fields terminated by '\t';

--向分区表中插入数据
load data local inpath '/opt/module/datas/student.txt' into table stu_par
partition (class = '01');
load data local inpath '/opt/module/datas/student.txt' into table stu_par
partition (class = '02');

--分区查询
select * from stu_par where  id = '1001';
select * from stu_par where class='01' and id = '1001';

-- 查看分区数量
show partitions stu_par;

-- 添加表分区
alter table  stu_par add partition (class = '06') partition (class = '07');

-- 删除表分区
alter table stu_par drop partition (class = '06'), partition(class='07');

-- 修复表分区
msck repair table stu_par;
-- 直接将数据load到进的分区
load data local inpath '/opt/module/datas/student.txt' into table stu_par partition (class = '05');

--建立一张带二级分区的表
-- 建立一张分区表
create table stu_par2
(id int,name string)
partitioned by (grade string,class string)
row format delimited fields terminated by '\t';

-- 查看分区数量
show partitions stu_par2;
-- 直接将数据load到进的分区
load data local inpath '/opt/module/datas/student.txt' into table stu_par2 partition (grade ='01',class = '01');
load data local inpath '/opt/module/datas/student.txt' into table stu_par2 partition (class = '02',grade ='02');