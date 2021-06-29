package cn.xiaodong.custom.group.orderMax;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @description: 分组比较器
 * @author: xiaodong
 * @create: 2021-03-29 00:47
 **/
public class OrderSortComparator extends WritableComparator {

    /**
     * 自定义排序方法，需要一个默认的无参构造方法，否则框架初始化会报错
     */
    protected OrderSortComparator() {
        super(OrderBean.class, true);
    }

    /**
     * 比较方法
     * @param a
     * @param b
     * @return
     */
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean oba = (OrderBean) a;
        OrderBean obb = (OrderBean) b;

        return oba.getOrderId().compareTo(obb.getOrderId());
    }
}
