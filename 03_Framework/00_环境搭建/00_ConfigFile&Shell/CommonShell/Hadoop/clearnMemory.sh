#!/bin/bash

# 释放Linux的内存缓存(此脚本未通过验证)

# 使用场景：大数据集群中，hadoop等程序计算过程中，读写了大量的文件，linux缓存到内存中。
# 计算完成后，内存不会立即释放，而是作为缓存用于加速文件读写。

# 在虚拟机使用场景中，这些缓存会大量占用宿主机的内存，不用的时候，应当释放掉。
# (此脚本实际未投入使用，Linux虚拟机内存释放后，vmware内存中占用并未释放)

# 前提：
# 1、集群之间需要配置ssh免密登录
# 2、网络名称已经修改，并且配置好host解析

# 输出释放前的内存占用情况

echo "========    【释放前】    ========"
for i in hadoop102 hadoop103 hadoop104
do
	echo "========    $i    ========"
	ssh $i "free -m"
done

echo "========    【内存释放处理中】    ========"
for i in hadoop102 hadoop103 hadoop104
do
  # 将所有未写的系统缓冲区写到磁盘中，防止数据丢失
	ssh $i "sync"

	# 释放缓存
	ssh $i "echo 1 > /proc/sys/vm/drop_caches"

  # 系统自动托管缓存
	# ssh $i "echo 0 > /proc/sys/vm/drop_caches"
done


echo "========    【释放后】    ========"
for i in hadoop102 hadoop103 hadoop104
do
	echo "========    $i    ========"
	ssh $i "free -m"
done

