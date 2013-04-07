<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Article" %>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%@page import="util.InitServlet"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	String columnId = request.getParameter("column_id");
	Article[] articles = (Article[])request.getAttribute("articles");
	if (userInfo==null || articles==null){
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}%>
<input type="hidden" id="page" value="SearchArticlePage" />
<!--main div begin-->
<div id="maindiv" class="maindivelse">
	<span class="linktextbutton">
		<span>当前的位置：<font color="purple">搜索</font></span>
	</span>

	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">（搜索）文章列表</span>
				<div>
					<span class="spanthree"></span>
					<!-- table list begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="5%">选择</td>
							<td class="tablelistfirst3r" width="5%">编号</td>
							<td class="tablelistfirst3r" width="38%">文章标题</td>
							<td class="tablelistfirst3r" width="12%">所属栏目</td>
							<td class="tablelistfirst3r" width="16%">管理</td>
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
							<td class="<%=className%>"><a href="javascript:showEditArticle(<%=articles[i].getId()%>,${pageNo},true);" >
							<%=articles[i].getTitle()%></a></td>
							<td class="<%=className%>"><%=articles[i].getColumnname()%></td>
						    <td class="<%=className%>">
						    	<a href="javascript:showEditArticle(<%=articles[i].getId()%>,${pageNo},true);">编辑</a>
					       		<a href="<%=request.getContextPath()%>/MainCtrl?page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.PREVIEW%>" target="_blank">预览</a>
					       		<a href="<%=request.getContextPath()%>/MainCtrl?page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.VIEW%>" target="_blank">查看</a>
					       		<a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.STATIC%>');">发布</a>				       		
					       		<a href="javascript:delSearchArticle(<%=articles[i].getId()%>,${pageNo});">删除</a>
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
	<span><input class="submitbutton" type="button" onclick="handleCheckeds('page=StaticArticleListPage&articles=','发布',${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>);"  value="批量发布"></input>&nbsp;&nbsp;</span>
	<span><input type="button" value="批量删除" onclick="handleCheckeds('page=DeleteCheckedArticlesPage&is_search=common&articles=','删除',${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>);" class="submitbutton"></input>&nbsp;&nbsp;</span>
	<span>
	<input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/jsp/inc/welcome.jsp',rightDivContent);" value="返回"></input>
	</span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->