package page.article;

import java.io.File;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabean.Article;
import javabean.UserInfo;
import table.ArticleTable;
import table.FellingTable;
import util.Constant;
import page.inc.TransactionPage;
import util.InitServlet;
import util.PubFun;

public class DeleteCheckedArticlesPage extends TransactionPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String articles = getStringParameter("articles", "", req);
		if(articles.equals("")||articles.equals("{}")){
			PubFun.ajaxPrintStr("请选择要删除的文章。", resp);
			return null;
		}else{
			Object[] args = new Object[1];
			args[0] = articles;
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
		}

	}
	
	@Override
	protected void handleTransaction(Connection conn, Object[] args)
			throws Exception {
		String articles = (String)args[0];
		ArticleTable.delArticles(conn, articles);
		FellingTable.delFeelings(conn, articles);
		delArticleFiles(articles);
	}
	
	private void delArticleFiles(String articles) throws Exception {
		Article[] articleList = ArticleTable.loadSimpleArticleByIds(articles);
		for(Article article : articleList){
			String htmlPath = InitServlet.WEB_SITE_PATH + article.getHtmlPath() + "/" + PubFun.getDateTime("yyyy-MM-dd", article.getCreatime());
			File file = new File(htmlPath + "/" + article.getId() + ".html");
			if(file.exists()){
				file.delete();
			}
			file = new File(htmlPath);
			if(file.exists()&&file.isDirectory()&&(file.list().length == 0)){
				file.delete();
			}
		}
	}

}
