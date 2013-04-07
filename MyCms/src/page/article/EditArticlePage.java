package page.article;

import java.sql.Connection;

import javabean.Article;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ArticleTable;
import table.ColumnTable;
import util.Constant;
import page.inc.TransactionPage;
import util.PubFun;

public class EditArticlePage extends TransactionPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		int columnId = getIntParameter("column_id", 0, req);
		UserInfo user = getSessionUser(req);
		if((user == null)||(!ColumnTable.isExistsById(columnId))) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		long articleId = getIntParameter("article_id", 0, req);
		if(articleId!=0&&!ArticleTable.isExistsById(articleId)){
			PubFun.ajaxPrintStr("此文章不存在", resp);
			return null;
		}
		String articleTitle = getStringParameter("article_title", "", req);
		if(articleTitle.equals("")){
			PubFun.ajaxPrintStr("请您输入文章名。", resp);
			return null;
		}
		String content = getStringParameter("content", "", req);
		if(content.equals("")){
			PubFun.ajaxPrintStr("请您输入文章内容。", resp);
			return null;
		}else{
			content = PubFun.filterWords(content);
		}
		String index = getStringParameter("article_index","",req).replaceAll(" |\t|\r|\n","");
		if(index.length()>251){
			index = index.substring(0, 251);
			if(!index.endsWith("...")){
				index = index + "...";
			}
		}
		Article article = new Article();
		article.setColumnid(columnId);
		article.setTitle(articleTitle);
		article.setAuthor(getStringParameter("article_author", "", req));
		article.setContent(PubFun.getDbContentFliterValue(content));
		article.setNote(index);
		article.setPicture(getStringParameter("index_picture", "", req));
		article.setSource(getStringParameter("source", "", req));
		article.setCreator(user.getId());
		article.setTop(getBooleanParameter("article_top", false, req));
		article.setOrdercnt(getIntParameter("order_cnt", 0, req));
		article.setId(articleId);
		
		if(article.isTop()){
			Object[] args = new Object[1];
			args[0] = article;
			if(transactionConn(args)){//置顶文章
			}else{
				PubFun.ajaxPrintStr("置顶文章失败。", resp);
				return null;
			}
		}else{
			if(article.getId() == 0){
				ArticleTable.insertArticle(article);
			}else{
				ArticleTable.updateArticle(article);
			}
		}

		if(getBooleanParameter("is_publish", false, req)){
			if(articleId == 0){
				articleId = ArticleTable.loadLastIdById();
			}
			StaticArticlePage StaticArticlePage = new StaticArticlePage();
			StaticArticlePage.publish(req, resp, ArticleTable.loadWholeArticleById(articleId), false);
		}
		
		if(getBooleanParameter("is_search", false, req)){
			return "/MainCtrl?page=SearchArticlePage";
		}else{
			return "/MainCtrl?page=ArticleManagePage";
		}
	}

	@Override
	protected void handleTransaction(Connection conn, Object[] args)
			throws Exception {
		Article article = (Article) args[0];
		ArticleTable.cancelTop(conn, article.getColumnid());
		if(article.getId() == 0){
			ArticleTable.insertTransArticle(conn, article);
		}else{
			ArticleTable.updateTransArticle(conn, article);
		}
		
	}

}
