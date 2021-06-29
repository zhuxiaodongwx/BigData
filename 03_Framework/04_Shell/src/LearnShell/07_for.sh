#!/bin/batch

# for循环
# 计算1~100所有整数的和

sum=0
for ((i = 1; i <= 100; i++)); do
  sum=$(($sum + $i))
done

echo $sum
