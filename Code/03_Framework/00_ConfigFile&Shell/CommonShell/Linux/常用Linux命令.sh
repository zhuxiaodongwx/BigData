#!/bin/bash

# Linux命令（待整理）


# 查看文件占用大小
du -h --max-depth=1 *

# 查看centOS系统版本
cat /etc/redhat-release


# 批量安装RPM包
ls *.rmp | xargs -n1 sudo rpm -ivh