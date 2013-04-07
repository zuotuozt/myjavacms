<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Col" %>
<%@page import="util.Constant"%>
<%@page import="util.InitServlet"%>
<%/***************session fail****************************/
	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;		
	}%>
<input type="hidden" id="page" value="ColEditPage"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">   
		<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">上传图片水印参数设置</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">是否加水印：</td>
						<td class="userlistinput" width="50%">
							<div class="anyinput">&nbsp;&nbsp;
								<input type="checkbox" id="is_water" onclick="showWaterMark(this);" 
								<%if(InitServlet.IS_WATER==1){%>checked="checked"<%}%>/>
							</div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist" <%if(InitServlet.IS_WATER==0){%>style="display:none;"<%}%> id="wm_type">
						<td class="userlisttext" width="20%">水印类型：</td>
						<td class="userlistinput" width="50%">
							<div class="anyinput">
								<input type="radio" value="1" name="wm_type" onclick="isWaterPic(true);" checked="checked"/>图片（/img/watermark.gif）
								<input type="radio" value="0" name="wm_type" <%if(InitServlet.IS_WATER_PIC==0){%>checked="checked"<%}%>
								 onclick="isWaterPic(false);" />文字录入
							</div>
							<div class="anyinput" <%if(InitServlet.IS_WATER_PIC==1){%>style="display:none;"<%}%> id="water_title">
								<input type="text" id="wm_type_title" maxlength="20" value="<%=InitServlet.WATER_TEXT%>"></input>
							</div>
							<form name="form1" action="<%=request.getContextPath()%>/UploadCtrl" method="post" enctype="multipart/form-data" target="uploadcallback" onsubmit="return uploadWaterGif();">
							<input type="hidden" name="page" value="UploadWaterPage"></input>
							<div class="anyinput" <%if(InitServlet.IS_WATER_PIC==0){%>style="display:none;"<%}%> id="water_pic" >
								<p><input type="file" id="wm_type_pic" name="water_gif"></input></p>&nbsp;&nbsp;&nbsp;
								<input type="submit" value="更换水印图片" ></input>
								<p>&nbsp;&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/img/watermark.gif" target="_blank"><font color="blue">预览水印图片</font></a>&nbsp;&nbsp;&nbsp;(点击后如果看到的还是老图片；请按<font color="red">CTRL+F5</font>清理缓存)</p>
							</div>
							</form>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist" <%if(InitServlet.IS_WATER==0){%>style="display:none;"<%}%> id="wm_place">
						<td class="userlisttext" width="20%">水印位置：</td>
						<td class="userlistinput" width="50%">
							<div class="anyinput">
								<input type="radio" value="0" name="wm_place" checked="checked"/>右下角
								<input type="radio" value="1" name="wm_place" <%if(InitServlet.IS_WATER_CENTER==1){%>checked="checked"<%}%>/>居中
							</div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b>
		<!--keboardfirst end--> 	
	</div>
	<span><input type="button" value="确定" onclick="editWaterMark()()" class="submitbutton"></input></span>
	</div>
<!--maindiv end-->

