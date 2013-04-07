package page.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import page.inc.HtmlPage;

public class LogoutPage extends HtmlPage {
  
	public String print(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		HttpSession session=req.getSession(true);
		session.removeAttribute("userInfo");
		resp.sendRedirect("sign_in.jsp");
		return null;
  }

}
