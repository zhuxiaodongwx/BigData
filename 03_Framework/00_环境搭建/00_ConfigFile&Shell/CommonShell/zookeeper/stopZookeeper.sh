#!/bin/bash

# 停止Zookeeper集群

# 前提：
# 1、集群之间需要配置ssh免密登录
# 2、网络名称已经修改，并且配置好host解析

echo "==========     停止Zookper集群     =========="

node=("hadoop102" "hadoop103" "hadoop104")

for i in ${node[@]}; do
  echo "========== $i =========="
  ssh $i "zkServer.sh stop"
done
