package page.col;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.col.inc.TreeColPage;
import util.Constant;
import javabean.Col;
import javabean.UserInfo;
import table.ColumnTable;

public class TreePage extends TreeColPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return super.print(req, resp);
	}
	
	public StringBuilder getTrees(int parentId,StringBuilder display,HttpServletRequest req) throws Exception{
		//取出id的子频道
		Col[] colTrees = null;
		UserInfo user = getSessionUser(req);
		if(user.getName().equals(Constant.CHIEF_EDITOR) || user.getName().equals(Constant.SUPER_USER)
				|| parentId != Constant.HOME_CLOUMN){
			colTrees = ColumnTable.loadColumnsForTree(parentId);
		}else{
			colTrees = ColumnTable.loadColumnsForHomeTree(user.getId());
		}
		display.append("<table border=\"0px\" cellpadding=\"0px\" cellspacing=\"0px\" width=\"100%\" >");
		for(int i = 0;i<colTrees.length;i++){
			colTrees[i].setLevel(this.getlevel(colTrees[i].getHtmlPath()));
			display.append("<tr class=\"tablelisttext3ro\" onmouseout=\"showThisLinkOut(this);\" onmouseover=\"showThisLinkOver(this);\">");
			display.append("<td class=\"tablelisttext3ro\" width=\"5%\">"+colTrees[i].getId()+"</td>");
			display.append("<td class=\"tablelisttext3ro\" width=\"53%\">");
			display.append("<div style=\"text-align:left;\">");
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
			if(colTrees[i].getColType()==Constant.EXTERNAL_LINK_CLOUMN){
				display.append("&nbsp;<font color=\"red\">(");
				display.append("外部链接");
				display.append(")</font>");
			}
			display.append("</div></td>");
			display.append("<td class=\"tablelisttext3ro\" width=\"42%\">");
			if(colTrees[i].getColType()!=Constant.EXTERNAL_LINK_CLOUMN){
				display.append("<a href=\""+req.getContextPath()+"/MainCtrl?page=PreviewPage&col_id="+colTrees[i].getId()+"&is_popup=true\" target=\"_blank\">预览</a>");
				display.append("&nbsp;");
				display.append("<a href=\"javascript:exeRequest(rootUrl+'/MainCtrl?page=StaticPage&col_id="+colTrees[i].getId()+"',rightDivContent);\">发布</a>");
				display.append("&nbsp;");
			}
			display.append("<a href=\""+req.getContextPath()+"/MainCtrl?page=ViewPage&col_id="+colTrees[i].getId()+"&is_popup=true\"  target=\"_blank\">查看</a>");
			display.append("&nbsp;");
			display.append("<a href=\"javascript:exeRequest(rootUrl+'/MainCtrl', rightDivContent, 'page=ShowColEditPage&colId="+colTrees[i].getId()+"');\">编辑</a>");
			display.append("&nbsp;"); 
			display.append("<a href=\"javascript:delColumn("+colTrees[i].getId()+");\">删除</a>");
			if(colTrees[i].getColType()==Constant.ARTICLES_CLOUMN){
				if(user.getName().equals(Constant.SUPER_USER) || 
						user.getName().equals(Constant.CHIEF_EDITOR) || user.isArticleRole()){
					display.append("&nbsp;");
					display.append("<a href=\"#\" onclick=\"changeSheet('/MainCtrl?page=ColLeftPage&parentId=1','/MainCtrl?page=ArticleManagePage&column_id="+colTrees[i].getId()+"','文章编辑',$('menu_sheet_1'));\">"+"文章管理</a>");
				}
			}
			if(colTrees[i].getColType()==Constant.ARTICLES_CLOUMN || colTrees[i].getColType()==Constant.COVER_CLOUMN){
				display.append("&nbsp;");
				display.append("<a href=\"javascript:exeRequest(rootUrl+'/MainCtrl', rightDivContent, 'page=ShowColEditPage&parentId="+colTrees[i].getId()+"');\">添加</a>");
				if(user.getName().equals(Constant.SUPER_USER) || 
						user.getName().equals(Constant.CHIEF_EDITOR) || user.isPublishRole()){
					display.append("&nbsp;");
					display.append("<a href=\"javascript:staticColumnsHtml("+colTrees[i].getId()+",true);\">发布所有</a>");
				}
			}
			display.append("</td></tr>");
			display.append("<tr class=\"tablelisttext3ro\"><td colspan=\"5\"><div id=\""+colTrees[i].getId()+"\" style=\"display:none;\">");
			getTrees(colTrees[i].getId(),display,req);
			display.append("</div></td></tr>");
		}
		display.append("</table>");
		return display;
	}

}

