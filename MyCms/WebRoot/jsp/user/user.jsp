<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%@page import="util.InitServlet"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	UserInfo[] users=(UserInfo[])request.getAttribute("users");%>
<input type="hidden" id="page" value="UserManagePage"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：系统管理&nbsp;\&nbsp;用户管理</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">关键字查询</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">请输入用户名：</td>
						<%String userName=request.getParameter("search_name");if(userName==null) userName="";%>
						<td class="userlistinput"  width="40%"><input id="search_name" type="text" value="<%=userName%>"></input>
						&nbsp;<input class="submitbutton" type="submit" onClick="searchUser();" value="查询"></input></td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">用户管理</span>
				<div>
					<span class="spanthree"></span>
					<!-- table list begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="5%">请选择</td>
							<td class="tablelistfirst3r" width="12%">创建日期</td>
							<td class="tablelistfirst3r" width="5%">用户ID</td>
							<td class="tablelistfirst3r" width="16%">用户名</td>
							<td class="tablelistfirst3r" width="16%">用户别名</td>
							<td class="tablelistfirst3r" width="15%">所属部门</td>
							<td class="tablelistfirst3r" width="12%">上次登录时间</td>
							<td class="tablelistfirst3r" width="15%">操作处理</td>
						</tr>
						<%if(users != null){
							String className = "tablelisttext3ro";
							for (int i=0;i<users.length;i++){%>
							<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
								<%if((i%2)==0){
									className = "tablelisttext3ro";
								}else{
									className = "tablelisttext3rt";
								}%>
							<td class="<%=className%>"><input type="checkbox" name="checks_name" value="<%=users[i].getId()%>"></td>
							<td class="<%=className%>"><%=PubFun.getDateTime("yyyy-MM-dd HH:mm:ss",users[i].getCreateTime())%></td>
					        <td class="<%=className%>"><%=users[i].getId()%></td>
							<td class="<%=className%>"><%=users[i].getName()%></td>
							<td class="<%=className%>"><%=users[i].getAlias()%></td>
							<td class="<%=className%>"><%=users[i].getDepartmentName()%></td>
							<td class="<%=className%>"><%=PubFun.getDateTime("yyyy-MM-dd HH:mm:ss",users[i].getLastLogin())%></td>
						    <td class="<%=className%>"><%if(users[i].isDel()){ %>
						    	<a href="javascript:reUser(<%=users[i].getId()%>,'<%=users[i].getName()%>');"><font color="red">恢复用户</font></a>
					       	<%}else{ %>
					       		<a href="javascript:showEditUser(<%=users[i].getId()%>,${pageNo});">修改</a>
					       		|&nbsp;<a href="javascript:initUserPasswd(<%=users[i].getId()%>,'<%=users[i].getName()%>');">重置密码</a>
					       		|&nbsp;<a href="javascript:delUser('<%=users[i].getName()%>',<%=users[i].getId()%>,${pageNo});">冻结</a>					       		
					       	<%}%>	
					       </td>
						</tr><%}}%>
					</table>
					<!-- table list end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<span><%@ include file="../inc/page.jsp"%></span>
	<!--submitbutton begin-->	
	<span><input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=UserInfoPage');" value="新增用户"></input></span>
	<span><input type="button" value="批量冻结用户" onclick="handleCheckeds('page=DeleteCheckedUsersPage&is_del=true&users=','冻结',${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>);" class="submitbutton" ></input></span>
		<span><input type="button" value="批量恢复用户" onclick="handleCheckeds('page=DeleteCheckedUsersPage&users=','恢复',${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>);" class="submitbutton" ></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->



