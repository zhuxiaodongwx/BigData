package cn.xiaodong.join.reduceJoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @description: mapper处理类
 * @author: xiaodong
 * @create: 2021-03-30 06:35
 **/
public class OrderMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

    /**
     * 当前正在处理的文件名字
     */
    String fileName = "";

    /**
     * 封装的结果
     */
    OrderBean orderBean = new OrderBean();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 文件切片
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        fileName = fileSplit.getPath().getName();
    }

    /**
     * 将文本输出，封装到bean对象
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 读取每一行的数据，进行拆分
        String[] split = value.toString().split("\t");

        // 按照输入文件的不同，进行封装成bean对象
        if ("order.txt".equals(fileName)) {
            orderBean.setId(split[0]);
            orderBean.setPid(split[1]);
            orderBean.setAmount(Double.parseDouble(split[2]));

            // 文件中没有的数据，要设置为空，否则序列化的时候，写入不进去，反序列化的时候读取错位
            // 或者new对象的时候，直接各个属性初始化为空
            orderBean.setPname("");
        } else {
            orderBean.setPid(split[0]);
            orderBean.setPname(split[1]);
            orderBean.setId("");
            orderBean.setAmount(0.00d);
        }

        // 将处理结果返回给框架
        context.write(orderBean, NullWritable.get());
    }
}
