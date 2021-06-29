package cn.xiaodong.custom.compare.PhoneFlow;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @description: FlowSum类作为Key专用的比较器
 * @author: xiaodong
 * @create: 2021-03-29 00:47
 **/
public class FlowSumSortComparator extends WritableComparator {

    /**
     * 自定义排序方法，需要一个默认的无参构造方法，否则框架初始化会报错
     */
    protected FlowSumSortComparator() {
        // 用要排序的bean java类初始化
        super(FlowSum.class, true);
    }

    /**
     * 比较方法
     * @param a
     * @param b
     * @return
     */
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        FlowSum fsa = (FlowSum) a;
        FlowSum fsb = (FlowSum) b;

        return Long.compare(fsa.getSumFlow(), fsb.getSumFlow());
    }
}
