package table;

import java.util.ArrayList;
import java.util.List;

import table.inc.DBTable;
import javabean.Department;

//部门表 cms_department
public class DepartmentTable extends DBTable {

	public static void updateDepartment(int id, String value) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_department set dep_name=? where id=?";
		args[PARAM_ERROR] = "DepartmentTable===============updateDepartment has error";

		Object[] params = new Object[2];
		params[0] = value;
		params[1] = id;
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	
	public static void insertDepartment(String depName) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "insert into cms_department (dep_name) values(?)";
		args[PARAM_ERROR] = "DepartmentTable===============insertDepartment has error";

		Object[] params = new Object[1];
		params[0] = depName;
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	
	public static void delDepartment(int id) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_department set is_del=1 where id=?";
		args[PARAM_ERROR] = "DepartmentTable===============delDepartment has error";

		Object[] params = new Object[1];
		params[0] = id;
		args[PARAM_ARGS] = params;
		
		update(args);
	}

	public static Department[] loadDepartment() throws Exception {
		ArrayList<Department> departmentList = new ArrayList<Department>();
		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = "select id,dep_name from cms_department where is_del=0";
		args[PARAM_ERROR] = "DepartmentTable============loadDepartment has error";
		
		List<Object> lists = select(args);	
		Department department = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			department = new Department();
			department.setId((Integer)list2[0]);
			department.setDepName((String)list2[1]);
			departmentList.add(department);
		}
		return (Department[]) departmentList.toArray(new Department[departmentList.size()]);
	}

}
