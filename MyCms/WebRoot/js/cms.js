function toPrePage(flg){
	var path=$('filepath').value.trim();
	var param='page=FileListPage&filepath='+path+'&flg='+flg+'&type='+$('type').value.trim();
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
}
function toPrePic(flg){
	window.location=rootUrl+'/MainCtrl?page=IndexPictureFileList&filepath='+$('filepath').value.trim()+'&flg='+flg;
}
function toCreatePage(flg){
	var path=$('filepath').value.trim();
	var param='page=ToCreateFilePage&filepath='+path+'&flg='+flg+'&type='+$('type').value.trim();
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
	}
function createPath(){
	var newpath=$('newpath').value.trim();
	if(checkName(newpath)){
		var path=$('filepath').value.trim();
		var param='page=CreatePathPage&filepath='+path+'&newpath='+newpath+'&type='+$('type').value.trim();
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
	}else{
		alert('新目录名必须由英文字母、数字以及下划线组成。');
		$('newpath').focus();
	}
}
function toEditFileName(filename){
	var path=$('filepath').value.trim();
	var param='page=ToFileManagePage&filepath='+path+'&filename='+filename+'&type='+$('type').value.trim();
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
}
function editFileName(){
	var filename=$('filename').value.trim();
	if (checkName(filename)){
		var path=$('filepath').value.trim();
		var param='page=FileManagePage&filepath='+path+'&filename='+filename+'&old_filename='+$('old_filename').value.trim()+'&type='+$('type').value.trim();
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
	}else{
		alert('新目录名必须由英文字母、数字以及下划线组成。');
		$('filename').focus();
	}
}
function delFile(filename){
	if (confirm('是否真的要删除（'+filename+')')){
		var path=$('filepath').value.trim();
		var param='page=FileManagePage&flg=1&filepath='+path+'&filename='+filename+'&type='+$('type').value.trim();
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
	}
}
function uploadFile(){
	var upfileName=$('upfile_name').value.trim();
	if (upfileName=='' || checkName(upfileName)){
		var upfile=$('upfile').value.trim();
		if (upfile==''){
			alert('必须有上传文件。');
			$('upfile').focus();
			return false;
		}
	}else{
		alert('文件名必须由英文字母、数字以及下划线组成。');
		$('upfile_name').focus();
		return false;
	}
	pauseAll();
	return true;
}
function toEditFileContent(name){
	var param='page=ShowEditFile&filepath='+$('filepath').value.trim()+'&filename='+name+'&type='+$('type').value.trim();
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
}
function editFileContent(){
	var filename=$('channel_path').value.trim();
	var type=$('type').value.trim();
	if(filename==''){
		alert('请输入文件名');
		$('channel_path').focus();
		return false;
	}else if(type=='1'){
		var ext=filename.substring(filename.lastIndexOf('.')+1);
		if(ext!='jsp'){
			alert('模板文件扩展名必须是.jsp');
			$('channel_path').focus();
			return false;
		}
	}
	var param='page=EditFile&filepath='+$('filepath').value.trim()+'&filename='+filename+'&content='+encodeURIComponent($('template').value.trim())+'&type='+$('type').value.trim();
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
}
function cancelEditFile(){
	var param='page=FileListPage&filepath='+$('filepath').value.trim()+'&flg=0'+'&type='+$('type').value.trim();
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
}
function selectPicture(){
	var selectPictureForm=window.open(rootUrl+'/MainCtrl?page=IndexPictureFileList&is_popup=true','缩略图选择页','height=400,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
	selectPictureForm.focus();
}
function choosePicure(path){
	loadCovering();
	pauseAll();
	window.opener.document.getElementById('articlethumbnails').value=path;
	window.opener.document.getElementById('articlethumbnailsimage').src=rootPath+path;
	toGetImageSize(rootPath+path,150,150);
}
function clearImage(){
	$("articlethumbnails").value='';
	$("articlethumbnailsimage").src=rootUrl+'/img/blank.gif';
}
function editPicure(path){
	if($("articlethumbnailsimage").src.indexOf('/img/blank.gif')==-1){
		var filename=$('articlethumbnails').value;
		var imgCrop=window.open(rootUrl+'/jsp/file/thumbnails.jsp?filepath='+path+filename,'缩略图选择页','height=400px,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
		imgCrop.focus();
	}else{
		alert('图片不存在，请重新选择图片。');
	}
}
function submitLogin(){
	var userName=$("user_name").value.trim();
	var passwd=$('passwd').value.trim();
	if(userName==""){
		alert("用户名不能为空。");
		$("user_name").focus();
		return false;
	}else if(passwd.length>16 || passwd.length<6){
		alert("密码长度必须在6~16位之间。");
		$("passwd").focus();
		return false;
	}
	return true;
}
function initInf(){
	if($('inf') && ($('inf').value!='null')){
		alert($('inf').value);
		if($('inf').value.indexOf('密码')>-1){
			$("passwd").focus();
		}else{
			$("user_name").focus();
		}
	}else{
		$("user_name").focus();
	}
}
function changePasswd(url,userId){
	var passwd=$('password').value.trim();
	var newPasswd=$('newPassword').value.trim();
	var notarizePassword=$('notarizePassword').value.trim();
	if(passwd.length > 16 || passwd.length < 6) {
		alert("原始密码长度必须在6~16位之间。");
		$('password').focus();
	}else if(newPasswd.length > 16 || newPasswd.length < 6) {
		alert("新密码长度必须在6~16位之间。");
		$('newPassword').focus();
	}else if(newPasswd != notarizePassword) {
		alert("两次输入的密码不一致,请重新输入!");
		$('newPassword').focus();
	}else{
		exeRequest(url,rightDivContent,'page=ChangePasswordPage&user_id='+userId+'&change_flag=true&password='+ encodeURIComponent(passwd) + '&new_passwd='+ encodeURIComponent(newPasswd));
	}
}
function checkUserName(){
	var userName=$('user_name').value.trim();
	if(userName.length<20 && (checkName(userName)||checkCN(userName))){
	}else{
		alert('用户名必须是纯汉字或者是英文字母、数字以及下划线组成并且长度小于20的字符串。');
		$("user_name").focus();
		return;
	}
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=CheckUserNamePage&user_name='+userName);
}
function addUser(){
	var userName=$('user_name').value.trim();
	if(userName.length<20 && (checkName(userName)||checkCN(userName))){
	}else{
		alert('用户名必须是纯汉字或者是英文字母、数字以及下划线组成并且长度小于20的字符串。');
		$("user_name").focus();
		return;
	}
	var depId=$("dep_id").value.trim();
	if (depId=="0"){
		alert("请选择所属部门。");
		return;
	}
   var checkboxs=$('makeSureItem').getElementsByTagName('input');
   var cols='';
   for(var i=0;i<checkboxs.length;i++){
    if(i==checkboxs.length-1){
    	cols+=checkboxs[i].value;
    }else{
    	cols+=checkboxs[i].value+',';
     }
   }
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,
			'page=InsertUserInfoPage&user_name='+userName
			+'&alias='+$('alias').value+'&dep_id='+depId
			+'&is_article='+$('is_article').value
			+'&is_ad='+$('is_ad').value
			+'&is_publish='+$('is_publish').value
			+'&is_column='+$('is_column').value
			+'&cols='+cols);
}
function initUserPasswd(userId,userName){
	if (confirm('真的要重置（用户名：'+userName+'）密码吗？')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=InitPasswordPage&user_id='+userId);
	}
}
function delUser(userName,userID,pageNo){
	if (confirm('是否真的要冻结('+userName+')?')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=DeleteUserInfoPage&is_del=true&user_id='+userID+"&search_name="+encodeURIComponent($('search_name').value.trim())+'&page_no='+pageNo);
	}
}
function delDepartment(depID){
	if (confirm('是否真的要删除(部门ID：'+depID+')?')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=DelDepartmentPage&dep_id='+depID);
	}
}
function reUser(userId,userName){
	if (confirm('真的要恢复（用户名：'+userName+ '）权限吗？')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=DeleteUserInfoPage&user_id='+userId);
	}
}
function editUser(userId){
   var checkboxs=$('makeSureItem').getElementsByTagName('input');
   var cols='';
   for(var i=0;i<checkboxs.length;i++){
    if(i==checkboxs.length-1){
    	cols+=checkboxs[i].value;
    }else{
    	cols+=checkboxs[i].value+',';
     }
   }
	exeRequest(rootUrl+"/MainCtrl",rightDivContent,
			"page=EditUserInfoPage&user_id="+userId+'&search_name='
			+encodeURIComponent($('search_name').value)
			+'&page_no='+$('page_no').value
			+'&alias='+$('alias').value+'&dep_id='+$('dep_id').value
			+'&is_article='+$('is_article').value
			+'&is_ad='+$('is_ad').value
			+'&is_publish='+$('is_publish').value
			+'&is_column='+$('is_column').value
			+'&cols='+cols);
}
function showEditUser(userId,pageNo){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=UserInfoPage&user_id='+userId+'&search_name='+encodeURIComponent($('search_name').value.trim())+'&page_no='+pageNo);
}
function cancelEditUser(userId){
   exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=UserManagePage&user_id='+userId+'&search_name='+encodeURIComponent($('search_name').value.trim())+'&page_no='+$('page_no').value.trim());
}
function searchUser(){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=UserManagePage&search_name='+encodeURIComponent($('search_name').value.trim()));
}
function addPreItem(selectSub,previewItem){
var items=$(selectSub).getElementsByTagName("input");
$(previewItem).innerHTML='';
 var len=0;
 for(var i=0;i<items.length;i++){
  if(items[i].checked){
   var mes="<input type='checkbox' checked value='"+items[i].value+"' title='"+items[i].title
   	+"' onclick='copyItem(\""+previewItem+"\",\""+previewItem+"\",\""
   	+selectSub+"\");same(this,\""+selectSub+"\");'>"+items[i].title+"&nbsp;";
   $(previewItem).innerHTML+=mes;
  }
 }
}
function makeSure(previewItem,makeSureItem,selectSub){
 copyItem(previewItem,makeSureItem,selectSub);
 closeUserInfo('col_info_zone','col_info_zone_bg');
}
function openZjh(previewItem,makeSureItem,selectSub,obj){
	showInfoZone(obj,'col_info_zone','col_info_zone_bg');
	 var items=$(makeSureItem).getElementsByTagName("input");
	 $(previewItem).innerHTML='';
	 var len=0;
	 for(var i=0;i<items.length;i++){
	  if(items[i].checked){
	   var mes="<input type='checkbox' checked value='"+items[i].value+"' title='"+items[i].title
	   	+"' onclick='copyItem(\""+previewItem+"\",\""+previewItem+"\",\""+selectSub+"\");same(this,\""
	   	+selectSub+"\");'>"+items[i].title+"&nbsp;";
	   $(previewItem).innerHTML+=mes;
	  }
	 }
	 var items=$(selectSub).getElementsByTagName("input");
	 for(var i=0;i<items.length;i++){
	 items[i].checked=false;
	 }
	 items=$(makeSureItem).getElementsByTagName("input");
	 for(var i=0;i<items.length;i++){
	  same(items[i],selectSub);
	 }
}
function copyItem(id1,id2,selectSub){
 var mes="";
 var items2=$(id1).getElementsByTagName("input");
 for(var i=0;i<items2.length;i++){
  if(items2[i].checked==true){
   mes+="<input type='checkbox' checked value='"+items2[i].value+"' title='"+items2[i].title
   	+"' onclick='copyItem(\""+id2+"\",\""+id1+"\",\""+selectSub+"\");same(this,\""
   	+selectSub+"\");'>&nbsp;"+items2[i].title+"&nbsp;";
  }
 }
 $(id2).innerHTML=mes;
 $(id1).innerHTML=mes;
}
function same(ck,selectSub){
var items=$(selectSub).getElementsByTagName("input");
 for(var i=0;i<items.length;i++){
  if(ck.value==items[i].value){
   items[i].checked=ck.checked;
  }
 }
}
function delColumn(columnId){
	if(confirm('真的要删除（栏目：'+columnId+'）吗？这会删除此栏目下的所有文件；慎重！！！')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=DelColPage&column_id='+columnId);
	}
}
function editColumn(){
	var columnName=$('columnname').value.trim();
	var htmlPath='';
	if($('columnsavefiledirectory')){
		htmlPath=$('columnsavefiledirectory').value.trim();
		if(htmlPath==''){
			alert('请您输入栏目路径名。');
			$('columnsavefiledirectory').focus();
			return;
		}else if(!checkName(htmlPath)){
			alert('栏目路径名只能为英文、数字和下划线组成。');
			$('columnsavefiledirectory').focus();
			return;
		}
	}
	var colIntro=$('columndescriptiontextarea').value.trim();
	var colIntroChecked=document.getElementsByName('columndescription');
	var isParent=false;
	for (i=0;i<colIntroChecked.length;i++){
	    if (colIntroChecked[i].checked && colIntroChecked[i].value=='1'){
	    	isParent=true;
	       break;
	    }
	}
	var seoTitle=$('seo_title').value.trim();
	var seoTitleChecked=document.getElementsByName('seotitle');
	var isParentSeoTitle=false;
	for (i=0;i<seoTitleChecked.length;i++) {
	    if (seoTitleChecked[i].checked && seoTitleChecked[i].value=='1'){
	    	isParentSeoTitle=true;
	       break;
	    }
	}
	var seoKeywords=$('seo_keywords').value.trim();
	var seoKeywordsChecked=document.getElementsByName('seokeywords');
	var isParentSeoKeywords=false;
	for (i=0;i<seoKeywordsChecked.length;i++){
	    if (seoKeywordsChecked[i].checked && seoKeywordsChecked[i].value=='1'){
	    	isParentSeoKeywords=true;
	       break;
	    }
	}
	var seoDescription=$('seo_description').value.trim();
	var seoDescriptionChecked=document.getElementsByName('seodescription');
	var isParentSeoDescription=false;
	for (i=0;i<seoDescriptionChecked.length;i++){
	    if (seoDescriptionChecked[i].checked && seoDescriptionChecked[i].value=='1'){
	    	isParentSeoDescription=true;
	       break;
	    }
	}
	var colType=getRadioValue('columnproperties');
	var link='';
	if($('columnlinkaddress')) link=$('columnlinkaddress').value.trim();
	var indexTemplate='';
	if($('columncovertemplate')) indexTemplate=$('columncovertemplate').value.trim();
	var listTemplate='';
	if($('columnlisttemplate')) listTemplate=$('columnlisttemplate').value.trim();
	var articleTemplate=''; 
	if($('columnarticletemplate')) articleTemplate=$('columnarticletemplate').value.trim();
	if(columnName==''){
		alert('请您输入栏目名。');
		$('columnname').focus();
	}else if(colType==-1){
		alert('请选择栏目属性！');
	}else{
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=ColEditPage&colName='
						+encodeURIComponent(columnName)+'&htmlPath='+encodeURIComponent(htmlPath)
						+'&is_parent='+isParent+'&is_parent_seo_title='+isParentSeoTitle
						+'&is_parent_seo_keywords='+isParentSeoKeywords
						+'&is_parent_seo_desc='+isParentSeoDescription
						+'&colId='+$('colId').value.trim()+'&colType='+colType
						+'&parentId='+$('col_checkbox_id').value.trim()
						+'&colIntro='+encodeURIComponent(colIntro)
						+'&seoTitle='+encodeURIComponent(seoTitle)
						+'&seoKeywords='+encodeURIComponent(seoKeywords)
						+'&seoDescription='+encodeURIComponent(seoDescription)
						+'&link='+encodeURIComponent(link)+'&indexTemplate='
						+encodeURIComponent(indexTemplate)+'&listTemplate='+encodeURIComponent(listTemplate)
						+'&articleTemplate='+encodeURIComponent(articleTemplate));
	}
}
function staticColumnsHtml(colId,isAll){
	if(colId>0){
		var pageSize=50;
		if($('page_size') && $('page_size').value.trim()!=''){
			if(isNumCheck($('page_size').value.trim())){
				pageSize=$('page_size').value.trim();
			}else{
				alert('每次生成文件数只能是数字。');
				$('page_size').select();
				return;
			}
		}
		if (confirm('真的要更新栏目下的所有静态化页面吗？这可能会消耗您大量时间；请耐心等待。。。')){
			var dateStr='';
			if($('f_date1')&&$('f_date2')){
				dateStr=" and creatime between '"+$('f_date1').value
					+"' and '"+$('f_date2').value+" 23:59:59'";
			}
			var progressBar=new ProgressBar(colId,dateStr,pageSize,isAll);
			progressBar.display();
		}
	}else{
		alert('请选择正确的栏目。');
	}
}
function staticColumns(){
	var colId=$('col_checkbox_id').value;
	if(colId>0){
		if (confirm('真的要更新栏目下的所有静态化栏目页面吗？这可能会消耗您大量时间；请耐心等待。。。')){
			exeRequest(rootUrl+'/MainCtrl',rightDivContent,"page=StaticColumnsPage&col_id="+colId);
		}
	}else{
		alert('请选择正确的栏目。');
	}
}
function showColumnTemplate(obj,selTemplate){
	showFloatInfoZone(obj);
	$('sel_template').value=selTemplate;
}
function tablePucker(values,obj){
	if(obj.className=='image_close'){
		tablePuckerClose();
		obj.className='image_add';
		$(values).style.display='none';
	}else if(obj.className=='image_add'){
		tablePuckerClose();
		obj.className='image_close';
		$(values).style.display='';
	}
}
function tablePuckerClose(){
	$("column_main_page_content").style.display='none';
	$("column_list_page_content").style.display='none';
	$("column_scrap_page_content").style.display='none';
	$("column_main_page_img").className='image_add';
	$("column_list_page_img").className='image_add';
	$("column_scrap_page_img").className='image_add';
}
function columnProperties(value){
	if(value==0){
		$("list_template").style.display="";
		$("article_template").style.display="";
		$("cover_template").style.display="none";
		$("external_links").style.display="none";
	}else if(value==1){
		$("cover_template").style.display="";
		$("list_template").style.display="none";
		$("article_template").style.display="none";
		$("external_links").style.display="none";
	}else if(value==2){
		$("external_links").style.display="";
		$("cover_template").style.display="none";
		$("list_template").style.display="none";
		$("article_template").style.display="none";
	}
}
function initCkeditor(){
	var ckEdit=CKEDITOR.replace('ckeditor',{
		enterMode:CKEDITOR.ENTER_BR,
		height:380,
		filebrowserUploadUrl:'SimpleUploader?type=file',
		filebrowserImageUploadUrl:'SimpleUploader?type=image',
		filebrowserFlashUploadUrl:'SimpleUploader?type=flash',
		font_names:'宋体;黑体;仿宋;楷体;隶书;幼圆;微软雅黑;Arial;Times New Roman;Verdana',
		toolbar:[
				['Source','Preview','Templates'],
			    ['Cut','Copy','Paste','PasteText','PasteFromWord','SpellChecker','Scayt'],
			    ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
			    ['Form','Checkbox','Radio','TextField','Textarea','Select','Button','ImageButton','HiddenField'],
			    ['Styles','Format'],'/',
			    ['Bold','Italic','Underline','Strike','Subscript','Superscript'],
			    ['NumberedList','BulletedList','Outdent','Indent','Blockquote'],
			    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			    ['Link','Unlink','Anchor'],
			    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
			    ['Font','FontSize','TextColor','BGColor']
		]
	});
	ckEdit.setData($('hidden_content').innerHTML);
}
function delArticle(articleId,columnId,pageNo){
	if (confirm('真的要删除（文章ID：'+articleId+'）吗？')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,
				'page=DeleteArticlePage&article_id='+articleId
				+"&search_name="+encodeURIComponent($('search_name').value.trim())
				+'&page_no='+pageNo+"&column_id="+columnId);
	}
}
function delSearchArticle(articleId,pageNo){
	if (confirm('真的要删除（文章ID：'+articleId+'）吗？')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,
				'page=DeleteArticlePage&article_id='+articleId
				+"&search_title="+encodeURIComponent($('search_title').value.trim())
				+'&page_no='+pageNo+"&is_search=common");
	}
}
function delAdvancedSearch(articleId,pageNo){
	if (confirm('真的要删除（文章ID：'+articleId+'）吗？')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,
				'page=DeleteArticlePage&article_id='+articleId
				+"&search_name="+encodeURIComponent($('search_name').value.trim())
				+'&begin_date='+$('f_date1').value
				+'&end_date='+$('f_date2').value
				+'&author='+$('author').value.trim()
				+'&editor='+$('editor').value.trim()
				+'&page_no='+pageNo+"&is_search=advanced");
	}
}
function delAdvancedSearchs(pageNo,sumPage,pageCnt,type){
	var params='page=DeleteCheckedArticlesPage&search_name='
				+encodeURIComponent($('search_name').value.trim())
				+'&begin_date='+$('f_date1').value
				+'&end_date='+$('f_date2').value
				+'&author='+$('author').value.trim()
				+'&editor='+$('editor').value.trim()
				+'&page_no='+pageNo+"&is_search="+type;
	handleCheckeds(params+'&articles=','删除',pageNo,sumPage,pageCnt);
}
function editArticle(articleId,isPublish){
	var columnId=$('col_checkbox_id').value.trim();
	var articleTitle=$('article_title').value.trim();
	var content=CKEDITOR.instances['ckeditor'].getData();
	var orderCnt=$('order_cnt').value.trim();
	var index='';
	if(getRadioValue('articledescription')==1){
		index=getPureData(content);
	}else if(getRadioValue('articledescription')==0 && $('articledescriptiontextarea').value==''){
		index=getPureData(content);
	}else if(getRadioValue('articledescription')==0){
		index=$('articledescriptiontextarea').value.trim();
	}
	if(articleTitle==''){
		alert('请您输入文章标题。');
		$('article_title').focus();
	}else if (!isNumCheck(orderCnt)){
		alert("排序只能是正数。");
		$('order_cnt').focus();
	}else if (content==''){
		alert("内容不能为空。");
	}else{
		var search=$('is_search').value.trim();
		if(search=='true'){
			search="&search_title="+encodeURIComponent($('search_name').value.trim())+'&is_search=true';
		}else{
			search="&search_name="+encodeURIComponent($('search_name').value.trim())+'&is_search=false';
		}
		exeRequest(rootUrl+"/MainCtrl",rightDivContent,"page=EditArticlePage&article_title="
					+encodeURIComponent(articleTitle)+"&content="
					+encodeURIComponent(content)+"&index_picture="
					+$('articlethumbnails').value.trim()+"&source="
					+$('articlesourceinput').value.trim()
					+"&article_top="+$('article_top').value.trim()
					+"&order_cnt="+orderCnt
					+"&article_index="+encodeURIComponent(index)
					+"&article_author="+$('article_author').value.trim()
					+"&column_id="+columnId+"&article_id="+articleId
					+'&page_no='+$('page_no').value.trim()
					+'&is_publish='+isPublish+search);
	}
}
function cancelEditArticle(columnId){
	var search=$('is_search').value.trim();
	if(search=='true'){
		search='page=SearchArticlePage&search_title='
		+encodeURIComponent($('search_name').value.trim())
		+'&page_no='+$('page_no').value.trim();
	}else{
		search='page=ArticleManagePage&column_id='+columnId+'&search_name='
		+encodeURIComponent($('search_name').value.trim())
		+'&page_no='+$('page_no').value.trim();
	}
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,search);
}
function showEditArticle(articleId,pageNo,isSearch){
	var search;
	if(isSearch){
		search=encodeURIComponent($('search_title').value.trim());
	}else{
		search=encodeURIComponent($('search_name').value.trim());
	}
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,
			'page=ShowEditArticlePage&article_id='+articleId
					+'&search_name='+search
					+'&page_no='+pageNo+'&is_search='+isSearch);
}
function searchArticle(columnId){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page='
			+$('page').value.trim()+'&search_name='
			+encodeURIComponent($('search_name').value.trim())+'&column_id='
			+columnId);
}
function advancedSearch(){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=AdvancedSearchPage&search_name='
			+encodeURIComponent($('search_name').value.trim())
			+'&begin_date='+$('f_date1').value
			+'&end_date='+$('f_date2').value
			+'&author='+$('author').value.trim()
			+'&editor='+$('editor').value.trim());
}/*** 文章相关信息展开****/
function openArticleInfo(){
	if($('article_info_div').style.display!='block'){
		$('article_info_div').style.display='block';
		$('article_info_img').src=rootUrl+'/img/main/delete.png';
	}else{
		$('article_info_div').style.display='none';
		$('article_info_img').src=rootUrl+'/img/main/add.png';
	}
}
function searchFragment(){
	exeRequest(rootUrl+'/MainCtrl',
		rightDivContent,
		'page=FragmentManagePage&search_name='+encodeURIComponent($('search_name').value.trim()));
}
function editFragment(fragmentId){
	var fragmentTitle=$('fragment_title').value.trim();
	var content=$('fragment_content').value.trim();
	if (fragmentTitle==''){
		alert('请您输入广告标题。');
		$('fragment_title').focus();
	}else if(content==''){
		alert("内容不能为空。");
	}else{
		exeRequest(rootUrl+"/MainCtrl",rightDivContent,
				"page=EditFragmentPage&fragment_title="
						+encodeURIComponent(fragmentTitle)+"&content="
						+encodeURIComponent(content)+"&fragment_id="
						+fragmentId+"&search_name="
						+encodeURIComponent($('search_name').value.trim())
						+"&page_no="+$('page_no').value.trim()
						+"&column_id="+$('column_id').value);
	}
}
function showEditFragment(fragmentId,pageNo){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,
			'page=ShowEditFragmentPage&fragment_id='+fragmentId
					+'&search_name='+encodeURIComponent($('search_name').value.trim())
					+'&page_no='+pageNo);
}
function cancelEditFragment(fragmentId){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,
			'page=FragmentManagePage&fragment_id='+fragmentId
					+'&search_name='+encodeURIComponent($('search_name').value.trim())
					+'&page_no='+$('page_no').value.trim());
}
function delFragment(fragmentId,pageNo){
	if (confirm('真的要删除（广告：'+fragmentId+'）吗？')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,
				'page=DeleteFragmentPage&fragment_id='+fragmentId
						+'&page_no='+pageNo);
	}
}
function showJsFragment(fragmentId,pageNo){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,
			'page=ShowJsFragmentPage&fragment_id='+fragmentId+'&page_no='+pageNo);
}
function showUpdateSys(obj){
	$('input_zone').style.display='block';
	$('input_zone_bg').style.display='block';
	var r=getAbsoultePosition(obj);
	$('input_zone').style.left=(r.x-260)+'px';
	$('input_zone').style.top=(r.y+12)+'px';
	$('param_id').innerHTML=obj.title;
	$('param_value').value=obj.name;
	$('input_zone_bg').style.left=(r.x-250)+'px';
	$('input_zone_bg').style.top=(r.y+24)+'px';
	$('hidden_param_value').value=obj.name;
	$('hidden_param_id').value=obj.id;
}
function updateSys(){
	var paramId=$('hidden_param_id').value;
	var paramValue=$('param_value').value.trim();
	if(paramId==6 && !isNumCheck(paramValue)){
		alert('分页显示条数必须是大于0的数字。');
	}else{
		if(paramValue!=$('hidden_param_value').value){
			exeRequest(rootUrl+"/MainCtrl",rightDivContent,
					"page=ConfigManagePage&edit_id="+paramId
							+'&edit_value='+paramValue);
		}
		closeSys();
	}
}
function updateDepartment(){
	var paramId=$('hidden_param_id').value;
	var paramValue=$('param_value').value.trim();
	if(paramValue!=$('hidden_param_value').value){
		if(paramValue==''){
			alert('请输入部门名称。');
			return;
		}
		exeRequest(rootUrl+"/MainCtrl",rightDivContent,
				"page=EditDepartmentPage&edit_id="+paramId
						+'&edit_value='+paramValue);
	}
	closeSys();
}
function closeSys(){
	$('input_zone').style.display='none';
	$('input_zone_bg').style.display='none';
}
function editWords(){
	var param='page=EditWordsPage&content='+encodeURIComponent($('content').value.trim());
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,param);
}
function showWaterMark(obj){
	if(obj.checked){
		$('wm_type').style.display='';
		$('wm_place').style.display='';
	}else{
		$('wm_type').style.display='none';
		$('wm_place').style.display='none';		
	}
}
function editWaterMark(){
	var parm='is_water=0';
	if($('is_water').checked){
		parm='is_water=1';
		var isWaterPicChecked=document.getElementsByName('wm_type');
		var isWaterPic=0;
		for (i=0;i<isWaterPicChecked.length;i++) {
		    if (isWaterPicChecked[i].checked && isWaterPicChecked[i].value=='1'){
		    	isWaterPic=1;
		        break;
		    }
		}		
		if(isWaterPic==0){
			parm+='&is_water_pic=0&water_text='+$('wm_type_title').value.trim();
		}else{
			parm+='&is_water_pic=1';
		}
		var isWaterPlaceChecked=document.getElementsByName('wm_place');
		var isWaterPlace=0;
		for (i=0;i<isWaterPlaceChecked.length;i++){
		    if (isWaterPlaceChecked[i].checked && isWaterPlaceChecked[i].value=='1'){
		    	isWaterPlace=1;
		        break;
		    }
		}
		parm+='&is_water_center='+isWaterPlace;
	}
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=EditWaterMarkPage&'+parm);
}
function isWaterPic(bol){
	if(bol){
		$("water_title").style.display='none';
		$("water_pic").style.display='';
	}else{
		$("water_title").style.display='';
		$("water_pic").style.display='none';
	}
}
function uploadWaterGif(){
	var upfile=$('wm_type_pic').value.trim();
	if (upfile==''){
		alert('请选择水印图片（gif）。');
		$('wm_type_pic').focus();
		return false;
	}
	var ext=upfile.substring(upfile.lastIndexOf('.')+1,upfile.length).toLowerCase();
	if (ext!='gif'){
		alert('水印图片只能是gif文件。');
		$('wm_type_pic').focus();
		return false;
	}
	pauseAll();
	return true;
}
function dbLoad(){
	var file=$('upfile').value.trim();
	if (file=="") {
		alert('请选择文件');
		$('upfile').focus();
		return false;
	}
	pauseAll();
}
function showInfoZone(obj,showid,showidbg,is_user){
	var rightSrcollTop=$('right').scrollTop;
	if(is_user) rightSrcollTop=0;
	var r=getAbsoultePosition(obj);
	$(showid).style.display='block';
	$(showidbg).style.display='block';
	$(showid).style.left=(r.x-$(showid).offsetWidth+obj.offsetWidth)+'px';
	$(showid).style.top=(r.y+obj.offsetHeight-rightSrcollTop)+'px';
	$(showidbg).style.height=($(showid).scrollHeight)+'px';
	$(showidbg).style.width=$(showid).offsetWidth+'px';
	$(showidbg).style.left=(r.x-$(showid).offsetWidth+obj.offsetWidth+10)+'px';
	$(showidbg).style.top=(r.y+obj.offsetHeight+10-rightSrcollTop)+'px';
}
function closeUserInfo(showid,showidbg){
	$(showid).style.display='none';
	$(showidbg).style.display='none';
}
function selectColumnPublish(columnId,columnName){
	closeUserInfo('col_info_zone','col_info_zone_bg');
	$('cb'+columnId).checked=false;
	$('sheet_column').innerHTML=columnName;
	$('col_checkbox_id').value=columnId;
}
function showReplyMessage(messageId,pageNo){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,
					'page=ShowReplyMessagePage&message_id='+messageId+'&search_name='
					+encodeURIComponent($('search_name').value.trim())
					+'&page_no='+pageNo);
}
function cancelReplyMessage(){
	exeRequest(rootUrl+'/MainCtrl',rightDivContent,
			'page=MessageManagePage&search_name='
					+encodeURIComponent($('search_name').value.trim())
					+'&page_no='+$('page_no').value.trim());
}
function submitMsgReply(messageId){
  if($("title").value.trim()==''){
	alert("请输入留言标题。");
	$("title").focus();
  }else  if($("content").value.trim()==''){
		alert("请输入留言内容。");
		$("content").focus();
  }else if($("reply").value.trim()==''){
		alert("请输入回复内容。");
		$("reply").focus();
  }else{
		exeRequest(rootUrl+"/MainCtrl",rightDivContent,
					"page=ReplyMessagePage&title="+encodeURIComponent($("title").value.trim())+
					"&content="+encodeURIComponent($("content").value.trim())+
					"&reply="+encodeURIComponent($("reply").value.trim())+'&message_id='+messageId+'&search_name='
					+encodeURIComponent($('search_name').value.trim())
					+'&page_no='+$('page_no').value.trim());
	}
}
function delMessage(messageId,pageNo){
	if (confirm('是否真的要删除（编号：'+messageId+')')){
		exeRequest(rootUrl+'/MainCtrl',rightDivContent,
		'page=DeleteMessagePage&message_id='+messageId+'&page_no='+pageNo);
	}
}