package page.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.DepartmentTable;
import util.Constant;
import page.inc.HtmlPage;
import javabean.UserInfo;

public class DepManagePage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}

		req.setAttribute("departments", DepartmentTable.loadDepartment());

		return "/jsp/user/department.jsp";
	}

}