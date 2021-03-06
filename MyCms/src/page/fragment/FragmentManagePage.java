package page.fragment;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.FragmentTable;
import util.Constant;
import page.inc.HtmlPage;

public class FragmentManagePage extends HtmlPage{

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int pageNo = getIntParameter("page_no", 1, req);
		String title = getStringParameter("search_name", "", req);//查找文章标题
		int cnt = FragmentTable.loadCntByTitle(title);
		pageNo = page(pageNo, cnt, req);
		req.setAttribute("fragments", FragmentTable.loadFragments(title, pageNo));
		return "/jsp/fragment/fragment.jsp";
	}
	
}

