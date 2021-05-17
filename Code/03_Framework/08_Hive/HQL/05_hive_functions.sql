// -------------------------Hive函数-----------------------------------

-- 查看系统自带的函数
show functions;
-- 函数模糊查询
show functions like '*date*';
show functions like '*collect*';
-- 查看函数的用法
desc function collect_list;
desc function concat;
-- 查看函数的详细用法
desc function extended concat_ws;

// ------------------------NVL函数----------------------------------
-- 如果奖金字段为null，赋值为0
select nvl(comm,0) from emp;

// ------------------------case with----------------------------------
-- 数据准备
create table emp_sex(
name string,
dept_id string,
sex string)
row format delimited fields terminated by "\t";
load data local inpath '/opt/module/datas/emp_sex.txt' into table emp_sex;

select * from emp_sex;

-- 统计不同部门，总人数，男生女生各多少人
select dept_id                                  as `部门id`,
       count(*)                                 as `总人数`,
       sum(case sex when '男' then 1 else 0 end) as `男生人数`,
       sum(case sex when '女' then 1 else 0 end) as `女生人数`
from emp_sex
group by dept_id;

-- 查看员工姓名，及其职位
select ename               as `姓名`,
       case job
           when 'CLERK' then '记账员'
           when 'SALESMAN' then '销售'
           when 'MANAGER' then '主管'
           when 'ANALYST' then '分析师'
           else '其他职位' end as `职位`
from emp;

// ------------------------行转列----------------------------------
-- 数据准备
create table person_info(
name string,
constellation string,
blood_type string)
row format delimited fields terminated by "\t";
load data local inpath "/opt/module/datas/constellation.txt" into table person_info;

select * from person_info;

-- 统计相同血型、星座的各有多少人
select constellation, blood_type, count(*)
from person_info
group by constellation, blood_type;

select concat(constellation,',',blood_type) ,name
from person_info;

-- 星座、血型一样的人，归在一起
select t1.base, concat_ws('|', collect_set(t1.name))
from (select concat(constellation, ',', blood_type) base, name
      from person_info) t1
group by t1.base;


// ------------------------列转行----------------------------------
-- 数据准备
create table movie_info(
    movie string,
    category string)
row format delimited fields terminated by "\t";
load data local inpath "/opt/module/datas/movie.txt" into table movie_info;

select * from movie_info;

select explode(split(category, ','))
from movie_info;

desc function extended split;
desc function extended explode;

--将电影分类中的标签展开
select movie, category_table.category_single
from movie_info
         lateral view explode(split(category, ',')) category_table as category_single;

-- 根据标签类型，查询所有的电影
select m.category_single, concat_ws(',', collect_set(movie))
from (
         select movie, category_table.category_single
         from movie_info
                  lateral view explode(split(category, ',')) category_table as category_single
     ) m
group by m.category_single
;

--
explain select m.category_single, concat_ws(',', collect_set(movie))
from (
         select movie, category_table.category_single
         from movie_info
                  lateral view explode(split(category, ',')) category_table as category_single
     ) m
group by m.category_single
;