import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;
import util.PubFun;

public class PluginCtrl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String jspStr = null;
		resp.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		HtmlPage page = initHtmlPage(req.getParameter("page"));
		if (page == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return;
		}
		try {
			jspStr = page.print(req, resp);
		} catch (Exception e) {
			if (jspStr == null) {
				PubFun.ajaxPrintC(e.getMessage(), resp);
			}else{
				req.setAttribute("inf", e.getMessage());
				jspStr = Constant.VIEW_INFO_PAGE;
			}
			System.out.println(e.getMessage());
		}
		if (jspStr != null) {
			req.getRequestDispatcher(jspStr).forward(req, resp);
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	private HtmlPage initHtmlPage(String page) {
		if ((page == null) || (page.trim().length() == 0))
			return null;
		else
			page = page.trim();

		return PubFun.getPluginPageMap().get(page);
	}

}