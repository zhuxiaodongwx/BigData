package cn.xiaodong.join.reduceJoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @description: ReduceJoin驱动类
 *
 * @author: xiaodong
 * @create: 2021-03-30 06:33
 **/
public class OrderReduceJoinDriver {

    /**
     * 设置任务属性
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws Exception {
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
//        job.setJarByClass(WordCountDriver.class);
        job.setJar("D:\\Code\\LearningBigData\\Code\\03_Framework\\05_Hadoop\\MapReduce01\\target\\MapReduce01-1.0-SNAPSHOT.jar");


        job.setMapperClass(OrderMapper.class);
        job.setReducerClass(OrderReducer.class);

        // 3.设置map、reduce的输出
        // mapper的输出格式
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);
        // reducer的输出格式
        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        // 设置分组比较器
        job.setGroupingComparatorClass(OrderBeanGroupingComparator.class);

        // 4、设置文件输入输出路径
        FileInputFormat.setInputPaths(job, new Path("/txt/reduceJoin"));
        FileOutputFormat.setOutputPath(job, new Path("/txt/reduceJoinOutput"));

        // 5.任务提交
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
