<%@ page contentType="text/html;charset=utf-8" %>
<%@page import="util.PubFun"%>
<%@page import="javabean.Message" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>留言板－国内优秀的JAVA(JSP)网站管理系统|开源java cms jsp ajax - powered by 51javacms</title>
<meta content="51JAVACMS,开源网站内容管理系统,java cms jsp ajax,站内搜索" name="keywords" />
<meta content="留言板,提供开源java cms jsp ajax系统下载,互联网应用,网站建设" name="description" />
<link rel="shortcut icon" href="http://www.51javacms.com/images/logo.ico"/>
<link rel="stylesheet" rev="stylesheet" href="<%=request.getContextPath()%>/css/plugin/message.css" type="text/css" media="all" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/system.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/plugin/plugin.js" ></script>
<script type="text/javascript">rootUrl='<%=request.getContextPath()%>';</script>
</head>
<body onload="loadCovering();">
<div id="head">
	<div id="logo">
		<h1><a href="http://www.51javacms.com"><img alt="51JavaCms!" src="<%=request.getContextPath()%>/img/logo.png">无忧javacms</a></h1>
	    <div id="gbook_head"><img border="0" alt="留言板" src="<%=request.getContextPath()%>/img/plugin/title.jpg" /></div>
	</div>
</div>
<!-- 头部结束 -->
    <!--主体Begin-->
    <form action="<%=request.getContextPath()%>/PluginCtrl" method="post" name="page_form">
<input type="hidden" name="page" value="ShowMessagePage"></input>
    <div id="gbook_content">
<%int sumPage = request.getAttribute("sumPage")==null?0:(Integer)request.getAttribute("sumPage");
	  if(sumPage>1){%><div class="msg_page"><%@ include file="../page.jsp"%></div><%}
    Message[] messages = (Message[])request.getAttribute("messages");
	if (messages!=null){
	for (int i=0; i<messages.length; i++){%>
	 <div class="gbook_msg_list">
	     <div class="msg_bar">
		    <div class="gbook_usrname">
			<img src="<%=request.getContextPath()%>/img/plugin/question.gif" border="0" align="absmiddle"/>
			&nbsp;<%=messages[i].getTitle()%>
			</div>
			<div class="msg_time"><%=PubFun.getDateTime("yyyy-MM-dd HH:mm:ss",messages[i].getCreatime() )%></div>
		 </div>		 
		 <div class="msg_content"><%=messages[i].getMessage() %></div>
		 
		 <div class="reply_content">
		 <img src="<%=request.getContextPath()%>/img/plugin/answer.gif" border="0" align="absmiddle"/>
		 &nbsp;<span class="gbook_answer">为您服务:</span><br />&nbsp;&nbsp; <%=messages[i].getReply() %></div>
	 </div><%}}%>
	 </form>
 <!--留言列表End-->
 <!--留言表单Begin-->
    <div class="gbook_form">
    <table border="0" cellspacing="1" cellpadding="0" width="98%" bgcolor="#7dbfdb" align="center" style="margin-top: 10px; margin-bottom: 10px">
      <tbody>
        <tr>
          <td bgcolor="#FFFFE0" height="25" align="left">&nbsp; <img border="0" alt="留言板" align="absMiddle" width="14" height="12" src="<%=request.getContextPath()%>/img/plugin/liuyan.gif" /> <strong>我 要 留 言</strong></td>
        </tr>
        <tr>
          <td bgcolor="#f5f4fa" height="200" valign="top">
            <table border="0" cellspacing="0" cellpadding="0" width="98%" align="center">
              <tbody>
                <tr>
                  <td width="66" align="right">留言标题：</td>
                  <td width="478" align="left"><input id="title" class="required" maxlength="50" size="56" maxlength="30"  type="text" /></td>
                </tr>
                <tr>
                  <td height="25" align="right">留言内容：</td>
                  <td align="left"><textarea id="content" class="required" rows="5" cols="66" maxlength="255" ></textarea></td>
                </tr>
                <tr>
                  <td height="30" align="right">验证码：</td>
                  <td align="left"><input id="certify" class="required" title="请输入验证码" size="4"  type="text" />
                  &nbsp;<img id="reg_random" src="<%=request.getContextPath()%>/RandomCodeCtrl" border="0"  />
                  &nbsp;<a href="javascript:randomChangeReg('reg_random');"><font color="blue">看不清</font></a></td>
                </tr>
                <tr>
                  <td height="30" colspan="2" align="center"><input type="button" value="发布留言" onclick="submitMsg();" /></td>
                </tr>
              </tbody>
            </table>
          <table border="0" cellspacing="0" cellpadding="0" width="98%" align="center">
            <tbody>
              <tr>
                <td align="left" style="line-height: 20px">&nbsp;&nbsp;1.用户发表留言仅代表其个人意见，并且承担一切因发表内容引起的纠纷和责任;<br />
                &nbsp;&nbsp;2.本站管理人员有权在不通知用户的情况下删除不符合规定的留言信息或留做证据;<br />
                &nbsp;&nbsp;3.请客观的评价您所看到的资讯，提倡就事论事，杜绝漫骂和人身攻击等不文明行为.</td>
              </tr>
            </tbody>
          </table>
          </td>
        </tr>
      </tbody>
    </table>
    </div></div>
    <!--留言表单End-->

<%@ include file="../footer.jsp"%>
</body>
</html>