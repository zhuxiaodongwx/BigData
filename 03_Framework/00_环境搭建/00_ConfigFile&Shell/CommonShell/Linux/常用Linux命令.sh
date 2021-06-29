#!/bin/bash

# Linux命令（待整理）


# 查看文件占用大小
du -h --max-depth=1 *

# 查看centOS系统版本
cat /etc/redhat-release

# 批量安装RPM包
ls *.rpm | xargs -n1 sudo rpm -ivh

# 文件查找
find /opt/module -name 'my*'

find /opt/module -name 'guava-*'

/opt/module/hadoop-3.1.3/share/hadoop/common/lib/guava-27.0-jre.jar
/opt/module/hadoop-3.1.3/share/hadoop/hdfs/lib/guava-27.0-jre.jar
/opt/module/hive/lib/guava-27.0-jre.jar
/opt/module/tez/lib/guava-27.0-jre.jar

rm -rf /opt/module/hive/lib/guava-27.0-jre.jar
rm -rf /opt/module/tez/lib/guava-27.0-jre.jar