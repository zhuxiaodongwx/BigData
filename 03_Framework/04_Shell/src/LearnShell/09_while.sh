#!/bin/batch

# for循环
# 计算1~100的值

sum=0
i=0
while [ $i -le 100 ]; do
  sum=$(($sum + $i))
  i=$(($i + 1))
done

echo $sum
