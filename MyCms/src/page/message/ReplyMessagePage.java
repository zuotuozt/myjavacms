package page.message;

import javabean.Message;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.inc.HtmlPage;
import table.MessageTable;
import util.Constant;
import util.PubFun;

public class ReplyMessagePage extends HtmlPage {

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
		}
		String title = getStringParameter("title", "", req);
		if(title.equals("")){
			PubFun.ajaxPrintStr("请输入留言标题。", resp);
			return null;
		}
		String content = getStringParameter("content", "", req);
		if(content.equals("")){
			PubFun.ajaxPrintStr("请输入留言内容。", resp);
			return null;
		}
		String reply = getStringParameter("reply", "", req);
		if(reply.equals("")){
			PubFun.ajaxPrintStr("请输入回复内容。", resp);
			return null;
		}
		
		Message message = new Message();
		message.setId(messageId);
		message.setTitle(title);
		message.setMessage(content);
		message.setReply(reply);
		message.setReplyer(user.getId());
		MessageTable.replyMessage(message);
		return "/MainCtrl?page=MessageManagePage";
	
	}
	
}
