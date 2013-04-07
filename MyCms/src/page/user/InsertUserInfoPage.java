package page.user;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabean.UserInfo;
import table.UserInfoTable;
import table.UserRoleTable;
import util.Constant;
import util.InitServlet;
import util.PubFun;
import util.SecurityMD5;
import page.inc.TransactionPage;

public class InsertUserInfoPage extends TransactionPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		/** ****************添加用户信息**************** **/
		String userName = getStringParameter("user_name", "", req);
		if(userName.equals("")){
			PubFun.ajaxPrintStr("请输入用户名。", resp);
			return null;
		}		
		if(UserInfoTable.isExistsUserByName(userName)){
			PubFun.ajaxPrintStr("此用户名不可用；因为用户名已存在。", resp);
			return null;
		}
		int depId = getIntParameter("dep_id", 0, req);
		if(depId == 0){
			PubFun.ajaxPrintStr("请选择所属部门。", resp);
			return null;
		}	
		UserInfo userInfo = new UserInfo();
		userInfo.setName(userName);
		userInfo.setAlias(getStringParameter("alias", "", req));
		userInfo.setDepId(depId);
		SecurityMD5 s = new SecurityMD5();
		userInfo.setPassword(s.MD5Encode(InitServlet.INIT_PASSWD));
		userInfo.setArticleRole(getBooleanParameter("is_article", false, req));
		userInfo.setAdRole(getBooleanParameter("is_ad", false, req));
		userInfo.setPublishRole(getBooleanParameter("is_publish", false, req));
		userInfo.setColumnRole(getBooleanParameter("is_column", false, req));
		
		Object[] args = new Object[2];
		args[0] = userInfo;
		args[1] = getStringParameter("cols", "", req);
		if(transactionConn(args)){//添加用户信息
		}else{
			PubFun.ajaxPrintStr("添加用户信息失败。", resp);
			return null;
		}

		return "/MainCtrl?page=UserManagePage";
	}

	@Override
	protected void handleTransaction(Connection conn, Object[] args)
			throws Exception {
		UserInfo userInfo = (UserInfo) args[0];
		UserInfoTable.insertUserInfo(conn, userInfo);

		String cols = (String) args[1];;
		if(!cols.equals("")){
			int userId = UserInfoTable.loadUserIDByUserName(conn, userInfo.getName());
			UserRoleTable.batchInsert(conn, cols.split(","), userId);
		}
		
	}

}
