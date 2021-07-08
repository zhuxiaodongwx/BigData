package cn.xiaodong.flume.components.source;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Flume Source组件
 * 需求:使用flume接收数据，并给每条数据添加前缀，输出到控制台。前缀可从flume配置文件中配置。
 *
 * @author: xiaodong
 * @create: 2021-07-08 04:29
 **/
public class CustomSource extends AbstractSource implements Configurable, PollableSource {

    /**
     * 获取数据，添加的前缀
     */
    String prefix;

    /**
     * 核心方法，获取数据封装成event，将创建的event放入channel中。
     * 该方法会被循环调用。
     */
    @Override
    public Status process() throws EventDeliveryException {
        // 处理状态，默认为正常
        /**
         * Status是一个枚举类
         * READY：表示添加Event成功
         * BACKOFF：表示添加Event失败
         */
        Status status = Status.READY;

        // 1.读取数据
        List<Event> listEvents = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // 创建Event对象
            SimpleEvent event = new SimpleEvent();
            // 读取数据
            String message = "hello xiaodong!";
            // 给数据添加前缀
            event.setBody((prefix + message + i).getBytes());
            // 向集合中添加event
            listEvents.add(event);
        }

        // 2.将读取到的数据，发送到channel Processor
        try {
            // 获取channel Processor
            ChannelProcessor channelProcessor = getChannelProcessor();
            // 将event批量放入到channel Processor中
            channelProcessor.processEventBatch(listEvents);
        } catch (Exception e) {
            // 进行异常处理
            status = Status.BACKOFF;
        }

        // 返回操作状态
        return status;
    }


    /**
     * 读取配置文件中的内容
     */
    @Override
    public void configure(Context context) {
        // 获取文件中指定的属性，如果没有配置，默认为“”
        prefix = context.getString("prefix", "");
    }

    /**
     * 当source没有数据可以封装时，会让source所在的线程休息一会
     * （暂时不用）
     *
     * @return 线程休息的时间，单位毫秒
     */
    @Override
    public long getBackOffSleepIncrement() {
        return 2000L;
    }

    /**
     * 当source没有数据可以封装时，会让source所在的线程休息一会，休息的最大值
     * （暂时不用）
     *
     * @return 线程休息的时间，单位毫秒
     */
    @Override
    public long getMaxBackOffSleepInterval() {
        return 5000L;
    }
}
