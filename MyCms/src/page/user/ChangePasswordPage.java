package page.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabean.UserInfo;
import table.UserInfoTable;
import util.Constant;
import page.inc.HtmlPage;
import util.PubFun;
import util.SecurityMD5;

public class ChangePasswordPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}		
		// 返回值
		String returnFlag = "/jsp/user/change_passwd.jsp";
		// 修改密码标志
		String changeFlag = getStringParameter("change_flag", "", req);
		if (changeFlag.equals("true")) {
			returnFlag = changePasssword(user, req, resp);
		}
		return returnFlag;
	}

	private String changePasssword(UserInfo user, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// 得到用户旧密码
		String password = getStringParameter("password", "", req);
		// 用户新密码
		String newPassword = getStringParameter("new_passwd", "", req);
		SecurityMD5 s = new SecurityMD5();
		// MD5 新密码
		String newPwd = s.MD5Encode(newPassword);
		if (s.MD5Encode(password).equals(user.getPassword())) {
			UserInfoTable.updateUserPassWord(newPwd, user.getId());
			PubFun.ajaxPrintStr("修改密码成功。", resp);
		}else{
			PubFun.ajaxPrintStr("原始密码不对。", resp);
		}
		return null;
	}

}
