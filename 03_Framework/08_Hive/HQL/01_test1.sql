-- 创建测试表
create table test(
name string,
friends array<string>,
children map<string, int>,
address struct<street:string, city:string>
)
-- 指定数据分隔符
row format delimited fields terminated by ','
collection items terminated by '_'
map keys terminated by ':'
lines terminated by '\n';

-- 查询语句
select name,friends[1],children['xiao song'],address.city from test
where name="songsong";

select * from default.test;

-- 强制转换
select '1'+ 2, cast('1'as int) + 2;

