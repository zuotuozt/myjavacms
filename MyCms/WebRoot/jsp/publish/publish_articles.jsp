<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%@page import="javabean.Col,java.util.Calendar" %>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	String selTxt = (String)request.getAttribute("selTxt");
	if(selTxt==null) selTxt = "";%>
<input type="hidden" id="col_checkbox_id" value="0"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：文章发布&nbsp;\&nbsp;文章发布</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">栏目文章发布</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">选择栏目：</td>
						<td width="30%">
		<div id="treemenulist">
	    	<div class="menulist"><a href="#x" onclick="showInfoZone($('sel_div'),'col_info_zone','col_info_zone_bg');">
	    	<span class="menubox" id="sel_div">
	    	<span class="menulistico"><img src="img/main/add.png" style="border:0px"/></span>
	    	<span class="menutitle" id="sheet_column" style="font-size:12px;">请选择。。。</span></span></a>
    	</div></div>
		</td><td class="userlistoutput" width="40%">&nbsp;</td>
					</tr><%Calendar c = Calendar.getInstance();c.add(Calendar.MONTH, -1); %>
					<tr class="userlist">
						<td class="userlisttext" width="20%">开始时间：</td>
						<td width="30%">
							<input type="text" id="f_date1" disabled="disabled"
							value="<%=request.getParameter("f_date1")==null?PubFun.getDateTime("yyyy-MM-dd",c.getTime()):request.getParameter("f_date1")%>" />&nbsp;
							<button id="f_btn1">...</button>&nbsp;
							<button onclick="$('f_date1').value='';">清除</button>
						</td>
						<td class="userlistoutput" width="40%">默认：一个月前</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">结束时间：</td>
						<td width="30%">
							<input type="text" id="f_date2" disabled="disabled"
							value="<%=request.getParameter("f_date2")==null?PubFun.getDateTime("yyyy-MM-dd",null):request.getParameter("f_date2")%>" />&nbsp;
							<button id="f_btn2">...</button>&nbsp;
							<button onclick="$('f_date2').value='';">清除</button>
						</td>
						<td class="userlistoutput" width="40%">默认：今天</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">每次生成：</td>
						<td width="30%">
							<input type="text" id="page_size" value="50"></input>个文件
						</td>
						<td class="userlistoutput" width="40%">&nbsp;默认：50</td>
					</tr>
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>
		<!--keboardfirst end--> 	
	</div>
	<!--submitbutton begin-->	
	<span><input type="button" onclick="staticColumnsHtml($('col_checkbox_id').value);" value="发布文章" class="submitbutton"></input></span>
	<!--submitbutton end-->
</div>
<div id="col_info_zone" class="infofloatzone">
	<div class="inputzoneinfo">
		<div class="inputzoneinfotitlecenter"><font class="floatlinktext"><font id="info_title_name">被选择的栏目列表</font>&nbsp;&nbsp;√请在要选择的栏目打勾&nbsp;&nbsp;</font><span class="inputzoneti"></span></div>
		<div class="inputzoneexit">
			<a title="安全退出" id="input_zone_info_close" class="exitout" onclick="closeUserInfo('col_info_zone','col_info_zone_bg');" onmouseover="this.className='exitmove';" onmouseout="this.className='exitout';" href="#" ></a>
		</div><%=selTxt%>
	</div>
</div>
<div id="col_info_zone_bg">&nbsp;</div>
<!--maindiv end-->

