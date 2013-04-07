<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;	
	}%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<!--main div begin-->
<div id="maindiv" class="maindivelse">
	<span class="linktextbutton">
		<span>当前的位置：欢迎页</span>
	</span>
	<!--welcome image first begin-->
	<a href="<%=request.getContextPath()%>/taghelp.html" target="_blank">
	<span class="welcomeimagefirst" onmouseout="className='welcomeimagefirst';" onmouseover="className='welcomeimagesecond';" title="51javaCms模板标签代码参考">
	</span><div><strong class="black">51javaCms模板标签代码参考</strong></div></a>
	<!--welcome image first end--> 
	<div class="viewfirst" onmouseout="className='viewfirst'" onmouseover="className='viewsecond'">
		<!--keyboard first begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="className='keyboardfirst'" onmouseover="className='keyboardsecond'">
				<span class="span_title">系统属性</span>
				<div>
					<span class="spanthree"></span>
					<!-- table list begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="30%">系统参数</td>
							<td class="tablelistfirst3r" width="70%">系统值</td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3ro" style="text-align:right;">51JAVACMS程序版本：</td>
					        <td class="tablelisttext3ro">51javacms 1.0.8</td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3rt" style="text-align:right;">操作系统版本：</td>
					        <td class="tablelisttext3rt"><%=System.getProperty("os.name")%>&nbsp;<%=System.getProperty("os.version")%></td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3ro" style="text-align:right;">操作系统类型：</td>
					        <td class="tablelisttext3ro"><%=System.getProperty("os.arch")%>
					        &nbsp;<%=System.getProperty("sun.arch.data.model")%>位</td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3rt" style="text-align:right;">JAVA运行版本：</td>
					        <td class="tablelisttext3rt">JDK&nbsp;<%=System.getProperty("java.version")%></td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3ro" style="text-align:right;">JAVA虚拟机：</td>
					        <td class="tablelisttext3ro"><%=System.getProperty("java.vm.name")%>&nbsp;<%=System.getProperty("java.vm.version")%></td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3rt" style="text-align:right;">可使用内存&nbsp;|&nbsp;剩余内存&nbsp;|&nbsp;最大可使用内存</td>
					        <td class="tablelisttext3rt"><%=Runtime.getRuntime().totalMemory()/1024/1024%>m
					        &nbsp;|&nbsp;<%=Runtime.getRuntime().freeMemory()/1024/1024%>m
					        &nbsp;|&nbsp;<%=Runtime.getRuntime().maxMemory()/1024/1024%>m</td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3ro" style="text-align:right;">web服务目录：</td>
					        <td class="tablelisttext3ro"><%=System.getProperty("user.dir")%></td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3rt" style="text-align:right;">浏览器信息：</td>
					        <td class="tablelisttext3rt"><%=request.getHeader("user-agent") %></td>
						</tr>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
					        <td class="tablelisttext3ro" style="text-align:right;">操作系统当前登陆用户：</td>
					        <td class="tablelisttext3ro"><%=System.getProperty("user.name")%></td>
						</tr>
					</table>
					<!-- table list end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keyboard first end-->	
	</div>
</div>
<!--maindiv end-->

