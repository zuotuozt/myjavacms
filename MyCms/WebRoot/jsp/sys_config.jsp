<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo,util.Constant,util.InitServlet"%>
<%/***************session fail****************************/
UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}%>
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：系统管理&nbsp;\&nbsp;配置管理</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">核心设置</span>
				<div>
					<span class="spanthree"></span>
					<!-- system config begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="40%">参数说明</td>
							<td class="tablelistfirst3r" width="40%">参数值</td>
							<td class="tablelistfirst3r" width="10%">管理</td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<td class="tablelisttext3ro">网站名称</td>
							<td class="tablelisttext3ro"><%=InitServlet.WEB_SITE_NAME%></td>
							<td class="tablelisttext3ro"><a href="#" onclick="showUpdateSys(this);" id="WEB_SITE_NAME" name="<%=InitServlet.WEB_SITE_NAME%>" title="网站名称">修改</a></td>						
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<td class="tablelisttext3ro">网站访问域名(url)</td>
							<td class="tablelisttext3ro"><%=InitServlet.WEB_SITE_URL%></td>
							<td class="tablelisttext3ro"><a href="#" onclick="showUpdateSys(this);" id="WEB_SITE_URL" name="<%=InitServlet.WEB_SITE_URL%>" title="网站访问域名">修改</a></td>						
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<td class="tablelisttext3rt">网站在服务器上的真实目录</td>
							<td class="tablelisttext3rt"><%=InitServlet.WEB_SITE_PATH%></td>
							<td class="tablelisttext3rt"><a href="#" onclick="showUpdateSys(this);" id="WEB_SITE_PATH" name="<%=InitServlet.WEB_SITE_PATH%>" title="网站在服务器上的真实目录">修改</a></td>						
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<td class="tablelisttext3ro">分页显示条数</td>
							<td class="tablelisttext3ro"><%=InitServlet.PAGE_SIZE%></td>
							<td class="tablelisttext3ro"><a href="#" onclick="showUpdateSys(this);" id="PAGE_SIZE" name="<%=InitServlet.PAGE_SIZE%>" title="分页显示条数">修改</a></td>						
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<td class="tablelisttext3rt">用户初始化密码</td>
							<td class="tablelisttext3rt"><%=InitServlet.INIT_PASSWD%></td>
							<td class="tablelisttext3rt"><a href="#" onclick="showUpdateSys(this);" id="INIT_PASSWD" name="<%=InitServlet.INIT_PASSWD%>" title="用户初始化密码">修改</a></td>						
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<td class="tablelisttext3ro">网站留言板分页显示条数</td>
							<td class="tablelisttext3ro"><%=InitServlet.MESSAGE_PAGE_SIZE%></td>
							<td class="tablelisttext3ro"><a href="#" onclick="showUpdateSys(this);" id="MESSAGE_PAGE_SIZE" name="<%=InitServlet.MESSAGE_PAGE_SIZE%>" title="网站留言板分页显示条数">修改</a></td>						
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<td class="tablelisttext3ro">数据库程序目录</td>
							<td class="tablelisttext3ro"><%=InitServlet.MYSQL_DB_PATH %></td>
							<td class="tablelisttext3ro"><a href="#" onclick="showUpdateSys(this);" id="MYSQL_DB_PATH " name="<%=InitServlet.MYSQL_DB_PATH %>" title="数据库程序目录">修改</a></td>						
						</tr>
					</table>
					<!-- system config end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<span><input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/jsp/inc/welcome.jsp', rightDivContent, null);" value="返回"></input></span>
</div>
<!--maindiv end-->

<!-- float input zone begin -->
<div id="input_zone">
<input type="hidden" id="hidden_param_value"></input>
<input type="hidden" id="hidden_param_id"></input>
	<div class="inputzoneinfo">
		<div class="inputzoneinfotitle"><font class="floatlinktext">修改参数值：<label id="param_id"></label></font><span class="inputzoneti"></span></div>
		<div class="inputzoneexit">
			<a title="安全退出" id="input_zone_info_close" class="exitout" onclick="closeSys();" onmouseover="this.className='exitmove';" onmouseout="this.className='exitout';" href="#"></a>
		</div>
		<div class="inputzoneinserttext">请输入参数：<input type="text" id="param_value"></input></div>
		<div class="inputzonebutton">
			<span><input class="submitbutton" type="submit" onclick="updateSys();" value="确定"></input></span>
			<span><input class="submitbutton" type="button" onclick="closeSys();" value="返回"></input></span>
		</div>
	</div>
</div>
<div id="input_zone_bg"></div>   
<!-- float input zone end -->