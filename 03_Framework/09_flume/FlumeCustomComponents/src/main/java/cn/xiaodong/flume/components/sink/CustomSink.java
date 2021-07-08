package cn.xiaodong.flume.components.sink;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义Sink
 * 需求：使用flume接收数据，并在Sink端给每条数据添加前缀和后缀，输出到控制台。前后缀可在flume任务配置文件中配置。
 *
 * @author: xiaodong
 * @create: 2021-07-08 05:54
 **/
public class CustomSink extends AbstractSink implements Configurable {

    /**
     * 获取数据，添加的后缀
     */
    String suffix;

    /**
     * 获取Logger，使用log4j存储数据
     */
    Logger logger = LoggerFactory.getLogger(CustomSink.class);

    @Override
    public Status process() throws EventDeliveryException {

        Status status = Status.READY;
        // 1.获取channel
        Channel channel = getChannel();

        // 2.从channel获取事务
        Transaction transaction = channel.getTransaction();
        try {
            transaction.begin();

            // 3.从channel中获取数据
            Event event = null;
            while (true) {
                event = channel.take();
                if (event != null) {
                    break;
                }
            }

            // 4.将数据写出
            logger.info(new String(event.getBody()) + suffix);

            // 5.事务提交
            transaction.commit();
        } catch (Exception e) {
            // 数据处理失败
            status = Status.BACKOFF;
            // 数物回滚
            transaction.rollback();
        } finally {
            // 最后，事务关闭
            transaction.close();
        }

        return status;
    }

    /**
     * 读取配置文件中的内容
     */
    @Override
    public void configure(Context context) {
        // 获取文件中指定的属性，如果没有配置，默认为“”
        suffix = context.getString("suffix", "");
    }
}
