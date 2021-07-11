#!/bin/bash

# 启动Kafka集群

# 前提：
# 1、集群之间需要配置ssh免密登录
# 2、网络名称已经修改，并且配置好host解析

echo "==========     启动Kafka集群     =========="

node=("hadoop102" "hadoop103" "hadoop104")

for i in ${node[@]}; do
  echo "========== $i =========="
  ssh $i '/opt/module/kafka/bin/kafka-server-start.sh -daemon /opt/module/kafka/config/server.properties'
  echo $?
done
