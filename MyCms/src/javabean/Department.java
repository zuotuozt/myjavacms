package javabean;

//部门表 cms_department
public class Department{
	
	private int id; //部门ID
	private String depName; //部门名称
	private boolean del; //是否删除
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	
}
