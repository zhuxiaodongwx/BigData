package cn.xiaodong.mapreduce.phoneFlow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @description: 手机流量计算数据汇总
 * @author: xiaodong
 * @create: 2021-02-16 17:56
 **/
public class PhoneFlowReducer extends Reducer<Text,FlowSum,Text,FlowSum> {

    @Override
    protected void reduce(Text key, Iterable<FlowSum> values, Context context) throws IOException, InterruptedException {
        // 上传流量汇总
        long sumUpFlow = 0;
        // 下载流量汇总
        long sumDownFlow = 0;

        for (FlowSum flowSum:values){
            sumUpFlow += flowSum.getUpFlow();
            sumDownFlow += flowSum.getDownFlow();
        }
        FlowSum flowSum = new FlowSum(sumUpFlow,sumDownFlow);

        // 协会计算结果
        context.write(key,flowSum);
    }
}
