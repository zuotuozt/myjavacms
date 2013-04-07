<%@page contentType="text/html;charset=utf-8"%>
<%@page import="util.Constant"%>
<%@page import="javabean.UserInfo"%>
<%@page import="javabean.Article"%>
<%@page import="javabean.Col"%>
<%@page import="util.InitServlet,util.PubFun"%>
<%UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
Article article = (Article)request.getAttribute("article");
if(article.getPicture()==null){
	article.setPicture("");
}
Col[] cols = (Col[])request.getAttribute("cols"); 
if((userInfo == null)) {
	response.sendRedirect(request.getContextPath() + Constant.ORGINAL_LOGIN_PAGE);
	return;		
}
String title = "创建";
long articleId = 0;
if(article.getId()!= 0){
	title = "修改";
}
String searchName = request.getParameter("search_name");
if(searchName == null) searchName = "";
String pageNo = request.getParameter("page_no");
if(pageNo == null) pageNo = "1";

String articlethumbnailsfilepath = (String)request.getAttribute("articlethumbnailsfilepath");
String articlethumbnailsfilename = (String)request.getAttribute("articlethumbnailsfilename");%>
<input id="search_name" type="hidden" value="<%=searchName%>" />
<input id="page_no" type="hidden" value="<%=pageNo%>" />
<input id="is_search" type="hidden" value="<%=request.getParameter("is_search")==null?false:request.getParameter("is_search")%>" />
<input id="articlethumbnailsfilepath" type="hidden" value="<%=articlethumbnailsfilepath%>"></input>
<input id="articlethumbnailsfilename" type="hidden" value="<%=articlethumbnailsfilename%>"></input>
<input type="hidden" id="col_checkbox_id" value="<%=article.getColumnid()%>"></input>
<!--main div begin-->
<div id="maindiv" class="maindivelse">
<span class="linktextbutton">
	<span>当前的位置：
		  <%if(cols!=null){
            for(int i=cols.length-1; i>=0; i--){
           		out.print("/"+cols[i].getName());
            }}%>
    </span>
</span>
	<div class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<div>
                      <span class="span_title"><%=title%>文章</span>
				<div>
					<span class="spanthree"></span>
					<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
						<tr class="userlist">
							<td class="userlisttext" width="20%">文章标题：</td>
							<td class="userlistinput" width="40%">
								<input type="text" id="article_title" maxlength="50" value="<%if(title.equals("修改")){%><%=article.getTitle()%><%}%>"></input>
							</td>
							<td class="userlistoutput" width="40%">&nbsp;文章标题不能为空！</td>
						</tr>
					</table>
                </div>
				</div>
			</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
    
    <div id="relatedarticle"  class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
		<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
			<span class="span_title"><a href="javascript:openArticleInfo();">
			<img src="<%=request.getContextPath()%>/img/main/add.png" id="article_info_img" style="border:0px"/>
			&nbsp;高级选项（文章相关信息）</a></span>
			<div id="article_info_div" style="display:none">
				<span class="spanthree"></span>
				<!-- input table begin -->
				<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">														
					<tr class="userlist">
						<td class="userlisttext" width="20%">缩略图：</td>
						<td class="userlistinput"  width="30%">
						<% String path = InitServlet.WEB_SITE_URL+"/upload/"+userInfo.getName()+"/image"; %>
							<div style="float:right;">
								<p><button type="button" onclick="editPicure('<%=path%>');">剪裁图片</button></p>
								<p><button type="button" onclick="clearImage();">清除图片</button></p>
							</div>
							<div id="thumbnailsdiv">
								<img id="articlethumbnailsimage" src="<%=path%><%=article.getPicture()%>" onerror="src='<%=request.getContextPath()%>/img/blank.gif;'" />
							</div>
							<div class="anyinput">								
								<input id="articlethumbnails" type="text" size="21" disabled="disabled" value="<%if(article.getPicture()!=null||article.getPicture()!=""){out.println(article.getPicture());}%>" />
								<button type="button" onclick="selectPicture();">选择图片</button>
							</div>
						</td>
						<td class="userlistoutput" width="40%">&nbsp;</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">选择上级栏目：</td>
						<td class="userlistinput" width="40%">
							<div id="treemenulist">
						    	<div class="menulist">
						    	<div class="menubox" id="sel_div">
						    	<a href="#x" onclick="showInfoZone($('sel_div'),'col_info_zone','col_info_zone_bg');">
						    	<span class="menulistico"><img src="img/main/add.png" style="border:0px"/></span>
						    	<span class="menutitle" id="sheet_column" style="font-size:12px;"><%=cols[0].getName()%></span></a>
						    	</div>
    						</div></div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">文章置顶：</td>
						<td class="userlistinput"  width="40%">
							<div class="anyinput">
								<input type="checkbox" value="<%if(article.isTop()){%>true<%}else{%>false<%}%>" id="article_top" 
								<%if(article.isTop()){%>checked="checked"<%}%> onclick="this.value=this.checked;" />
							</div>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">文章排序：</td>
						<td class="userlistinput"  width="40%">
							<input id="order_cnt" type="text" value="<%=title.equals("修改")?article.getOrdercnt():0%>"/>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">文章来源：</td>
						<td class="userlistinput"  width="40%">
							<input id="articlesourceinput" type="text" value="<%if(title.equals("修改")){%><%=article.getSource() %><%}%>"/>
						</td>
						<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist">
							<td class="userlisttext" width="20%">文章作者：</td>
							<td class="userlistinput" width="40%">
								<input type="text" id="article_author" maxlength="20" value="<%if((title.equals("修改")&&(article.getAuthor()!=null))){%><%=article.getAuthor()%><%}%>"></input>
							</td>
							<td class="userlistoutput" width="30%">&nbsp;</td>
					</tr>
					<tr class="userlist">
						<td class="userlisttext" width="20%">文章摘要：</td>
						<% String strChecked1 = "checked=\"checked\"";
						   String strChecked2 = "";
							if(article.getId()== 0){
							   strChecked1 = "";
							   strChecked2 = "checked=\"checked\"";
							}%>
						<td class="userlistinput"  width="40%">
							<div class="anyinput">
								<input type="radio" value="0" name="articledescription" <%=strChecked1%> onclick="$('articledescriptiontextarea').disabled=false;"/>文字录入
								<input type="radio" value="1" name="articledescription" <%=strChecked2%> onclick="$('articledescriptiontextarea').disabled=true;" />从文章中获取
							</div>
							<div class="anyinput">
								<textarea id="articledescriptiontextarea" rows="3" cols="50" <%if(article.getId()==0){%>disabled="disabled"<%}%>><%=article.getNote()==null?"":article.getNote()%></textarea>
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
	<div id="cu_channel" class="viewfirst" onmouseout="ViewShowFirst(this);" onmouseover="ViewShowSecond(this);">
		<!--keboardfirst begin-->
		<b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
			<div class="keyboardfirst" onmouseout="KeyboardShowFirst(this);" onmouseover="KeyboardShowSecond(this);">
				<span class="span_title">文章内容</span>
					<div>
						<span class="spanthree"></span>
						<table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
							<tr class="tablelist">
								<td class="tablelistfirst3rt" width="100%">
									<textarea id="ckeditor">&nbsp;</textarea>
								</td>
							</tr>
						</table>					
					</div>			
				</div>
		<b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b> 
		<!--keboardfirst end-->	
	</div>
	<span><input type="button" value="<%=title%>" onclick="editArticle(<%=article.getId()%>);" class="submitbutton"></input></span>
	<span><input type="button" value="<%=title%>+发布" onclick="editArticle(<%=article.getId()%>,true);" class="submitbutton"></input></span>
    <span><input type="button" value="取消" onclick="cancelEditArticle(<%=article.getColumnid()%>);" class="submitbutton"></input></span>
	<!--submitbutton end-->
</div>
<!--maindiv end-->
<%String selTxt = (String)request.getAttribute("selTxt");
	if(selTxt==null) selTxt="";%>
<div id="col_info_zone" class="infofloatzone">
	<div class="inputzoneinfo">
		<div class="inputzoneinfotitlecenter"><font class="floatlinktext"><font id="info_title_name">被选择的栏目列表</font>&nbsp;&nbsp;√请在要选择的栏目打勾&nbsp;&nbsp;</font><span class="inputzoneti"></span></div>
		<div class="inputzoneexit">
			<a title="安全退出" id="input_zone_info_close" class="exitout" onclick="closeUserInfo('col_info_zone','col_info_zone_bg');return false;" onmouseover="this.className='exitmove';" onmouseout="this.className='exitout';" href="#" ></a>
		</div><%=selTxt%>
	</div>
</div>
<div id="col_info_zone_bg">&nbsp;</div>
<div id="hidden_content" style="display:none;"><%=article.getContent()==null?"":article.getContent()%></div>