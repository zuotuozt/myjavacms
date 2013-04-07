<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%@page import="javabean.Col" %>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	Col[] cols = (Col[])request.getAttribute("cols");%>
<!--main div begin-->
<div id="maindiv" class="maindivelse">   
	<span class="linktextbutton">
		<span>当前的位置：文章发布&nbsp;\&nbsp;首页发布</span>
	</span><%if(cols!= null){
	for(int i=0;i<cols.length;i++){%>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">首页发布</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%"><%=cols[i].getName()%>：</td>
						<td width="30%" style="font-size:12px;font-weight:bold;" align="center">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="<%=request.getContextPath()%>/MainCtrl?page=PreviewPage&col_id=<%=cols[i].getId()%>&is_popup=true" target="_blank">预览首页</a>
						</td>
						<td class="userlistoutput" width="40%" style="font-weight:bold;">
						<a href="javascript:exeRequest(rootUrl+'/MainCtrl?page=StaticPage&col_id=<%=cols[i].getId()%>',rightDivContent);">发布首页</a>
						</td>
					</tr>
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>
		<!--keboardfirst end--> 	
	</div>
	<%}}%>
</div>
<!--maindiv end-->

