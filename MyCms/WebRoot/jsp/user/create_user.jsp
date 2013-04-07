<%@page contentType="text/html;charset=utf-8"%>
<%@page import="util.Constant"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Department "%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}%>
<!--main div begin-->
<div id="maindiv" class="maindivelse"> 
	<span class="linktextbutton">
		<span>当前的位置：系统管理&nbsp;\&nbsp;用户管理&nbsp;\&nbsp;
		<font color="red">新增用户</font></span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">新增用户</span>
				<div>
				<span class="spanthree"></span>
					<!-- update password begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="userlist">
							<td class="userlisttext" width="20%">用户名：</td>
							<td class="userlistinput" width="40%"><input type="text" id="user_name" maxlength="20"></input>
							&nbsp;<input type="button" value="检查用户名" onclick="checkUserName();"></input></td>
							<td class="userlistoutput" width="30%">用户名不能重复和空；但可以是中文。</td>
						</tr>
						<tr class="userlist">
							<td class="userlisttext">用户别名：</td>
							<td class="userlistinput"><input type="text" id="alias"></input></td>
							<td class="userlistoutput">可以为空。</td>
						</tr>
						<tr class="userlist">
							<td class="userlisttext">所属部门：</td>
							<td class="userlistinput">
								<select id="dep_id">
								<option value="0">&nbsp;</option>
						     <%Department[] departmentList=(Department[])request.getAttribute("departmentList");
							if(departmentList!=null){
								for(int i=0;i<departmentList.length;i++){%>
								<option value="<%=departmentList[i].getId() %>">
								<%=departmentList[i].getDepName() %></option>
						  	<%}}%></select>
							</td>
							<td class="userlistoutput">必须选择。</td>
						</tr>
					</table>
					<!-- update password end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>

	<div id="cu_channel" class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">用户权限角色</span>
				<div>
					<span class="spanthree"></span>
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="25%"><input type="checkbox" id="is_article" value="false" onclick="this.value=this.checked;"/>文章编辑</td>
							<td class="tablelistfirst3r" width="25%"><input type="checkbox" id="is_ad" value="false" onclick="this.value=this.checked;"/>广告管理</td>
							<td class="tablelistfirst3r" width="25%"><input type="checkbox" id="is_publish" value="false" onclick="this.value=this.checked;"/>文章发布</td>
							<td class="tablelistfirst3r" width="25%"><input type="checkbox" id="is_column" value="false" onclick="this.value=this.checked;"/>栏目管理</td>
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
				<span class="span_title">用户频道权限选择</span>
				<div>
					<span class="spanthree"></span>
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="userlist">
							<td class="userlisttext" width="20%">选择频道：</td>
							<td class="userlistinput">
								<div id="treemenulist">
							    	<div class="menulist" id="sel_div">
							    	<a href="#x" onclick="openZjh('previewItem','makeSureItem','selectSub',$('sel_div'));">
							    	<div class="menubox">
							    	<span class="menulistico"><img src="img/main/add.png" style="border:0px"/></span>
							    	<span class="menutitle" id="sheet_column" style="font-size:12px;">请选择</span></div></a>
							    </div></div>
							</td>
						</tr>
						<tr class="userlist">
							<td class="userlisttext">您已选择的频道汇总：</td>
							<td class="userlistinput"><div class="selectSub" id="makeSureItem"></div></td>
						</tr>
					</table>
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input type="submit" value="新  增" onclick="addUser();" class="submitbutton"></input></span>
	<span><input type="button" value="取 消" onclick="exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=UserManagePage&userID=<%=userInfo.getId()%>');" class="submitbutton" ></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->
<%String selTxt = (String)request.getAttribute("selTxt");
	if(selTxt==null) selTxt="";%>
<div id="col_info_zone" class="infofloatzone">
	<div class="inputzoneinfo">
		<div class="inputzoneinfotitlecenter"><font class="floatlinktext"><font id="info_title_name">请选择频道</font>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="makeSure('previewItem','makeSureItem','selectSub');">【确定】</a></font><span class="inputzoneti"></span></div>
		<div class="inputzoneexit">
			<a title="取消" id="input_zone_info_close" class="exitout" onclick="closeUserInfo('col_info_zone','col_info_zone_bg');return false;" onmouseover="this.className='exitmove';" onmouseout="this.className='exitout';" href="#" ></a>
		</div><%=selTxt%><div class="floatline"></div>
		<div class="inputzoneinfotitlecenter"><font class="floatlinktext"><font id="info_title_name">您已选择的频道</font></font></div>
		<div class="selectSub" id="previewItem"></div>
	</div>
</div>
<div id="col_info_zone_bg">&nbsp;</div>