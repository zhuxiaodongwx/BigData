#!/bin/batch

# 函数求和

function sum() {
  sum=0
  sum=$(($1 + $2))
  echo $sum
  return 1
}

read -p "请输入第一个数：" num1
read -p "请输入第二个数：" num2

# 函数调用
sum $num1 $num2
