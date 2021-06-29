package cn.xiaodong.join.mapJoin;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

/**
 * @description: OrderMapJoin map处理类
 * @author: xiaodong
 * @create: 2021-03-31 06:12
 **/
public class OrderMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    /**
     * 产品信息，key是产品id，value是产品名字
     */
    private HashMap product = new HashMap<String, String>();

    /**
     * 将商品文件从分布式文件缓存中读取出来，然后处理成KV值的形式，放到文件内存中
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取分布式缓存文件
         URI[] cacheFiles = context.getCacheFiles();

        // 开文件流
        Configuration conf;
        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        FSDataInputStream fsDataInputStream = fileSystem.open(new Path(cacheFiles[0]));

        // 将hdfs数据流转换成缓存字符流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream));
        String line;
        while (StringUtils.isNotEmpty(line = bufferedReader.readLine())) {
            String[] split = line.split("\t");
            product.put(split[0], split[1]);
        }

        IOUtils.closeStream(bufferedReader);

    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 将每一行的输入拆分
        String[] split = value.toString().split("\t");

        // 将每一行的第二列pid换成商品名称
        String pname = (String) product.get(split[1]);

        // 组装新的一行
        String newLine = split[0] + "\t" + pname + "\t" + split[2];

        // 返回处理结果
        context.write(new Text(newLine), NullWritable.get());
    }
}
