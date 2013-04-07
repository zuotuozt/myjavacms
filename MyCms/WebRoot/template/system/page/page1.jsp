<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/51java" prefix="51java" %>
<p align="center"> 第<font color="red">${pageno} / ${totalpage}</font>页
<a href="${path}1.html">首页</a>
<a href="${path}<51java:choose><51java:when test="${pageno>1}">${pageno-1}.html</51java:when><51java:otherwise>1.html</51java:otherwise></51java:choose>">上页</a>
<a href="${path}<51java:choose><51java:when test="${pageno==totalpage}">${totalpage}.html</51java:when><51java:otherwise>${pageno+1}.html</51java:otherwise></51java:choose>">下页</a>
<a href="${path}${totalpage}.html">尾页</a>
 共<font color="red">${totalcnt}</font>条记录
 转到第<input type="text" id="to_page_no" size="1"></input>页
 <input type="button" onclick="submitGo();" value="go" /></p>
<script language="javascript">
function submitGo(){
   var pageNo=document.getElementById("to_page_no").value;
   if(pageNo.match(/\D/)==null){
   }else{
      alert("请输入数字。");
      document.getElementById("to_page_no").select();
      return;
   }	  
   if (${pageno}==pageNo){
   }else if(pageNo<1){
       alert("选择的页数必须大于0。");
       document.getElementById("to_page_no").select();
   }else if(pageNo>${totalpage}){
       alert("选择的页数不能大于总页数。");
       document.getElementById("to_page_no").select();
   }else{
       location='${path}'+pageNo+'.html';
   }
}
</script>