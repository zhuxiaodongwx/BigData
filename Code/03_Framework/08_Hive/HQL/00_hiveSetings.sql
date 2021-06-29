// ================  Hive优化参数设置（仅针对MR引擎）  =====================

-- 设置本地模式
set hive.exec.mode.local.auto = true;

--打开任务并行执行
set hive.exec.parallel=true;
--同一个sql允许最大并行度，默认为8。
set hive.exec.parallel.thread.number=16;


set io.sort.mb=30;