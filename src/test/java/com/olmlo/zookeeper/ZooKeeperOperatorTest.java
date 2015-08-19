package com.olmlo.zookeeper;

import java.io.IOException;
import java.util.Arrays;

import org.apache.zookeeper.KeeperException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooKeeperOperatorTest {

    private final static Logger log = LoggerFactory.getLogger(ZooKeeperOperatorTest.class);

    @Test
    public void testCreate() {
        String data = "root";

        try {
            ZooKeeperOperator zkoperator = connect();
            zkoperator.create("/root", data.getBytes());
            Assert.assertTrue(data.equals(Arrays.toString(zkoperator.getData("/root"))));
            System.out.println(Arrays.toString(zkoperator.getData("/root")));
            byte[] data2 = new byte[] { 'a', 'b', 'c', 'd' };

            zkoperator.create("/root/child1", data2);
            System.out.println(Arrays.toString(zkoperator.getData("/root/child1")));

            zkoperator.create("/root/child2", data2);
            System.out.println(Arrays.toString(zkoperator.getData("/root/child2")));

            String zktest = "ZooKeeper的Java API测试";
            zkoperator.create("/root/child3", zktest.getBytes());
            log.debug("获取设置的信息：" + new String(zkoperator.getData("/root/child3")));

            zkoperator.close();
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetChild() {
        try {
            ZooKeeperOperator zkoperator = connect();
            System.out.println("节点孩子信息:");
            zkoperator.getChild("/root");

            zkoperator.close();
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    private static ZooKeeperOperator connect() throws IOException, InterruptedException {
        ZooKeeperOperator zkoperator = new ZooKeeperOperator();
        zkoperator.connect(ZooKeeperOperator.HOSTS_PORT);
        return zkoperator;
    }
}
