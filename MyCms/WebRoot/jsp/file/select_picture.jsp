<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%@page import="util.InitServlet"%>
<%@page import="java.io.File,java.util.Date,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title>51JAVACMS－国内优秀的JAVA(JSP)网站管理系统|开源java cms jsp ajax - powered by 51javacms</title>
<meta content="51JAVACMS,国内开源网站内容管理系统,java cms jsp ajax 51javacms" name="keywords" />
<meta content="提供国内开源java cms jsp ajax系统下载,互联网应用,网站建设,java虚拟主机等服务" name="description" />
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/global.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/system.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cms.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/floatdiv.js"></script>
<%	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	File[] files = (File[])request.getAttribute("files");
		if (userInfo == null || files == null) {
			response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
			return;		
	}
	String path=(String)request.getAttribute("filepath");
%>
<script type="text/javascript">
var rootUrl='<%=request.getContextPath()%>';
var rootPath = '<%=InitServlet.WEB_SITE_URL%>'+'/upload/'+'<%=userInfo.getName() %>'+'/image';
</script>
</head>
<body>
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：缩略图选择 &nbsp;</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
	<!--keboardfirst begin-->
	<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">
			<!-- 空格不能省略 -->
			<span class="span_select_left">&nbsp;&nbsp;&nbsp;</span>
			<span class="span_select_center">当前路径：当前路径：&lt;51java:webroot/&gt;<%=Constant.UPLOAD_PATH+"/"+userInfo.getName()+"/image"+path%>
</span><span class="span_select_right">&nbsp;&nbsp;&nbsp;</span>
			</span>
		</div>
	<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
	<!--keboardfirst end-->	
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">图片文件管理</span>
				<div>
					<span class="spanthree"></span>
					<!-- system config begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="20%">名称</td>
							<td class="tablelistfirst3r" width="20%">大小</td>
							<td class="tablelistfirst3r" width="20%">类型</td>
							<td class="tablelistfirst3r" width="20%">最后修改时间</td>
						</tr>
							<% if (files != null ){
							String className = "tablelisttext3ro";
							for (int i=0;i<files.length;i++){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
								Date d=new Date(files[i].lastModified());
								String dateStr = sdf.format(d);
								if(!files[i].getName().equals("cms_hidden")){
									%>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<%if((i%2)==0){
								className = "tablelisttext3ro";
							}else{
								className = "tablelisttext3rt";
							}%>
							<td class="<%=className%>">
								<%if(files[i].isDirectory()){ %>
									<a href="javascript:toPrePic('<%=files[i].getName()%>');"><%=files[i].getName()%></a>
						        <%}else{%>
						        	<a href="javascript:choosePicure('<%=path+"/"+files[i].getName()%>');"><%=files[i].getName()%></a>
						        <% }%>
							</td>
							<td class="<%=className%>"><%if(files[i].isDirectory()){%>&nbsp;<%}else{%><%=files[i].length()/1024 %>kb<%}%></td>
							<td class="<%=className%>"><%if(files[i].isDirectory()){%>文件夹<%}else{%>文件<%}%></td>
							<td class="<%=className%>"><%=dateStr %></td>					
						</tr>
							
						<%}}}%>
					</table>
					<!-- system config end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
<!--submitbutton begin-->	
<span><input class="submitbutton" type="submit" onclick="javascript:toPrePic('1');"  value="返回上级目录"></input></span>
<form action="<%=request.getContextPath()%>/UploadCtrl" method="post" enctype="multipart/form-data">
<input type="hidden" name="filepath" id="filepath" value="<%=path%>"/>
<input type="hidden" name="page" value="UploadIndexPicturePage"/>
<span><input class="submitbutton" type="file" id="upfile" name="upfile"></input></span>
<input type="submit" value="上传"/>
</form>
<!--submitbutton end-->	
</div>
<!--maindiv end-->
</body>
</html>

