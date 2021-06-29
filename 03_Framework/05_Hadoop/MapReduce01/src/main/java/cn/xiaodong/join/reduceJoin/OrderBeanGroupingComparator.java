package cn.xiaodong.join.reduceJoin;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @description: OrderBean分组比较器
 * @author: xiaodong
 * @create: 2021-03-30 07:12
 **/
public class OrderBeanGroupingComparator extends WritableComparator {

    protected OrderBeanGroupingComparator() {
        super(OrderBean.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean orderBeanA = (OrderBean) a;
        OrderBean orderBeanB = (OrderBean) b;
        return orderBeanA.getPid().compareTo(orderBeanB.getPid());
    }
}
