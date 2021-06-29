package cn.xiaodong.join.reduceJoin;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @description: reducer处理逻辑
 * @author: xiaodong
 * @create: 2021-03-30 06:39
 **/
public class OrderReducer extends Reducer<OrderBean, NullWritable,OrderBean,NullWritable> {

    /**
     * 商品名字
     */
    String pname = "";

    OrderBean orderBean = new OrderBean();


    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        Iterator<NullWritable> iterator = values.iterator();
        iterator.next();

        // 输入文件的第一个是商品信息
        pname = key.getPname();

        // 处理订单信息，设置pname
        while (iterator.hasNext()){
            NullWritable next = iterator.next();
            orderBean = key;
            orderBean.setPname(pname);

            context.write(orderBean, NullWritable.get());
        }
    }
}
