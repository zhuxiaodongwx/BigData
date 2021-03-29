package cn.xiaodong.custom.group.orderMax;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapTask;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @description: mapper处理，将文本数据封装成bean对象
 * @author: xiaodong
 * @create: 2021-03-29 21:11
 **/
public class OrderMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

    private OrderBean orderBean = new OrderBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 拆分输入行
        String[] split = value.toString().split("\t");

        // 将输入的文本封装成bean对象
        orderBean.setOrderId(split[0]);
        orderBean.setProductId(split[1]);
        orderBean.setPrice(Double.parseDouble(split[2]));

        // 将处理结果写回
        context.write(orderBean, NullWritable.get());
    }
}
