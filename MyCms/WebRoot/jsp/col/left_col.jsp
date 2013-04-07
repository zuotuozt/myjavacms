<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant"%>
<%@page import="util.PubFun"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if(userInfo == null){%>
		<script type="text/javascript">
		location=rootUrl+'/sign_in.jsp;';
		</script>
		<%return;		
	}
	String display = (String)request.getAttribute("display");%>
<input type="hidden" id="leftcol_parentId" value="<%=request.getAttribute("parentId") %>"></input>
<div align="left"><%=display%>
</div>

