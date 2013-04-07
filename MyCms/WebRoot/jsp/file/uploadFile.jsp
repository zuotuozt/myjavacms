<%@page contentType="text/html;charset=utf-8"%>
<%@page import="util.Constant"%>
<%@page import="javabean.UserInfo"%>
<%UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		if (userInfo == null) {
			response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
			return;		
	}
String path=(String)request.getAttribute("path");
int type =(Integer)request.getAttribute("type");
%>
<form name="form1" action="<%=request.getContextPath()%>/UploadCtrl" method="post" enctype="multipart/form-data" target="uploadcallback" onsubmit="return uploadFile();">
<!-- input hidden begin -->
<input type="hidden" name="page" value="UploadFilePage"></input>
<input type="hidden" name="filepath" id="filepath" value="<%=path%>"></input>	
<input type="hidden" id="type" name="type" value="<%=type%>"/>
<!-- input hidden end -->
<!--main div begin-->
<div id="maindiv" class="maindivelse">   
	<span class="linktextbutton">
		<span>当前的位置：<%if(type==Constant.FILE_PATH_TYPE){%>文章附件<%}else{%>模板<%}%>管理&nbsp;\&nbsp;
		<font color="red">上传文件</font></span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
	<!--keboardfirst begin-->
	<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">
			<!-- 空格不能省略 -->
			<span class="span_select_left">&nbsp;&nbsp;&nbsp;</span><span class="span_select_center">
			当前路径：<%if(type==Constant.FILE_PATH_TYPE){%>&lt;51java:webroot/&gt;<%=path%><%}else{%><%=path%><%}%></span>
			<span class="span_select_right">&nbsp;&nbsp;&nbsp;</span>
			</span>
		</div>
	<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
	<!--keboardfirst end-->	
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">上传文件</span>
				<div>
					<span class="spanthree"></span>
					<!-- update password begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="userlist">
							<td class="userlisttext" width="16%">请输入文件名：</td>
							<td class="userlistinput" width="39%"><input type="text" name="upfile_name" id="upfile_name" size="50px"></input></td>
							<td class="userlistoutput" width="45%">文件名为空时；默认上传文件原名！不用输入扩展名；默认原文件扩展名。</td>
						</tr>
						<tr class="userlist">
							<td class="userlisttext">请输入文件：</td>
							<td class="userlistinput"><input type="file" name="upfile" id="upfile" size="42"></input></td>
							<td class="userlistoutput">文件不能为空！</td>
						</tr>
					</table>
					<!-- update password end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input class="submitbutton" type="submit"  value="上传文件"></input></span>
	<span><input class="submitbutton" type="button"  onclick="toPrePage('0');" value="返回"></input></span>  
	<!--submitbutton end-->
</div>
<!--maindiv end-->
</form>	
