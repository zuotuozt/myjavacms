package page.article;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javabean.Article;
import javabean.Col;
import javabean.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.col.inc.StaticBasePage;

import table.ArticleTable;
import table.ColumnTable;
import util.Constant;
import util.InitServlet;
import util.PubFun;

public class StaticArticlePage extends StaticBasePage {
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		int flg = getIntParameter("flg", -1, req);
		if(user == null){
			if(flg == Constant.STATIC){
				resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			}else{
				PubFun.ajaxPrint("alert('页面失效；请重新登陆。');opener.location='"
						+ req.getContextPath() + "/" + Constant.REDIRECT_LOGIN_PAGE
						+ "';close();", resp);
			}
			return null;
		}
		long articleId = getLongParameter("article_id", 0, req);
		if(articleId != 0 && ArticleTable.isExistsById(articleId)){
			if(flg == Constant.VIEW){
				Article article = ArticleTable
						.loadArticleForStaticPage(articleId);
				String htmlPath = article.getHtmlPath() + "/"
					+ PubFun.getDateTime("yyyy-MM-dd", article.getCreatime()) + "/"	+ articleId + ".html";
				if(new File(InitServlet.WEB_SITE_PATH + htmlPath).exists()){
					htmlPath = InitServlet.WEB_SITE_URL + htmlPath;
					resp.sendRedirect(htmlPath);
				}else{
					PubFun.ajaxPrint("alert('网页文件不存在；请发布后再查看 。');close();", resp);
				}
			}else{
				Article article = ArticleTable.loadWholeArticleById(articleId);
				if(flg == Constant.PREVIEW){
					if((!article.getArticleTemplate().equals("")) &&
							new File(InitServlet.CONTENT_REALPATH + article.getArticleTemplate()).exists()){
						article.setUrl(InitServlet.WEB_SITE_URL + article.getHtmlPath() + "/" 
								+ PubFun.getDateTime("yyyy-MM-dd", article.getCreatime()) 
								+ "/" + article.getId() + ".html");
						req.setAttribute("article", article);
						req.setAttribute("columnid", article.getColumnid());
						req.setAttribute("seotitle", article.getSeoTitle());
						req.setAttribute("seokeywords", article.getSeoKeywords());
						req.setAttribute("seodescription", article.getSeoDescription());
						Col parentCol = ColumnTable.loadColForPreview(article.getColumnid());
						ArrayList<Col> cols = new ArrayList<Col>();
						if(parentCol != null){
							cols.add(parentCol);
							getParentsCol(parentCol, cols);
							Collections.reverse(cols);
							req.setAttribute("parentcols", cols);
						}
						return article.getArticleTemplate() + "?id=" + articleId;
					}else{
						PubFun.ajaxPrint("alert('无法预览；请到\"模板管理\" 去 选定此栏目的文章模板文件。');close();", resp);
					}
				}else if(flg == Constant.STATIC){
					publish(req, resp, article, true);
				}
			}
		}else{
			if(flg == Constant.STATIC){
				PubFun.ajaxPrintStr("文章不存在。", resp);
			}else{
				PubFun.ajaxPrint("alert('文章不存在。');close();", resp);
			}
		}

		return null;
	}

	public void publish(HttpServletRequest req, HttpServletResponse resp,
			Article article,boolean isPopup) throws Exception, IOException, ServletException {
		Col parentCol = ColumnTable.loadColForPreview(article.getColumnid());
		ArrayList<Col> cols = new ArrayList<Col>();
		if(parentCol != null){
			cols.add(parentCol);
			getParentsCol(parentCol, cols);
			Collections.reverse(cols);
			req.setAttribute("parentcols", cols);
		}
		String msg = this.staticArticle(article, req, resp);
		if(msg.equals("")){
			if(isPopup) PubFun.ajaxPrintStr("成功发布这一篇文章。", resp);
			for(Col c : cols){
				if(c.getColType() == Constant.ARTICLES_CLOUMN){
					this.staticList(c, req, resp, 10);//10:只更新列表栏目头10页
				}else if(c.getColType() == Constant.COVER_CLOUMN){
					this.staticIndex(c, req, resp);
				}
			}
			this.indexF(article);
		}else{
			PubFun.ajaxPrintStr(msg, resp);
		}
	}

}
