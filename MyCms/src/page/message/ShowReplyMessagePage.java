package page.message;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.inc.HtmlPage;
import table.MessageTable;
import util.Constant;
import util.PubFun;

public class ShowReplyMessagePage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		long messageId = getLongParameter("message_id", 0, req);
		
		if(messageId==0  || !MessageTable.isExistsById(messageId)){
				PubFun.ajaxPrintStr("留言不存在。", resp);
				return null;
		}else{
			req.setAttribute("message", MessageTable.loadMessage(messageId));
			return "/jsp/message/reply.jsp";
		}
		
	}
	
}
