package cn.xiaodong.join.reduceJoin;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @description: 订单信息
 * @author: xiaodong
 * @create: 2021-03-30 06:24
 **/
public class OrderBean implements WritableComparable<OrderBean> {

    // 订单id
    private String id;
    // 商品id
    private String pid;
    // 商品数量
    private Double amount;
    // 商品名字
    private String pname;

    /**
     * 无参构造
     */
    public OrderBean() {
        this.id = "";
        this.pid = "";
        this.amount = 0.00d;
        this.pname = "";
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "\t" + pname + "\t" + amount;
    }

    /**
     * 排序策略
     * 先按照pid排序，订单id相同的，按照商品信息名降序排序（商品信息表的数据）
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(OrderBean o) {
        int i = this.pid.compareTo(o.getPid());
        if (i == 0) {
            int j = o.getPname().compareTo(this.pname);
            if (j == 0) {
                // 如果商品名字相同，按照订单id升序排列
                return this.id.compareTo(o.getId());
            } else {
                return j;
            }
        } else {
            return i;
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
        out.writeUTF(id);
        out.writeUTF(pid);
        out.writeDouble(amount);
        out.writeUTF(pname);
    }

    /**
     * 反序列化方法
     *
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readUTF();
        this.pid = in.readUTF();
        this.amount = in.readDouble();
        this.pname = in.readUTF();
    }
}
