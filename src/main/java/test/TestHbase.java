package test;

import com.phantom.dao.basedao.HbaseDao;
import com.phantom.dao.conn.HbaseConn;
import com.phantom.util.common.ByteUtils;
import com.phantom.util.common.PropertiesUtils;
import com.phantom.util.common.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/6
 * @Package: test
 * @Description:
 * @ModifiedBy:
 */
public class TestHbase extends BaseJunit4Test {
    private final String basePath = "/clouddisk/";
//
//    @Resource  //自动注入,默认按名称
//    private JdbcTemplate jdbcTemplate;
//    @Resource  //自动注入,默认按名称
//    private HibernateTemplate hibernateTemplate;
    @Resource(name = "hbaseDao")
    private HbaseDao hbaseDao;
//

    @Before
    public void init() {

    }

    @Test
    public void test1() {
        String url = PropertiesUtils.getPropertiesMap("/hadoop.properties").get("hdfs.url");
        System.out.println("url:" + url);
    }


//    @Test
//    public void test2() {
//        SysUser user = hibernateTemplate.get(SysUser.class, 1);
//        try {
//            File file = new File("lib/jconn3.jar");
//            FileInputStream fis = new FileInputStream(new File("E:\\LIB\\jconn3.jar"));
//            HdfsDao hdfsDao = new HdfsDao();
//            hdfsDao.put(fis, file, user);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void test3() {
        HbaseDao hbaseDao = new HbaseDao();
        Result result = hbaseDao.getResultByRow("user", "row1");
        System.out.println("result ==============" + result);
        //byte[] value = result.getValue("info".getBytes(),"file".getBytes());
        List<Cell> cellList = result.listCells();
        //Cell[] cells = result.rawCells();
        String family;
        String rowname;
        String row;
        String value;
        for (Cell cell: cellList) {
            rowname = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            row = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
            value = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

            System.out.println();
            System.out.println(
                    "=======family==========" + family +
                    "|rowkey==========" + row +
                    "|rowname========" + rowname +
                    "|value=========" + value
            );

        }
//        for (Cell c : cells) {
//
//            family = new String(c.getFamilyArray());
//            row = new String(c.getRowArray());
//            value = new String(c.getValueArray());
//
//            System.out.println("=======family:" + family + "=========row:" + row + "======value:" + value);
//        }
//        //getFileByBytes(value,"e:\\", "jconn3.jar");
        System.out.println("task complete！");

    }
    @Test
    public void test4() {
        HbaseDao hbaseDao = new HbaseDao();
        File file = new File("E:\\Tools\\360dwjjx_v1.0.0.1001\\360dwjjxxx\\360NetRepair3.exe");
        InputStream input = null;
        byte[] byt = null;
        try {
            input = new FileInputStream(file);
            byt = new byte[input.available()];
            input.read(byt);
            hbaseDao.updateOneData("user","row","info","file",byt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("执行完毕");

    }
    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test5() {
        Cell cell = hbaseDao.getCellByRowkey(
                "cloudfile",
                "7c5ad03f71e86bacd5e0bf63897d98f6[2]",
                "bytes",
                "bytes");
        int offset = cell.getValueOffset();
        int len = cell.getValueLength();
        byte[] array = cell.getValueArray();
        byte[] value = ByteUtils.subBytes(array, offset, len);
        array = null;
        System.out.println("size============" + value.length);
        System.out.println("value=========" + value);
    }


    @Test
    public void test6() {
        Cell cell = hbaseDao.getCellByRowkey(
                "user",
                "row",
                "info",
                "filefil"
        );
        System.out.println("cell=============" + cell);
    }

    @Test
    public void test7() throws IOException {
        Connection conn = HbaseConn.getConn();
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf("user"));
            Put put = new Put(Bytes.toBytes("row123456"));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("count"), Bytes.toBytes(123));
            table.put(put);


        } catch (Exception e) {

        } finally {
            table.close();
        }
    }


    @Test
    public void test8 () {
        try {
            // read file content from file
            StringBuffer sb= new StringBuffer("");

            FileReader reader = new FileReader("c://test.txt");
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while((str = br.readLine()) != null) {
                sb.append(str+"\r\n");

                System.out.println(str);
            }

            br.close();
            reader.close();

            // write string to file
            FileWriter writer = new FileWriter("c://test2.txt");
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(sb.toString());

            bw.close();
            writer.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test9() {
        Cell cell = hbaseDao.getCellByRowkey(
                "user",
                "row1",
                "info",
                "age");
        int offset = cell.getValueOffset();
        int len = cell.getValueLength();
        byte[] array = cell.getValueArray();
        byte[] value = ByteUtils.subBytes(array, offset, len);
        array = null;
        System.out.println("size============" + value.length);
        System.out.println("value=========" + new String(value));
    }


    @Test
    public void test() {
        System.out.println("total " + countPathLine(new File("E:\\Workspaces\\IdeaProjects\\clouddisk")) + "lines");
    }

    private static int countPathLine (File dir) {
        String path = dir.getPath();
        if(path.endsWith("lib")) {
            return 0;
        }
        int count = 0;
        try {
            File[] files = dir.listFiles();
            for (File f: files) {
                if(f.isDirectory()) {
                    count += countPathLine(f);
                } else {
                    count += countLine(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(count + "lines ============================== curr DIR:" + dir.getPath());
        return count;
    }

    private static int countLine (File file) {
        String path = file.getPath();
        //if(path.endsWith(".java") || path.endsWith(".js") || path.endsWith(".html")) {
        if(path.endsWith(".java") || path.endsWith(".jsp") || path.endsWith("js") && !path.contains("jquery") & !path.contains("bootstrap") && !path.contains("webupload")) {
            int count = 0;
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String str = null;
                while ((str = br.readLine()) != null) {
                    if(StringUtils.trim(str).length() > 1) {
                        count ++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(count + "lines===========================currFile : " + file.getPath());
            return count;
        } else {
            return 0;
        }
    }


    @Test
    public void test11() {

    }
}
