// ======================= Hive企业优化 ===========================

// =============== fetch抓取  ===============
set hive.fetch.task.conversion ;

// =============== 本地模式  ===============
-- 查看本地模式是否开启
set hive.exec.mode.local.auto;
-- 设置本地模式
set hive.exec.mode.local.auto = true;


// =============== 并行执行  ===============
--打开任务并行执行
set hive.exec.parallel=true;
--同一个sql允许最大并行度，默认为8。
set hive.exec.parallel.thread.number=16;
