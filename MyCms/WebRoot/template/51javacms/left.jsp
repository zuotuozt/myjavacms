<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/51java" prefix="51java" %>
					<div id="my_menu" class="sdmenu">
						<div><span>栏目列表</span>
							<a href="<51java:webroot/>/plugin/pages/1.html" 
							<51java:if test="${columnid==3}">class="current"</51java:if>>插件使用</a>
							<a href="<51java:webroot/>/tags/pages/1.html" 
							<51java:if test="${columnid==4}">class="current"</51java:if>>标签使用</a>
							<a href="<51java:webroot/>/template/pages/1.html" 
							<51java:if test="${columnid==5}">class="current"</51java:if>>模板设计</a>
							<a href="<51java:webroot/>/xdth/pages/1.html" 
							<51java:if test="${columnid==6}">class="current"</51java:if>>心得体会</a>
							<a href="<51java:webroot/>/azsy/pages/1.html" 
							<51java:if test="${columnid==7}">class="current"</51java:if>>安装使用</a>
							<a href="<51java:webroot/>/zxdt/pages/1.html" 
							<51java:if test="${columnid==2}">class="current"</51java:if>>最新动态</a>
							<a href="<51java:webroot/>/faq/pages/1.html" 
							<51java:if test="${columnid==8}">class="current"</51java:if>>常见问题</a>
						</div>
						<div><span>知识库</span>
							<a href="<51java:webroot/>/repository/jzjq/pages/1.html" 
							<51java:if test="${columnid==11}">class="current"</51java:if>>建站技巧</a>
							<a href="<51java:webroot/>/repository/wyjc/pages/1.html" 
							<51java:if test="${columnid==12}">class="current"</51java:if>>网页基础</a>
							<a href="<51java:webroot/>/repository/wlbc/pages/1.html" 
							<51java:if test="${columnid==13}">class="current"</51java:if>>网络编程</a>
							<a href="<51java:webroot/>/repository/dbserver/pages/1.html" 
							<51java:if test="${columnid==14}">class="current"</51java:if>>数据库和服务器</a>
						</div>
					</div>
					<script language="JavaScript">
						var myMenu=new SDMenu("my_menu");
						myMenu.init();
					    myMenu.expandMenu(myMenu.submenus[<51java:choose>
						<51java:when test="${columnid<10}">0</51java:when>
						<51java:otherwise>1</51java:otherwise>
						</51java:choose>]);
					</script>