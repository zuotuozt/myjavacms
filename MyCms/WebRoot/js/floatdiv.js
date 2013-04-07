function DrawImage(imgD,FitWidth,FitHeight){
	var r=[imgD.width,imgD.height];
    if(imgD.width>0 && imgD.height>0){      
        if(imgD.width/imgD.height>=FitWidth/FitHeight){    
            if(imgD.width>FitWidth){
            	r=[FitWidth,(imgD.height*FitWidth)/imgD.width];
            }
         }else{   
            if(imgD.height>FitHeight){
            	r=[(imgD.width*FitHeight)/imgD.height,FitHeight];
            }
         } 
    }
    return r;
}
function treeDiv(colId,pix,page){
	if($(pix+colId).style.display==''){
		$(pix+colId).style.display='none';
	}else{
		$(pix+colId).style.display='';
		exeRequest(rootUrl+"/MainCtrl",treeDivContent,"page="+page+"&colId="+colId,pix+colId);
	}
}
function getImageDiv(){
  var elements=$('load_image').childNodes;	
  for (var i=0;i<elements.length;i++){
       if(elements[i].tagName && elements[i].tagName=='A'){
    	   for (var j=0;j<elements[i].childNodes.length;j++){
    		   if(elements[i].childNodes[j].tagName && elements[i].childNodes[j].tagName=='IMG'){
    			   elements[i].childNodes[j].id='img_'+i+'_'+j;
    			   getImageSize('img_'+i+'_'+j);
    		   }
    	   }		   
       }
    }	
}
function getImageSize(el){
	var MAX_WIDTH=100;
	var MAX_HEIGHT=100;
	var r = DrawImage($(el),MAX_WIDTH,MAX_HEIGHT);
	if(r[0]>0){
		$(el).width=r[0]; 
		$(el).height=r[1]; 
	}else{
		setTimeout("getImageSize('"+el+"')",1000);
	}
}
function toGetImageSize(path,MAX_WIDTH,MAX_HEIGHT){
	var imgObj=new Image();
	imgObj.src=path;
	if(imgObj.width>0){
		window.opener.document.getElementById('articlethumbnailsimage').src=path;
		var r=DrawImage(imgObj,MAX_WIDTH,MAX_HEIGHT);
		window.opener.document.getElementById('articlethumbnailsimage').style.width=r[0]+'px'; 
		window.opener.document.getElementById('articlethumbnailsimage').style.height=r[1]+'px';
		imgObj=null;
		window.close();
	}else{
		setTimeout("toGetImageSize('"+path+"','"+MAX_WIDTH+"','"+MAX_HEIGHT+"')",1000);
	}
}
function SDMenu(id){
	this.menu=$(id);
	this.submenus=this.menu.getElementsByTagName("div");
	this.remember=true;
	this.speed=5;
	this.oneSmOnly=true;
}
SDMenu.prototype.init=function(){
	var mainInstance=this;
	for(var i=0;i<this.submenus.length;i++)
		this.submenus[i].getElementsByTagName("span")[0].onclick=function(){
			mainInstance.toggleMenu(this.parentNode);
	}
}
SDMenu.prototype.toggleMenu=function(submenu){
	if (submenu.className=="collapsed")
		this.expandMenu(submenu);
	else
		this.collapseMenu(submenu);
}
SDMenu.prototype.expandMenu=function(submenu){
	var fullHeight=submenu.getElementsByTagName("span")[0].offsetHeight;
	var links=submenu.getElementsByTagName("a");
	for(var i=0;i<links.length;i++){
		fullHeight+=links[i].offsetHeight;
		links[i].onclick=function(){
			var links=$('my_menu').getElementsByTagName("a");
			for(var i=0;i<links.length;i++){
				links[i].className='';
			}
			this.className='current';
		};
	}
	var moveBy=Math.round(this.speed*links.length);	
	var mainInstance=this;
	var intId=setInterval(function(){
		var curHeight=submenu.offsetHeight;
		var newHeight=curHeight+moveBy;
		if(newHeight<fullHeight)
			submenu.style.height=newHeight+"px";
		else{
			clearInterval(intId);
			submenu.style.height="";
			submenu.className="";
			mainInstance.memorize();
		}
	}, 30);
	this.collapseOthers(submenu);
}
SDMenu.prototype.collapseMenu=function(submenu){
	var minHeight=submenu.getElementsByTagName("span")[0].offsetHeight;
	var moveBy=Math.round(this.speed*submenu.getElementsByTagName("a").length);
	var mainInstance=this;
	var intId=setInterval(function(){
		var curHeight=submenu.offsetHeight;
		var newHeight=curHeight-moveBy;
		if(newHeight>minHeight)
			submenu.style.height=newHeight+"px";
		else{
			clearInterval(intId);
			submenu.style.height="";
			submenu.className="collapsed";
			mainInstance.memorize();
		}
	}, 30);
}
SDMenu.prototype.collapseOthers=function(submenu){
	if(this.oneSmOnly){
		for(var i=0;i<this.submenus.length;i++)
			if(this.submenus[i]!=submenu && this.submenus[i].className!="collapsed")
				this.collapseMenu(this.submenus[i]);
	}
}
SDMenu.prototype.expandAll=function(){
	var oldOneSmOnly=this.oneSmOnly;
	this.oneSmOnly=true;
	for (var i=0;i<this.submenus.length;i++)
		if(this.submenus[i].className=="collapsed")
			this.expandMenu(this.submenus[i]);
	this.oneSmOnly=oldOneSmOnly;
}
SDMenu.prototype.collapseAll=function(){
	for(var i=0;i<this.submenus.length;i++)
		if (this.submenus[i].className!="collapsed")
			this.collapseMenu(this.submenus[i]);
}
SDMenu.prototype.memorize=function(){
	if(this.remember){
		var states=new Array();
		for (var i=0;i<this.submenus.length;i++)
			states.push(this.submenus[i].className=="collapsed"?0:1);
		var d=new Date();
		d.setTime(d.getTime()+(30*24*60*60*1000));
		document.cookie="sdmenu_"+encodeURIComponent(this.menu.id)+"="+states.join("")+";expires="+d.toGMTString()+";path=/";
	}
}