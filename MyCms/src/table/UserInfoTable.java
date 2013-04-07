package table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javabean.UserInfo;
import table.inc.DBTable;
import util.Constant;
import util.InitServlet;

public class UserInfoTable extends DBTable {

	public static UserInfo loadUserInfoByName(String userName) throws Exception {
		UserInfo userInfo = null;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select * from cms_user where name=?";
		args[PARAM_ERROR] = "UserInfoTable============loadUserInfoByName has error";

		Object[] params = new Object[1];
		params[0] = userName;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);		
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			userInfo = new UserInfo();
			userInfo.setId((Integer)list2[0]);
			userInfo.setName((String)list2[1]);
			userInfo.setAlias((String)list2[2]);
			userInfo.setDepId((Integer)list2[3]);
			userInfo.setPassword((String)list2[4]);
			userInfo.setLastLogin((Date)list2[5]);
			userInfo.setCurrentLogin((Date)list2[6]);
			userInfo.setCreateTime((Date)list2[7]);
			userInfo.setDel((Boolean)list2[8]);
			userInfo.setArticleRole((Boolean)list2[9]);
			userInfo.setAdRole((Boolean)list2[10]);
			userInfo.setPublishRole((Boolean)list2[11]);
			userInfo.setColumnRole((Boolean)list2[12]);
		}
		return userInfo;
	}

	/**
	 * 功能描述：根据用户ID查询用户信息
	 */
	public static UserInfo loadUserInfoByID(int userID) throws Exception {
		UserInfo userInfo = null;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select a.id,a.name,a.alias,a.dep_id,a.last_login,a.create_time,"
			+ "is_article_role,is_ad_role,is_publish_role,is_column_role,b.dep_name"
			+ " from cms_user a,cms_department b where a.dep_id=b.id and a.id=?";
		args[PARAM_ERROR] = "UserInfoTable============loadUserInfoByID has error";

		Object[] params = new Object[1];
		params[0] = userID;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			userInfo = new UserInfo();
			userInfo.setId((Integer)list2[0]);
			userInfo.setName((String)list2[1]);
			userInfo.setAlias((String)list2[2]);
			userInfo.setDepId((Integer)list2[3]);
			userInfo.setLastLogin((Date)list2[4]);
			userInfo.setCreateTime((Date)list2[5]);
			userInfo.setArticleRole((Boolean)list2[6]);
			userInfo.setAdRole((Boolean)list2[7]);
			userInfo.setPublishRole((Boolean)list2[8]);
			userInfo.setColumnRole((Boolean)list2[9]);
			userInfo.setDepartmentName((String)list2[10]);
		}
		return userInfo;
	}

	/**
	 * 功能描述：加载全部用户信息
	 */
	public static UserInfo[] loadUserInfoByUserName(String userName, int pageNo) throws Exception {
		ArrayList<UserInfo> userList = new ArrayList<UserInfo>();
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select a.id,a.name,a.alias,a.dep_id,a.last_login,a.create_time,a.is_del,b.dep_name"
			+ " from cms_user a,cms_department b where a.dep_id=b.id and not a.name in('" +
				Constant.SUPER_USER + "','" + Constant.CHIEF_EDITOR 
			+ "') and a.name like ? order by a.is_del,a.id desc limit ?,?";
		args[PARAM_ERROR] = "UserInfoTable============loadUserInfoByUserName has error";

		Object[] params = new Object[3];
		params[0] = "%" + userName + "%";
		params[1] = (pageNo - 1) * InitServlet.PAGE_SIZE;
		params[2] = InitServlet.PAGE_SIZE;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		UserInfo userInfo = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			userInfo = new UserInfo();
			userInfo.setId((Integer)list2[0]);
			userInfo.setName((String)list2[1]);
			userInfo.setAlias((String)list2[2]);
			userInfo.setDepId((Integer)list2[3]);
			userInfo.setLastLogin((Date)list2[4]);
			userInfo.setCreateTime((Date)list2[5]);
			userInfo.setDel((Boolean)list2[6]);
			userInfo.setDepartmentName((String)list2[7]);
			userList.add(userInfo);
		}
		return (UserInfo[]) userList.toArray(new UserInfo[userList.size()]);
	}

	/**
	 * 功能描述：更改用户密码
	 */
	public static void updateUserPassWord(String passWord, int userID)
			throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_user set password=? where id=?";
		args[PARAM_ERROR] = "UserInfoTable===============updateUserPassWord has error";

		Object[] params = new Object[2];
		params[0] = passWord;
		params[1] = userID;
		args[PARAM_ARGS] = params;
		
		update(args);
	}

	/**
	 * 功能描述：更改用户最后登陆时间
	 */
	public static void updateUserLoginTime(int userID) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_user set last_login=current_login,current_login=now() where id=?";
		args[PARAM_ERROR] = "UserInfoTable============updateUserLonginTime has error";

		Object[] params = new Object[1];
		params[0] = userID;
		args[PARAM_ARGS] = params;
		
		update(args);
	}	

	public static void insertUserInfo(Connection conn, UserInfo userInfo) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "insert into cms_user (name,alias,dep_id,password,last_login,create_time,current_login,is_article_role,"
			+"is_ad_role,is_publish_role,is_column_role) values(?,?,?,?,now(),now(),now(),?,?,?,?)";
		args[PARAM_ERROR] = "UserInfoTable===============insertUserInfo has error";

		Object[] params = new Object[8];
		params[0] = userInfo.getName();
		params[1] = userInfo.getAlias();
		params[2] = userInfo.getDepId();
		params[3] = userInfo.getPassword();
		params[4] = userInfo.isArticleRole();
		params[5] = userInfo.isAdRole();
		params[6] = userInfo.isPublishRole();
		params[7] = userInfo.isColumnRole();
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}

	//功能描述：根据usermane得到userid
	public static int loadUserIDByUserName(Connection conn, String userName) throws Exception {
		int userID = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_user where name=?";
		args[PARAM_ERROR] = "UserInfoTable============loadUserIDByUserName has error";

		Object[] params = new Object[1];
		params[0] = userName;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = selectTransction(conn, args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			userID = (Integer)list2[0];
		}
		return userID;
	}
	
	public static boolean isExistsUserById(int userID) throws Exception {
		boolean isExists = false;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_user where id=?";
		args[PARAM_ERROR] = "UserInfoTable============isExistsUserById has error";

		Object[] params = new Object[1];
		params[0] = userID;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		if(lists.size() > 0){
			isExists = true;
		}
		return isExists;
	}
	
	public static boolean isExistsUserByDepId(int depID) throws Exception {
		boolean isExists = false;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_user where dep_id=?";
		args[PARAM_ERROR] = "UserInfoTable============isExistsUserById has error";

		Object[] params = new Object[1];
		params[0] = depID;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		if(lists.size() > 0){
			isExists = true;
		}
		return isExists;
	}
	
	public static boolean isExistsUserByName(String userName) throws Exception {
		boolean isExists = false;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_user where name=?";
		args[PARAM_ERROR] = "UserInfoTable============isExistsUserByName has error";

		Object[] params = new Object[1];
		params[0] = userName;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		if(lists.size() > 0){
			isExists = true;
		}
		return isExists;
	}
	//功能描述：冻结和恢复用户
	public static void delUserInfo(int userID, boolean isDel) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_user set is_del=? where id=?";
		args[PARAM_ERROR] = "UserInfoTable===============delUserInfo has error";

		Object[] params = new Object[2];
		params[0] = isDel;
		params[1] = userID;
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	//功能描述：批量冻结和恢复用户
	public static void delUsers(String users, boolean isDel) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_user set is_del=? where id in(" + users + ")";
		args[PARAM_ERROR] = "UserInfoTable===============delUsers has error";
		
		Object[] params = new Object[1];
		params[0] = isDel;
		args[PARAM_ARGS] = params;
		
		update(args);
	}

	/**
	 * 功能描述：修改用户信息
	 */
	public static void updateUserInfo(Connection conn, UserInfo userInfo) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_user set alias=?,dep_id=?,is_article_role=?," +
				"is_ad_role=?,is_publish_role=?,is_column_role=? where id=?";
		args[PARAM_ERROR] = "UserInfoTable============updateUserInfo has error";

		Object[] params = new Object[7];
		params[0] = userInfo.getAlias();
		params[1] = userInfo.getDepId();
		params[2] = userInfo.isArticleRole();
		params[3] = userInfo.isAdRole();
		params[4] = userInfo.isPublishRole();
		params[5] = userInfo.isColumnRole();
		params[6] = userInfo.getId();
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}

	/**
	 * 功能描述：取得记录数
	 */
	public static int loadRecordCountByUserName(String userName) throws Exception {
		int num = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select count(id) from cms_user where not name in('" +
				Constant.SUPER_USER + "','" + Constant.CHIEF_EDITOR + "') and name like ?";
		args[PARAM_ERROR] = "UserInfoTable============loadRecordCountByUserName has error";
		
		Object[] params = new Object[1];
		params[0] = "%" + userName + "%";
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			num = ((Long)list2[0]).intValue();
		}
		return num;
	}

}
