package page.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabean.UserInfo;
import table.UserInfoTable;
import util.Constant;
import util.PubFun;
import page.inc.HtmlPage;

public class DeleteCheckedUsersPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String users = getStringParameter("users", "", req);
		if(users.equals("")||users.equals("{}")){
			PubFun.ajaxPrintStr("请选择要操作的用户。", resp);
			return null;
		}// 批量冻结和恢复用户信息
		UserInfoTable.delUsers(users, getBooleanParameter("is_del", false, req));		
		return "/MainCtrl?page=UserManagePage";
	}

}
