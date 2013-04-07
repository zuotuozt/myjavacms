package javabean;

import java.util.Date;

public class UserInfo{
	
	private int id; 	//  用户id主键
	private String name;	//	用户名
	private String alias;	//	用户别名
	private int depId; 	//  外键部门id主键
	private String password;	//	用户密码	
	private Date lastLogin;		//	最后登录时间	
	private Date currentLogin;		//	当前登录时间	
	private Date createTime;		//	创建时间	
	private boolean del;		//是否已被删除
	private boolean articleRole; //是否有文章编辑权限
	private boolean adRole;      //是否有广告管理权限
	private boolean publishRole; //是否有文章发布权限
	private boolean columnRole; //是否有栏目管理权限
	/***********计算字段*************/
	private String departmentName;//部门名称
	private String uploadFileRoot;//文件管理根目录
	private long articleCnt; //此用户发布文章总数

	/************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCurrentLogin() {
		return currentLogin;
	}
	public void setCurrentLogin(Date currentLogin) {
		this.currentLogin = currentLogin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUploadFileRoot() {
		return uploadFileRoot;
	}
	public void setUploadFileRoot(String uploadFileRoot) {
		this.uploadFileRoot = uploadFileRoot;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public boolean isArticleRole() {
		return articleRole;
	}
	public void setArticleRole(boolean articleRole) {
		this.articleRole = articleRole;
	}
	public boolean isAdRole() {
		return adRole;
	}
	public void setAdRole(boolean adRole) {
		this.adRole = adRole;
	}
	public boolean isPublishRole() {
		return publishRole;
	}
	public void setPublishRole(boolean publishRole) {
		this.publishRole = publishRole;
	}
	public boolean isColumnRole() {
		return columnRole;
	}
	public void setColumnRole(boolean columnRole) {
		this.columnRole = columnRole;
	}
	public long getArticleCnt() {
		return articleCnt;
	}
	public void setArticleCnt(long articleCnt) {
		this.articleCnt = articleCnt;
	}

}
