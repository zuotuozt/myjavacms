<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Message " %>
<%@page import="util.Constant"%>
<%@page import="util.InitServlet"%>
<%/***************session fail****************************/
Message message = (Message)request.getAttribute("message");
	if(message==null){
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;	
	}
	String searchName = request.getParameter("search_name");
     if(searchName == null) searchName = "";
     String pageNo = request.getParameter("page_no");
     if(pageNo == null) pageNo = "1";%>
<input id="search_name" type="hidden" value="<%=searchName%>"></input>
<input id="page_no" type="hidden" value="<%=pageNo%>"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">   
	<span class="linktextbutton">
		<span>当前的位置：系统管理&nbsp;\&nbsp;模板&nbsp;\&nbsp;留言管理&nbsp;\&nbsp;回复留言</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">回复留言</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">留言标题：</td>
						<td class="userlistinput"  width="40%">
							<input id="title" type="text" value="<%=message.getTitle() %>" ></input>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;留言标题不能为空。</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">留言内容：</td>
						<td class="userlistinput" width="40%">
							<div class="anyinput">
							<textarea id="content"  rows="6" cols="70" maxlength="255" ><%=message.getMessage()  %></textarea>
							</div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;留言内容不能为空。</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">回复内容：</td>
						<td class="userlistinput" width="40%">
							<div class="anyinput">
							<textarea id="reply"  rows="6" cols="70" maxlength="255" ><%=message.getReply()==null?"":message.getReply()  %></textarea>
							</div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;回复内容不能为空。</td>
					</tr>				
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>
		<!--keboardfirst end--> 	
	<span><input type="button" value="回复" onclick="submitMsgReply(<%=message.getId() %>)" class="submitbutton"></input></span>
	<span><input type="button" value="取消" onclick="cancelReplyMessage();" class="submitbutton"></input></span>				
	</div>
	</div>