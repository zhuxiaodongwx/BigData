package cn.xiaodong.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Zookeeper 客户端API调用
 *
 * @author: xiaodong
 * @create: 2021-04-05 16:33
 **/
public class APIDemoTest {

    ZooKeeper zooKeeper = null;

    /**
     * 连接Zookeeper集群
     *
     * @throws IOException
     */
    @Before
    public void Before() throws IOException {

        // 1、连接Zookeeper节点
        // Zookeeper连接设置
        String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
        // 会话超时时间
        int sessionTimeOut = 2000;

        Watcher watcher = new Watcher() {
            /**
             * 回调函数
             * @param event
             */
            @Override
            public void process(WatchedEvent event) {
//                System.out.println("回调函数");
            }
        };
        zooKeeper = new ZooKeeper(connectString, sessionTimeOut, watcher);
    }

    /**
     * 测试完成后，关闭Zookeeper集群
     *
     * @throws InterruptedException
     */
    @After
    public void After() throws InterruptedException {
        zooKeeper.close();
    }


    /**
     * 创建Zookeeper节点
     */
    @Test
    public void createTest() throws KeeperException, InterruptedException {
        zooKeeper.create("/APITest", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create("/APITest/hadoop", "hadoopDataNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 查询子节点
     */
    @Test
    public void lsTest() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/APITest", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("自定义回调函数");
            }
        });

        for (String child : children) {
            System.out.println("子节点是：" + child);
        }

        // 主线程等待
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 查询子节点
     */
    @Test
    public void getTest() throws KeeperException, InterruptedException, IOException {

        Stat stat = new Stat();
        byte[] data = zooKeeper.getData("/APITest/hadoop", false, stat);

        System.out.println("data = ");
        System.out.write(data);
        System.out.println();

        System.out.println(stat.getVersion());
        System.out.println(stat.getCtime());

    }

    /**
     * 查询节点状态
     */
    @Test
    public void statTest() throws KeeperException, InterruptedException {
        // 获取节点信息
        Stat exists = zooKeeper.exists("/APITest", false);

        if (exists == null) {
            System.out.println("节点不存在");
        } else {
            System.out.println("节点信息如下：");
            System.out.println(exists);
        }

    }

    /**
     * 设置节点的状态
     */
    @Test
    public void setTest() throws KeeperException, InterruptedException {
        // 获取节点信息
        Stat exists = zooKeeper.exists("/APITest", false);

        if (exists != null) {
            // 乐观锁
            // 看到的数据版本，与实际修改的数据版本一致，才能修改成功
            zooKeeper.setData("/APITest", "2233".getBytes(), exists.getVersion());
        }

    }

    /**
     * 节点删除
     */
    @Test
    public void deleteTest() throws KeeperException, InterruptedException {
        String path = "/APITest/hadoop";
        // 获取节点信息
        Stat exists = zooKeeper.exists(path, false);

        if (exists != null) {
            // 乐观锁
            // 看到的数据版本，与实际修改的数据版本一致，才能修改成功
            zooKeeper.delete(path, exists.getCversion());
        }
    }
}
