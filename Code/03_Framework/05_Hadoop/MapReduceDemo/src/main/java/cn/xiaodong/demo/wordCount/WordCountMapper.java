package cn.xiaodong.demo.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @description: 单词计数mapper处理
 * @author: xiaodong
 * @create: 2021-02-16 11:44
 **/
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    // 计数的单词
    private Text text = new Text();
    // 单词的数量
    private IntWritable count = new IntWritable(1);

    /**
     * 数据处理业务逻辑
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

}
