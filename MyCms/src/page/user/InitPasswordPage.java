package page.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabean.UserInfo;
import table.UserInfoTable;
import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;
import util.SecurityMD5;

public class InitPasswordPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int userId = getIntParameter("user_id", 0, req);
		if(!UserInfoTable.isExistsUserById(userId)){
			PubFun.ajaxPrintStr("重置密码的用户不存在。", resp);
			return null;
		}
		SecurityMD5 s = new SecurityMD5();
		// MD5 新密码
		String newPwd = s.MD5Encode(InitServlet.INIT_PASSWD);
		UserInfoTable.updateUserPassWord(newPwd, userId);
		String desc = "重置（用户ID：" + userId + "）密码成功；初始化密码为："+InitServlet.INIT_PASSWD; 
		PubFun.ajaxPrintStr(desc, resp);
		return null;
	}

}
