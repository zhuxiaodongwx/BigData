#!/bin/bash

# 显示当前运行的Java进程

# 前提：
# 1、集群之间需要配置ssh免密登录
# 2、网络名称已经修改，并且配置好host解析

for i in hadoop102 hadoop103 hadoop104
do
	echo "========    $i    ========"
	ssh $i "jps" | grep -v Jps
done

