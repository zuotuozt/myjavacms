<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%@page import="util.InitServlet"%>
<%@page import="util.PubFun"%>
<%@page import="java.io.File,java.util.Date,java.text.SimpleDateFormat"%>
<title>51JAVACMS－国内优秀的JAVA(JSP)网站管理系统|开源java cms jsp ajax - powered by 51javacms</title>
<meta content="51JAVACMS,国内开源网站内容管理系统,java cms jsp ajax 51javacms" name="keywords" />
<meta content="提供国内开源java cms jsp ajax系统下载,互联网应用,网站建设,java虚拟主机等服务" name="description" />
<link href="<%=request.getContextPath()%>/css/global.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/float.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/system.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/floatdiv.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/img_crop/thumbnails_init.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/img_crop/ImgCropper.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/img_crop/Drag.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/img_crop/Resize.js"></script>
<%UserInfo userInfo=(UserInfo)session.getAttribute("userInfo");%>
<script type="text/javascript">rootUrl='<%=request.getContextPath()%>';</script>
<%if (userInfo==null){%>
		<script type="text/javascript">
		alert('页面失效；请重新登陆。');
		window.opener.location=rootUrl+'/sign_in.jsp;';
		window.close();
		</script>
		<%return;		
	}
	String path=(String)request.getParameter("filepath");%>
<script type="text/javascript">
var rootPath='<%=InitServlet.WEB_SITE_URL%>'+'/upload/'+'<%=userInfo.getName() %>'+'/image';
var path='<%=path%>';
</script>
</head>
<body>
<table width="100%" height="300px" border="0px" cellspacing="0px" cellpadding="0px">
  <tr>
    <td width="100%" height="100%">
		<div id="bgDiv">
	        <div id="dragDiv">
	          <div id="rRightDown"></div>
	          <div id="rLeftDown"></div>
	          <div id="rRightUp"></div>
	          <div id="rLeftUp"></div>
	          <div id="rRight"></div>
	          <div id="rLeft"></div>
	          <div id="rUp"></div>
	          <div id="rDown"></div>
	        </div>
		</div>
	</td>
  </tr>
</table>
<br/>
	<input id="dragDivwidth" type="text" size="3"/>
	<input id="dragDivheight" type="text" size="3"/>
	<input name="" type="button" value="设定图片尺寸" onclick="editImage()"/>	
	<input id="idOpacity" type="button" value="全透明" />
	<input id="idColor" type="button" value="使用白色背景" />
	<input id="idScale" type="button" value="使用比例" />
	<input type="button" value="生成图片" onclick="thumbnailsCreate('<%=path%>');" />
  <br /><br />
  	图像大小：
  	<input id="divWidthSize" type="text" size="5" value=""/>
  	<input id="divheightSize" type="text" size="5" value=""/>
	<img id="imgCreat" style="display:none;" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/img_crop/thumbnails.js"></script>
</body>
</html>
