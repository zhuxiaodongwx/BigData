package com.atguigu.mapreduce.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @description:
 * @author: xiaodong
 * @create: 2021-02-16 11:44
 **/
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    public WordCountMapper() {
        super();
    }

    /**
     * 任务开始前，执行前期处理
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    // 计数的单词
    private Text text = new Text();
    // 单词的数量
    private IntWritable count = new IntWritable(1);

    /**
     * 数据处理业务逻辑
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1、获取每一行的值
        String textValue = value.toString();

        // 2、将每一行，使用空格，拆分成各个单词
        String[] textSplit = textValue.split(" ");

        // 3、返回单词的统计个数
        for (String word : textSplit) {
            text.set(word);
            context.write(text, count);
        }
    }

    /**
     * 任务完成后，执行清理
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
    }

    /**
     * 执行调度顺序
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void run(Context context) throws IOException, InterruptedException {
        super.run(context);
    }
}
