var rootUrl;
var isIE=(document.all && window.ActiveXObject && !window.opera)?true:false;
var $=function(id){
	return "string"==typeof id?document.getElementById(id):id;
};
function getRadioValue(name){
	var radio=document.getElementsByName(name);
	var checkedValue=-1;
	for(var i=0;i<radio.length;i++){
		if(radio[i].checked==true){
			checkedValue=radio[i].value;
		}
	}
	return checkedValue;
}
String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, '');
}
function stopEvent(evt){
	var event=window.event?window.event:evt;
	if(event.preventDefault){
		event.preventDefault();
		event.stopPropagation();
	}else{
		event.returnValue=false;
	} 
}
function pointerX(event){
	return event.pageX || (event.clientX+(document.documentElement.scrollLeft || document.body.scrollLeft));
}
function pointerY(event){
	return event.pageY || (event.clientY+(document.documentElement.scrollTop || document.body.scrollTop));
}
function checkName(s){
	var patrn=/^(\w){1,50}$/;
	if(patrn.exec(s)){
		return true;
	}else{
		return false;
	}
}
function checkCN(str){
 if(str.match(/^[\u0391-\uFFE5]+$/g)){
  return true;
 } else {
  return false;
 }
}
function isNumCheck(str){
  if(str.match(/\D/)==null){
   return true;
  } else {
   return false;
  }
}
function getAbsoultePosition(el){
	var sLeft=0,sTop=0;
	var isDiv=/^div$/i.test(el.tagName);
	if(isDiv && el.scrollLeft){
		sLeft=el.scrollLeft;
	}
	if(isDiv && el.scrollTop){
		sTop=el.scrollTop;
	}
	var r={x: el.offsetLeft-sLeft, y: el.offsetTop-sTop};
	if(el.offsetParent){
		var tmp=getAbsoultePosition(el.offsetParent);
		r.x+=tmp.x;
		r.y+=tmp.y;
	}
	return r;
}
if (window.ActiveXObject && !window.XMLHttpRequest){
	window.XMLHttpRequest=function(){
		return new ActiveXObject((navigator.userAgent.toLowerCase().indexOf(
				'msie 5')!=-1)?'Microsoft.XMLHTTP':'Msxml2.XMLHTTP');
	}
}
function exeRequest(url,handle,v,i){
	pauseAll();
	var xmlHttp=new XMLHttpRequest();
	xmlHttp.onreadystatechange=function(){
		if (xmlHttp.readyState==4){
			if (xmlHttp.status==200){
				var repTxt=xmlHttp.responseText;
				handle(repTxt,i);
			}
		}
	}
	xmlHttp.open("post",url);
	xmlHttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	xmlHttp.send(v);
}
var Json={
	toString:function(obj){
		switch (typeof (obj)){
		case 'string':
			return '"'+obj.replace(/(["\\])/g, '\\$1')+'"';
		case 'array':
			return '['+obj.map(Json.toString).join(',')+']';
		case 'object':
			var string=[];
			for( var property in obj)
				string.push(Json.toString(property)+':'
						+Json.toString(obj[property]));
			return '{'+string.join(',')+'}';
		case 'number':
			if(isFinite(obj))
				break;
		case false:
			return 'null';
		}
		return String(obj);
	},
	evaluate:function(str,secure){
		return ((typeof (str)!='string') || 
				(secure && !str.test(/^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/)))
				?null:eval('('+str+')');
	}
}
var coveringWrapper=document.createElement('div');
var covering=document.createElement('div');
function pauseAll(){
	coveringWrapper.style.display='block';
	covering.style.display='block';
}
function contiueAll(){
	covering.innerHTML='<div class="spinner"><div align="center">请耐心等待&nbsp;。。。</div><div align="center"><img src="'+rootUrl+'/img/loading2.gif" width="190px;" height="14px;"></img></div></div>';
	covering.style.display='none';
	coveringWrapper.style.display='none';
}
function loadCovering(){
	covering.className='covering';
	coveringWrapper.className='covering_wrapper';
	covering.innerHTML='<div class="spinner"><div>请耐心等待&nbsp;。。。</div><div><img src="'+rootUrl+'/img/loading2.gif" width="190px;" height="14px;"></img></div></div>';
	coveringWrapper.style.display='none';
	covering.style.display='none';
	document.body.appendChild(coveringWrapper);
	document.body.appendChild(covering);
	var bodySize=getBodySize();
	coveringWrapper.style.top='0px';
	coveringWrapper.style.left='0px';
	covering.style.left=bodySize[2]+'px';
	covering.style.top=bodySize[3]+'px';
	coveringWrapper.style.width=bodySize[0]+'px';
	coveringWrapper.style.height=bodySize[1]+'px';
}
function getBodySize(){
	var bodySize=[];
	with (document.documentElement){
		bodySize[0]=(scrollWidth>clientWidth)?scrollWidth:clientWidth;
		bodySize[1]=(scrollHeight>clientHeight)?scrollHeight:clientHeight;
		bodySize[2]=(clientWidth/2-130)+scrollLeft;
		bodySize[3]=(clientHeight/2-60)+scrollTop;
	}
	return bodySize;
}
function getPureData(data){
    data=data.replace(/<br[\s\/]{0,2}>/ig,"\r\n");
    data=data.replace(/<.*?>/ig,"");
    data=data.replace(new RegExp("&nbsp;","g")," ");
    data=data.replace(new RegExp("&lt;","g"),"<");
    data=data.replace(new RegExp("&gt;","g"),">");
    data=data.replace(new RegExp("&amp;","g"),"&");
    return data;
}