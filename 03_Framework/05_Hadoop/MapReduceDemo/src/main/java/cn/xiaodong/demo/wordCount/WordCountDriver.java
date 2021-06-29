package cn.xiaodong.demo.wordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @description: MapReduce任务驱动类
 * @author: xiaodong
 * @create: 2021-02-16 11:35
 **/
public class WordCountDriver {

    /**
     * 设置任务属性
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws Exception {
        // 1.获取Job
        Job job = Job.getInstance(new Configuration());

        // 2.设置driver类、map类、reduce类
        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 3.设置map、reduce的输出
        // mapper的输出格式
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        // reducer的输出格式
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 4、设置文件输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 5.任务提交
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
