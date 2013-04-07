package page.publish;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import table.ColumnTable;
import util.Constant;
import page.inc.HtmlPage;

public class PublishIndexPage extends HtmlPage {

	@Override //主要任务是显示的准备工作 
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		req.setAttribute("cols", ColumnTable.loadColumnsForTree(Constant.TOP_CLOUMN_TREE));
		/*********************************************************/

		return "/jsp/publish/publish_index.jsp";
	}

}
