package com.phantom.dao.conn;

import com.phantom.util.common.PropertiesUtils;
import com.phantom.util.common.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/6
 * @Package: com.phantom.dao.conn
 * @Description:
 * @ModifiedBy:
 */
@Repository("hbaseConn")
public class HbaseConn {

    private static Map<String, String> configMap = PropertiesUtils.getPropertiesMap("/hadoop.properties");

    private static String quorem;

    private static String rootdir;

    private static final String CLIENT_PORT = "2181";

    private HBaseAdmin admin;

    private Configuration configuration = null;

    private Connection connection = null;

    private static class SingletonHolder{
        private static final HbaseConn INSTANCE = new HbaseConn();
    }

    private HbaseConn(){
        init();
    }

    private void init() {
        try {
            configMap = PropertiesUtils.getPropertiesMap("/hadoop.properties");

            configuration = HBaseConfiguration.create();
            /**
             * 获取配置文件hadoop.properties中的配置信息
             */
            //Map<String, String> map = PropertiesUtils.getPropertiesMap("/hadoop.properties");

            quorem = StringUtils.trim(configMap.get("hbase.zookeeper.quorum"));
            rootdir = StringUtils.trim(configMap.get("hbase.rootdir"));
            System.out.println("quorem:" + quorem);
            System.out.println("rootdir:" + rootdir);


            configuration.set("hbase.zookeeper.quorum", quorem);
            configuration.set("hbase.rootdir", rootdir);
            configuration.set("zookeeper.znode.parent", "/hbase-unsecure");
            configuration.set("hbase.zookeeper.property.clientPort", CLIENT_PORT);
            configuration.setInt("hbase.rpc.timeout",20000);


            configuration.setInt("hbase.client.operation.timeout",30000);


            configuration.setInt("hbase.client.scanner.timeout.period",200000);
            connection = ConnectionFactory.createConnection(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static final Connection getConn() {
        System.out.println("HbaseConn.getConn()");
        return SingletonHolder.INSTANCE.connection;
    }
}
