package page.user;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.DepartmentTable;
import table.UserInfoTable;
import page.inc.HtmlPage;
import util.Constant;
import util.PubFun;

public class DelDepartmentPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int depId = getIntParameter("dep_id", 0, req);
		if(depId != 0){
			if(UserInfoTable.isExistsUserByDepId(depId)){
				PubFun.ajaxPrintStr("此部门已经被用户使用；不能被删除。", resp);
				return null;
			}else{
				DepartmentTable.delDepartment(depId);
			}
		}

		return "/MainCtrl?page=DepManagePage";
	}

}
