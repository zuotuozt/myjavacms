package page.fragment;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.FragmentTable;
import util.Constant;
import page.inc.HtmlPage;

public class ShowJsFragmentPage extends HtmlPage{

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int fragmentId = getIntParameter("fragment_id", 0, req);
		req.setAttribute("fragment", FragmentTable.loadSimpleColById(fragmentId));
		return "/jsp/fragment/show_js_fragment.jsp";
	}

}
