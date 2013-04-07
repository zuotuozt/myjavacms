package table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javabean.UserRole;
import table.inc.DBTable;

//用户权限表cms_role
public class UserRoleTable extends DBTable {
	//删除用户角色
	public static void delUserRole(Connection conn, int userId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "delete from cms_role where user_id=?";
		args[PARAM_ERROR] = "UserRoleTable===============delUserRole has error";
		
		Object[] params = new Object[1];
		params[0] = userId;
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}
	//批量插入用户角色
	public static void batchInsert(Connection conn, String[] cols, int userId) throws Exception {
		Statement pstm = null;
		try {
			pstm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(int i=0;i<cols.length;i++){
				pstm.execute("insert into cms_role(user_id,column_id) values("
							+ userId + "," + cols[i] + ")");
			}
		} catch (SQLException e) {
			throw new Exception("UserRoleTable===============batchInsert has error");
		} finally {
			if (pstm != null) {
				try{
					pstm.close();
				} catch (Exception e) {
				}
			}
		}
	}
	//查找用户的栏目权限
	public static UserRole[] loadUserRoleList(int userId) throws Exception {
		ArrayList<UserRole> userRoleList = new ArrayList<UserRole>();
		String sql = "select column_id,col_name from cms_role a,cms_column b where a.column_id=b.id and a.user_id=?";
		Object[] args = new Object[3];
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "UserRoleTable===============loadUserRoleList has error";
		
		Object[] params = new Object[1];
		params[0] = userId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			UserRole userRole = new UserRole();
			userRole.setId((userId));
			userRole.setColumnId((Integer)list2[0]);
			userRole.setColName((String)list2[1]);
			userRoleList.add(userRole);
		}	

		return (UserRole[]) userRoleList.toArray(new UserRole[userRoleList.size()]);
	}
	
}
