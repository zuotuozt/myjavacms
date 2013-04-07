package javabean;

import java.util.Date;
//广告表 fragment
public class Fragment{

	private int id; 	// 广告id主键
	private String title;	//	广告名称
	private String content;	//	广告html代码内容
	private int columnId;	//	广告投放栏目范围 -1：所有栏目
	private Date createTime; //	广告创建时间
	
	/********计算字段**********/
	private String columnName;	//	广告投放栏目范围
/***********************************************/
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public int getColumnId() {
		return columnId;
	}
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
