package com.phantom.util.common;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/1
 * @Package: com.phantom.util
 * @Description:
 * @ModifiedBy:
 */
public class SpringUtils {
    /**
     * 私有构造函数.
     */
    private SpringUtils() {
        //do nothing.
    }

    /**
     * 从SpringContext中获取一个Object.
     * @param request   请求对象.获取当前的web容器上下文.
     * @param beanName  对象的名称对应<bean id="">
     * @return Object--需要强制转型.
     */
    public static Object getBean(HttpServletRequest request, String beanName) {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(
                request.getSession().getServletContext()).getBean(beanName);
    }

    /**
     * 获取数据库连接.
     * @param request  页面的request对象.
     * @param datasource 定义的datasource名称.
     * @return  Connection.
     */
    public static Connection getConnection(HttpServletRequest request, String datasource){
        return DataSourceUtils.getConnection((DataSource) getBean(request, datasource));
    }

    /**
     * 获取数据库默认连接.
     * @param request HTTPREQUEST
     * @return Connection
     */
    public static Connection getConn(HttpServletRequest request){
        //TODO: 默认数据库连接，
        return getConnection(request,"");
    }
}
