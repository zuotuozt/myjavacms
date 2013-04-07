<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Col" %>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	String display = (String)request.getAttribute("display");%>
<input type="hidden" id="col_parentId" value="<%=request.getAttribute("parentId") %>"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">   
	<span class="linktextbutton">
		<span>当前的位置：栏目管理</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">栏目列表</span>
				<div>
				<span class="spanthree"></span>
				<!-- table list begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="tablelist">
						<td class="tablelistfirst3r" width="5%">编号</td>
						<td class="tablelistfirst3r" width="53%">栏目名称</td>
						<td class="tablelistfirst3r" width="42%">管理</td>
						</tr>
						<%=display%>
				</table>
				<!-- table list end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/jsp/inc/welcome.jsp', rightDivContent);"  value="返回"></input></span>

	<!--submitbutton end-->
</div>
<!--maindiv end-->

