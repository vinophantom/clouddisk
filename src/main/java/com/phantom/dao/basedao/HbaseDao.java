package com.phantom.dao.basedao;

import com.phantom.dao.conn.HbaseConn;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/8
 * @Package: com.phantom.dao.basedao
 * @Description:
 * @ModifiedBy:
 */
@Component("hbaseDao")
public class HbaseDao {

    private static Log logger = LogFactory.getLog(HbaseDao.class);


    /**
     * 根据行键rowKey获取某一列的数据
     * @param tableName 表名
     * @param rowKey 行键
     * @param family 列族名
     * @param qualifier 列名
     * @return
     */
    public Cell getCellByRowkey (String tableName, String rowKey, String family, String qualifier) {
        Cell cell = null;
        Table table = null;
        try {
            Connection conn = HbaseConn.getConn();
            table = conn.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            Result result = table.get(get);
            if(result.isEmpty()) return null;
            List<Cell> cellList = result.listCells();
            if (cellList != null || cellList.size() != 0) {
                cell = cellList.get(0);
            }

        } catch (Exception e) {
            logger.error(e);
        } finally {
            IOUtils.closeStream(table);
        }
        return cell;
    }


    /**
     * 根据行键rowKey获取某一列的数据
     * @param tableName 表名
     * @param rowKey 行键
     * @param family 列族名
     * @param qualifier 列名
     * @return
     */
    public Cell getCellByRowkey (String tableName, String rowKey, String family, int qualifier) {
        Cell cell = null;
        Table table = null;
        try {
            Connection conn = HbaseConn.getConn();
            table = conn.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            Result result = table.get(get);
            if(result.isEmpty()) return null;
            List<Cell> cellList = result.listCells();
            if (cellList != null || cellList.size() != 0) {
                cell = cellList.get(0);
            }

        } catch (Exception e) {
            logger.error(e);
        } finally {
            IOUtils.closeStream(table);
        }
        return cell;
    }

    public Result getResultByRow(String tableName, String rowKey) {
        Result result = null;
        Table table = null;
        try {
            Connection connection = HbaseConn.getConn();
            //Admin admin = connection.getAdmin();
            //System.out.println("===getResultByRow() admin:" + admin);
            System.out.println("===getResultByRow() connection:" + connection);
            table = connection.getTable(TableName.valueOf(tableName));
            //System.out.println("table exists? " + admin.tableExists(TableName.valueOf(tableName)));
            System.out.println("getResultByRow() table:" + table);
            Get get = new Get(Bytes.toBytes(rowKey));
            //get.addFamily("info2".getBytes());
            get.addColumn("info2".getBytes(), "123".getBytes());
            result = table.get(get);

            table.close();
        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(table);
        }
        return result;
    }


    /**
     * 插入或修改一条数据，针对列族中有一个列，value为String类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @return boolean
     * @throws IOException
     */
    public void updateOneData(String tableName, String rowKey, String family, String column, String value) {
        try {
            Connection connection = HbaseConn.getConn();
            System.out.println("updateOneData() connection:" + connection);
            Table table = connection.getTable(TableName.valueOf(tableName));
            System.out.println("updateOneData() table:" + table);
            Put put = new Put(Bytes.toBytes(rowKey));
            System.out.println("updateOneData() put:" + put);
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
            table.put(put);
            table.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }
    /**
     * 插入或修改一条数据，针对列族中有一个列，value为bytes类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param bytes
     * @return boolean
     * @throws IOException
     */
    @SuppressWarnings("Duplicates")
    public void updateOneData(String tableName, String rowKey, String family, String column, byte[] bytes) {
        try {
            Connection connection = HbaseConn.getConn();
            System.out.println("updateOneData() connection:" + connection);
            Table table = connection.getTable(TableName.valueOf(tableName));
            System.out.println("updateOneData() table:" + table);
            Put put = new Put(Bytes.toBytes(rowKey));
            System.out.println("updateOneData() put:" + put);
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), bytes);
            table.put(put);
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 插入或修改一条数据，针对列族中有一个列，value为bytes类型
     * @category put 'tableName','rowKey','familyName:columnName'
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param bytes
     * @return boolean
     * @throws IOException
     */
    @SuppressWarnings("Duplicates")
    public void updateOneData(String tableName, String rowKey, String family, int column, byte[] bytes) {
        try {
            Connection connection = HbaseConn.getConn();
            System.out.println("updateOneData() connection:" + connection);
            Table table = connection.getTable(TableName.valueOf(tableName));
            System.out.println("updateOneData() table:" + table);
            Put put = new Put(Bytes.toBytes(rowKey));
            System.out.println("updateOneData() put:" + put);
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), bytes);
            table.put(put);
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
