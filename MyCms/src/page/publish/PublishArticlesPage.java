package page.publish;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ColumnTable;
import util.Constant;
import page.col.inc.TreeColPage;

public class PublishArticlesPage  extends TreeColPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		boolean isArticle = getBooleanParameter("is_article", true, req);
		StringBuilder display = new StringBuilder("<div>&nbsp;</div>");
		display = getTrees(0, display, req);
		req.setAttribute("selTxt", display.append("<div class=\"floatline\"></div><div>&nbsp;</div>").toString());
		if(isArticle){
			return "/jsp/publish/publish_articles.jsp";
		}else{
			return "/jsp/publish/publish_columns.jsp";	
		}
		
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
	
				display.append("&nbsp;&nbsp;<input type=\"checkbox\" ");
				display.append("onclick=\"selectColumnPublish("+colTrees[i].getId()+",'"+colTrees[i].getName()+"');\" ");
				display.append("id=\"cb" + colTrees[i].getId()+"\" /></div>");
				display.append("<div id=\""+colTrees[i].getId()+"\" style=\"display:none;\">");
				getTrees(colTrees[i].getId(),display,req);
				display.append("</div>");
			}
		}
		return display;
	}

}