import java.io.IOException;
import javabean.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import page.inc.HtmlPage;

import util.MultipartRequest;
import util.Constant;
import util.PubFun;

public class UploadCtrl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// doPost方法
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			resp.setContentType("text/html; charset=utf-8");
			req.setCharacterEncoding("utf-8");
			String jspStr = null;
			HttpSession session = req.getSession();
			UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
			long userId = 0;
			if (userInfo == null) {
				PubFun.ajaxPrintUpStr("alert('页面失效；请重新登陆。');parent.location='"
						+req.getContextPath()+'/'+Constant.REDIRECT_LOGIN_PAGE+"';", resp);
				return;
			} else {
				userId = userInfo.getId();
			}
			if (req.getMethod().equals("POST")
					&& MultipartRequest.isMultipart(req)) {
				req = new MultipartRequest(req, userId);
				HtmlPage page = initHtmlPage(req.getParameter("page"));
				if (page == null) {
					PubFun.ajaxPrintUpStr("alert('页面失效；请重新登陆。');parent.location='"
							+req.getContextPath()+'/'+Constant.REDIRECT_LOGIN_PAGE+"';", resp);
					return;
				}
				try {
					jspStr = page.print(req, resp);
				} catch (Exception e) {
					PubFun.ajaxPrint("alert('" + e.getMessage() + "');parent.contiueAll();", resp);
					System.out.println(e.getMessage());
					jspStr = null;
				}
				if (jspStr != null) {
					req.getRequestDispatcher(jspStr).forward(req, resp);
				}

			} else {
				PubFun.ajaxPrintUpStr("alert('不是HTTP的上传文件');", resp);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (req instanceof MultipartRequest) {
				((MultipartRequest) req).deleteTemporaryFile();
			}
		}
	}

	private static HtmlPage initHtmlPage(String page) {
		if ((page == null) || (page.trim().length() == 0))
			return null;
		else
			page = page.trim();

		return PubFun.getPageMap().get(page);
	}

}
