package com.atguigu.mapreduce.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @description:
 * @author: xiaodong
 * @create: 2021-02-16 11:44
 **/
public class WordCountReducer extends Reducer<Text, IntWritable,Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // 将处理结果加起来
        int sum = 0;
        for (IntWritable value : values){
            sum+= value.get();
        }

        // 返回处理结果
        context.write(key,new IntWritable(sum));
    }
}
