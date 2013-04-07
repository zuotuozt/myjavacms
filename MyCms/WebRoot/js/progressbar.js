var ProgressBar= function(){
	 this.init.apply(this,arguments);
}
ProgressBar.prototype={
	init:function(colId,dateStr,pageSize,isAll){
		this.holder=document.createElement('div');
		this.bar=document.createElement('div'); 
		this.holder.appendChild(this.bar); 
		this.holder.style.border='solid #FF00ff 1px';
		this.holder.style.background='#ffffff';
		this.holder.style.textAlign='left';
		this.holder.style.margin='0 auto';
		this.bar.style.backgroundColor='#0033ff'; 
		this.bar.style.width='0px';
		this.bar.style.height='20px';
		this.holder.style.width='300px';
		this.bar.style.height='20px';
		this.colId=colId;
		this.dateStr=dateStr;
		this.pageSize=pageSize;
		this.isAll=isAll;
	},
	display: function(){
		var w = (window.innerWidth) ? window.innerWidth : (document.documentElement && document.documentElement.clientWidth) ?
			 document.documentElement.clientWidth : document.body.offsetWidth;
		var h = (window.innerHeight) ? window.innerHeight : (document.documentElement && document.documentElement.clientHeight) ?
			 document.documentElement.clientHeight : document.body.offsetHeight;
		this.holder.style.top = h/2 + 'px';
		this.holder.style.left = w/2 + 'px';		
		covering.innerHTML = '<div class="spinner"><div style="background-color:#edf9d5;height:23px;font-size:14px;">信息提示：&nbsp;&nbsp;请耐心等待&nbsp;。。。</div><div align="left" style="font-weight:bold;">&nbsp;&nbsp;文章总数：<font color="red" id="total_cnt"></font>，&nbsp;&nbsp;已更新：<font color="red" id="update_cnt"></font>，&nbsp;&nbsp;进度：<font color="red" id="percent"></font>%</div></div>';
		covering.appendChild(this.holder);
		exeRequest(rootUrl+'/MainCtrl',ajaxProgressBar,
				"page=StaticArticlesPage&col_id="+this.colId+"&date_str="
				+this.dateStr+"&page_size="+this.pageSize, this);
	},
	increase: function(){
		var holderWidth=parseInt(this.holder.style.width);
		var barWidth=parseInt(this.bar.style.width)+(this.pageSize/parseInt($('total_cnt').innerHTML))*holderWidth;
		barWidth=Math.min(barWidth, holderWidth);

		this.bar.style.width=barWidth+'px';
		if(barWidth==holderWidth){
		   this.onEnd();
		}
	},
	onEnd: function(){
		$('update_cnt').innerHTML=$('total_cnt').innerHTML;
		$('percent').innerHTML='100';
		alert("更新文章页面已完成。");
		covering.removeChild(this.holder);
		contiueAll();
		if(this.isAll){
			alert("更新文章页面已完成；接下来将继续进行更新栏目下的所有静态化栏目页面；这可能会消耗您大量时间；请耐心等待。。。");
				exeRequest(rootUrl+'/MainCtrl',rightDivContent,
						"page=StaticColumnsPage&col_id="+this.colId);
		}
	}
}
var ajaxProgressBar=function(txt,progressBar){
	var jsonArray=Json.evaluate(txt);
	if(jsonArray['total']==-1){
		alert(jsonArray['info']);
		if(jsonArray['info'].indexOf('页面失效')==0) location=rootUrl+"/sign_in.jsp";
		contiueAll();
	}else{
		if(jsonArray['info']<jsonArray['total']){
			exeRequest(rootUrl+"/MainCtrl",ajaxProgressBar,'page=StaticArticlesPage'
				+"&col_id="+progressBar.colId+"&date_str="+progressBar.dateStr
				+"&page_size="+progressBar.pageSize
				+"&page_begin="+jsonArray['info'],progressBar);
		}
		$('total_cnt').innerHTML=jsonArray['total'];
		$('update_cnt').innerHTML=jsonArray['info'];
		$('percent').innerHTML=Math.floor(jsonArray['info']*100/jsonArray['total']);
		progressBar.increase();
	}
}