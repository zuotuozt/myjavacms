package page.plugin.message;

import javabean.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import table.MessageTable;
import util.PubFun;
import page.inc.HtmlPage;

public class PublishMessagePage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		String title = getStringParameter("title", "", req);
		if(title.equals("")){
			PubFun.ajaxPrintC("请输入留言标题。", resp);
			return null;
		}
		String content = getStringParameter("content", "", req);
		if(content.equals("")){
			PubFun.ajaxPrintC("请输入留言内容。", resp);
			return null;
		}
		HttpSession session = req.getSession(true);
		String certify = getStringParameter("certify", "", req);
		if(!session.getAttribute("Rand").equals(certify)){
			PubFun.ajaxPrintC("验证码错误；请重新输入。", resp);
			return null;
		}
		Message message = new Message();
		message.setTitle(title);
		message.setMessage(content);
		message.setIp(getRemortIP(req));
		try{
			MessageTable.insertMessage(message);
		} catch (Exception e) {
			PubFun.ajaxPrintC("发布留言不成功；可能是数据库方面的问题："+e.getMessage(), resp);
			return null;
		}
		
		PubFun.ajaxPrintC("发布留言成功；但需要网站管理员回复后才能显示。", resp);
		session.setAttribute("Rand", "");

		return null;
	}

}
