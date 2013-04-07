package page.user;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.UserInfoTable;
import util.Constant;
import page.inc.HtmlPage;

public class UserManagePage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		/** ****************加载用户信息（显示第一页）**************** **/
		int pageNo = getIntParameter("page_no", 1, req);
		String userName = getStringParameter("search_name", "", req);//查找用户名
		int cnt = UserInfoTable.loadRecordCountByUserName(userName);
		pageNo = page(pageNo, cnt, req);
		UserInfo[] users = UserInfoTable.loadUserInfoByUserName(userName, pageNo);
		/** ****************************************************** */
		req.setAttribute("users", users);
		/** ****************************************************** */

		return "/jsp/user/user.jsp";
	}

}