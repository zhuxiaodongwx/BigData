package cn.xiaodong.custom.group.orderMax;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @description: 订单类
 * @author: xiaodong
 * @create: 2021-03-29 20:06
 **/
public class OrderBean implements WritableComparable<OrderBean> {

    /**
     * 业务属性
     */
    // 订单编号
    private String orderId = "";
    // 商品编号
    private String productId = "";
    // 商品价格
    private double price = 0.00d;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 重写toString方法
     *
     * @return
     */
    @Override
    public String toString() {
        return orderId + "/t" + productId + "/t" + price;
    }

    /**
     * 比较方法
     * 先按照订单id排序，订单号相同，按照价格是降序排列
     * @param o
     * @return
     */
    @Override
    public int compareTo(OrderBean o) {
        int compareTo = this.orderId.compareTo(o.getOrderId());

        if (compareTo == 0) {
            return Double.compare(o.getPrice(), this.price);
        } else {
            return compareTo;
        }
    }

    /**
     * 序列化方法
     *
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        // 序列化写入String类型的数据，用writeUTF方法
        out.writeUTF(orderId);
        out.writeUTF(productId);
        out.writeDouble(price);
    }

    /**
     * 反序列化方法
     *
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.orderId = in.readUTF();
        this.productId = in.readUTF();
        this.price = in.readDouble();
    }

    /**
     * 空参构造方法
     */
    public OrderBean() {
    }
}
