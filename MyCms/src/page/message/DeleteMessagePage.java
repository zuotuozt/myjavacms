package page.message;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.MessageTable;
import util.Constant;
import page.inc.HtmlPage;

public class DeleteMessagePage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		long messageId = getLongParameter("message_id", 0, req);
		int pageNo = getIntParameter("page_no",1,req);
		MessageTable.delMessage(messageId);
		return "/MainCtrl?page=MessageManagePage&page_no="+pageNo;

	}

}
