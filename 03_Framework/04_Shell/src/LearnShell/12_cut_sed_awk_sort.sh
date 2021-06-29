#!/bin/batch

# cut工具

# 以：为分隔符，获取第二行
cut -f 2 -d : /root/learnShell/file/12.txt

# 获取每行的第一个字符
cut -c 1 /root/learnShell/file/12.txt

# ----------- sed工具 -----------

# 每一行后面添加xiaodong
sed -e 'axiaodong' 12.txt

# 在第二行后面添加xiaodong
sed -e '2axiaodong' 12.txt

# 将所有的81替换为99
sed -e 's/81/99/g' 12.txt

# 将含有81的所有行删除
sed -e '/81/d' 12.txt

# ----------- awk工具 -----------

#以：为切割符号，将第三列的数据加10并输出
awk -F : -v i=10 '{print $3+i}' 12.txt

awk -F : '/^dbus/{print"这一行以root开头！第一列的值是："$1} /^x/{print"这一行以b开头，第一行是"$1}' 12.txt

# 数据处理前先打印开始处理，处理完成后打印处理结束
# 以：拆分每一列，打印输出第1列和第3列
awk -F : 'BEGIN{print "开始处理"} {print "第一列是："$1 "第三列的值是" $3}  END{print "处理结束"}' 12.txt

# 输出文件名，每一行的行数，列数
awk -F : 'BEGIN {print "文件名是："FILENAME} {print "当前行数"NR} {print "当前列数"NF}' 12.txt


# ----------- sort工具 -----------
# 将文件以：分割的第三列，按照数值大小，倒序排列
sort -n -r -t : -k 3 12.txt