package page.article;

import java.util.ArrayList;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ArticleTable;
import table.ColumnTable;
import util.Constant;
import page.col.inc.StaticBasePage;

public class ArticleManagePage extends StaticBasePage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int colId = getIntParameter("column_id", 0, req);
		String title = getStringParameter("search_name", "", req);//查找文章标题
		int pageNo = getIntParameter("page_no", 1, req);
		int cnt = ArticleTable.loadCntByTitle(colId, title, user.getId());
		pageNo = page(pageNo, cnt, req);
		Col col = ColumnTable.loadColById(colId);
		if(col != null){
			ArrayList<Col> cols = new ArrayList<Col>();
			cols.add(col);
			this.getParents(col, cols);
			req.setAttribute("cols", cols.toArray(new Col[cols.size()]));
			req.setAttribute("articles", ArticleTable.loadArticles(colId, title, pageNo, user.getId()));
		}		

		return "/jsp/article/article.jsp";
	}

}
