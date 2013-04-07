<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Fragment" %>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	Fragment[] fragments = (Fragment[])request.getAttribute("fragments");%>
<input type="hidden" id="page" value="FragmentManagePage"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：碎片管理&nbsp;\&nbsp;碎片内容管理</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">关键字查询</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">请输入名称：</td>
						<%String fragmentTitle = request.getParameter("search_name");if(fragmentTitle == null) fragmentTitle = "";%>
						<td class="userlistinput" width="45%"><input id="search_name" type="text" value="<%=fragmentTitle%>"></input>
						&nbsp;<input class="submitbutton" type="submit" onclick="searchFragment();" value="查询"></input></td>
						<td class="userlistoutput" width="25%">
						<input type="button" onclick="exeRequest(rootUrl+'/jsp/fragment/edit_fragment.jsp', rightDivContent);" value="新增碎片"></input>
						</td>
					</tr>
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">碎片列表</span>
				<div>
				<span class="spanthree"></span>
				<!-- table list begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="tablelist">
						<td class="tablelistfirst3r" width="7%">请选择</td>
						<td class="tablelistfirst3r" width="5%">编号</td>
						<td class="tablelistfirst3r" width="39%">碎片位名称</td>
						<td class="tablelistfirst3r" width="30%">投放范围</td>
						<td class="tablelistfirst3r" width="19%">管理</td>
					</tr>
					<%if(fragments != null){
						String className = "tablelisttext3ro";
						for(int i=0;i<fragments.length;i++){%>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<%if((i%2)==0){
								className = "tablelisttext3ro";
							}else{
								className = "tablelisttext3rt";
							}%>
						<td class="<%=className%>"><input type="checkbox" name="checks_name" value="<%=fragments[i].getId()%>"></td>
						<td class="<%=className%>"><%=fragments[i].getId()%></td>
						<td class="<%=className%>"><a href="javascript:showJsFragment(<%=fragments[i].getId()%>,${pageNo});" title="点击预览">
						<%=fragments[i].getTitle()%></a></td>
						<td class="<%=className%>"><%=fragments[i].getColumnName()%></td>
					    <td class="<%=className%>">
					    	<a href="javascript:showJsFragment(<%=fragments[i].getId()%>,${pageNo});">预览</a>
				       		<a href="javascript:showEditFragment(<%=fragments[i].getId()%>,${pageNo});">更改</a>
				       		<a href="javascript:delFragment(<%=fragments[i].getId()%>,${pageNo});">删除</a>
				       	</td>					
					</tr>	
					<%}}%>
				</table>
				<!-- table list end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<span><%@ include file="../inc/page.jsp"%></span>
	<!--submitbutton begin-->	
    <span><input class="submitbutton" type="button" onclick="showEditFragment(0,${pageNo});" value="新增碎片"></input></span>
	<span><input type="button" onclick="exeRequest(rootUrl+'/MainCtrl?page=GenerateAllFragments', rightDivContent);" value="发布所有碎片" class="submitbutton"></input></span>
	<span><input type="button" value="批量删除" onclick="handleCheckeds('page=DeleteCheckedFragmentPage&fragments=','删除',${pageNo},${sumPage},12);" class="submitbutton"></input></span>	
	<span><input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/jsp/inc/welcome.jsp', rightDivContent);"  value="返回"></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->