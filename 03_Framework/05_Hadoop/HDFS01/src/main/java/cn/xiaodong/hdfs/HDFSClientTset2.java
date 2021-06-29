package cn.xiaodong.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * @description: HDFS常用操作
 * @author: xiaodong
 * @create: 2021-02-07 05:35
 **/
public class HDFSClientTset2 {

    FileSystem fileSystem;

    /**
     * 设置HDFS
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Before
    public void before() throws IOException, InterruptedException {
        // 测试设置
        Configuration configuration = new Configuration();
        // 设置文件的副本数
        configuration.set("dfs.replication", "3");

//        configuration.set("dfs.client.block.write.replace-datanode-on-failure.policy","NEVER");
//        configuration.set("dfs.client.block.write.replace-datanode-on-failure.enable","true");

        // 1.新建HDFS对象
        fileSystem = FileSystem.get(URI.create("hdfs://hadoop102:8020"), configuration, "atguigu");
    }

    /**
     * 关闭HDFS资源
     *
     * @throws IOException
     */
    @After
    public void after() throws IOException {
        // 3、关闭资源
        fileSystem.close();
    }


    /**
     * 测试文件下载
     */
    @Test
    public void testGet() throws IOException, InterruptedException {
        // 2、操作集群
        fileSystem.copyFromLocalFile(new Path("D:\\Users\\XiaoDong\\Downloads"), new Path("/test/hello.txt"));
    }

    /**
     * 测试文件追加
     */
    @Test
    public void testAppend() throws IOException, InterruptedException {
        // 2、操作集群
        FSDataOutputStream appendStream = fileSystem.append(new Path("/test/hello2.txt"));
        appendStream.write("API文件追加测试".getBytes());
        // 使用Hadoop工具，关闭Hadoop的流
        IOUtils.closeStream(appendStream);
    }

    /**
     * 测试文件状态查看
     */
    @Test
    public void testLs() throws IOException, InterruptedException {
        // 2、操作集群
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/test"));
        for (FileStatus fileStatus : fileStatuses) {
            System.out.print(fileStatus.getPath());
            System.out.print(" | ");
            System.out.print(fileStatus.getOwner());
            System.out.println();
        }
    }

    /**
     * 测试文件快信息查看
     */
    @Test
    public void testLf() throws IOException, InterruptedException {
        // 2、操作集群
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(new Path("/"), true);

        while (locatedFileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus fileStatus = locatedFileStatusRemoteIterator.next();
            System.out.println("文件名称:" + fileStatus.getPath());
            // 文件块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                int i = 1;
                System.out.print("第" + i + "块 :");
                System.out.print("host：" + arraysToString(blockLocation.getHosts()));
                System.out.println();
                i++;
            }
        }
    }

    /**
     * 字符串数组打印
     *
     * @param array
     * @return
     */
    public static String arraysToString(String[] array) {
        String result = "";
        for (String s : array) {
            result += s;
            result += ",";
        }
        result = result.substring(0, result.length());
        return result;
    }

    /**
     * 文件的移动与重命名
     */
    @Test
    public void testRename() throws IOException {
        // 文件移动
        boolean result = fileSystem.rename(new Path("/test/ALI213.txt"), new Path("/"));
    }

}
