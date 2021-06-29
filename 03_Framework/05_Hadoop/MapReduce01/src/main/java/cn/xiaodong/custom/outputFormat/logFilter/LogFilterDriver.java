package cn.xiaodong.custom.outputFormat.logFilter;

import cn.xiaodong.custom.group.orderMax.OrderBean;
import cn.xiaodong.custom.group.orderMax.OrderMapper;
import cn.xiaodong.custom.group.orderMax.OrderReducer;
import cn.xiaodong.custom.group.orderMax.OrderSortComparator;
import cn.xiaodong.custom.inputformat.MyInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

public class LogFilterDriver {
    /**
     * 需求：过滤输入的log日志，包含atguigu的网站输出到e:/atguigu.log，不包含atguigu的网站输出到e:/other.log。
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 设置连接Hadoop的用户名
        System.setProperty("HADOOP_USER_NAME", "atguigu");

        // 0、系统设置
        Configuration configuration = new Configuration();

        // 数据源使用HDFS
        configuration.set("fs.defaultFS", "hdfs://hadoop102:8020");
        // 数据源使用本地磁盘
        // configuration.set("fs.defaultFS", "file:///");

        // 此处写local表示程序在本地上运行，可以进行断点调试
        configuration.set("mapreduce.framework.name", "local");
        // 此处写yarn表示程序在集群上运行，yarn负责调度，无法进行断点调试
        // configuration.set("mapreduce.framework.name","yarn");

        configuration.set("mapreduce.app-submission.cross-platform", "true");
        configuration.set("yarn.resourcemanager.hostname", "hadoop103");

        // 1.获取Job
        Job job = Job.getInstance(configuration);

        // 2.设置driver类、map类、reduce类
        //job.setJarByClass(PhoneFlowDriver.class);
        job.setJar("D:\\Code\\LearningBigData\\Code\\03_Framework\\05_Hadoop\\MapReduce01\\target\\MapReduce01-1.0-SNAPSHOT.jar");


        // 设置自定义OutputFormat
        job.setOutputFormatClass(MyOutputFormat.class);

        // 4、设置文件输入输出路径
        FileInputFormat.setInputPaths(job, new Path("/txt/log.txt"));
        // 必须设置输出文件
        FileOutputFormat.setOutputPath(job, new Path("/txt/logFilter"));

        // 5.任务提交
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
