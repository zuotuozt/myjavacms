package page.file;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;

public class ToFileManagePage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String filepath = getStringParameter("filepath",
				user.getUploadFileRoot(), req);
		String filename = getStringParameter("filename", "", req);
		int type = getIntParameter("type",Constant.FILE_PATH_TYPE,req);
		req.setAttribute("filename", filename);
		req.setAttribute("filepath", filepath);
		req.setAttribute("type", type);
		return "/jsp/file/editFileName.jsp";
	}
}
