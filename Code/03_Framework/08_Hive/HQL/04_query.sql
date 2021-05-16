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
comm double comment '其他信息',
deptno int  comment '所属部门id')
row format delimited fields terminated by '\t';

-- 导入数据
load data local inpath '/opt/module/datas/dept.txt' into table dept;
load data local inpath '/opt/module/datas/emp.txt' into table emp;

// -------------------------数据查询-----------------------------------
select * from dept;
select ename,job,sal from emp;
-- 中文别名需要用反引号引起来
select ename AS `姓名`,job as job,sal from emp;

desc dept;
desc emp;