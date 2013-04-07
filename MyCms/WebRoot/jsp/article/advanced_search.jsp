<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Article" %>
<%@page import="util.Constant,util.InitServlet"%>
<%@page import="util.PubFun,java.util.Calendar"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	String columnId = request.getParameter("column_id");
	Article[] articles = (Article[])request.getAttribute("articles");
	if (userInfo==null){
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;	
	}%>
<input type="hidden" id="page" value="AdvancedSearchPage" />
<!--main div begin-->
<div id="maindiv" class="maindivelse">
	<span class="linktextbutton">
		<span>当前的位置：<font color="purple">高级搜索</font></span>
	</span><form>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">高级搜索</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">文章标题：</td>
						<td width="30%"><input type="text" id="search_name" 
						value="<%=request.getParameter("search_name")==null?"":request.getParameter("search_name")%>" /></td>
						<td class="userlistoutput" width="40%">默认：全部</td>
					</tr><%Calendar c = Calendar.getInstance();c.add(Calendar.MONTH, -1); %>
					<tr class="userlist">
						<td class="userlisttext" width="20%">开始时间：</td>
						<td width="30%">
							<input type="text" id="f_date1" disabled="disabled"
							value="<%=request.getParameter("begin_date")==null?PubFun.getDateTime("yyyy-MM-dd",c.getTime()):request.getParameter("begin_date")%>" />&nbsp;
							<button id="f_btn1">...</button>&nbsp;
							<button onclick="$('f_date1').value='';">清除</button>
						</td>
						<td class="userlistoutput" width="40%">默认：一个月前</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">结束时间：</td>
						<td width="30%">
							<input type="text" id="f_date2" disabled="disabled"
							value="<%=request.getParameter("end_date")==null?PubFun.getDateTime("yyyy-MM-dd",null):request.getParameter("end_date")%>" />&nbsp;
							<button id="f_btn2">...</button>&nbsp;
							<button onclick="$('f_date2').value='';">清除</button>
						</td>
						<td class="userlistoutput" width="40%">默认：今天</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">文章作者：</td>
						<td width="30%"><input type="text" id="author"
						value="<%=request.getParameter("author")==null?"":request.getParameter("author")%>" /></td>
						<td class="userlistoutput" width="40%">默认：全部</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">文章编辑：</td>
						<td width="30%"><input type="text" id="editor" 
						value="<%=request.getParameter("editor")==null?"":request.getParameter("editor")%>" /></td>
						<td class="userlistoutput" width="40%">默认：全部</td>
					</tr>
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>
		<!--keboardfirst end--> 	
	</div><!--submitbutton begin-->
	<p><input type="button" onclick="advancedSearch();"  value="高级搜索" />
		&nbsp;&nbsp;&nbsp;<input type="reset" value="重 置" />
	</p></form>
	<%if(articles!=null){%>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">（高级搜索）文章列表</span>
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
						</tr><%String className="tablelisttext3ro";
						for(int i=0;i<articles.length;i++){%>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<%if((i%2)==0){
								className = "tablelisttext3ro";
							}else{
								className = "tablelisttext3rt";
							}%>
							<td class="<%=className%>"><input type="checkbox" name="checks_name" value="<%=articles[i].getId()%>"></td>
					        <td class="<%=className%>"><%=articles[i].getId()%></td>
							<td class="<%=className%>"><a href="javascript:showEditArticle(<%=articles[i].getId()%>,${pageNo});" >
							<%=articles[i].getTitle()%></a></td>
							<td class="<%=className%>"><%=articles[i].getColumnname()%></td>
						    <td class="<%=className%>">
						    	<a href="javascript:showEditArticle(<%=articles[i].getId()%>,${pageNo});">编辑</a>
					       		<a href="<%=request.getContextPath()%>/MainCtrl?page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.PREVIEW%>" target="_blank">预览</a>
					       		<a href="<%=request.getContextPath()%>/MainCtrl?page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.VIEW%>" target="_blank">查看</a>
					       		<a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=StaticArticlePage&article_id=<%=articles[i].getId()%>&flg=<%=Constant.STATIC%>');">发布</a>				       		
					       		<a href="javascript:delAdvancedSearch(<%=articles[i].getId()%>,${pageNo});">删除</a>
					       </td>
					       <td class="<%=className%>"><%=PubFun.getDateTime("yyyy-MM-dd HH:mm:ss",articles[i].getCreatime())%></td>
					       <td class="<%=className%>"><%=articles[i].getCreatorName()%></td>
						</tr><%}%>
					</table>
					<!-- table list end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<%if(articles.length>0){%><span><%@ include file="../inc/page.jsp"%></span>
	<!--submitbutton begin-->
	<span><input class="submitbutton" type="button" onclick="handleCheckeds('page=StaticArticleListPage&articles=','发布',${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>);"  value="批量发布"></input>&nbsp;&nbsp;</span>
	<span><input type="button" value="批量删除" onclick="delAdvancedSearchs(${pageNo},${sumPage},<%=InitServlet.PAGE_SIZE%>,'advanced');" class="submitbutton"></input>&nbsp;&nbsp;</span>
	<%}}%>
	<span><input class="submitbutton" type="button" onclick="exeRequest(rootUrl+'/jsp/inc/welcome.jsp',rightDivContent);" value="返回"></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->