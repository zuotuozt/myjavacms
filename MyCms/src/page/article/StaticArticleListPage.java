package page.article;

import java.util.ArrayList;
import java.util.Collections;

import javabean.Article;
import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.col.inc.StaticBasePage;

import table.ArticleTable;
import table.ColumnTable;
import util.Constant;
import util.PubFun;

public class StaticArticleListPage extends StaticBasePage {
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null){
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int columnId = getIntParameter("column_id",0,req);
		String articleStr = getStringParameter("articles", "", req);
		if(articleStr.equals("")||articleStr.equals("{}")){
			PubFun.ajaxPrintStr("请选择要发布的文章。", resp);
			return null;
		}else{
			Article[] articles = ArticleTable.loadWholeArticleByIds(articleStr);
			if(articles.length == 0){
				PubFun.ajaxPrintStr("请选择要发布的文章。", resp);
				return null;
			}
			if(columnId == 0) columnId = articles[0].getColumnid();
			Col parentCol = ColumnTable.loadColForPreview(columnId);
			ArrayList<Col> cols = new ArrayList<Col>();
			if(parentCol != null){
				cols.add(parentCol);
				getParentsCol(parentCol, cols);
				Collections.reverse(cols);
				req.setAttribute("parentcols", cols);
			}
			for (Article article: articles){
				String msg = this.staticArticle(article, req, resp);
				if(!msg.equals("")){
					PubFun.ajaxPrintStr(msg, resp);
					return null;
				}
			}
			PubFun.ajaxPrintStr("批量发布文章成功。", resp);
			for(Col c : cols){
				if(c.getColType() == Constant.ARTICLES_CLOUMN){
					this.staticList(c, req, resp, 10);//10:只更新列表栏目头10页
				}else if(c.getColType() == Constant.COVER_CLOUMN){
					this.staticIndex(c, req, resp);
				}
			}
			this.indexF(articles);
		}
		return null;
	}

}
