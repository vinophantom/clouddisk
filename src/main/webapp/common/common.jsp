<%@ page import="com.phantom.util.common.WebUtils" %><%--
  Created by IntelliJ IDEA.
  User: Jason Xu
  Date: 2018/4/1
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%
    String ___path = WebUtils.getContextPath(request) + "/";
%>
<script src="<%=___path %>js/jquery-3.0.0.js"></script>
<script src="<%=___path %>js/bootstrap.min.js"></script>
<link href="<%=___path %>css/bootstrap.min.css" rel="stylesheet" type="text/css">