<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.Fragment"%>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%@page import="javabean.UserInfo"%>
<%UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
Fragment fragment = (Fragment)request.getAttribute("fragment");
	if(userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	String title = "新增";
	long fragmentId = 0;
	String content = "";
	if(fragment != null){
		title = "更改";
		fragmentId = fragment.getId();
		content = PubFun.getJsFilterValue(fragment.getContent());
	}
String searchName = request.getParameter("search_name");
	      if(searchName == null) searchName = "";
	      String pageNo = request.getParameter("page_no");
	      if(pageNo == null) pageNo = "1";
String selTxt = (String)request.getAttribute("selTxt");
if(selTxt==null){
	selTxt = "<select id=\"column_id\"><option value=\"-1\" style=\"background:#dcdcdc;\">所有栏目</option></select>";
}%>
<input id="search_name" type="hidden" size="16px" value="<%=searchName%>"></input>
<input id="page_no" type="hidden" size="16px" value="<%=pageNo%>"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：碎片管理&nbsp;\&nbsp;<%=title%>碎片</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title"><%=title%>碎片</span>
				<div>				
					<span class="spanthree"></span>
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="userlist">
							<td class="userlisttext" width="20%">碎片位名称：</td>
							<td class="userlistinput" width="40%">
								<input type="text" id="fragment_title" maxlength="50" value="<%if(title.equals("更改")){%><%=fragment.getTitle()%><%}%>"></input>
							</td>
							<td class="userlistoutput" width="30%">&nbsp;碎片位名称不能为空！</td>
						</tr>
						<tr class="userlist">
							<td class="userlisttext" width="20%">投放范围：</td>
							<td class="userlistinput" width="40%"><span id="sel_txt"><%=selTxt%></span></td>
							<td class="userlistoutput" width="30%">&nbsp;</td>
						</tr>
					</table>
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<div id="cu_channel" class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">碎片内容</span>
					<div>
						<span class="spanthree"></span>
						<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
							<tr class="tablelist">
								<td class="tablelistfirst3rt" width="100%">
								<textarea rows="12" cols="50" id="fragment_content"><%=PubFun.getJsFilterValue(content)%></textarea>
								</td>
							</tr>
						</table>					
					</div>			
				</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input type="submit" value="<%=title%>" onclick="editFragment(<%=fragmentId%>);" class="submitbutton"></input></span>
	<span><input type="button" value="取消" onclick="cancelEditFragment(<%=fragmentId%>);" class="submitbutton"></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->