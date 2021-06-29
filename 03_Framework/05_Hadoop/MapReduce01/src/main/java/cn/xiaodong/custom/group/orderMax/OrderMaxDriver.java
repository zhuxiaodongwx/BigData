package cn.xiaodong.custom.group.orderMax;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @description: 获取订单中价格最高的两个商品
 * @author: xiaodong
 * @create: 2021-02-16 18:02
 **/
public class OrderMaxDriver {

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

        job.setMapperClass(OrderMapper.class);
        job.setReducerClass(OrderReducer.class);

        // 自定义排序比较器
//        job.setSortComparatorClass(FlowSumSortComparator.class);

        // 自定义分组比较器
        job.setGroupingComparatorClass(OrderSortComparator.class);

//        // 设置mapTask的数量
//        job.setNumReduceTasks(5);
//        // 指定分区策略
//        job.setPartitionerClass(MyPartitioner.class);

        // 3.设置map、reduce的输出
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        // 4、设置文件输入输出路径
        FileInputFormat.setInputPaths(job, new Path("/txt/orderMax.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/txt/orderMax"));

        // 5.任务提交
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
