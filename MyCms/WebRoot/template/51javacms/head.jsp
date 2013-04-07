<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/51java" prefix="51java" %>
	<div id="logo">
		<h1><a href="<51java:webroot/>"><img alt="51JavaCms!" src="<51java:webroot/>/images/logo.png">无忧javacms</a></h1>
		<p>
			<a href="#">网站导航</a><em>|</em>
			<a href="#">联系我们</a><em>|</em>
			<a href="<51java:webroot/>/aboutus/2011-08-21/54.html" target="_blank">免责声明</a>
		</p>
	</div>
	<div id="menu">
		<ul>
			<li><a <51java:if test="${columnid==1}">id="menu_home"</51java:if> href="<51java:webroot/>/index.html">首页</a></li>
			<li><a <51java:if test="${columnid==9}">class="here"</51java:if> href="<51java:webroot/>/ud_center/2011-07-26/19.html">下载中心</a></li>
			<li><a <51java:if test="${columnid==10}">class="here"</51java:if> href="<51java:webroot/>/repository/index.html">知识库</a></li>
			<li><a href="http://${header["host"]}${pageContext.request.contextPath}/PluginCtrl?page=ShowMessagePage" target="_blank">留言板</a></li>
			<li><a href="http://www.51javacms.com/democms/sign_in.jsp " target="_blank">在线演示</a></li>
		</ul>
	</div>