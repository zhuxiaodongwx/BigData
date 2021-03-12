#!/bin/batch

# for循环
# 计算输入值的和

for i in $*; do
  echo "输入是"$i
done
echo "=============================="

for i in $@; do
  echo "输入是"$i
done
echo "=============================="

for i in "$*"; do
  echo "输入是"$i
done

echo "=============================="
for i in "$@"; do
  echo "输入是"$i
done
