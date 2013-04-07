<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		if (userInfo == null) {
			response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
			return;		
	}
	String path=(String)request.getAttribute("path");
	int type =(Integer)request.getAttribute("type");
	%>


<!-- input hidden begin -->
<input type="hidden" id="filepath" value="<%=path%>"/>
<input type="hidden" id="type" value="<%=type%>"/>
<!-- input hidden end -->

<!--main div begin-->
<div id="maindiv" class="maindivelse">
	<span class="linktextbutton">
		<span>当前的位置：文章附件管理&nbsp;\&nbsp;新建目录</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
	<!--keboardfirst begin-->
	<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">
			<!-- 空格不能省略 -->
			<span class="span_select_left">&nbsp;&nbsp;&nbsp;</span>
			<span class="span_select_center">当前路径：<%if(type==Constant.FILE_PATH_TYPE){%>&lt;51java:webroot/&gt;<%=path%><%}else{%><%=path%><%}%>
			</span><span class="span_select_right">&nbsp;&nbsp;&nbsp;</span>
			</span>
		</div>
	<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
	<!--keboardfirst end-->	
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">新建目录</span>
				<div>				
					<span class="spanthree"></span>
					<!-- create file path begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="userlist">
							<td class="userlisttext" width="20%">请输入新目录：</td>
							<td class="userlistinput" width="40%"><input type="text" id="newpath" /></td>
							<td class="userlistoutput" width="30%">&nbsp;新目录不能为空！</td>
						</tr>
					</table>
					<!-- create file path end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input class="submitbutton" type="submit" onclick="createPath();" onFocus="this.blur()" value="创建"></input></span> 
	<span><input class="submitbutton" type="button" onclick="toPrePage('0');" onFocus="this.blur()" value="返回"></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->