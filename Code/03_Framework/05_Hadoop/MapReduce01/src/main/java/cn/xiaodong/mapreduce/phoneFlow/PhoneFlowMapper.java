package cn.xiaodong.mapreduce.phoneFlow;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @description: 流量日志数据汇总
 * @author: xiaodong
 * @create: 2021-02-16 17:47
 **/
public class PhoneFlowMapper extends Mapper<LongWritable, Text, Text, FlowSum> {

    private FlowSum flowSum = new FlowSum();
    private Text text = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 日志的一行
        String lineString = value.toString();

        // 日志的每一行，用制表符分割
        String[] split = lineString.split("\t");

        long upFlow = Long.parseLong(split[split.length - 3]);
        long downFlow = Long.parseLong(split[split.length - 2]);

        // 用户手机号
        text.set(split[1]);
        flowSum.set(upFlow, downFlow);

        context.write(text, flowSum);
    }
}
