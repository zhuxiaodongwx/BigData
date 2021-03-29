package cn.xiaodong.custom.outputFormat.logFilter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @description:将日志文件，输出到两个文件
 * @author: xiaodong
 * @create: 2021-03-30 05:21
 **/
public class MyRecordWriter extends RecordWriter<LongWritable, Text> {

    FSDataOutputStream fsAtguigu = null;
    FSDataOutputStream fsOther = null;

    public MyRecordWriter(TaskAttemptContext job) throws IOException {
        Configuration configuration = job.getConfiguration();
        // 获取任务的输出路径
        String outputDir = configuration.get(FileOutputFormat.OUTDIR);
        FileSystem fileSystem = FileSystem.get(configuration);

        fsAtguigu = fileSystem.create(new Path(outputDir+"/atguigu.log"));
        fsOther = fileSystem.create(new Path(outputDir+"/other.log"));

    }

    /**
     * 接收key value值，并按照值的不同，输出到不同的文件
     *
     * @param key   读取的偏移量
     * @param value 读取的内容（网址）
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void write(LongWritable key, Text value) throws IOException, InterruptedException {
        // 获取本行数据
        String line = value.toString() + "\n";

        // 判断网址信息中是否有atguigu
        if (line.contains("atguigu")) {
            fsAtguigu.write(line.getBytes());
        } else {
            fsOther.write(line.getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStream(fsOther);
        IOUtils.closeStream(fsAtguigu);
    }
}
