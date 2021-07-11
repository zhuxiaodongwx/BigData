#!/bin/bash

# 启动Hive服务

# 前提：
# 1、集群之间需要配置ssh免密登录
# 2、网络名称已经修改，并且配置好host解析

echo "==========     启动MySQL服务     =========="
ssh hadoop102 "sudo systemctl start mysqld"

echo "==========     MySQL启动结果     =========="
# todo：

echo "==========     启动Hive     =========="
# todo：



echo "==========     Hive启动结果     =========="
# todo：












