package cn.xiaodong.custom.inputformat;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * 自定义InputFormat
 *
 * 继承了FileInputFormat，FileInputFormat已经实现了getSplits()切片方法
 * 本类只实现createRecordReader方法即可
 */
public class MyInputFormat extends FileInputFormat<Text, BytesWritable> {

    /**
     * 返回一个自定义RecordReader
     * @param split
     * @param context
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new MyRecordReader();
    }
}
