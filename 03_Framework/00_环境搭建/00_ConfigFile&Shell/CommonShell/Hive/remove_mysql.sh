#!/bin/bash

# 删除已安装的Mysql服务

service mysql stop 2>/dev/null
service mysqld stop 2>/dev/null
rpm -qa | grep -i mysql | xargs -n1 rpm -e --nodeps 2>/dev/null
rpm -qa | grep -i mariadb | xargs -n1 rpm -e --nodeps 2>/dev/null
rm -rf /var/lib/mysql
rm -rf /usr/lib64/mysql
rm -rf /etc/my.cnf
rm -rf /usr/my.cnf