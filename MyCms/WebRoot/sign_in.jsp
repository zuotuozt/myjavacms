<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title>51JAVACMS－国内优秀的JAVA(JSP)网站管理系统|开源java cms jsp ajax - powered by 51javacms</title>
<meta content="51JAVACMS,国内开源网站内容管理系统,java cms jsp ajax 51javacms" name="keywords" />
<meta content="提供国内开源java cms jsp ajax系统下载,互联网应用,网站建设,java虚拟主机等服务" name="description" />
<link rel="shortcut icon" href="http://www.51javacms.com/images/logo.ico"/>
<link href="css/cmslogin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/system.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/cms.js"></script>
</head>
<%String userName=(String)request.getAttribute("userName");
if (userName == null) {
	userName = "";
}%>
<body onload="initInf();">
<form action="<%=request.getContextPath()%>/MainCtrl" onsubmit="return submitLogin();" method="post">
<!-- input hidden begin -->
<input type="hidden" name="page" value="LoginPage"></input>
<input type="hidden" id="cms_login" value="cms_login_reload"></input>
<input type="hidden" id="inf" value="<%=request.getAttribute("inf")%>"></input>
<!-- input hidden begin -->
<div style="margin-top:20px;">
<div id="titles"></div>
<span id="signInBody">
<span id="titleimage"></span>
<span class="logininfo"><br/>登录到您的帐户!..........&nbsp;&nbsp;&nbsp;<br/>Login to your account&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
<span class="info">
<div class="infolistimage" id="infolistimagea"><span><a href="#" ><b>在线升级！</b><br/>无论您在何时何地！<br/>都能一键获取最新版本！</a></span></div>
<div class="infolistimage" id="infolistimageb"><span><a href="#" ><b>实时响应！</b><br/>您所做出的决定将<br/>不会让其他人改变！</a></span></div>
<div class="infolistimage" id="infolistimagec"><span><a href="#" ><b>良好的用户体验！</b><br/>强大的个性化设置<br/>为您提供更多的选择！</a></span></div>
<div class="infolistimage" id="infolistimaged"><span><a href="#" ><b>BUG及时修复！</b><br/>您的及时反馈，将会给予我们最大的帮助！</a></span></div>
</span>
<span class="logininput">
<div id="logininputbg" class="mouse_out">
	<div class="logindiv">
	<span class="usernametype">用户名:</span>
	<span class="usernameinput"><input name="user_name" id="user_name" type="text" value="<%=userName%>"></input></span>
	</div>
	<div class="logindiv">
	<span class="usernametype">密&nbsp;&nbsp;码:</span>
	<span class="usernameinput"><input name="passwd" id="passwd" type="password"></input></span>
	</div>
</div>
<div align="center"><input type="submit" value="登&nbsp;&nbsp;录" id="loginbutton" class="out"></input></div>
</span></span>
<!--copyright begin-->	
<div class="copyright">
	<strong class="none">©&nbsp;Copyright 2010-2012 by 
	<a href="http://www.51javacms.com" target="_blank">
	<font color="yellow">51javaCms</font></a>
	&nbsp;Technologies Limited&nbsp;&nbsp;Powered by 
	<a href="http://www.51javacms.com" target="_blank">
	<font color="yellow">51javaCms1.0.8</font></a>
	&nbsp;Final © 2010-2012&nbsp;<a href="http://www.51javacms.com" target="_blank">
	<font color="yellow">51javaCms</font></a></strong>
</div>
<!--copyright end-->
</div>
</form>
</body>
</html>
