package cn.xiaodong.custom.compare.PhoneFlow;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @description: 流量统计计算类
 * @author: xiaodong
 * @create: 2021-02-16 17:35
 **/
public class FlowSum implements WritableComparable<FlowSum> {

    // 上传流量
    private long upFlow;

    // 下载流量
    private long downFlow;

    // 流量汇总
    private long sumFlow;

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    /**
     * 序列化方法
     *
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    /**
     * 反序列化方法
     *
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.upFlow = in.readLong();
        this.downFlow = in.readLong();
        this.sumFlow = in.readLong();
    }

    /**
     * 自定义set方法
     *
     * @param upFlow
     * @param downFlow
     */
    public void set(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    /**
     * 空参构造方法
     */
    public FlowSum() {
        super();
    }

    /**
     * 有参构造方法
     *
     * @param upFlow
     * @param downFlow
     */
    public FlowSum(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

    /**
     * 排序方法，按照总流量降序排列
     * @param o
     * @return
     */
    @Override
    public int compareTo(FlowSum o) {

//        if (this.sumFlow < o.sumFlow) {
//            return -1;
//        }
//        if (this.sumFlow == o.sumFlow) {
//            return 0;
//        }
//        if (this.sumFlow > o.sumFlow) {
//            return 1;
//        }
//
//        return 0;

        return Long.compare(o.sumFlow,this.sumFlow);
    }
}
