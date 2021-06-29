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

// ------------------------开窗函数---------------------------------
-- 数据准备
create table business(
name string,
orderdate string,
cost int
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
load data local inpath "/opt/module/datas/business.txt" into table business;

select * from business;
--（1）查询在2017年4月份购买过的顾客及总人数
select name, count(*) over ()
from business
where substring(orderdate, 1, 7) = '2017-04'
group by name;
--（2）查询顾客的购买明细及月购买总额(所有客户的)
select name,cost,orderdate,
sum(cost)  over (partition by substring(orderdate, 1, 7)  )
from business;
--（3）上述的场景, 将每个顾客的cost按照日期进行累加
select name,cost,orderdate,
sum(cost)  over (partition by name order by orderdate rows between unbounded preceding and current row )
from business;

--(拓展)求明细，以及每个月，有哪些客户来过
select name ,orderdate ,cost ,
collect_set(name)
     over (partition by substring(orderdate, 1, 7)  )
from business;

select name ,orderdate ,cost ,
concat_ws("," , collect_set(name)
over (partition by substring(orderdate, 1, 7)  ))
from business;

--（4）查看顾客上次的购买时间
select name ,orderdate ,cost,
       lag(orderdate,1,"1970-01-01") over (partition by name order by orderdate) lastorder
from business;

--（5）查询前20%时间的订单信息
select name ,orderdate ,cost,
       ntile(5) over (order by orderdate asc)
from business;

select *
from (
         select name,
                orderdate,
                cost,
                ntile(5) over (order by orderdate asc) n
         from business) t1
where n = 1;

// =========================排名================================
create table score(
name string,
subject string,
score int)
row format delimited fields terminated by "\t";
load data local inpath '/opt/module/datas/score.txt' into table score;

select * from score;

-- 计算每门学科成绩排名
select *,
       rank() over (partition by subject order by score desc) rank,
       dense_rank() over (partition by subject order by score desc) dense_rank ,
       row_number()  over (partition by subject order by score desc) row_number
from score;

-- 计算英语学科的成绩排名
select name, subject, score,
       rank() over (partition by subject order by score desc) rank,
       dense_rank() over (partition by subject order by score desc) dense_rank ,
       row_number()  over (partition by subject order by score desc) row_number
from score where subject = "英语" ;


// =========================时间计算================================
-- 获取当前时间
select current_date();

-- 今天开始，90天后的时间
select date_add(current_date(), 90);
-- 今天开始，90天前的时间
select date_sub(current_date(), 90);

-- 计算两个日期之间的时间差
select datediff("2021-05-20", "2021-08-15");

