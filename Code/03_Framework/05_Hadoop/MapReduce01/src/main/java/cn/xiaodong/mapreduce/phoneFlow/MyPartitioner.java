package cn.xiaodong.mapreduce.phoneFlow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @description: 自定义分区策略
 * @author: xiaodong
 * @create: 2021-03-23 06:51
 **/
public class MyPartitioner extends Partitioner<Text, FlowSum> {

    /**
     * 对每一条KV对，返回对应的分区号（必须实现的方法）
     *
     * @param text          key值 手机号
     * @param flowSum       value值 流量数据
     * @param numPartitions 总分区的数量
     * @return 这条数据的分区号
     */
    @Override
    public int getPartition(Text text, FlowSum flowSum, int numPartitions) {

        // 手机号前三位
        String phone_head = text.toString().substring(0, 3);
        switch (phone_head) {
            case "136":
                return 0;
            case "137":
                return 1;
            case "138":
                return 2;
            case "139":
                return 3;
            default:
                return 4;
        }
    }
}
