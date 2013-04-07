<%@page contentType="text/html;charset=utf-8"%>
<%	int sum = new Integer(request.getAttribute("sumPage").toString()).intValue();
	int pageNo = new Integer(request.getAttribute("pageNo").toString()).intValue();
	int cnt = new Integer(request.getAttribute("cnt").toString()).intValue();
	String[] all = (String[])request.getAttribute("allPages");%>
<div class="pagestyle">
<%if(cnt!=0){
%><!-- 显示全部选中 --><a class="submitinput"><input type="checkbox" onclick="checkAll(this.checked);"></input></a> <a class="allbuttom">&nbsp;全部选中&nbsp;</a>
<%}
if(cnt!=0&&sum!=1){%> 
<!-- 显示上页按钮 --><a class="leftbuttom" onmouseover="this.className='leftbuttomover'" onmouseout="this.className='leftbuttom'" onclick="submitDL(${pageNo-1},${sumPage},${pageNo});">&lt;</a>
<%} 
for(int t = 0 ;t<all.length;t++){
  		if(!all[t].equals("")){
  			if(all[t].equals(""+pageNo)){%>
<!-- 当前页 --><a class="currentbottom" onmouseover="this.className='currentbottomover'" onmouseout="this.className='currentbottom'" onclick="submitDL(<%=all[t] %>,${sumPage},${pageNo})"><%=all[t]%></a>
<%}else{%>
<!-- 显示页数 --> <a class="numberbuttom" onmouseover="this.className='numberbuttomover'" onmouseout="this.className='numberbuttom'" onclick="submitDL(<%=all[t] %>,${sumPage},${pageNo})"><%=all[t]%></a>
<%}	} }%> 
<%if(cnt!=0&&sum!=1){%>
	<!-- 显示下页按钮 --><a class="rightbuttom" onmouseover="this.className='rightbuttomover'" onmouseout="this.className='leftbuttom'" onclick="submitDL(${pageNo+1},${sumPage},${pageNo});">&gt;</a>
<%}
if(cnt!=0){%> 
	<!-- 显示总的信息数目 --><a class="allbuttom">&nbsp;共&nbsp;</a><a class="currentbottom"><b><%=cnt%></b></a><a class="allbuttom">&nbsp;条信息&nbsp;</a>
<%if(cnt!=0&&sum!=1){%>	
	<!-- 显示每页的信息数目 --><a class="allbuttom">&nbsp;每页最多显示数</a><a class="currentbottom"><b>${pageSize}</b>&nbsp;</a>
    <!-- 显示跳转框 -->
	<a class="allbuttom">&nbsp;跳转到&nbsp;</a>
	<a class="jumpbuttom"><input class="jumppage" type="text" id="to_page_no" size="4" maxlength="4"></input></a>
    <a class="allbuttom">&nbsp;页&nbsp;</a>
    <a class="go" onmouseover="this.className='goover'" onmouseout="this.className='go'" onclick="submitGo(${sumPage},${pageNo});">&nbsp;确&nbsp;定&nbsp;</a> 
<% }  }%>
</div>

