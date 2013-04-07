package page.message;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.MessageTable;
import util.Constant;
import page.inc.HtmlPage;

public class MessageManagePage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String title = getStringParameter("search_name", "", req);//查找留言标题
		int pageNo = getIntParameter("page_no", 1, req);
		int cnt = MessageTable.loadCntByTitle(title);
		pageNo = page(pageNo, cnt, req);
		req.setAttribute("messages", MessageTable.loadMessages(title, pageNo));

		return "/jsp/message/message_list.jsp";
	}

}
