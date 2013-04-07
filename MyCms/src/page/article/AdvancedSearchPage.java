package page.article;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import table.ArticleTable;
import util.Constant;

import page.col.inc.StaticBasePage;

//高级搜索
public class AdvancedSearchPage extends StaticBasePage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String title = getStringParameter("search_name", "", req);//文章标题
		String beginDate = getStringParameter("begin_date", "2012-12-22 00::00", req);
		String endDate = getStringParameter("end_date", "2012-12-22 23:59::59", req);
		String dateStr = "and creatime between '" + beginDate + "' and '" + endDate + " 23:59:59'";
		String author = getStringParameter("author", "", req);//文章作者
		String editor = getStringParameter("editor", "", req);//文章编辑
		int pageNo = getIntParameter("page_no", 1, req);
		int cnt = ArticleTable.advancedSearchCnt(title, dateStr, author, editor);
		pageNo = page(pageNo, cnt, req);
		req.setAttribute("articles", ArticleTable.advancedSearch(title, dateStr, author, editor, pageNo));
		
		return "/jsp/article/advanced_search.jsp";
	}

}
