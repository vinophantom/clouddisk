package test;

import com.phantom.dao.OriginFileDao;
import com.phantom.dao.UserDao;
import com.phantom.dao.UserFileDao;
import com.phantom.entity.OriginFile;
import com.phantom.entity.SysUser;
import com.phantom.entity.SysUserImage;
import com.phantom.util.LoginUtil;
import com.phantom.util.common.FileUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/2
 * @Package: java.test
 * @Description:
 * @ModifiedBy:
 */

public class TestDao extends test.BaseJunit4Test{
    @Resource  //自动注入,默认按名称
    private JdbcTemplate jdbcTemplate;
    @Resource  //自动注入,默认按名称
    private HibernateTemplate hibernateTemplate;
    @Resource
    private SessionFactory sessionFactory;
    @Resource
    private UserDao userDao;
    @Resource
    private UserFileDao userFileDao;
    @Resource
    private OriginFileDao originFileDao;

    @Test   //标明是测试方法
    @Transactional("jdbcTemplateTxManager")   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void test1() {

        String pwd = LoginUtil.encodePwd("123", "9052e391-2efa-4643-9332-585a4e657473");
        jdbcTemplate.execute("INSERT INTO sys_user " +
                "(id, username, password, salt, email, mobile, valid, createdTime, modifiedTime, createdUser, modifiedUser)\n" +
                "VALUES(1, 'sa', '" + pwd + "', '9052e391-2efa-4643-9332-585a4e657473', 'vinophantom@163.com', " +
                "'13624356789', 1, NULL, '2017-07-18 17:13:39.000', NULL, NULL);\n");
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM sys_user");
        System.out.println("list:" + list);


    }

    @Test   //标明是测试方法
    @Transactional("hibernateTxManager")   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void test2() {
        SysUser user = hibernateTemplate.get(SysUser.class,1);
        System.out.println(user);
    }

    @Test
    @Transactional("hibernateTxManager")
    public void test3() {
        Session session = sessionFactory.getCurrentSession();

    }

    @Test
    @Transactional("hibernateTxManager")
    @Rollback(false)
    public void test4() {
        // 得到一个信息摘要器
        try {
            //确定计算方法
            MessageDigest md5=MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String salt = UUID.randomUUID().toString();
            String pwd = "123";
            pwd = base64en.encode(md5.digest((salt + pwd + salt).getBytes("utf-8")));
            SysUser user = new SysUser(1, "sa", pwd, salt, "", "110", 1, new Date(), new Date(), null, null);
            hibernateTemplate.update(user);
            System.out.println("执行完毕");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional("hibernateTxManager")
    @Rollback(false)
    public void test5() {
        List<SysUser> userList = userDao.findUserByUsername("sa");
        System.out.println("userList:" + userList);
    }

    @Test
    @Transactional("hibernateTxManager")
    @Rollback(false)
    public void test6() throws IOException {
        SysUserImage image = new SysUserImage();
        image.setId(1);
        image.setUserid(1);
        byte[] content = FileUtils.InputStream2ByteArray("C:\\Users\\Jason Xu\\Desktop\\bits\\web\\users\\photo\\001.jpg");
        System.out.println("content.length=======" + content.length);
        image.setContent(content);
        hibernateTemplate.save(image);

    }

    @Test
    @Transactional("hibernateTxManager")
    @Rollback(false)
    public void test7() throws IOException {
        hibernateTemplate.get(SysUser.class,1);
    }

    @Test
    @Rollback
    public void test8() {
        userFileDao.listByFileType(1, new String[]{"doc"});
    }

    @Test
    @Rollback
    public void test9() {

        OriginFile originFile = originFileDao.getByFileMd5("9ab6562b2169356d371d5cc9231c4346");
        System.out.println("originFile===========" + originFile);
    }

}
