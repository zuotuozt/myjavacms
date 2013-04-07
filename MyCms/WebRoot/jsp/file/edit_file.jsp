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
	String contents = (String)request.getAttribute("content");
	String path = (String)request.getAttribute("filepath");
	String fileName = (String)request.getAttribute("filename");
	int type =(Integer)request.getAttribute("type");%>
<!--main div begin-->
<div id="maindiv" class="maindivelse">   
    <input type="hidden" id="filepath" value="<%=path%>"/>
    <input type="hidden" id="filename" value="<%=fileName%>"/>
    <input type="hidden" id="type" value="<%=type%>"/>
	<span class="linktextbutton">
		<span>当前的位置：<%if(type==Constant.FILE_PATH_TYPE){%><a href="javascript:exeRequest(rootUrl+'/MainCtrl', rightDivContent, 'page=FileListPage');">文章附件管理<%}else{%><a href="javascript:exeRequest(rootUrl+'/MainCtrl', rightDivContent, 'page=FileListPage&type=<%=Constant.TEMPLATE_PATH_TYPE%>');">模板管理<%}%></a>&nbsp;\&nbsp;<%if(type==Constant.FILE_PATH_TYPE){%>编辑文件<%}else{%>编辑模板<%}%></span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title"><%if(type==Constant.FILE_PATH_TYPE){%>文件<%}else{%>模板<%}%>管理</span>
			<div>	
				<span class="spanthree"></span>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">文件路径：</td>
						<td class="userlistinput" width="40%">
                            <%if(type==Constant.FILE_PATH_TYPE){
                            %><input type="text" id="channel_name" maxlength="50" value="<51java:webroot/><%=path%>" disabled="disabled"></input>
                            <%}else{%><input type="text" id="channel_name" maxlength="50" value="<%=path%>" disabled="disabled"></input>
                            <%}%>							
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext">文件名称：</td>
						<td class="userlistinput">
						<input type="text" id="channel_path" maxlength="20" value="<%=fileName%>" 
						<%if(!fileName.equals("")){%>disabled="disabled"<%}%>></input>
					</td>
						<td class="userlistoutput"><%if(type==Constant.TEMPLATE_PATH_TYPE){%>文件扩展名必须是.jsp<%}%></td>
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
				<span class="span_title"><%if(type==Constant.FILE_PATH_TYPE){%>文件<%}else{%>模板<%}%>内容</span>
				<div>
					<span class="spanthree"></span>
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3rt" width="100%">
								<textarea rows="18" cols="100" id="template"><%=contents%></textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->
    <%if(path.equals(Constant.FRAGMENT_PATH)){%>
    		<span><input type="button" value="取消" onclick="cancelEditFile()" class="submitbutton" ></input></span>
    	<%}else{%>
        	<span><input type="submit" <%if(fileName.equals("")){%>value="增加"<%}else{%>value="修改"<%}%>
        	onclick="editFileContent()" class="submitbutton" ></input></span>
	        <span><input type="button" value="取消" onclick="cancelEditFile()" class="submitbutton" ></input></span>
        <%}%>
	<!--submitbutton end-->
</div>
<!--maindiv end-->