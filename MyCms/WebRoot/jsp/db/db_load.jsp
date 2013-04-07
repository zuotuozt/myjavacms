<%@page contentType="text/html;charset=utf-8"%>
<%@page import="util.Constant"%>
<%@page import="javabean.UserInfo"%>
<%UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
}%>
<!--main div begin-->
<div id="maindiv" class="maindivelse"> 
	<span class="linktextbutton">
		<span>当前的位置：数据库管理&nbsp;</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
	<!--keboardfirst begin-->
	<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">
			<!-- 空格不能省略 -->
			<span class="span_select_left">&nbsp;&nbsp;&nbsp;</span><span class="span_select_center">数据库管理：备份和恢复</span><span class="span_select_right">&nbsp;&nbsp;&nbsp;</span>
			</span>
		</div>
	<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
	<!--keboardfirst end-->	
	</div>
	<form action="<%=request.getContextPath()%>/UploadCtrl" method="post" enctype="multipart/form-data" target="uploadcallback" onsubmit="return dbLoad();">
    <input type="hidden" id="page" name="page" value="LoadPage"/>
    <input type="hidden" id="flg" name="flg" value="2"/>
    <input type="hidden" id="type" name="type" value="0"/>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">数据库恢复</span>
				<div>
					<span class="spanthree"></span>
					<!-- update password begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="userlist">
							<td class="userlisttext">请输入文件：</td>
							<td class="userlistinput">
								<input id="upfile" type="file" size="21" name="upfile"/>
							</td>
							<td class="userlistoutput">&nbsp;选择数据库备份文件</td>
						</tr>
					</table>
					<!-- update password end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input class="submitbutton" type="button" value="备份数据库" onclick="document.backup_form.submit();"></input></span>
	<span><input class="submitbutton" type="submit" value="恢复"></input></span>
	</form>
	<!--submitbutton end-->
</div>
<form name="backup_form" action="<%=request.getContextPath()%>/MainCtrl?page=BackUpPage&flg=0" method="post" target="uploadcallback">
</form>
<!--maindiv end-->

