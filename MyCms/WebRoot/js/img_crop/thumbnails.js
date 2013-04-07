function editImage(){
	if($("dragDivheight").value && $("dragDivwidth").value){
		$("dragDiv").style.height=$("dragDivheight").value+'px';
		$("dragDiv").style.width=$("dragDivwidth").value+'px';
		ic.Init();
	}else{
		alert('请输入尺寸。');
	}
}
function thumbnailsCreate(path){
	var p = ic.Url, o = ic.GetPos();
	x = o.Left, y = o.Top, w = o.Width - 2, h = o.Height - 2,pw = ic._layBase.width, ph = ic._layBase.height;
	if (x < 0) x = 0;
	if (y < 0) y = 0;
	var fileName=path.substring(path.lastIndexOf("/")+1);
	exeRequest(rootUrl+"/MainCtrl",thumbnailDivContent,
			"page=ArticleThumbnailsPage&oldfilepath="+path+"&imgtop="+y+"&imgleft="+x
				+"&imgwidth="+w+"&imgheight="+h+"&fileName="+fileName);
}
function thumbnailDivContent(txt){
	if(txt=='session'){
		alert('页面失效；请重新登陆。');
		window.opener.location=rootUrl+"/sign_in.jsp";
		window.close();
	}else if(txt=='error'){
		alert('生成图片失败；可能是没有真实图片区域。');
		window.close();
	}else{
		toGetImageSize(rootPath+txt,150,150);
		window.opener.document.getElementById('articlethumbnails').value=txt;
	}
}
var ic = new ImgCropper("bgDiv","dragDiv",path,{
	Width: 100+'%', Height: 100+'%', Color: "#000",
	Resize: true,
	Right: "rRight", Left: "rLeft", Up:	"rUp", Down: "rDown",
	RightDown: "rRightDown", LeftDown: "rLeftDown", RightUp: "rRightUp", LeftUp: "rLeftUp",
	Preview: "viewDiv", viewWidth: 300, viewHeight: 300
});
$("idOpacity").onclick=function(){
	if(ic.Opacity==0){
		ic.Opacity=50;
		this.value="全透明";
	}else{
		ic.Opacity=0;
		this.value="半透明";
	}
	ic.Init();
};
$("idColor").onclick = function(){
	if(ic.Color == "#000"){
		ic.Color = "#fff";
		this.value = "使用黑色背景";
	}else{
		ic.Color = "#000";
		this.value = "使用白色背景";
	}
	ic.Init();
};
$("idScale").onclick = function(){
	if(ic.Scale){
		ic.Scale = false;
		this.value = "使用比例";
	}else{
		ic.Scale = true;
		this.value = "取消比例";
	}
	ic.Init();
};
$("bgDiv").onmousemove = function(){
	$("dragDivheight").value=($("dragDiv").style.height).replace(/px/,'');
	$("dragDivwidth").value=($("dragDiv").style.width).replace(/px/,'');
	if($("dragDivwidth").value > ic._layBase.width){
		$("dragDivwidth").value = ic._layBase.width;
		$("dragDiv").style.width= ic._layBase.width+'px';		
	}else if($("dragDivheight").value > ic._layBase.height){
		$("dragDivheight").value = ic._layBase.height;
		$("dragDiv").style.height=ic._layBase.height+'px';
	}	
};