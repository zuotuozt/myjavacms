<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/51java" prefix="51java" %>
&nbsp;&nbsp;浏览次数：<span id="browse_cnt">&nbsp;</span>
<script type="text/javascript" src='http://${header["host"]}${pageContext.request.contextPath}/js/system.js' ></script>
<script type="text/javascript">
function updateBrowseCnt(txt){
	$('browse_cnt').innerHTML=txt;
}
exeRequest('http://${header["host"]}${pageContext.request.contextPath}/PluginCtrl',
		updateBrowseCnt,
		'page=LoadBrowseCntPage&article_id=${article.id}');
</script>