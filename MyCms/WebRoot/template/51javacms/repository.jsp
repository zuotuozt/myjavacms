<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/51java" prefix="51java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>51JAVACMS－国内优秀的JAVA(JSP)网站管理系统|开源 中文java cms jsp ajax - powered by 51javacms</title>
<meta content="51JAVACMS,国内开源网站内容管理系统,中文 java cms jsp ajax 51javacms" name="keywords" />
<meta content="提供国内开源java cms jsp ajax系统下载,中文网站建设,java虚拟主机等服务" name="description" />
<link rel="shortcut icon" href="<51java:webroot/>/images/logo.ico"/>
<link rel="stylesheet" rev="stylesheet" href="<51java:webroot/>/css/repository.css" type="text/css" media="all" />
</head>
<body>
<div id="head"><%@ include file="head.jsp"%></div>
<!-- 主体开始 -->
	<!-- 内容区域开始 -->
	<div class="title">
		<div class="search">
			<form action="http://${header["host"]}${pageContext.request.contextPath}/PluginCtrl?page=SearchPage" method="post">
									关键字
									<input name="search_txt" type="text" class="txtSearch" />
									<input type="submit" value="搜索" class="btnSubmit" />
								</form>
								</div>
当前位置：首页 > <span style="color:#c60000;font-weight:bold;">知识库</span>
</div>
<div class="introduction">
<div class="intro_box">
<div><span style="font-size:16px;font-weight:bold;">建站技巧</span><a href="<51java:webroot/>/repository/jzjq/pages/1.html" style="float:right;color:blue;" target="_blank">更多...</a></div>
<div class="summary">
<ul><51java:arlist pagesize="8" columnid="11" titlelen="13">
    <li>* <a href="${article.url}" target="_blank">${article.title}</a></li></51java:arlist>
</ul></div>
<div><img src="<51java:webroot/>/images/website.jpg"></div></div>

<DIV class=intro_box>
<div><span style="font-size:16px;font-weight:bold;">网页基础</span><a href="<51java:webroot/>/repository/wyjc/pages/1.html" style="float:right;color:blue;" target="_blank">更多...</a></div>
<div class="summary">
<ul><51java:arlist pagesize="8" columnid="12" titlelen="13">
    <li>* <a href="${article.url}" target="_blank">${article.title}</a></li></51java:arlist>
</ul></div>
<div><img src="<51java:webroot/>/images/coding.jpg"></div></div>

<div class="intro_box">
<div><span style="font-size:16px;font-weight:bold;">网络编程</span><a href="<51java:webroot/>/repository/wlbc/pages/1.html" style="float:right;color:blue;" target="_blank">更多...</a></div>
<div class="summary">
<ul><51java:arlist pagesize="8" columnid="13" titlelen="13">
    <li>* <a href="${article.url}" target="_blank">${article.title}</a></li></51java:arlist>
</ul></div>
<div><img src="<51java:webroot/>/images/net.jpg"></div></div>

<div class="intro_box">
<div><span style="font-size:16px;font-weight:bold;">数据库和服务器</span><a href="<51java:webroot/>/repository/dbserver/pages/1.html" style="float:right;color:blue;" target="_blank">更多...</a></div>
<div class="summary">
<ul><51java:arlist pagesize="8" columnid="14" titlelen="13">
    <li>* <a href="${article.url}" target="_blank">${article.title}</a></li></51java:arlist>
</ul></div>
<div><img src="<51java:webroot/>/images/computer.jpg"></div></div>
</div>
<!-- 主体结束 -->
<%@ include file="foot.jsp"%>
</body>
</html>