package page.article;

import java.io.File;
import java.sql.Connection;

import javabean.Article;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ArticleTable;
import table.FellingTable;
import util.Constant;
import page.inc.TransactionPage;
import util.InitServlet;
import util.PubFun;

public class DeleteArticlePage extends TransactionPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		long articleId = getLongParameter("article_id", 0, req);
		if(articleId>0 && ArticleTable.isExistsById(articleId)){
			Object[] args = new Object[1];
			args[0] = articleId;
			if(transactionConn(args)){//删除文章
				String search = this.getStringParameter("is_search", "", req);
				if(search.equals("common")){
					return "/MainCtrl?page=SearchArticlePage";
				}else if(search.equals("advanced")){
					return "/MainCtrl?page=AdvancedSearchPage";
				}else{
					return "/MainCtrl?page=ArticleManagePage";
				}
			}else{
				PubFun.ajaxPrintStr("删除文章失败。", resp);
				return null;
			}
		}else{
			PubFun.ajaxPrintStr("要删除的文章不存在。", resp);
			return null;
		}

	}
	
	@Override
	protected void handleTransaction(Connection conn, Object[] args)
			throws Exception {
		long articleId = (Long)args[0];
		ArticleTable.delArticle(conn, articleId);
		FellingTable.delFeeling(conn, articleId);
		delArticleFile(articleId);
	}

	private void delArticleFile(long articleId) throws Exception {
		Article article = ArticleTable.loadArticleForDelete(articleId);
		String htmlPath = InitServlet.WEB_SITE_PATH + article.getHtmlPath()
				+ "/" + PubFun.getDateTime("yyyy-MM-dd", article.getCreatime());
		File file = new File(htmlPath + "/" + articleId + ".html");
		if (file.exists()) {
			file.delete();
		}
		file = new File(htmlPath);
		if (file.exists() && file.isDirectory() && (file.list().length == 0)) {
			file.delete();
		}
	}

}
