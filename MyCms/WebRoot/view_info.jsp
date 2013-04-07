<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
th{
	background-color: #4455aa;
	color: white;
	font-size: 14px;
	font-weight:bold;
}
.tablebody1{
	background-color: #FFFFF0;
	color: red;
	font-size: 14px;
	font-weight:bold;
}
.tableborder1{
	width:97%;
	border: 1px; 
	background-color: #6595D6;
}
</style>
</head>
<body>
<%String inf = (String) request.getAttribute("inf");
			if (inf == null) {
				inf = request.getParameter("inf");
			}%>
<table class="tableborder1" style="width: 75%;" align="center" cellpadding="3" cellspacing="1">
	<tbody>
		<tr align="center">
			<th colspan="2" height="25" width="100%">51javavcms信息提示:</th>
		</tr>
		<tr align="center">
			<td class="tablebody1" colspan="2" width="100%" height="200"><%=inf%></td>
		</tr>
		<tr align="center">
			<td>
				<input type="button" value="返回"
				 onclick="history.back();" />
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>
