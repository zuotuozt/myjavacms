package page.plugin.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.MessageTable;
import util.InitServlet;
import page.inc.HtmlPage;

public class ShowMessagePage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		int pageNo = getIntParameter("page_no", 1, req);
		int cnt = MessageTable.loadCnt();
		int sumPage = this.getTotalPage(cnt, InitServlet.MESSAGE_PAGE_SIZE);
		req.setAttribute("cnt", cnt);
		req.setAttribute("sumPage", sumPage);
		req.setAttribute("pageSize", InitServlet.MESSAGE_PAGE_SIZE);
		req.setAttribute("pageNo", pageNo);
		req.setAttribute("messages", MessageTable.loadMessageList(pageNo));

		return "/jsp/plugin/message/message.jsp";

	}

}
