package test;

import com.phantom.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/16
 * @Package: test
 * @Description:
 * @ModifiedBy:
 */
public class TestUtils {


    @Test
    public void test1() {
        String token = JwtUtil.generateToken("sa");
        String result = JwtUtil.parseToken(token);
        //String result = JwtUtil.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYSIsImV4cCI6MTUyMzg4MTcwN30.l6MBUKte6gShdVJRVtGGx_-xrcP4N1HfDn43p0EDPgAfuJY2zHn_P5A0e5zVCY9mD_Z9XlBpKWLQCwdDTTsaCQ");
        System.out.println("parseToken==========" + result);
    }

    @Test
    public void test2() {
        String token = JwtUtil.generateToken("sa");
        Claims c = JwtUtil.verifyToken(token);
        System.out.println("result=========" + c);
    }


    @Test
    public void test3() {
        String token = JwtUtil.sign("sa", 1000L);
        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = JwtUtil.unsign(token, String.class);

        System.out.println("result=========" + result);

    }


}
