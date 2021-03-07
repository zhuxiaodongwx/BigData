#!/bin/batch

# 条件判断

# 要求
# 根据输入的值判断
# 输入1   输出小东帅呆了
# 输入2   输出小西美爆了

if [ $1 -eq "1" ]; then
  echo "小东帅呆了"
elif [ $1 -eq "2" ]; then
  echo "小西美爆了"
else
  echo "我傻了"
fi
