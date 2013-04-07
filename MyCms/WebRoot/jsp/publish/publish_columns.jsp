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
	String selTxt = (String)request.getAttribute("selTxt");
	if(selTxt==null) selTxt = "";%>
<input type="hidden" id="col_checkbox_id" value="0"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：文章发布&nbsp;\&nbsp;栏目发布</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">栏目发布</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">选择栏目：</td>
						<td width="30%">
		<div id="treemenulist">
	    	<div class="menulist"><a href="#x" onclick="showInfoZone($('sel_div'),'col_info_zone','col_info_zone_bg');">
	    	<div class="menubox" id="sel_div">
	    	<span class="menulistico"><img src="img/main/add.png" style="border:0px"/></span>
	    	<span class="menutitle" id="sheet_column" style="font-size:12px;">请选择。。。</span></div></a>
    	</div></div>
		</td><td class="userlistoutput" width="40%">&nbsp;</td>
					</tr>
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>
		<!--keboardfirst end--> 	
	</div>
	<!--submitbutton begin-->	
	<span><input type="button" onclick="staticColumns();" value="发布栏目" class="submitbutton"></input></span>
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

