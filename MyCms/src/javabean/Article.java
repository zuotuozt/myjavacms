package javabean;

import java.util.Date;

import util.PubFun;
//文章表article
public class Article{
	
	private long id; 	    // 文章id主键
	private int columnid;   //文章所属栏目id
	private String title;//	文章标题
	private String author;//文章作者
	private Date creatime;//	创建时间	
	private String content; //	文章内容
	private String source;    //	文章来源
	private String note; //文章摘要
	private String picture; //文章缩略图
	private boolean top; //文章置顶
	private int creator; // 创建者id
	private int ordercnt; // 排序字段
	
	/******* 计算字段 ********/	
	private String htmlPath; //文章存放路径
	private String articleTemplate ;//文章使用的文章模板
	private String url; 	// 文章的静态化url地址
	private String columnname ;//文章所属栏目名称
	private String creatorName; // 创建者名字
	private String seoTitle; //页面seo搜索Title
	private String seoKeywords; //页面seo搜索keywords
	private String seoDescription; //页面seo搜索description
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getColumnid() {
		return columnid;
	}
	public void setColumnid(int columnid) {
		this.columnid = columnid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getCreatime() {
		return creatime;
	}
	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getContent() {
		return PubFun.getArContentValue(content);
	}
	public void setContent(String content) {
		this.content = PubFun.getDbContentFliterValue(content);
	}
	public String getHtmlPath() {
		return htmlPath;
	}
	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
	public String getArticleTemplate() {
		return articleTemplate;
	}
	public void setArticleTemplate(String articleTemplate) {
		this.articleTemplate = articleTemplate;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public boolean isTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public int getOrdercnt() {
		return ordercnt;
	}
	public void setOrdercnt(int ordercnt) {
		this.ordercnt = ordercnt;
	}
	
/*************************************/	
	
}
