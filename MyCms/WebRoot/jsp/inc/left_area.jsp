<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {%>
		<script type="text/javascript">
		location = rootUrl + '/sign_in.jsp;';
		</script>
		<%return;		
	}
	String type = request.getParameter("type")==null?"publish":request.getParameter("type");%>
	<div class="menulistsecond"><ul>
	<%if(type.equals("fragment")){ %>
		<li><a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=FragmentManagePage');">碎片管理</a></li>
	<%}else if(type.equals("publish")){ %>
		<li><a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=PublishIndexPage');">首页发布</a></li>
		<li><a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=PublishArticlesPage&is_article=false');">栏目发布</a></li>
		<li><a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=PublishArticlesPage');">文章发布</a></li>
   	<%}else if(type.equals("column")){%>
		<li><a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=ColManagePage&parentId=<%=Constant.HOME_CLOUMN %>');">栏目管理</a></li>
		<li><a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=ShowColEditPage&parentId=<%=Constant.HOME_CLOUMN %>');">增加新栏目</a></li>
   	<%}%></ul>
   	<%if(type.equals("system")){%>
	<div id="my_menu" class="sdmenu">
		<div><span>用户</span><%if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR)){%>
		      <a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=UserManagePage');">用户管理</a>
		      <a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=DepManagePage');">部门管理</a><%}%>
		  	  <a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=ChangePasswordPage&user_id=<%=userInfo.getId()%>');" class="current">密码修改</a>
		  </div><%if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR)){%>
		<div><span>模板</span>
			<a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=FileListPage&type=<%=Constant.TEMPLATE_PATH_TYPE%>');">模板管理</a>
			<a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=MessageManagePage');">留言管理</a>
		</div><%} if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR) || userInfo.isArticleRole()){%>
		<div><span>文章附件</span>
		<a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=FileListPage');">文章附件管理</a></div>
		<%} if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR)){%>
		<div><span>系统配置</span><%if(userInfo.getName().equals(Constant.SUPER_USER)){%>
		<a href="javascript:exeRequest(rootUrl+'/jsp/sys_config.jsp',rightDivContent);">系统参数配置</a><%}%>
		<a href="javascript:exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=ShowEditWordsPage');">文章敏感词编辑</a>
		<a href="javascript:exeRequest(rootUrl+'/jsp/water_mark.jsp',rightDivContent);">图片水印参数配置</a>
		<%if(userInfo.getName().equals(Constant.SUPER_USER)){%>
		<a href="javascript:exeRequest(rootUrl+'/jsp/db/db_load.jsp',rightDivContent);">数据库管理</a><%}%>
		</div><%}%>
		</div><%}%>
   	</div>