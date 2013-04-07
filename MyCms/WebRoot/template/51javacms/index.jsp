<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/51java" prefix="51java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>51JAVACMS－国内优秀的JAVA(JSP)网站管理系统|开源 中文java cms jsp ajax - powered by 51javacms</title>
<meta content="51JAVACMS,国内开源网站内容管理系统,中文 java cms jsp ajax 51javacms" name="keywords" />
<meta content="提供国内开源java cms jsp ajax系统下载,中文网站建设,java虚拟主机等服务" name="description" />
<link rel="shortcut icon" href="<51java:webroot/>/images/logo.ico"/>
<link rel="stylesheet" rev="stylesheet" href="<51java:webroot/>/css/home.css" type="text/css" media="all" />
<script type="text/javascript" src="<51java:webroot/>/js/51javacms.js" ></script>
</head>
<body>
<div id="head"><%@ include file="head.jsp"%></div>
	<!-- 快速通道开始 -->
	<div class="homeBanner">
			<div id="download">
				<div id="down">
					<div style="margin-left:18px;color:#0099FF;font-weight:bold;font-size:12pt;">真正的开源！真正的免费！</div>
					<a class="download_btn" href='http://${header["host"]}${pageContext.request.contextPath}/PluginCtrl?page=DownLoadFilePage&name=install.zip'
					target="_blank" title="点击立刻下载安装程序" ></a>
					<div style="margin-top: 5px">
						<div style="margin-left: 28px"><img src="<51java:webroot/>/images/jianzhan.jpg" src="建站如此轻松" /></div>
						<div style="margin-top: 8px"><a href="#" target="_blank">版本信息：V1.0.8版</a>
						(2012-7-10)<br/>软件大小：<strong style="color: #ff0000; font-weight: normal">3.99MB</strong><br/>
						Apache + Tomcat + JDK6以上 + MySQL5<br/>更新时间：2012年7月10日</div>
					</div>
				</div>
			</div>
			<div class="flash"><script src="<51java:webroot/>/fragment/1.js"></script></div>
	</div>
	<div class="clear"></div>
	    	<div id="dedenews">
		<div id="newtitle" style="float:left;">最新动态：</div>
		<div id="news" style="float:left;">
			<marquee class="homeMsg" onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" direction="up" hspace="0" vspace="0">
<51java:arlist pagesize="5" columnid="2">
<p><a href="${article.url}" target="_blank"><font color="#ff0000">${article.title}</font>(<51java:formatdate datetime="${article.creatime}" />)
				</a></p>
</51java:arlist>
			</marquee>
			<div style="float:left;"><a href="<51java:webroot/>/zxdt/pages/1.html" target="_blank"> <img src="<51java:webroot/>/images/more.gif"/> </a></div>
			<div class="homeSearch">
				<form action='http://${header["host"]}${pageContext.request.contextPath}/PluginCtrl?page=SearchPage' method="post">
						<input type="text" class="input1" name="search_txt" />
						<input type="submit" value="搜索" class="input2" />
				</form>
			</div>
		</div>
	</div>		
	  <!-- 主体开始 -->
	<div id="main">
			<div class="left">
					<div class="column">
							<h3><a class="more" href="<51java:webroot/>/faq/pages/1.html" target="_blank">more</a>常见问题</h3>
							<div class="b" style="height:170px;">
									<ul><51java:arlist pagesize="6" columnid="8">
										<li><a href="${article.url}" target="_blank">${article.title}</a></li>
										</51java:arlist>
									</ul>
							</div>
					</div>
					<div class="column">
							<h3><a class="more" href="<51java:webroot/>/plugin/pages/1.html" target="_blank">more</a>插件使用</h3>
							<div class="b" style="height:170px;">
									<ul><51java:arlist pagesize="6" columnid="3">
										<li><a href="${article.url}" target="_blank">${article.title}</a></li>
										</51java:arlist>
									</ul>
							</div>
					</div>
			</div>
			<div class="center">
					<div class="column">
							<h3>
								<a class="more" href="<51java:webroot/>/tags/pages/1.html" target="_blank">more</a>
								<ul>
									<li id="scBtn0" class="current"><a href="javascript:;" onmouseover="showDIV(0,'scBtn','scTab')">标签使用</a></li>
									<li id="scBtn1"><a href="javascript:;" onmouseover="showDIV(1,'scBtn','scTab')">模板设计</a></li>
								</ul>
							</h3>
							<div id="scTab0" class="b2" style="height:190px;">
									<ul class="list">
									<51java:arlist pagesize="7" columnid="4">
										<li><span class="time"><51java:formatdate format="yyyy-MM-dd" datetime="${article.creatime}" />
										</span><a href="${article.url}" target="_blank">${article.title}</a></li>
									</51java:arlist></ul>
							</div>
							<div id="scTab1" class="b2" style="display:none;height:190px;">
									<ul class="list">
									<51java:arlist pagesize="7" columnid="5">
										<li><span class="time"><51java:formatdate format="yyyy-MM-dd" datetime="${article.creatime}" />
										</span><a href="${article.url}" target="_blank">${article.title}</a></li>
									</51java:arlist></ul>
							</div>
					</div>
					<div class="column">
							<h3>
								<a class="more" href="<51java:webroot/>/xdth/pages/1.html" target="_blank">more</a>
								<ul>
									<li id="hjBtn0" class="current"><a href="javascript:;" onmouseover="showDIV(0,'hjBtn','rjTab')">心得体会</a></li>
									<li id="hjBtn1"><a href="javascript:;" onmouseover="showDIV(1,'hjBtn','rjTab')">安装使用</a></li>
								</ul>
							</h3>
							<div id="rjTab0" class="b2" style="height:190px;">
									<ul class="list">
									<51java:arlist pagesize="7" columnid="6">
										<li><span class="time"><51java:formatdate format="yyyy-MM-dd" datetime="${article.creatime}" />
										</span><a href="${article.url}" target="_blank">${article.title}</a></li>
									</51java:arlist></ul>
							</div>
							<div id="rjTab1" class="b2" style="display:none;height:190px;">
									<ul class="list">
									<51java:arlist pagesize="7" columnid="7">
										<li><span class="time"><51java:formatdate format="yyyy-MM-dd" datetime="${article.creatime}" />
										</span><a href="${article.url}" target="_blank">${article.title}</a></li>
									</51java:arlist></ul>
							</div>
					</div>
			</div>
			<div class="right">
					<div class="column">
							<h3>
								<a class="more" href="<51java:webroot/>/zxdt/2011-07-25/1.html" target="_blank">more</a>
								<b>系统简介</b>
							</h3>
							<div class="b" style="height:175px;">
									<div class="columnRb">
											<h4>51JAVACMS：</h4>
											<p>国内优秀的JAVA(JSP,AJAX)开源网站内容管理系统</p>
									</div>
									<div class="img"><a href="#"><img src="<51java:webroot/>/images/logo.png" /></a></div>
									<div class="clear"></div>
									<div class="txt">51JAVACMS是一款基于JAVA平台研发的内容管理系统，依托JAVA的高效、安全、稳定等优势，并且汲取了php的简洁和方便，
									纯ajax的mvc架构，开创国内JAVA版开源CMS之先河。我们的理念：大道至简；做一款简单实用的java版本的开源cms......</div>
							</div>
					</div>
					<div class="column">
							<h3><b>联系我们</b></h3>
							<div class="b2" style="height:170px;">
									<div class="gt">
											<p>51JavaCMS：</p>
											<p>Content Management System</p>
											<p>联系人：夜雨孤独客</p>
											<p>Email：<a href="mailto:zhoujh7201@gmail.com">夜雨孤独客</a></p>
									</div>
							</div>
					</div>
			</div>
			<div class="clear"></div>
			
	</div>	<!-- 主体结束 -->
	<!-- 友情链接开始 -->
	<div class="link">
     <div class="linkfriend">
     <select onchange="yqLink(this)">
        <option value="-1">---友情链接---</option>
        <option value="http://drupal.org/">drupal CMS</option>
        <option value="http://www.joomla.org/">joomla CMS</option>
        <option value="http://wordpress.org/">wordpress CMS</option>
        <option value="http://www.qibosoft.com/">齐博CMS</option>
        <option value="http://www.php168.net/">国微php168 CMS</option>
        <option value="http://www.phome.net/">帝国CMS</option>
        <option value="http://www.phpcms.cn/">phpCMS</option>
        <option value="http://www.dedecms.com/index.html">织梦CMS</option>
        <option value="http://www.comsharp.com/">锐商企业CMS</option>
      </select>
     </div>
     <div class="linktext">JAVA开源CMS | 51JAVACMS&nbsp;</div>
  </div>
<!-- 友情链接结束 -->
<!-- 页脚开始 -->
<%@ include file="foot.jsp"%>
<!-- 页脚结束 -->
</body>
</html>