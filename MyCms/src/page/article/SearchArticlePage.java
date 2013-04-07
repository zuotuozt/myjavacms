package page.article;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import table.ArticleTable;
import util.Constant;

import page.col.inc.StaticBasePage;

//依据文章标题搜索文章
public class SearchArticlePage extends StaticBasePage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String title = getStringParameter("search_title", "", req);//查找文章标题
		int pageNo = getIntParameter("page_no", 1, req);
		int cnt = ArticleTable.searchCntByTitle(title);
		pageNo = page(pageNo, cnt, req);
		req.setAttribute("articles", ArticleTable.searchArticles(title, pageNo));
		
		return "/jsp/article/search_article.jsp";
	}

}
