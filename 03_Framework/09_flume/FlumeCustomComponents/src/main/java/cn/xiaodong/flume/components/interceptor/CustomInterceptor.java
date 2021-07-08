package cn.xiaodong.flume.components.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;

/**
 * Flume自定义拦截器
 * 判断字符串是否只含有数字
 * 如果event中的内容，只含有数字，在header中，添加key="type"，value="number"的键值
 * 如果event中的内容，不只含有数字，在header中，添加key="type"，value="else"的键值
 *
 * @author: xiaodong
 * @create: 2021-06-30 07:37
 **/
public class CustomInterceptor implements Interceptor {

    /**
     * 拦截器初始化
     */
    @Override
    public void initialize() {

    }

    /**
     * channelProcessor调用拦截器时会调用该方法并将event传过来
     * 为每个event中的header添加key-value
     *
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {
        byte[] body = event.getBody();
        String bodyString = new String(body);
        if (isDigit(bodyString)) {
            // 向header中添加type = number
            event.getHeaders().put("type", "number");
        } else {
            // 向header中添加type = else
            event.getHeaders().put("type", "else");
        }
        return null;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        //从集合中遍历每一个event
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    /**
     * 拦截器结束时调用，用于关闭资源
     */
    @Override
    public void close() {

    }

    /**
     * 判断字符串是否只有数字
     *
     * @param str 要判断字符串
     * @return true：只含有数字；false：不满足条件
     */
    public static boolean isDigit(String str) {
        String regex = "^[0-9]+$";
        return str.matches(regex);
    }

    /**
     * 静态内部类
     * 1、静态内部类
     * 2、权限是public
     */
    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new CustomInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
