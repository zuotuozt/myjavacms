<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Col" %>
<%@page import="javabean.Article" %>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%@page import="util.InitServlet"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	String columnId = request.getParameter("column_id");
	Article[] articles = (Article[])request.getAttribute("articles");
	Col[] cols = (Col[])request.getAttribute("cols");
	if (userInfo==null || articles==null){
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}%>
<input type="hidden" id="page" value="ArticleManagePage&column_id=<%=columnId %>"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">
	<span class="linktextbutton">
		<span>当前的位置：<%StringBuilder location = new StringBuilder("");
		  if(cols!=null){
            for(int i=cols.length-1; i>=0; i--){
           		location.append("&nbsp;/&nbsp;"+cols[i].getName());	
            }}%><%=location %>
	</span>
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
						<td class="userlistinput" width="8%">
						&nbsp;<input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=ShowEditArticlePage&col_id=<%=columnId%>');"
						 value="新增文章" style="color:blue;font-weight:bold;"></input></td>
						<td class="userlisttext" width="7%">文章标题：</td>
						<td class="userlistinput" width="55%">
						<input id="search_name" type="text" value="<%=request.getParameter("search_name")==null?"":request.getParameter("search_name")%>"></input>
						<input class="submitbutton" type="submit" value="查询" onClick="searchArticle(<%=columnId%>);" />&nbsp;
						<input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=StaticPage&col_id=<%=columnId%>');" value="更新栏目页" />&nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/MainCtrl?page=ViewPage&col_id=<%=columnId%>&is_popup=true" target="_blank">
						<font style="color:blue;font-weight:bold;">查看栏目页</font></a></td>
						<td class="userlisttext" width="20%">&nbsp;</td>
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
				<span class="span_title">（ <%=location %>）栏目所属的文章列表</span>
				<div>
					<span class="spanthree"></span>
					<!-- table list begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="5%">选择</td>
							<td class="tablelistfirst3r" width="5%">编号</td>
							<td class="tablelistfirst3r" width="5%">排序</td>
							<td class="tablelistfirst3r" width="41%">文章标题</td>
							<td class="tablelistfirst3r" width="20%">管理</td>
							<td class="tablelistfirst3r" width="13%">录入时间</td>
							<td class="tablelistfirst3r" width="13%">录入员</td>
						</tr>
						<%if(articles != null){
						String className = "tablelisttext3ro";
						for (int i = 0; i < articles.length; i++){%>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<%if((i%2)==0){
								className = "tablelisttext3ro";
							}else{
								className = "tablelisttext3rt";
							}%>
							<td class="<%=className%>"><input type="checkbox" name="checks_name" value="<%=articles[i].getId()%>"></td>
					        <td class="<%=className%>"><%=articles[i].getId()%></td>
					        <td class="<%=className%>"><%=articles[i].getOrdercnt()%></td>
							<td class="<%=className%>"><a href="javascript:showEditArticle(<%=articles[i].getId()%>,${pageNo});" >
							<%=articles[i].getTitle()%><%if(articles[i].isTop()){%>&nbsp;<img src="img/main/up.gif" border="0px" /><%}%></a></td>
						    <td class="<%=className%>">
						    	<a href="javascript:showEditArticle(<%=articles[i].getId()%>,${pageNo});">编辑</a>
					       		<a href="<%=request.getContextPath()%>/MainCtrl?page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.PREVIEW%>" target="_blank">预览</a>
					       		<a href="<%=request.getContextPath()%>/MainCtrl?page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.VIEW%>" target="_blank">查看</a>
					       		<a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.STATIC%>');">发布</a>				       		
					       		<a href="javascript:delArticle(<%=articles[i].getId()%>,<%=columnId%>,${pageNo});">删除</a>
					       </td>
					       <td class="<%=className%>"><%=PubFun.getDateTime("yyyy-MM-dd HH:mm:ss",articles[i].getCreatime())%></td>
					       <td class="<%=className%>"><%=articles[i].getCreatorName()%></td>
						</tr><%}}%>
					</table>
					<!-- table list end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<span><%@ include file="../inc/page.jsp"%></span>
	<!--submitbutton begin-->
	<span><input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=ShowEditArticlePage&col_id=<%=columnId%>');" value="新增文章" style="color:blue;font-weight:bold;"></input>&nbsp;&nbsp;</span>	
	<span><input class="submitbutton" type="button" onclick="handleCheckeds('page=StaticArticleListPage&column_id=<%=columnId %>&articles=','发布',${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>);"  value="批量发布"></input>&nbsp;&nbsp;</span>
	<span><input type="button" value="批量删除" onclick="handleCheckeds('page=DeleteCheckedArticlesPage&column_id=<%=columnId %>&articles=','删除',${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>);" class="submitbutton"></input>&nbsp;&nbsp;</span>
	<span>
	<input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/jsp/inc/welcome.jsp',rightDivContent);" value="返回"></input>
	</span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->