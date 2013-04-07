<%@page contentType="text/html;charset=utf-8"%>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%@page import="javabean.UserInfo"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	String contents = (String)request.getAttribute("content");%>
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：系统管理&nbsp;\&nbsp;系统配置&nbsp;\&nbsp;文章敏感词编辑</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">编辑文件</span>
			<div>	
				<span class="spanthree"></span>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr class="userlist">
						<td class="userlisttext">文件名称：</td>
						<td class="userlistinput">
						<input type="text" id="channel_path" maxlength="20" value="words.properties" disabled="disabled"></input>
					</td>
						<td class="userlistoutput">&nbsp;</td>
					</tr>
				</table>
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<div id="cu_channel" class="viewfirst">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardsecond">
				<span class="span_title">文件内容<font color="red">（一行代表一个敏感词）</font></span>
				<div>
					<span class="spanthree"></span>
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3rt" width="100%">
								<textarea rows="20" cols="100" id="content"><%=contents%></textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->
        	<span><input type="submit" value="修改" 	onclick="editWords();" class="submitbutton" ></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->