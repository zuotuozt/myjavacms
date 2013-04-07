<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title>51JAVACMS－国内优秀的JAVA(JSP)网站管理系统|开源java cms jsp ajax - powered by 51javacms</title>
<meta content="51JAVACMS,国内开源网站内容管理系统,java cms jsp ajax 51javacms" name="keywords" />
<meta content="提供国内开源java cms jsp ajax系统下载,互联网应用,网站建设,java虚拟主机等服务" name="description" />
<%@page import="javabean.UserInfo,util.Constant,util.InitServlet,util.PubFun"%>
<%	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}
	String roleCalss1 = "purview";
	String roleCalss2 = "purview";
	String roleCalss3 = "purview";
	String roleCalss4 = "purview";
	if(userInfo.getName().equals(Constant.SUPER_USER)){
		roleCalss1 = "purview3";
	}else if(userInfo.getName().equals(Constant.CHIEF_EDITOR)){
		roleCalss2 = "purview3";
	}else if(!userInfo.isArticleRole() && !userInfo.isAdRole() && 
			!userInfo.isPublishRole() && !userInfo.isColumnRole()){
		roleCalss4 = "purview3";
	}else{
		roleCalss3 = "purview3";
	}
	String userRole = "普通用户";
	if(userInfo.getName().equals(Constant.SUPER_USER)){
		userRole = "系统管理员";
	}else if(userInfo.getName().equals(Constant.CHIEF_EDITOR)){
		userRole = "总编辑";
	}%>
<link rel="shortcut icon" href="http://www.51javacms.com/images/logo.ico"/>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link href="css/float.css" rel="stylesheet" type="text/css" />
<link href="calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/system.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/cms.js"></script>
<script type="text/javascript" src="js/floatdiv.js"></script>
<script type="text/javascript">rootUrl='<%=request.getContextPath()%>';</script>
</head>
<body>
    <!--menu_round-->
        <div class="RoundedCornertitle"> 
            <b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
        </div>
       <div class="topdiv">
           <font style="color:purple;">您好！【<%=userInfo.getAlias().equals("")?userInfo.getName():userInfo.getAlias()%>】</font>
           <a href="<%=InitServlet.WEB_SITE_URL%>" target="_blank">【站点首页】</a>
           <a href="<%=request.getContextPath()%>/PluginCtrl?page=SearchPage" target="_blank">【站点搜索】</a>
           <a href="<%=request.getContextPath()%>/PluginCtrl?page=ShowMessagePage" target="_blank">【留言板】</a>
           <%if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR)){%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文章标题：<input type="text" id="search_title" />
<input type="button" value="搜索" onclick="exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=SearchArticlePage&search_title='+encodeURIComponent($('search_title').value.trim()));"/>
<input type="button" value="高级搜索" onclick="exeRequest(rootUrl+'/jsp/article/advanced_search.jsp',rightDivContent);"/><%}%></div>
	<div id="menulist">
            <span style="float:left;"><img src="img/logo.png"/></span>
            <span class="titlename"><%=InitServlet.WEB_SITE_NAME%><br/>
            <span style="font-size:12px;margin-top:8px;color:black;" id="clock">&nbsp;</span></span>
			<span id="sheets" style="float:left;margin-left:18px;">
			<%if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR) || userInfo.isArticleRole()){%>
			<a href="#" onclick="changeSheet('/MainCtrl?page=ColLeftPage&parentId=1','/jsp/inc/welcome.jsp','文章编辑',this);" id="menu_sheet_1">    
			<span class="image_mouse_out">文章编辑</span></a><%}%>
			<%if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR) || userInfo.isAdRole()){%>
			<a href="#" onclick="changeSheet('/jsp/inc/left_area.jsp?type=fragment','/MainCtrl?page=FragmentManagePage','广告管理',this);" id="menu_sheet_2"> 
			<span class="image_mouse_out">碎片管理</span></a><%}%>
			<%if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR) || userInfo.isPublishRole()){%>
			<a href="#" onclick="changeSheet('/jsp/inc/left_area.jsp?type=publish','/MainCtrl?page=PublishIndexPage','文章发布',this);" id="menu_sheet_3">
			<span class="image_mouse_out">文章发布</span></a><%}%>
			<%if(userInfo.getName().equals(Constant.SUPER_USER) || userInfo.getName().equals(Constant.CHIEF_EDITOR) || userInfo.isColumnRole()){%>
			<a href="#" onclick="changeSheet('/jsp/inc/left_area.jsp?type=column','/MainCtrl?page=ColManagePage&parentId=1','栏目管理',this);" id="menu_sheet_4"> 
			<span class="image_mouse_out">栏目管理</span></a><%}%>
			<a href="#" onclick="changeSheet('/jsp/inc/left_area.jsp?type=system','/MainCtrl?page=ChangePasswordPage','系统管理',this);" id="menu_sheet_5"> 
			<span class="image_mouse_out">系统管理</span></a>
			</span>
			<span style="margin-right:60px;"><iframe id="weather_iframe" frameborder="0" scrolling="no" style="height:56px; width:250px;" 
		    src="http://m.weather.com.cn/m/firefox_new/index.html?v1"></iframe>
		  <script type="text/javascript">document.getElementById("weather_iframe").setAttribute("allowTransparency","true");
		  </script></span>
		  <span>
		  	<img id="userimage" src="img/main/user.jpg" title="<%=userInfo.getAlias().equals("")?userInfo.getName():userInfo.getAlias()%>（用户别名）" onclick="showInfoZone(this,'user_info_zone','user_info_zone_bg',true);"></img>
		  </span>
        <span>
        	<a href="MainCtrl?page=LogoutPage" id="exit" class="exitout" title="安全退出" onmouseout="this.className='exitout'" onmouseover="this.className='exitmove'"></a>
        </span>			
       </div>
       <!--menu_list_end-->    

<!--tree_menu_begin-->
<div class="RoundedCornerlmt"> 
    <b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
</div>
<div class="border"> 
<div id="CNLTreeMenu">
	<!--[if !IE]><!--><div id="control" class="control_style_out" onmouseout="mouceContrlOut(this);" onmouseover="mouceContrlOver(this);"></div><!--<![endif]-->    
    <!--[if IE]><div id="control" class="ie_control_style_out" onmouseout="mouceContrlOutIe(this);" onmouseover="mouceContrlOverIe(this);"></div><![endif]-->    
    <div class="linktextbutton">
        <div id="treemenulist">
    	<div class="menubox">
	    	<div class="menulist" style="margin:4px auto;"><span class="menulistico"><img src="img/main/add.png"/></span>
	    	<span class="menutitle" id="sheet_title" style="color:blue;font-weight:bold;font-size:14px;">文章编辑</span></div>
    	</div></div></div>
    <div id="left_content">&nbsp;</div>
</div>
<!--tree_menu_end-->

<!--main_window_begin-->
<div id="right">
	<div id="change_right"></div>
	<!--copyright begin-->	
	<div class="copyright">
		<strong class="black">©&nbsp;Copyright&nbsp;2010-2012&nbsp;by&nbsp;
		<a href="http://www.51javacms.com" target="_blank"><span class="blue">51java</span><span class="gray">Cms</span></a>
		&nbsp;Technologies&nbsp;Limited&nbsp;&nbsp;Powered&nbsp;by&nbsp;
		<a href="http://www.51javacms.com" target="_blank"><span class="red">51javaCms</span><span class="kesumba">1.0.8</span></a>
		&nbsp;Final&nbsp;©&nbsp;2010-2012<a href="http://www.51javacms.com" target="_blank"><span class="blue">&nbsp;51java</span><span class="gray">Cms</span></a></strong>
	</div>
	<!--copyright end-->
</div>
</div>
<div id="move_button" class="move"><img src="img/main/move.png"></img></div>
<!--main_window_end-->
<!--window end-->
<iframe name="uploadcallback" id="uploadcallback" style="width:0px;height:0px;border:0px;"></iframe>
<!-- div id="mource_control"></div-->

<!-- float user info zone begin -->
<div id="user_info_zone" class="infofloatzone">
	<div class="inputzoneinfo">
		<div class="inputzoneinfotitlecenter"><font class="floatlinktext">用户别名：<font id="info_title_name"><%=userInfo.getAlias().equals("")?userInfo.getName():userInfo.getAlias()%></font></font><span class="inputzoneti"></span></div>
		<div class="inputzoneexit">
			<a title="安全退出" id="input_zone_info_close" class="exitout" onclick="closeUserInfo('user_info_zone','user_info_zone_bg');" onmouseover="this.className='exitmove';" onmouseout="this.className='exitout';" href="#" ></a>
		</div>
		<div class="inputzoneinserttext"><font class="infotitle">用户登陆名：</font><font class="info"><%=userInfo.getName()%></font></div>
		<div class="inputzoneinserttext"><font class="infotitle">用户角色：</font><font class="info"><%=userRole%></font></div>
		<div class="inputzoneinserttext"><font class="infotitle">权限等级：</font><font class="<%=roleCalss1%>">（极高）</font><font class="<%=roleCalss2%>">（高）</font><font class="<%=roleCalss3%>">（中）</font><font class="<%=roleCalss4%>">（低）</font></div>
		<div class="floatline">&nbsp;</div>
		<div class="inputzoneinserttext"><font class="infotitle">已发布信息总数：</font><font class="info"><%=userInfo.getArticleCnt()%>条</font></div>
		<div class="floatline">&nbsp;</div>
		<div class="inputzoneinserttext"><font class="infotitle">上次登录时间：</font><font class="info"><%=PubFun.getDateTime("yyyy年MM月dd日  HH点mm分ss秒", userInfo.getLastLogin())%></font></div>
		<div class="inputzoneinserttext"><font class="infotitle">注册时间：</font><font class="info"><%=PubFun.getDateTime("yyyy年MM月dd日  HH点mm分ss秒", userInfo.getCreateTime())%></font></div>
	</div>
</div>
<div id="user_info_zone_bg">&nbsp;</div>    
<!-- float user info zone end -->
<script type="text/javascript">cmsLoad();</script>
<script type="text/javascript" src="calendar/calendar.js"></script>
<script type="text/javascript" src="calendar/cn.js"></script>
<script type="text/javascript" src="js/progressbar.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
</body>
</html>