package page.user;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import table.ArticleTable;
import table.UserInfoTable;
import util.Constant;
import util.InitServlet;
import page.inc.HtmlPage;
import util.SecurityMD5;

public class LoginPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		String userName = getStringParameter("user_name", "", req);
		String password = getStringParameter("passwd", "", req);
		if (userName.equals("")) {
			req.setAttribute("inf", "用户名不能为空。");
			return Constant.ORGINAL_LOGIN_PAGE;
		}
		SecurityMD5 s = new SecurityMD5();
		// 用户表用户信息查询方法
		UserInfo userInfo = UserInfoTable.loadUserInfoByName(userName);
		if (userInfo == null) {
			req.setAttribute("inf", "没有此用户名。");
			return Constant.ORGINAL_LOGIN_PAGE;
		} else if (!userInfo.getPassword().equals(s.MD5Encode(password))) {
			req.setAttribute("inf", "密码不对。");
			return Constant.ORGINAL_LOGIN_PAGE;
		}
		
		String currentDirPath = InitServlet.WEB_SITE_PATH + Constant.UPLOAD_PATH + "/" + userName;
		File current = new File(currentDirPath);
		if (!current.exists()) {
			if (current.getParentFile().exists()) {
				current.mkdir();
			}else{
				current.getParentFile().mkdir();
				current.mkdir();
			}
		}
		if (userName.equals(Constant.SUPER_USER)) {//系统管理员(admin)
			userInfo.setUploadFileRoot("");
		}else if (userName.equals(Constant.CHIEF_EDITOR)) {//总编辑（chief_editor）
			userInfo.setUploadFileRoot(Constant.UPLOAD_PATH);
		}else {//普通用户
			userInfo.setUploadFileRoot(Constant.UPLOAD_PATH + "/" + userName);
		}
		// 用户表登录时间更新方法
		UserInfoTable.updateUserLoginTime(userInfo.getId());

		userInfo.setArticleCnt(ArticleTable.loadAllCntByUserId(userInfo.getId()));
		HttpSession session = req.getSession(true);
		session.setAttribute("userInfo", userInfo);

		resp.sendRedirect("home.jsp");
		return null;
	}

}
