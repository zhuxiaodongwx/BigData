package cn.xiaodong.custom.outputFormat.logFilter;

import cn.xiaodong.custom.inputformat.MyRecordReader;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @description:自定义数据输出
 * @author: xiaodong
 * @create: 2021-03-30 05:17
 **/
public class MyOutputFormat extends FileOutputFormat<LongWritable, Text> {

    /**
     * 返回一个处理数据的recordWriter
     * @param job
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public RecordWriter<LongWritable, Text> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        return new MyRecordWriter(job);
    }
}
