package com.phantom.util.common;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;


public class WebUtils {

    /**
     * 获取当前应用地址路径.
     *
     * @param request
     *            请求.
     * @return http://localhost:8080/court/
     */
    public static String getContextPath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path;
        return basePath;
    }

    /**
     * 获取服务器的地址路径.
     *
     * @param request
     *            请求.
     * @return http://localhost:8080/
     */
    public static String getWebContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort();
    }

    /**
     * Escape字符串.
     *
     * @param input
     *            输入的字符.
     * @return 如:& 字段转换成&amp;
     */
    public static String htmlEscape(String input) {
        return HtmlUtils.htmlEscape(input);
    }

    /**
     * 获取ip地址
     *
     * @param request 当前request对象
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) ) {
            ip = request.getHeader("sso-tdh-x-forwarded-ip");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "".equals(ip.trim()) ) {
            ip = request.getRemoteAddr();
        }

        if (ip != null) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
