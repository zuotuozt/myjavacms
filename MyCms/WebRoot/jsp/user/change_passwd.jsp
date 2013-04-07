<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
}%>
<!--main div begin-->
<div id="maindiv" class="maindivelse">   
	<span class="linktextbutton">
		<span>当前的位置：系统管理&nbsp;\&nbsp;用户&nbsp;\&nbsp;修改密码</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">更改用户（<%=userInfo.getName()%>）密码</span>
				<div>
					<span class="spanthree"></span>
						<!-- update password begin -->
						<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
							<tr class="userlist">
								<td class="userlisttext" width="20%">请输入原始密码：</td>
								<td class="userlistinput" width="40%"><input type="password" id="password" size="20" maxlength="16"></input></td>
								<td class="userlistoutput" width="30%">&nbsp;原始密码不能为空！</td>
							</tr>
							<tr class="userlist">
								<td class="userlisttext">请输入新密码：</td>
								<td class="userlistinput"><input type="password" id="newPassword" size="20" maxlength="16"></input></td>
								<td class="userlistoutput">&nbsp;新密码不能为空！</td>
							</tr>
							<tr class="userlist">
								<td class="userlisttext">请再次输入新密码确认：</td>
								<td class="userlistinput"><input type="password" id="notarizePassword" size="20" maxlength="16"></input></td>
								<td class="userlistoutput">&nbsp;再次确认密码不能为空！</td>
							</tr>
						</table>
						<!-- update password end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input class="submitbutton" type="submit" onclick="changePasswd('<%=request.getContextPath()%>/MainCtrl', <%=userInfo.getId()%>);" value="确定"></input></span> 
	<span><input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/jsp/inc/welcome.jsp', rightDivContent);"  value="返回"></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->
