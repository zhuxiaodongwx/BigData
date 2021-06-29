package cn.xiaodong.custom.group.orderMax;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @description: 获取订单中价格最贵的商品
 * @author: xiaodong
 * @create: 2021-03-29 21:25
 **/
public class OrderReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable> {

    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
//        // 获取价格最贵的商品
//        context.write(key, values.iterator().next());

        Iterator<NullWritable> iterator = values.iterator();
        int i = 0;
        // 获取每个订单中，价格前二的商品
        while (iterator.hasNext()){
            NullWritable nullWritable = iterator.next();
            context.write(key, nullWritable);

            i++;
            if (i>=2){
                break;
            }
        }

    }
}
