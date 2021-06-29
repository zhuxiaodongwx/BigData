#!/bin/batch

# case语法

case $1 in
"1")
  echo "hello World!"
  ;;

"2")
  echo "hello 2!"
  ;;

"3")
  echo "hello 3"
  ;;

*)
  echo "hello *"
  ;;

esac
