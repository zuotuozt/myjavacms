<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Message" %>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%@page import="util.InitServlet"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
   Message[] messages = (Message[])request.getAttribute("messages");
	if (userInfo==null){
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}%>
<!--main div begin-->
<input type="hidden" id="page" value="MessageManagePage"></input>
<div id="maindiv" class="maindivelse">
	<span class="linktextbutton">
		<span>当前的位置：系统管理&nbsp;\&nbsp;模板&nbsp;\&nbsp;留言管理
	</span>
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
						<td class="userlisttext" width="20%">请输入留言标题：</td>
						<%String messageName=request.getParameter("search_name");if(messageName==null) messageName="";%>
						<td class="userlistinput" width="40%">
						<input id="search_name" type="text" value="<%=messageName%>"></input>
						<input class="submitbutton" type="submit" value="查询" 
onclick="exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=MessageManagePage&search_name='+encodeURIComponent($('search_name').value.trim()));"></input></td>
						<td class="userlisttext" width="40%">&nbsp;</td>
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
				<span class="span_title">留言列表</span>
				<div>
					<span class="spanthree"></span>
					<!-- table list begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="5%">选择</td>
							<td class="tablelistfirst3r" width="5%">编号</td>
							<td class="tablelistfirst3r" width="30%">留言标题</td>
							<td class="tablelistfirst3r" width="13%">留言时间</td>
							<td class="tablelistfirst3r" width="10%">来自ip</td>
							<td class="tablelistfirst3r" width="7%">回复状态</td>
							<td class="tablelistfirst3r" width="13%">回复时间</td>
							<td class="tablelistfirst3r" width="9%">回复人</td>
							<td class="tablelistfirst3r" width="8%">管理</td>
						</tr>
						<%if(messages != null){
						String className = "tablelisttext3ro";
						for (int i = 0; i < messages.length; i++){%>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<%if((i%2)==0){
								className = "tablelisttext3ro";
							}else{
								className = "tablelisttext3rt";
							}%>
							<td class="<%=className%>"><input type="checkbox" name="checks_name" value="<%=messages[i].getId()%>"></td>
					        <td class="<%=className%>"><%=messages[i].getId()%></td>
							<td class="<%=className%>"><a href="javascript:showReplyMessage(<%=messages[i].getId()%>,${pageNo});" ><%=messages[i].getTitle() %></a></td>
					       <td class="<%=className%>"><%=PubFun.getDateTime("yyyy-MM-dd HH:mm:ss",messages[i].getCreatime())%></td>
					       <td class="<%=className%>"><%=messages[i].getIp() %></td>
					       <td class="<%=className%>"><%=messages[i].isReplied()?"已回复":"<font color=\"red\">未回复</font>" %></td>
					       <td class="<%=className%>"><%=messages[i].getReplytime()==null?"":PubFun.getDateTime("yyyy-MM-dd HH:mm:ss",messages[i].getReplytime()) %></td>
					       <td class="<%=className%>"><%=messages[i].getReplyName()==null?"":messages[i].getReplyName()%></td>
					       <td class="<%=className%>"><a href="javascript:showReplyMessage(<%=messages[i].getId()%>,${pageNo});">回复</a>
					       		&nbsp;<a href="javascript:delMessage(<%=messages[i].getId()%>,${pageNo});">删除</a>
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
	<span><input type="button" value="批量删除" onclick="handleCheckeds('page=DeleteCheckedMessagesPage&messages=','删除',${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>);" class="submitbutton"></input></span>
	<span>
	<input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/jsp/inc/welcome.jsp',rightDivContent);" value="返回"></input>
	</span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->