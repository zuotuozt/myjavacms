<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%@page import="javabean.Department"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	Department[] departments=(Department[])request.getAttribute("departments");%>
<!--main div begin-->
<div id="maindiv" class="maindivelse"> 
	<span class="linktextbutton">
		<span>当前的位置：系统管理&nbsp;\&nbsp;用户&nbsp;\&nbsp;部门管理</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">部门管理</span>
				<div>
					<span class="spanthree"></span>
					<!-- table list begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="20%">部门ID</td>
							<td class="tablelistfirst3r" width="50%">部门名称</td>
							<td class="tablelistfirst3r" width="30%">操作处理</td>
						</tr>
						<%if(departments != null){
							String className = "tablelisttext3ro";
							for (int i=0;i<departments.length;i++){%>
							<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
								<%if((i%2)==0){
									className="tablelisttext3ro";
								}else{
									className="tablelisttext3rt";
								}%>
					        <td class="<%=className%>"><%=departments[i].getId()%></td>
							<td class="<%=className%>"><%=departments[i].getDepName()%></td>
						    <td class="<%=className%>">
					       		<a href="#" onclick="showUpdateSys(this);" title="修改" name="<%=departments[i].getDepName()%>" id="<%=departments[i].getId()%>">修改</a>
					       		&nbsp;|&nbsp;&nbsp;<a href="javascript:delDepartment(<%=departments[i].getId()%>);">删除</a>
					       </td>
						</tr><%}}%>
					</table>
					<!-- table list end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<!--submitbutton begin-->	
	<span><input class="submitbutton" type="button" onclick="showUpdateSys(this);" title="新增" value="新增部门"></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->

<!-- float input zone begin -->
<div id="input_zone">
<input type="hidden" id="hidden_param_value"></input>
<input type="hidden" id="hidden_param_id"></input>
	<div class="inputzoneinfo">
		<div class="inputzoneinfotitle"><font class="floatlinktext"><label id="param_id"></label>部门：</font><span class="inputzoneti"></span></div>
		<div class="inputzoneexit">
			<a title="安全退出" id="input_zone_info_close" class="exitout" onclick="closeSys();" onmouseover="this.className='exitmove';" onmouseout="this.className='exitout';" href="#"></a>
		</div>
		<div class="inputzoneinserttext">部门名称：<input type="text" id="param_value"></input></div>
		<div class="inputzonebutton">
			<span><input class="submitbutton" type="submit" onclick="updateDepartment();" value="确定"></input></span>
			<span><input class="submitbutton" type="button" onclick="closeSys();" value="返回"></input></span>
		</div>
	</div>
</div>
<div id="input_zone_bg"></div>   
<!-- float input zone end -->



