// ======================= Hive企业优化 ===========================

// =============== fetch抓取  ===============
set hive.fetch.task.conversion ;

// =============== 本地模式  ===============
-- 查看本地模式是否开启
set hive.exec.mode.local.auto;
-- 设置本地模式
set hive.exec.mode.local.auto = true;

