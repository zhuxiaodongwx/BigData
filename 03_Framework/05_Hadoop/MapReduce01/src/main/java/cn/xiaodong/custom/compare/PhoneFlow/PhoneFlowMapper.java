package cn.xiaodong.custom.compare.PhoneFlow;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @description: 按照手机流量排序，将文件中的数据读到bean中即可，无需其他处理
 * @author: xiaodong
 * @create: 2021-02-16 17:47
 **/
public class PhoneFlowMapper extends Mapper<LongWritable, Text, FlowSum, Text> {

    private FlowSum flowSum = new FlowSum();
    private Text phoneNumber = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 一行数据
        String lineString = value.toString();

        // 日志的每一行，用制表符分割
        String[] split = lineString.split("\t");

        // 手机号
        phoneNumber.set(split[0]);

        flowSum.setUpFlow(Long.parseLong(split[1]));
        flowSum.setDownFlow(Long.parseLong(split[2]));
        flowSum.setSumFlow(Long.parseLong(split[3]));

        context.write(flowSum, phoneNumber);
    }
}
