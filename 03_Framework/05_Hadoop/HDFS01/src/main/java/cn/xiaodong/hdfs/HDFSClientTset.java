package cn.xiaodong.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * @description: HDFS测试类
 * @author: xiaodong
 * @create: 2021-02-07 04:53
 **/
public class HDFSClientTset {

    /**
     * 测试文件上传
     */
    @Test
    public void testCopyFromLocal() throws IOException, InterruptedException {

        // 1.新建HDFS对象
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://hadoop102:8020"), new Configuration(), "atguigu");

        // 2、操作集群
        fileSystem.copyFromLocalFile(new Path("D:\\Users\\XiaoDong\\Downloads\\安装方法.txt"), new Path("/test"));

        // 3、关闭资源
        fileSystem.close();
    }


    /**
     * 测试文件上传
     */
    @Test
    public void testCopyFromLocal2() throws IOException, InterruptedException {
        // 测试设置
        Configuration configuration = new Configuration();
        // 设置文件的副本数
        configuration.set("dfs.replication","3");

        // 1.新建HDFS对象
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://hadoop102:8020"), configuration, "atguigu");

        // 2、操作集群
        fileSystem.copyFromLocalFile(new Path("C:\\Users\\XiaoDong\\Downloads\\AMD-Ryzen-Master.exe"), new Path("/test"));

        // 3、关闭资源
        fileSystem.close();
    }

}
