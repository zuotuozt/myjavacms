package page.config;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class ShowEditWordsPage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String contents = PubFun.getFileContent(InitServlet.CONTENT_REALPATH, "words.properties");
		contents = PubFun.getJsFilterValue(contents);
		req.setAttribute("content", contents);
		return "/jsp/edit_words.jsp";
	}
}
