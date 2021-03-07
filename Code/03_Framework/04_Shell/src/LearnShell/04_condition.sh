#!/bin/batch

# shell条件判断

[ 22 -lt 23 ] && echo "22小于23"
[ 22 -gt 23 ] || echo "22大于23错误"

[ -r /root/learnShell/file/file.txt ] && "root用户对/root/learnShell/file/file.txt有读的权限"
[ -x /root/learnShell/file/file.txt ] || "root用户对/root/learnShell/file/file.txt没有执行的权限"

[ -f /root/learnShell/file/file.txt ] && "/root/learnShell/file/file.txt文件存在，是一个常规文件"