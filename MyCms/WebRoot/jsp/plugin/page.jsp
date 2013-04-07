<%@page contentType="text/html;charset=utf-8"%>
<input type="hidden" name="page_no" id="page_no" value="${pageNo}"></input>
	<div class="wp-pagenavi">
		<span class="pages">页数 <font color="red">${pageNo}</font> / <font color="red">${sumPage}</font></span>
		<a href="#" onclick="submitDL(1,${sumPage},${pageNo});">首页</a>
		<a href="#" onclick="submitDL(${pageNo-1},${sumPage},${pageNo});">上一页</a>
		<a href="#" onclick="submitDL(${pageNo+1},${sumPage},${pageNo});">下一页</a>
		<a href="#" onclick="submitDL(${sumPage},${sumPage},${pageNo});">尾页</a>
		<span class="pages">共<font color="red">${cnt}</font>条记录&nbsp;
转到第<input type="text" id="to_page_no" size="3"></input>
页</span> <a href="#"> <img src="<%=request.getContextPath()%>/img/plugin/go.gif" border="0px" onclick="submitGo(${sumPage},${pageNo});"></img></a>
	</div>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/plugin/page.js"></script>