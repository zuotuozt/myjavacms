package page.file;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;

public class ToCreateFilePage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String flg = getStringParameter("flg", "0", req);
		String path = getStringParameter("filepath", user.getUploadFileRoot(), req);
		int type = getIntParameter("type",Constant.FILE_PATH_TYPE,req);
		req.setAttribute("path", path);
		req.setAttribute("type", type);
		if ("1".equals(flg)) {
			return "/jsp/file/uploadFile.jsp";
		}else {
			return "/jsp/file/createPath.jsp";
		}
	}
}
