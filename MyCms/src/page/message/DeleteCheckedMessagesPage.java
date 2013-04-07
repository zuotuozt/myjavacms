package page.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabean.UserInfo;
import table.MessageTable;
import util.Constant;
import page.inc.HtmlPage;
import util.PubFun;

public class DeleteCheckedMessagesPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int pageNo = getIntParameter("page_no",1,req);
		String messages = getStringParameter("messages", "", req);
		if(messages.equals("") || messages.equals("{}")){
			PubFun.ajaxPrintStr("请选择要删除的留言。", resp);
			return null;
		}else{
			MessageTable.delMessages(messages);
			return "/MainCtrl?page=MessageManagePage&page_no="+pageNo;
		}

	}

}
