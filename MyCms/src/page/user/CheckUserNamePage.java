package page.user;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.inc.HtmlPage;
import table.UserInfoTable;
import util.Constant;
import util.PubFun;

public class CheckUserNamePage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String userName = getStringParameter("user_name", "", req);
		if(userName.equals("")){
			PubFun.ajaxPrintStr("请输入用户名。", resp);
			return null;
		}
		
		if(UserInfoTable.isExistsUserByName(userName)){
			PubFun.ajaxPrintStr("此用户名不可用；因为用户名已存在。", resp);			
		}else{
			PubFun.ajaxPrintStr("此用户名可用。", resp);
		}
		return null;
	}

}
