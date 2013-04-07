<%@page contentType="text/html;charset=utf-8"%>
<%@page import="util.Constant"%>
<%@page import="javabean.UserInfo"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		if (userInfo == null) {
			response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
			return;		
	}
	String path=(String)request.getAttribute("filepath");
	String filename=(String)request.getAttribute("filename");
	int type = (Integer)request.getAttribute("type");
	
%>

<!-- input hidden begin -->
<input type="hidden" id="filepath" value="<%=path%>"/>
<input type="hidden" id="old_filename" value="<%=filename%>"/>
<input type="hidden" id="type" value="<%=type%>"/>
<!-- input hidden end -->

<!--main div begin-->
<div id="maindiv" class="maindivelse">   
	<span class="linktextbutton">
		<span>当前的位置：文件管理&nbsp;\&nbsp;更改文件名</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
	<!--keboardfirst begin-->
	<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">
			<!-- 空格不能省略 -->
			<span class="span_select_left">&nbsp;&nbsp;&nbsp;</span>
			<span class="span_select_center">当前路径：<%if(type==Constant.FILE_PATH_TYPE){%>&lt;51java:webroot/&gt;<%=path%><%}else{%><%=path%><%}%></span>
			<span class="span_select_right">&nbsp;&nbsp;&nbsp;</span>
			</span>
		</div>
	<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
	<!--keboardfirst end-->	
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">更改文件名</span>
				<div>
					<span class="spanthree"></span>
					<!-- update password begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="userlist">
							<td class="userlisttext" width="20%">旧名称：</td>
							<td class="userlistinput" width="40%"><%=filename%></td>
							<td class="userlistoutput" width="30%">&nbsp;</td>
						</tr>
						<tr class="userlist">
							<td class="userlisttext">请输入新名称：</td>
							<td class="userlistinput"><input type="text" id="filename"></input></td>
							<td class="userlistoutput">&nbsp;新名称不能为空！不能改扩展名。</td>
						</tr>
					</table>
					<!-- update password end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input class="submitbutton" type="submit" onclick="editFileName();" onFocus="this.blur()" value="更 换"></input></span>
	<span><input class="submitbutton" type="button" onclick="toPrePage('0');" onFocus="this.blur()" value="返 回"></input></span> 
	<!--submitbutton end-->
</div>
<!--maindiv end-->