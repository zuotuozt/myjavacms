function submitMsg(){
  if($("title").value.trim()==''){
	alert("请输入留言标题。");
	$("title").focus();
  }else  if($("content").value.trim()==''){
		alert("请输入留言内容。");
		$("content").focus();
  }else if($("certify").value.length!=4){
		alert("留言验证码是4位数字。");
		$("certify").focus();
  }else if(!isNumCheck($("certify").value.trim())){
		alert("留言验证码是大于0的数字。");
		$("certify").focus();
	}else{
		exeRequest(rootUrl+"/PluginCtrl",returnMsg,
					"page=PublishMessagePage&title="+encodeURIComponent($("title").value.trim())+
					"&content="+encodeURIComponent($("content").value.trim())+
					"&certify="+$("certify").value.trim());
	}
}
function returnMsg(txt){
	alert(txt);
	randomChangeReg('reg_random');
	contiueAll();
}
function randomChangeReg(obj){
	 $(obj).src=rootUrl+'/RandomCodeCtrl?'+Math.random();
}