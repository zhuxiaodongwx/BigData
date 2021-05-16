// -------------------------数据初始化-----------------------------------
create table if not exists dept(
deptno int comment '部门id',
dname string comment '部门名称',
loc int comment '部门地址'
)
row format delimited fields terminated by '\t';

create table if not exists emp(
empno int  comment '员工id',
ename string comment '员工姓名',
job string comment '岗位',
mgr int comment '上级主管',
hiredate string comment '雇佣时间',
sal double comment '薪资',
comm double comment '奖金',
deptno int  comment '所属部门id')
row format delimited fields terminated by '\t';

create table if not exists location(
loc int,
loc_name string
)
row format delimited fields terminated by '\t';

load data local inpath '/opt/module/datas/location.txt' into table location;


-- 导入数据
load data local inpath '/opt/module/datas/dept.txt' into table dept;
load data local inpath '/opt/module/datas/emp.txt' into table emp;

// -------------------------数据查询-----------------------------------
-- 全表查询
select * from dept;
-- 特定列查询
select ename,job,sal from emp;

-- 注意：中文别名需要用反引号引起来
select ename AS `姓名`,job as job,sal from emp;

-- 算数运算符
select ename as `姓名`,  sal +1000 as `涨薪后工资` from emp;

-- 拼接string，不能使用 + 运算，要使用函数
select  'NO' + empno as `新员工号` from emp;
select  concat('NO',empno)   as `新员工号` from emp;

-- UDF函数
-- 查询工资最大值
select max(sal) from emp;

-- 限制查询行数
select * from emp limit 10;

-- 条件查询
-- 查询工资大于2000
select * from emp where sal >=2000;
-- 查询没有奖金
select * from emp where comm is null or comm = 0;

-- 比较运算符
select null = null;  -- 结果：null
select  null <=> null;-- 结果：true
select  2 <>2;

-- 员工号以73开头的员工
select * from emp where empno like '73%';

-- 计算每个部门的平均工资
select avg(sal) as `平均工资`,deptno
from emp group by deptno;

-- 计算平均工资大于2000的部门
select avg(sal) as salavg,deptno
from emp group by deptno having salavg > 2000;

-- 查询员工号、员工名字、所在部门名称
select e.deptno ,e.ename,d.dname
from emp e inner join dept d on e.deptno = d.deptno;

-- 多表连接
select e.ename `员工名`,d.dname `部门名称`,l.loc_name `办公地点`
from emp e
inner join dept d on e.deptno = d.deptno
inner join location l on d.loc = l.loc;

-- 全局排序
select * from emp order by sal desc;
-- 全局排序,取前五
select * from emp order by sal desc limit 5;
-- 多条件排序
select * from emp order by deptno desc, job desc;

-- 按照两倍工资排序
select ename, sal*2 twosal from emp order by twosal;

-- 按照部分分区，按照薪资降序排列
select * from xiaodong.emp distribute by deptno sort by sal desc;