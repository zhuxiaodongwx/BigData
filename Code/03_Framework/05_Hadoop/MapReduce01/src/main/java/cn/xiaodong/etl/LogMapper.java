package cn.xiaodong.etl;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @description: ETL map处理类
 * @author: xiaodong
 * @create: 2021-03-31 06:12
 **/
public class LogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    /**
     * 计数器：通过的日志数
     */
    private Counter pass;
    /**
     * 计数器：失败的日志数
     */
    private Counter fail;
    /**
     * 计数器：总日志数
     */
    private Counter total;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 计数器初始化，获取计数器
        // 通过计数器组名，计数器名获取
        pass = context.getCounter("ETL","pass");
        fail = context.getCounter("ETL","fail");
        total = context.getCounter("ETL","total");
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split(" ");

        total.increment(1);
        // 每行日志超过8个字段，日志通过
        if (split.length > 16) {
            pass.increment(1);
            context.write(value, NullWritable.get());
        }else {
            fail.increment(1);
        }

    }
}
