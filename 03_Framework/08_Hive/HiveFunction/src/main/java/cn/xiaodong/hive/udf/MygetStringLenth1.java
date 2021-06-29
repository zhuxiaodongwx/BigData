package cn.xiaodong.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 老接口，给一个字符串，返回字符串长度
 * @author: xiaodong
 * @create: 2021-05-20 07:03
 **/
public class MygetStringLenth1 extends UDF {

    /**
     * 老接口，给一个字符串，返回字符串长度
     * @param input 输入字符串
     * @return 字符串的长度
     */
    public int evalute(String input){

        if (input == null) {
            return 0;
        }
        return input.length();

    }
}
