<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/51java" prefix="51java" %>
<div style="background-color:#E8F8FF;">
	<p style="font-weight:bold;padding-left:5px;padding-top:10px;">看完本文后的感受：</p>
	<div style="padding-top:10px;height:100px;">
	   <div style="float:left;padding-left:26px;padding-right:26px;">
	   		<a herf="#" style="cursor:pointer;" onclick="vote(1);"><p><img src='http://${header["host"]}${pageContext.request.contextPath}/img/plugin//face/3.gif'/></p>
	   		<p style="padding-left:10px;padding-top:6px;">给力</p>
	   		<p style="align:center;padding-top:6px;">（<span id="face1_cnt" style="color:red">0</span>票）</p></a>
	   	</div>
	   <div style="float:left;padding-left:26px;padding-right:26px;">
	   		<a herf="#" style="cursor:pointer;" onclick="vote(2);"><p><img src='http://${header["host"]}${pageContext.request.contextPath}/img/plugin//face/1.gif'/></p>
	   		<p style="padding-left:10px;padding-top:6px;">动心</p>
	   		<p style="align:center;padding-top:6px;">（<span id="face2_cnt" style="color:red">0</span>票）</p></a>
	   	</div>
	   	<div style="float:left;padding-left:26px;padding-right:26px;">
	   		<a herf="#" style="cursor:pointer;" onclick="vote(3);"><p><img src='http://${header["host"]}${pageContext.request.contextPath}/img/plugin//face/9.gif'/></p>
	   		<p style="padding-left:10px;padding-top:6px;">废话</p>
	   		<p style="align:center;padding-top:6px;">（<span id="face3_cnt" style="color:red">0</span>票）</p></a>
	   	</div>
	   <div style="float:left;padding-left:26px;padding-right:26px;">
	   		<a herf="#" style="cursor:pointer;" onclick="vote(4);"><p><img src='http://${header["host"]}${pageContext.request.contextPath}/img/plugin//face/2.gif'/></p>
	   		<p style="padding-left:10px;padding-top:6px;">专业</p>
	   		<p style="align:center;padding-top:6px;">（<span id="face4_cnt" style="color:red">0</span>票）</p></a>
	   	</div>
	   	<div style="float:left;padding-left:26px;padding-right:26px;">
	   		<a herf="#" style="cursor:pointer;" onclick="vote(5);"><p><img src='http://${header["host"]}${pageContext.request.contextPath}/img/plugin//face/5.gif'/></p>
	   		<p style="padding-left:10px;padding-top:6px;">标题党</p>
	   		<p style="align:center;padding-top:6px;">（<span id="face5_cnt" style="color:red">0</span>票）</p></a>
	   	</div>
	   <div style="float:left;padding-left:26px;padding-right:26px;">
	   		<a herf="#" style="cursor:pointer;" onclick="vote(6);"><p><img src='http://${header["host"]}${pageContext.request.contextPath}/img/plugin/face/4.gif'/></p>
	   		<p style="padding-left:10px;padding-top:6px;">路过</p>
	   		<p style="align:center;padding-top:6px;">（<span id="face6_cnt" style="color:red">0</span>票）</p></a>
	   	</div>
	</div>
</div>
<script type="text/javascript" src='http://${header["host"]}${pageContext.request.contextPath}/js/system.js' ></script>
<script type="text/javascript">
function initFeeling(txt){
	var feelings=txt.split(",");
	$('face1_cnt').innerHTML=feelings[0];
	$('face2_cnt').innerHTML=feelings[1];
	$('face3_cnt').innerHTML=feelings[2];
	$('face4_cnt').innerHTML=feelings[3];
	$('face5_cnt').innerHTML=feelings[4];
	$('face6_cnt').innerHTML=feelings[5];
}
function updateFeeling(txt,i){
	alert(txt);
	if(txt=='感谢您的投票。'){
		$('face'+i+'_cnt').innerHTML=parseInt($('face'+i+'_cnt').innerHTML)+1;
	}
}
function vote(i){
	exeRequest('http://${header["host"]}${pageContext.request.contextPath}/PluginCtrl',
			updateFeeling,'page=UpdateFeelingPage&article_id=${article.id}&i='+i,i);
}
exeRequest('http://${header["host"]}${pageContext.request.contextPath}/PluginCtrl',
		initFeeling,
		'page=LoadFeelingPage&article_id=${article.id}');
</script>