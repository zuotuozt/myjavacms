function submitDL(pageNo,sumPage,pageNoJsp){
  if(pageNoJsp==pageNo){
  }else if((pageNoJsp==1) && (pageNo==0)){
  }else if((pageNoJsp==sumPage) && (pageNo==sumPage+1)){
  }else if(pageNo<1){
  }else if(pageNo>sumPage){
  }else{
  	$('page_no').value=pageNo;
  	document.page_form.submit();
  }
}
function submitGo(sumPage,pageNoJsp){
  var pageNo=$("to_page_no").value.trim();
  if(pageNo.match(/\D/)==null){
  } else {
	alert("请输入数字。");
	$("to_page_no").select();
	return;
  }	  
  if (pageNoJsp==pageNo){
  }else if(pageNo<1){
  	alert("选择的页数必须大于0。");
  	$("to_page_no").select();
  }else if(pageNo>sumPage){
  	alert("选择的页数不能大于总页数。");
  	$("to_page_no").select();
  }else{
	 $('page_no').value=pageNo;
	 document.page_form.submit();
  }
}