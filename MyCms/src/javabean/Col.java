package javabean;

public class Col{
	
	private int id ;  //栏目号
	private int parentid ; //上级栏目号
	private String name; //栏目名称
	private String htmlPath; //栏目保存路径
	private int colType; // 栏目类型
	private String indexTemplate; // 首页模板
	private String listTemplate; // 列表模板
	private String articleTemplate; //文章模板
	private String link; //链接地址
	private String colIntro; //栏目简介，栏目描述
	private String seoTitle; //页面seo搜索Title
	private String seoKeywords; //页面seo搜索keywords
	private String seoDescription; //页面seo搜索description

	
	/************计算字段*************/
	private String lastHtmlpath ;//上级栏目路径
	private String parentName ;//上级栏目名称
	private int level;
	private boolean hasChildren; ///是否有下级栏目

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public int getColType() {
		return colType;
	}

	public void setColType(int colType) {
		this.colType = colType;
	}

	public String getIndexTemplate() {
		return indexTemplate;
	}

	public void setIndexTemplate(String indexTemplate) {
		this.indexTemplate = indexTemplate;
	}

	public String getListTemplate() {
		return listTemplate;
	}

	public void setListTemplate(String listTemplate) {
		this.listTemplate = listTemplate;
	}

	public String getArticleTemplate() {
		return articleTemplate;
	}

	public void setArticleTemplate(String articleTemplate) {
		this.articleTemplate = articleTemplate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getColIntro() {
		return colIntro;
	}

	public void setColIntro(String colIntro) {
		this.colIntro = colIntro;
	}

	public String getLastHtmlpath() {
		return lastHtmlpath;
	}

	public void setLastHtmlpath(String lastHtmlpath) {
		this.lastHtmlpath = lastHtmlpath;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
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
	
}
