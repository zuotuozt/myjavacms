<%@page contentType="text/html;charset=utf-8"%>
<%@page import="util.Constant"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Fragment" %>
<%UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
 Fragment fragment = (Fragment)request.getAttribute("fragment");

	if((userInfo == null)||(fragment == null)) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}%>
<!--main div begin-->
<div id="maindiv" class="maindivelse"> 
<span class="linktextbutton">
	<span>当前的位置：<a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=FragmentManagePage');">碎片管理</a>
&nbsp;\&nbsp;预览：<%=fragment.getTitle()%></span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">预览碎片（<%=fragment.getTitle()%>）</span>
				<div>
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3rt" width="100%">
								<div height="200px;" style="overflow:auto;"><%=fragment.getContent()%></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">（<%=fragment.getTitle()%>）碎片嵌入模板中的代码：</span>
				<div>
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3rt" width="100%">
								<textarea rows="2" cols="100" readonly="readonly"><script src="&lt;51java:webroot/&gt;<%=Constant.FRAGMENT_PATH%>/<%=fragment.getId()%>.js" language="javascript"></script>
								</textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input type="button" value="返回" onclick="exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=FragmentManagePage&fragment_id=<%=fragment.getId()%>&page_no=<%=request.getParameter("page_no")%>');" class="submitbutton"></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->