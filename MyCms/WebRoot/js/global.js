var info;
function moveDiv(el){
	var yPos=parseInt($("move_button").style.top);
	var step=6;
	if (yPos==el){
		clearInterval(info);
	}else if(yPos<el){
		if(yPos+step<el){
			$("move_button").style.top=yPos+step+'px';
		}else{
			$("move_button").style.top=el+'px';
		}
	}else if(yPos>el){
		if(yPos-step>el){
			$("move_button").style.top=yPos-step+'px';
		}else{
			$("move_button").style.top=el+'px'; 
		}
	}
}
function drag2(){
	var offsetX=0;
	var isDrag=false;
	var MAX_LEFT_WIDTH=430;
	var MIN_LEFT_WIDTH=120;
	var currentLeft=MIN_LEFT_WIDTH;
	var leftWidth=MIN_LEFT_WIDTH + 80;
	var moveButtonLeft=MIN_LEFT_WIDTH;
	var bodySize=getBodySize();
	var MAX_TOP=bodySize[1]-100;
	var b=23;
	if(isIE){b=21;}else{b=24;}
	$('CNLTreeMenu').style.width=leftWidth+'px';
	$('control').style.left=leftWidth+'px';
	$('move_button').style.left=leftWidth-b+'px';
	$('move_button').style.top=bodySize[1]/2+'px';
	$('control').onmouseover=function(evt){
		var evt=window.event?window.event:evt;
		$('move_button').style.top=pointerY(evt)-b+'px';
	};
	$('move_button').onmousedown=function(evt){
		var evt=window.event?window.event:evt;
		if ((evt.which && evt.which==1) || (evt.button && evt.button==1)){
			isDrag=true;
			offsetX=evt.clientX;
			zjhDrag(evt);
		}
		stopEvent(evt);
	};
	$('control').onmouseover=function(evt){
		clearInterval(info);
		var evt=window.event?window.event:evt;
		var moveTop=pointerY(evt)-b;
		var MIN_TOP=96;
		if(pointerY(evt)>MAX_TOP){
				moveTop=MAX_TOP;
		}else if(pointerY(evt)<MIN_TOP){
					moveTop=MIN_TOP;
		}
		info=setInterval("moveDiv('"+moveTop+"')",10);
	};
	document.onmousemove=function(evt){
		if(isDrag){
			var evt=window.event?window.event:evt;
			var bodySize=getBodySize();
			if(pointerX(evt)>MAX_LEFT_WIDTH){
				$('control').style.left=MAX_LEFT_WIDTH+'px';
				$('CNLTreeMenu').style.width=MAX_LEFT_WIDTH+'px';
				$('move_button').style.left=MAX_LEFT_WIDTH-b+'px';
			}else if(pointerX(evt)<MIN_LEFT_WIDTH){
				$('control').style.left=MIN_LEFT_WIDTH+'px';
				$('CNLTreeMenu').style.width=MIN_LEFT_WIDTH+'px';
				$('move_button').style.left=MIN_LEFT_WIDTH-b+'px';
			}else{
				$('control').style.left=pointerX(evt)-offsetX+currentLeft+'px';
				$('CNLTreeMenu').style.width=pointerX(evt)-offsetX+leftWidth+'px';
				$('move_button').style.left=pointerX(evt)-offsetX+moveButtonLeft+'px';
			}
			stopEvent(evt);
		}
	};
	document.onmouseup=function(evt){
		isDrag=false;
		var evt=window.event?window.event:evt;
		zjhDrag(evt);
		stopEvent(evt);
	};
	function zjhDrag(evt){
		if(pointerX(evt)>MAX_LEFT_WIDTH){
			currentLeft=MAX_LEFT_WIDTH;
			leftWidth=MAX_LEFT_WIDTH;
			moveButtonLeft=MAX_LEFT_WIDTH-b;
		}else if(pointerX(evt)<MIN_LEFT_WIDTH){
			currentLeft=MIN_LEFT_WIDTH;
			leftWidth=MIN_LEFT_WIDTH;
			moveButtonLeft=MIN_LEFT_WIDTH-b;
		}else{
			currentLeft=parseInt($('control').style.left);
			leftWidth=parseInt($('CNLTreeMenu').style.width);
			moveButtonLeft=parseInt($('move_button').style.left);
		}
	}
}
function getHeight(){
	var hei=98;
	if(isIE) hei=105;
	$('CNLTreeMenu').style.height='auto';
	$('control').style.height='auto';
	$('right').style.height='auto';
	var bodySize=getBodySize();
	$('CNLTreeMenu').style.height=(bodySize[1]-hei)+'px';
	$('control').style.height=(bodySize[1]-hei)+'px';
	$('right').style.height=(bodySize[1]-hei)+'px';
}
function cmsLoad(){
    var clock = new Clock();
    clock.display($("clock"));
	getHeight();
    drag2();
	loadCovering();
	initMenu();
}
function initMenu(){
	if($('menu_sheet_1')){
		changeSheet('/MainCtrl?page=ColLeftPage&parentId=1','/jsp/inc/welcome.jsp','文章编辑',$('menu_sheet_1'));
	}else if($('menu_sheet_2')){
		changeSheet('/jsp/inc/left_area.jsp?type=fragment','/MainCtrl?page=FragmentManagePage','广告管理',$('menu_sheet_2'));
	}else if($('menu_sheet_3')){
		changeSheet('/jsp/inc/left_area.jsp?type=publish','/MainCtrl?page=PublishIndexPage','文章发布',$('menu_sheet_3'));
	}else if($('menu_sheet_4')){
		changeSheet('/jsp/inc/left_area.jsp?type=column','/MainCtrl?page=ColManagePage&parentId=1','栏目管理',$('menu_sheet_4'));
	}else if($('menu_sheet_5')){
		changeSheet('/jsp/inc/left_area.jsp?type=system','/MainCtrl?page=ChangePasswordPage','系统管理',$('menu_sheet_5'));
	}	
}
function Clock(){
	var date=new Date();
	this.year=date.getFullYear();
	this.month=date.getMonth()+1;
	this.date=date.getDate();
	this.day=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六")[date.getDay()];
	this.hour=date.getHours()<10?"0"+date.getHours():date.getHours();
	this.minute=date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes();
	this.second=date.getSeconds()<10?"0"+date.getSeconds():date.getSeconds();
	this.toString=function(){
		return this.year+"年"+this.month+"月"+this.date+"日 "+this.hour+":"+this.minute+":"+this.second+" "+this.day; 
	};	
	this.toSimpleDate=function(){
		return this.year+"-"+this.month+"-"+this.date;
	};	
	this.toDetailDate=function(){
		return this.year+"-"+this.month+"-"+this.date+" "+this.hour+":"+this.minute+":"+this.second;
	};	
	this.display=function(ele){
		var clock=new Clock();
		ele.innerHTML=clock.toString();
		window.setTimeout(function(){clock.display(ele);},1000);
	};
}
function submitDL(pageNo,sumPage,pageNoJsp,page){
	if(pageNoJsp==pageNo){		
	}else if((pageNoJsp==1) && (pageNo==0)){
	}else if((pageNoJsp==sumPage) && (pageNo==sumPage+1)){
	}else if(pageNo<1){
	}else if(pageNo>sumPage){
	}else{
		var search;
		if($('search_name')){
			search="&search_name="+encodeURIComponent($('search_name').value.trim());
			if($('f_date1')&&$('f_date2')){
				search+='&begin_date='+$('f_date1').value
						+'&end_date='+$('f_date2').value
						+'&author='+$('author').value.trim()
						+'&editor='+$('editor').value.trim();
			}
		}else{
			search="&search_title="+encodeURIComponent($('search_title').value.trim());
		}
		exeRequest(rootUrl+"/MainCtrl",rightDivContent,"page="+$('page').value+"&page_no="+pageNo+search);
	}
}
function submitGo(sumPage,pageNoJsp,page){
	var txtObj=$("to_page_no");
	var pageNo=txtObj.value.trim();
	if (pageNo.match(/\D/)== null){
	}else{
		alert("请输入数字。");
		txtObj.select();
		return;
	}
	if(pageNoJsp==pageNo){		
	}else if(pageNo<1){
		alert("选择的页数必须大于0。");
		txtObj.select();
	}else if(pageNo>sumPage){
		alert("选择的页数不能大于总页数。");
		txtObj.select();
	}else{
		var search;
		if($('search_name')){
			search="&search_name="+encodeURIComponent($('search_name').value.trim());
			if($('f_date1')&&$('f_date2')){
				search+='&begin_date='+$('f_date1').value
						+'&end_date='+$('f_date2').value
						+'&author='+$('author').value.trim()
						+'&editor='+$('editor').value.trim();
			}
		}else{
			search="&search_title="+encodeURIComponent($('search_title').value.trim());
		}
		exeRequest(rootUrl+"/MainCtrl",rightDivContent,"page="+$('page').value+"&page_no="+pageNo+search);
	}
}
function checkAll(isChencked){
	var checkObj=document.getElementsByName('checks_name');
	for(var i=0;i<checkObj.length;i++){
		if (isChencked){
			checkObj[i].checked=true;
		}else{
			checkObj[i].checked=false;
		}
	}
}
function handleCheckeds(param,strView,pageNo,sum,size){
	var checkObj=document.getElementsByName('checks_name');
	var objLen=checkObj.length;
	var isChecked=false;
	for(var i=0;i<objLen;i++){
		if (checkObj[i].checked){
			isChecked=true;
			break;
		}
	}
	if(isChecked && objLen>0){
		if(confirm('是否真的要'+strView+'被选中的对象?')){
			var tmpArr='';
			var cnt=0;
			for(var i=0;i<objLen;i++){
				if(checkObj[i].checked){
					tmpArr=tmpArr+checkObj[i].value+',';
					cnt++;
				}
			}
			tmpArr=tmpArr.substring(0,tmpArr.length-1);
			if(pageNo==1 && sum==1){
				if(cnt==size){
					pageNo=1;
				}
			}else if(pageNo==sum){
				if(cnt==size){
					pageNo=pageNo-1;
				}
			}
			if(pageNo){
				var index=param.indexOf('&',1);
				var begin=param.substring(0,index);
				var end=param.substring(index);
				param=begin+'&page_no='+pageNo+end+tmpArr;
				if($('search_name')){
					param+="&search_name="+encodeURIComponent($('search_name').value.trim());
				}else{
					param+="&search_title="+encodeURIComponent($('search_title').value.trim());
				}
				exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
			}else{
				alert('参数不全；无法执行。');
			}
		}
	}else{
		alert('请选择要'+strView+ '的对象。');
	}
}
function ksf(obj){
	obj.className='keyboardfirst';
}
function _ksf(obj){
	return function(){
		ksf(obj);
	};
}
function KeyboardShowFirst(obj){
	obj.className='keyboardfirst';
}
function kss(obj){
	obj.className='keyboardsecond';
}
function _kss(obj){
	return function(){
		kss(obj);
	};
}
function KeyboardShowSecond(obj){
	obj.className='keyboardsecond';
}
function vsf(obj){
	obj.className='viewfirst';
}
function _vsf(obj){
	return function(){
		vsf(obj);
	};
}
function ViewShowFirst(obj){
	obj.className='viewfirst';
}
function vss(obj){
	obj.className='viewsecond';
}
function _vss(obj){
	return function(){
		vss(obj);
	};
}
function ViewShowSecond(obj){
	obj.className='viewsecond';
}
function PageShowFirst(obj){
	obj.className='pageshowfirst';
}
function PageShowSecond(obj){
	obj.className='pageshowsecond';
}
function PageShowFirstGo(obj){
	obj.className='pageshowfirstgo';
}
function PageShowSecondGo(obj){
	obj.className='pageshowsecondgo';
}
function showThisLinkOut(obj){
	obj.className='tablelist';
}
function showThisLinkOver(obj){
	obj.className='tableListShow';
}
function mouceContrlOut(obj){
	obj.className='control_style_out';
}
function mouceContrlOver(obj){
	obj.className='control_style_over';
}
function mouceContrlOutIe(obj){
	obj.className='ie_control_style_out';
}
function mouceContrlOverIe(obj){
	obj.className='ie_control_style_over';
}
function handleDateComponent(){
    var cal=Calendar.setup({
        onSelect: function(cal) {cal.hide()}
    });
    if($("f_btn1")) cal.manageFields("f_btn1","f_date1","%Y-%m-%d");
    if($("f_btn2")) cal.manageFields("f_btn2","f_date2","%Y-%m-%d");
}
function showFloatInfoZone(obj){
	$('float_info_zone').style.display='block';
	$('input_zone_bg').style.display='block';
	var r=getAbsoultePosition(obj);
	$('float_info_zone').style.left=(r.x-$('float_info_zone').offsetWidth+obj.offsetWidth)+'px';
	$('float_info_zone').style.top=(r.y+obj.offsetHeight)+'px';
	$('input_zone_bg').style.height=($('float_info_zone').scrollHeight)+'px';
	$('input_zone_bg').style.width=$('float_info_zone').offsetWidth+'px';
	$('input_zone_bg').style.left=(r.x-$('float_info_zone').offsetWidth+obj.offsetWidth+10)+'px';
	$('input_zone_bg').style.top=(r.y+obj.offsetHeight+10)+'px';
}
function closeFloatInfo(){
	$('float_info_zone').style.display='none';
	$('input_zone_bg').style.display='none';
}
function controlTree(id,type){
	if($(id).style.display=='block'){
		$(id).style.display='none';
		$('src_'+id).src=rootUrl+'/img/tree/folder.gif';
		$('src2_'+id).src=rootUrl+'/img/tree/plusbottom.gif';
	}else{
		$(id).style.display='block';
		$('src_'+id).src=rootUrl+'/img/tree/folderopen.gif';
		$('src2_'+id).src=rootUrl+'/img/tree/minusbottom.gif';
	}
	if(type=='file'){
		$('input_zone_bg').style.height=($('float_info_zone').scrollHeight)+'px';
		$('input_zone_bg').style.width=$('float_info_zone').offsetWidth+'px';
	}else if($('col_info_zone')){
		$('col_info_zone_bg').style.height=($('col_info_zone').scrollHeight)+'px';
		$('col_info_zone_bg').style.width=$('col_info_zone').offsetWidth+'px';
	}
}
function selectColumnTemplate(templatePath,obj){
	$($('sel_template').value).value=templatePath.trim();
	obj.checked=false;
	closeFloatInfo();
}
function rightDivContent(txt){
	if(txt.indexOf('cmsinfo:alert(')==0){
		eval(txt.substring(8));
		if(txt.indexOf('页面失效')==0){
			location=rootUrl+"/sign_in.jsp"; 
		}
	}else{
		$('change_right').innerHTML=txt;
		if ($('cms_login') && ($('cms_login').value=='cms_login_reload')){
			location=rootUrl+"/sign_in.jsp";
		}else if($('msg') && ($('msg').value!='null')){
			alert($('msg').value);
		}else if($('articlethumbnailsfilepath')!=null && $('articlethumbnailsfilepath').value!='null'){
			$('articlethumbnails').value=$('articlethumbnailsfilename').value;
			$('articlethumbnailsimage').value=$('articlethumbnailsfilepath').value;
		}
		if($("f_btn1")){
			handleDateComponent();
		}
	}
	if($('ckeditor')){
		if(CKEDITOR.instances['ckeditor']){
			CKEDITOR.instances['ckeditor'].destroy(true);
		}
		initCkeditor();
	}
	contiueAll();
	getHeight();
}
function treeDivContent(txt,colId){
	if(txt=='session'){
		location=rootUrl+"/sign_in.jsp";
	}else if(txt.indexOf('cmsinfo:alert(')==0){
		eval(txt.substring(8));
	}else{
		$(colId).innerHTML=txt;
	}
	contiueAll();
}
function colTreeDivContent(txt,colId){
	if (txt.indexOf('cmsinfo:alert(')!=-1){
		eval(txt.substring(8));
	}else{
		$(colId).innerHTML=txt;
	}
	contiueAll();
}
function textInfoDivContent(txt,colId){
	if (txt.indexOf('cmsinfo:alert(')!=-1){
		eval(txt.substring(8));
	}else{
		$(colId).value=txt;
	}
	contiueAll();
}
function imageDivContent(txt){
	if(txt.indexOf('cmsinfo:alert(')!=-1){
		eval(txt.substring(8));
	}else{
		$('image_div_submit').innerHTML=txt;
		getImageDiv();
	}
	contiueAll();
}
function changeSheet(leftUrl,RightUrl,sheetTitle,obj){
	exeRequest(rootUrl+leftUrl,leftDivContent,null,RightUrl);
	$('sheet_title').innerHTML=sheetTitle;
	handleSheets(obj);
}
function handleSheets(obj){
	var elements=$("sheets").childNodes;
	var j=0;
	for(var i=0;i<elements.length;i++){
	  if(elements[i].tagName && elements[i].tagName=='A' && elements[i]!=obj){
		  j++;
		  elements[i].onmouseout=function(){
			  this.className='menu_button_out';
		  }
		  elements[i].onmouseover=function(){
			  this.className='menu_button_over';
			  this.style.color='blue';
		  }
		  elements[i].className='menu_button_out';
      }
    }
	obj.onmouseover=null;
	obj.onmouseout=null;
	if(j<2){
		obj.className='menu_button_out';
	}else{
		obj.className='menu_button_over';
		obj.style.color='#FD7E00';
	}
}
function leftDivContent(txt,RightUrl){
	if(txt=='session'){
		location=rootUrl+"/sign_in.jsp";
	}else{
		$('left_content').innerHTML=txt;
		if(RightUrl){
			exeRequest(rootUrl+RightUrl,rightDivContent);
		}else{
			contiueAll();
		}
		if($('my_menu')){
			var	myMenu=new SDMenu('my_menu');
			myMenu.init();
		    myMenu.expandMenu(myMenu.submenus[0]);
		}
	}
}