package cn.xiaodong.custom.compare.PhoneFlow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @description: 计算结果按照手机号，排序后的流量输出
 * @author: xiaodong
 * @create: 2021-02-16 17:56
 **/
public class PhoneFlowReducer extends Reducer<FlowSum, Text, Text, FlowSum> {

    private Text phoneNumber;

    @Override
    protected void reduce(FlowSum key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text value :values){
            // 手机号
            phoneNumber = value;

            context.write(phoneNumber, key);
        }

    }
}
