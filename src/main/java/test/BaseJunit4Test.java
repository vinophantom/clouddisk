package test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/2
 * @Package: java.test
 * @Description:
 * @ModifiedBy:
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"classpath:spring.xml"})
public class BaseJunit4Test {
}
