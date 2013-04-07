<%@page contentType="text/html;charset=utf-8"%>
<%@page import="javabean.UserInfo"%>
<%@page import="util.Constant,util.InitServlet"%>
<%@page import="java.io.File,java.util.Date,java.text.SimpleDateFormat"%>
<%	UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		if (userInfo == null) {
			response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
			return;		
	}
	File[] files = (File[])request.getAttribute("files");
	String path=(String)request.getAttribute("filepath");
	String msg = (String)request.getAttribute("msg");
	int type =(Integer)request.getAttribute("type");
	String[] suffix = new String[5]; 
		suffix[0] = "js";
		suffix[1] = "css";
		suffix[2] = "jsp";
		suffix[3] = "html";
		suffix[4] = "htm";
	String[] suffix2 = new String[7]; 
		suffix2[0] = "jpg";
		suffix2[1] = "jpeg";
		suffix2[2] = "png";
		suffix2[3] = "html";
		suffix2[4] = "htm";
		suffix2[5] = "gif";
		suffix2[6] = "bmp";%>
<!-- input hidden begin -->
<input type="hidden" name="filepath" id="filepath" value="<%=path%>"/>
<input type="hidden" id="msg" value="<%=msg%>"/>
<input type="hidden" name="type" id="type" value="<%=type%>"/>
<!-- input hidden end -->
<!--main div begin-->
<div id="maindiv" class="maindivelse">  
	<span class="linktextbutton">
		<span>当前的位置：<%if(type==Constant.FILE_PATH_TYPE){%>系统管理&nbsp;\&nbsp;文章附件&nbsp;\&nbsp;文章附件管理
		<%}else{%>系统管理&nbsp;\&nbsp;模板&nbsp;\&nbsp;模板管理<%} %>&nbsp;</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
	<!--keboardfirst begin-->
	<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">
			<!-- 空格不能省略 -->
			<span class="span_select_left">&nbsp;&nbsp;&nbsp;</span><span class="span_select_center">
			当前路径：<%if(type==Constant.FILE_PATH_TYPE){%>&lt;51java:webroot/&gt;<%=path%><%}else{%><%=path%><%}%></span><span class="span_select_right">&nbsp;&nbsp;&nbsp;</span>
			</span>
		</div>
	<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
	<!--keboardfirst end-->	
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">文件管理</span>
				<div>
					<span class="spanthree"></span>
					<!-- system config begin -->
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="tablelist">
							<td class="tablelistfirst3r" width="20%">名称</td>
							<td class="tablelistfirst3r" width="16%">大小</td>
							<td class="tablelistfirst3r" width="12%">类型</td>
							<td class="tablelistfirst3r" width="16%">最后修改时间</td>
							<td class="tablelistfirst3r" width="36%">操作（<font color="red">文件扩展名：html,htm,js,css,jsp才能修改</font>）</td>
						</tr>
							<%if (files!=null){
							String className = "tablelisttext3ro";
							for (int i=0;i<files.length;i++){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
								Date d=new Date(files[i].lastModified());
								boolean canEdit = false;
								boolean canPreview = false;
								String dateStr = sdf.format(d);
								if(!files[i].getName().equals("cms_hidden")){%>
						<tr class="tablelist" onmouseout="showThisLinkOut(this);" onmouseover="showThisLinkOver(this);">
							<%if((i%2)==0){
								className = "tablelisttext3ro";
							}else{
								className = "tablelisttext3rt";
							}%>
							<td class="<%=className%>">
								<%if(files[i].isDirectory()){ %>
									<a href="javascript:toPrePage('<%=files[i].getName()%>');"><%=files[i].getName()%></a>
						        <%}else{
						        	String fileKind = files[i].getName().substring(files[i].getName().lastIndexOf(".")+1).toLowerCase();
						        	for(int n=0;n<suffix.length;n++){
										if(fileKind.equals(suffix[n])){
											canEdit = true;
											break;
										}
                                    }
						        	for(int n=0;n<suffix2.length;n++){
										if(fileKind.equals(suffix2[n])){
											canPreview = true;
											break;
										}
                                    }
						        	if(canEdit){%>
						            	<a href="javascript:toEditFileContent('<%=files[i].getName()%>');"><%=files[i].getName()%></a>
						            <%}else{%>
						        		<%=files[i].getName()%>
						        <%}}%>
							</td>
							<td class="<%=className%>"><%if(files[i].isDirectory()){%>&nbsp;<%}else{%><%=files[i].length()/1024 %>kb<%}%></td>
							<td class="<%=className%>"><%if(files[i].isDirectory()){%>文件夹<%}else{%>文件<%}%></td>
							<td class="<%=className%>"><%=dateStr %></td>
							<td class="<%=className%>">
                                <%if(files[i].isDirectory()){
                                		if(path.equals("") || (type==Constant.TEMPLATE_PATH_TYPE && files[i].getName().equals("system"))){%>
                                		<a href="javascript:toPrePage('<%=files[i].getName()%>');">进入</a><% 
                                		}else{%>
                                			<a href="javascript:toEditFileName('<%=files[i].getName()%>');">改名</a>
											<a href="javascript:delFile('<%=files[i].getName()%>');">删除</a>
											<a href="javascript:toPrePage('<%=files[i].getName()%>');">进入</a>
                                			<%}
                                	}else{
                                		if(canPreview){%>
						                    <a href="<%=InitServlet.WEB_SITE_URL %><%=path%>/<%=files[i].getName()%>" target="_blank">预览</a>
                                		<%}
                                		if(canEdit){
                                			if(path.equals(Constant.FRAGMENT_PATH)){%>
                                					<a href="javascript:toEditFileContent('<%=files[i].getName()%>');">查看</a>
                                			<%}else{%>
                                                    <a href="javascript:toEditFileName('<%=files[i].getName()%>');">改名</a>
								                    <a href="javascript:toEditFileContent('<%=files[i].getName()%>');">编辑</a>
                               				<%}
                                		}%>
                               		<a href="javascript:delFile('<%=files[i].getName()%>');">删除</a>
                               		<a href="<%=request.getContextPath()%>/MainCtrl?page=DownLoadPage&name=<%=files[i].getName() %>&type=<%=type %>&filepath=<%=path %>" target="uploadcallback">下载</a>
                               	<%}%></td>						
						</tr><%}}}%>
					</table>
					<!-- system config end -->
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
<!--submitbutton begin-->	
<span><input class="submitbutton" type="submit" onclick="toPrePage('1');"  value="返回上级目录"></input></span>
<%if(type==Constant.FILE_PATH_TYPE){%>
	<span><input class="submitbutton" type="button" onclick="exeRequest('<%=request.getContextPath()%>/MainCtrl', rightDivContent, 'page=FileListPage');"  value="根目录"></input></span>
<%}else{%>
	<span><input class="submitbutton" type="button" onclick="exeRequest('<%=request.getContextPath()%>/MainCtrl', rightDivContent, 'page=FileListPage&type=<%=type %>');"  value="根目录"></input></span>
<%}%>
<span><input class="submitbutton" type="button" onclick="toCreatePage();"  value="新建目录"></input></span>
<span><input class="submitbutton" type="button" onclick="toCreatePage('1');"  value="文件上传"></input></span>
<%if(type==Constant.TEMPLATE_PATH_TYPE){%>
<span><input class="submitbutton" type="button" onclick="toEditFileContent('');"  value="新建模板"></input></span>
<%}%>
<!--submitbutton end-->	
</div>
<!--maindiv end-->