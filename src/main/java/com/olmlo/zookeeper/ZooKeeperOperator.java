package com.olmlo.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooKeeperOperator extends AbstractZooKeeper {
    static String HOSTS_PORT = "192.168.91.146:2181";

    private final static Logger log = LoggerFactory.getLogger(ZooKeeperOperator.class);

    /**
     * <b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过
     */
    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        /**
         * 此处采用的是CreateMode是PERSISTENT 表示The znode will not be automatically
         * deleted upon client's disconnect. EPHEMERAL 表示The znode will be
         * deleted upon the client's disconnect.
         */
        zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void getChild(String path) throws KeeperException, InterruptedException {
        try {
            List<String> list = this.zooKeeper.getChildren(path, false);
            if (list.isEmpty()) {
                log.debug(path + "中没有节点");
            } else {
                log.debug(path + "中存在节点");
                for (String child : list) {
                    log.debug("节点为：" + child);
                }
            }
        } catch (KeeperException.NoNodeException e) {
            throw e;
        }
    }

    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getData(path, false, null);
    }

}