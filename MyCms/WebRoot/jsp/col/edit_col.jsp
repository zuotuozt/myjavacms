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
	}
	Col column = (Col)request.getAttribute("col");
	if(column==null){
		response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
		return;	
	}
	int colId = column.getId();
	int parentId = column.getParentid();%>
<input type="hidden" id="page" value="ColEditPage"></input>
<input type="hidden" id="colId" value="<%=colId%>"></input>
<input type="hidden" id="colType" value="<%=column.getColType()%>"></input>
<input type="hidden" id="col_checkbox_id" value="<%=parentId%>"></input>
<input type="hidden" id="sel_template" value="columncovertemplate"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">   
	<span class="linktextbutton">
		<span>当前的位置：栏目管理/<%=colId==0?"新增":"修改"%>栏目</span>
	</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">
			<!-- 空格不能省略 -->
			<span class="span_select_left">&nbsp;&nbsp;&nbsp;</span>
			<span class="span_select_center">默认上级栏目：<%=column.getParentName()==null?InitServlet.WEB_SITE_URL: column.getParentName()%></span><span class="span_select_right">&nbsp;&nbsp;&nbsp;</span>
			</span>
		</div>
	<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
	<!--keboardfirst end-->
	</div>			
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">栏目基本信息</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">栏目名称：</td>
						<td class="userlistinput"  width="40%">
							<input id="columnname" type="text" <%if(colId!=0){%>value="<%=column.getName() %>" <%}%>></input>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;栏目名称不能为空。</td>
					</tr><%if(parentId!=Constant.TOP_CLOUMN_TREE){%>
					<tr class="userlist">
						<td class="userlisttext" width="20%">文件保存目录：</td>
						<td class="userlistinput"  width="40%">
							<input id="columnsavefiledirectory" type="text" size="21" 
							<%if(colId!=0){%>value="<%=column.getHtmlPath() %>" disabled="disabled" <%}%>/>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;栏目路径名只能为英文、数字和下划线组成。</td>
					</tr><%if(colId==0){%>
					<tr class="userlist">
						<td class="userlisttext" width="20%">选择上级栏目：</td>
						<td class="userlistinput" width="40%">
							<div id="treemenulist">
						    	<div class="menulist"><a href="#x" onclick="showInfoZone(this,'col_info_zone','col_info_zone_bg');">
						    	<div class="menubox">
						    	<span class="menulistico"><img src="img/main/add.png" style="border:0px"/></span>
						    	<span class="menutitle" id="sheet_column" style="font-size:12px;"><%=column.getParentName()%></span></div></a>
    						</div></div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr><%}}%>
					<tr class="userlist">
						<td class="userlisttext" width="20%">栏目描述：</td>
						<td class="userlistinput" width="40%">
							<div class="anyinput">
								<input type="radio" value="0" name="columndescription" onclick="$('columndescriptiontextarea').disabled=false;" checked="checked"/>文字录入
								<input type="radio" value="1" name="columndescription" <%if(parentId==Constant.TOP_CLOUMN_TREE){%>disabled="disabled"<%}else if(colId==0){%>checked="checked"<%}%>
								 onclick="$('columndescriptiontextarea').disabled=true;" />从上级栏目中获取
							</div>
							<div class="anyinput">
								<textarea id="columndescriptiontextarea" rows="3" cols="60"><%if(colId!=0){%><%=column.getColIntro()%><%}%></textarea>
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
		<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">栏目页面seo搜索</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">seo搜索title：</td>
						<td class="userlistinput" width="50%">
							<div class="anyinput">
								<input type="radio" value="0" name="seotitle" onclick="$('seo_title').disabled=false;" checked="checked"/>文字录入
								<input type="radio" value="1" name="seotitle" <%if(parentId==Constant.TOP_CLOUMN_TREE){%>disabled="disabled"<%}else if(colId==0){%>checked="checked"<%}%>
								 onclick="$('seo_title').disabled=true;" />从上级栏目中获取
							</div>
							<div class="anyinput">
								<input type="text" id="seo_title" maxlength="50" value="<%if(colId!=0){%><%=column.getSeoTitle()%><%}%>"></input>
							</div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">seo搜索keywords：</td>
						<td class="userlistinput" width="50%">
							<div class="anyinput">
								<input type="radio" value="0" name="seokeywords" onclick="$('seo_keywords').disabled=false;" checked="checked"/>文字录入
								<input type="radio" value="1" name="seokeywords" <%if(parentId==Constant.TOP_CLOUMN_TREE){%>disabled="disabled"<%}else if(colId==0){%>checked="checked"<%}%>
								 onclick="$('seo_keywords').disabled=true;" />从上级栏目中获取
							</div>
							<div class="anyinput">
								<textarea id="seo_keywords" rows="3" cols="60"><%if(colId!=0){%><%=column.getSeoKeywords()%><%}%></textarea>
							</div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">seo搜索description：</td>
						<td class="userlistinput" width="50%">
							<div class="anyinput">
								<input type="radio" value="0" name="seodescription" onclick="$('seo_description').disabled=false;" checked="checked"/>文字录入
								<input type="radio" value="1" name="seodescription" <%if(parentId==Constant.TOP_CLOUMN_TREE){%>disabled="disabled"<%}else if(colId==0){%>checked="checked"<%}%>
								 onclick="$('seo_description').disabled=true;" />从上级栏目中获取
							</div>
							<div class="anyinput">
								<textarea id="seo_description" rows="3" cols="60"><%if(colId!=0){%><%=column.getSeoDescription()%><%}%></textarea>
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
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">栏目属性</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<tr class="userlist">
						<td class="userlisttext" width="20%">栏目属性：</td>
						<td class="userlistinput"  width="40%">
							<div class="anyinput"><input type="radio" value="0" id="columnproperties" name="columnproperties" onclick="columnProperties(this.value);" <%if(column.getColType()==Constant.ARTICLES_CLOUMN){%>checked="checked"<%}else if(column.getColType()!=-1){%>disabled="disabled"<%}%>/>最终列表栏目（在本栏目发布文档，并生成文档列表）</div>
							<div class="anyinput"><input type="radio" value="1" id="columnproperties1" name="columnproperties" onclick="columnProperties(this.value);" <%if(column.getColType()==Constant.COVER_CLOUMN){%>checked="checked"<%}else if(column.getColType()!=-1){%>disabled="disabled"<%} %>/>栏目封面（栏目本身不允许发布文档）</div>
							<div class="anyinput"><input type="radio" value="2" id="columnproperties2" name="columnproperties" onclick="columnProperties(this.value);" <%if(column.getColType()==Constant.EXTERNAL_LINK_CLOUMN){%>checked="checked"<%}else if(column.getColType()!=-1){%>disabled="disabled"<%} %>/>外部连接（填写链接网址）</div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;栏目属性不能为空。</td>
					</tr>														
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->
	</div>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title">栏目模板</span>
			<div>
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
					<%if(column.getColType()==Constant.COVER_CLOUMN || column.getColType()==-1){%>
					<tr class="userlist" id="cover_template">
						<td class="userlisttext" width="20%">封面模板：</td>
						<td class="userlistinput"  width="40%">
							<input id="columncovertemplate" type="text" size="21" <%if(colId!=0){%>value="<%=column.getIndexTemplate() %> "<%}%> disabled/> <a href="javascript:;" onclick="showColumnTemplate(this,'columncovertemplate');"> 选 择</a>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr><%} if(column.getColType()==Constant.ARTICLES_CLOUMN || column.getColType()==-1){%>
					<tr class="userlist" id="list_template">
						<td class="userlisttext" width="20%">列表模板：</td>
						<td class="userlistinput"  width="40%">
							<input id="columnlisttemplate" type="text" size="21" <%if(colId!=0){%>value="<%=column.getListTemplate() %> "<%}%> disabled/> <a href="javascript:;" onclick="showColumnTemplate(this,'columnlisttemplate');"> 选 择 </a>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist" id="article_template">
						<td class="userlisttext" width="20%">文章模板：</td>
						<td class="userlistinput"  width="40%">
							<input id="columnarticletemplate" type="text" size="21" <%if(colId!=0){%>value="<%=column.getArticleTemplate() %>" <%}%> disabled/> <a href="javascript:;" onclick="showColumnTemplate(this,'columnarticletemplate');"> 选 择 </a>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr><%} if(column.getColType()==Constant.EXTERNAL_LINK_CLOUMN || column.getColType()==-1){%>
					<tr class="userlist" id="external_links">
						<td class="userlisttext" width="20%">链接地址：</td>
						<td class="userlistinput"  width="40%">
							<input id="columnlinkaddress" type="text" <%if(colId!=0){%>value="<%=column.getLink()%>"<%}%> readonly/>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr><%}%>					
				</table>
				<!-- input table end -->
			</div>
		</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->
	<span><input type="button" value="确定" onclick="editColumn()" class="submitbutton"></input></span>
	<span><input type="button" value="取消" onclick="exeRequest('<%=request.getContextPath()%>/MainCtrl',rightDivContent,'page=ColManagePage&parentId=<%=parentId%>');" class="submitbutton"></input></span>				
	</div>
	</div>
<!--maindiv end-->
<%String selTxt = (String)request.getAttribute("selTxt");
	if(selTxt==null) selTxt = "";%>
<div id="float_info_zone" class="infofloatzone">
	<div class="inputzoneinfo">
		<div class="inputzoneinfotitlecenter"><font class="floatlinktext"><font id="info_title_name">模板文件列表&nbsp;&nbsp;&nbsp;</font>&nbsp;&nbsp;√请在要选择的模板文件打勾&nbsp;&nbsp;</font><span class="inputzoneti"></span></div>
		<div class="inputzoneexit">
			<a title="安全退出" id="input_zone_info_close" class="exitout" onclick="closeFloatInfo();return false;" onmouseover="this.className='exitmove';" onmouseout="this.className='exitout';" href="#" ></a>
		</div><%=selTxt%>
	</div>
</div>
<div id="input_zone_bg"></div>
<%String selTxt2 = (String)request.getAttribute("selTxt2");
	if(selTxt2==null) selTxt2 = "";%>
<div id="col_info_zone" class="infofloatzone">
	<div class="inputzoneinfo">
		<div class="inputzoneinfotitlecenter"><font class="floatlinktext"><font id="info_title_name">被选择的栏目列表</font>&nbsp;&nbsp;√请在要选择的栏目打勾&nbsp;&nbsp;</font><span class="inputzoneti"></span></div>
		<div class="inputzoneexit">
			<a title="安全退出" id="input_zone_info_close" class="exitout" onclick="closeUserInfo('col_info_zone','col_info_zone_bg');return false;" onmouseover="this.className='exitmove';" onmouseout="this.className='exitout';" href="#" ></a>
		</div><%=selTxt2%>
	</div>
</div>
<div id="col_info_zone_bg">&nbsp;</div>

