package javabean;

//用户权限表cms_role
public class UserRole{
	
	private int id; 	//  id主键
	private int userId;	//	用户id
	private int columnId; 	//  栏目id主键
	/***计算字段********/
	private String colName; //  栏目名称

	/************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getColumnId() {
		return columnId;
	}
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}


}
