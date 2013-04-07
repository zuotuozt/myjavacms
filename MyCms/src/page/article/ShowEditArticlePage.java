package page.article;

import java.util.ArrayList;

import javabean.Article;
import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ArticleTable;
import table.ColumnTable;
import util.Constant;
import page.col.inc.TreeColPage;
import util.PubFun;

public class ShowEditArticlePage extends TreeColPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int colId = getIntParameter("col_id", 0, req);
		long articleId = getLongParameter("article_id", 0, req);
		Article article = new Article();
		
		if(articleId == 0){//增加新文章
			if(ColumnTable.isExistsById(colId)){
				article.setColumnid(colId);
				article.setTop(false);
			}else{
				PubFun.ajaxPrintStr("栏目不存在。", resp);
				return null;
			}
		}else{//修改新文章
			if(ArticleTable.isExistsById(articleId)){
				article = ArticleTable.loadArticleForModify(articleId);
				if(article.getContent() != null){
					article.setContent(PubFun.getArContentValue(article.getContent()));
				}
			}else{
				PubFun.ajaxPrintStr("文章不存在。", resp);
				return null;
			}
		}
		Col col = ColumnTable.loadColById(article.getColumnid());
		if(col != null){
			ArrayList<Col> cols = new ArrayList<Col>();
			cols.add(col);
			this.getParents(col, cols);
			req.setAttribute("cols", cols.toArray(new Col[cols.size()]));
		}else{
			PubFun.ajaxPrintStr("栏目不存在。", resp);
			return null;
		}
		StringBuilder display = new StringBuilder("<div>&nbsp;</div>");
		display = getTrees(Constant.TOP_CLOUMN_TREE, display, req);
		req.setAttribute("selTxt", display.append("<div class=\"floatline\"></div><div>&nbsp;</div>").toString());
		
		req.setAttribute("article", article);
		return "/jsp/article/edit_article.jsp";
	}
	
	private ArrayList<Col> getParents(Col col,ArrayList<Col> cols) throws Exception{
		Col c = ColumnTable.loadColById(col.getParentid());
		if(c!=null){
			cols.add(c);
			getParents(c,cols);
		}
		return cols;
	}
	
	protected StringBuilder getTrees(int parentId,StringBuilder display,HttpServletRequest req) throws Exception{
		//取出id的子频道
		Col[] colTrees = null;
		UserInfo user = getSessionUser(req);
		if(user.getName().equals(Constant.CHIEF_EDITOR) || user.getName().equals(Constant.SUPER_USER)
				|| parentId != Constant.HOME_CLOUMN){
			colTrees = ColumnTable.loadColumnsForTree(parentId);
		}else{
			colTrees = ColumnTable.loadColumnsForHomeTree(user.getId());
		}
		for(int i = 0;i < colTrees.length; i++){
			if(colTrees[i].getColType()!=Constant.EXTERNAL_LINK_CLOUMN){
				colTrees[i].setLevel(this.getlevel(colTrees[i].getHtmlPath()));
				display.append("<div style=\"text-align:left;font-size:12px;\">");
				for(int j=0; j<colTrees[i].getLevel(); j++){
					display.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				}
				if(colTrees[i].getColType()==Constant.ARTICLES_CLOUMN || colTrees[i].getColType()==Constant.COVER_CLOUMN){
					display.append("<a href=\"javascript:controlTree('"+colTrees[i].getId()+"');\">");
					display.append("<img src=\""+req.getContextPath()+"/img/tree/plusbottom.gif\" id=\"src2_"+colTrees[i].getId()+"\" border=\"0px\"/>");
					display.append("<img src=\""+req.getContextPath()+"/img/tree/folder.gif\" id=\"src_"+colTrees[i].getId()+"\" border=\"0px\"/>");
					display.append(colTrees[i].getName()+"</a>");
				}else{
					display.append("<img src=\""+req.getContextPath()+"/img/tree/joinbottom.gif\" id=\"src2_"+colTrees[i].getId()+"\"/>");
					display.append("<img src=\""+req.getContextPath()+"/img/tree/page.gif\" id=\"src_"+colTrees[i].getId()+"\"/>");
					display.append(colTrees[i].getName());				
				}
				if(colTrees[i].getColType() == Constant.ARTICLES_CLOUMN){
					display.append("&nbsp;&nbsp;<input type=\"checkbox\" ");
					display.append("onclick=\"selectColumnPublish("+colTrees[i].getId()+",'"+colTrees[i].getName()+"');return false;\" ");
					display.append("id=\"cb" + colTrees[i].getId()+"\" />");
				}
				display.append("</div><div id=\""+colTrees[i].getId()+"\" style=\"display:none;\">");
				getTrees(colTrees[i].getId(),display,req);
				display.append("</div>");
			}
		}
		return display;
	}

}
