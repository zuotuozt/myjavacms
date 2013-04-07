package page.col;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.col.inc.TreeColPage;
import javabean.Col;
import javabean.UserInfo;
import table.ColumnTable;
import util.Constant;

public class TreeLeftPage extends TreeColPage {

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
		for(int i = 0;i < colTrees.length; i++){
			colTrees[i].setLevel(this.getlevel(colTrees[i].getHtmlPath()));
			display.append("<div style=\"text-align:left;font-size:12px;\">");
			for(int j=0; j<colTrees[i].getLevel(); j++){
				display.append("&nbsp;&nbsp;&nbsp;");
			}
			if(colTrees[i].getColType()==Constant.ARTICLES_CLOUMN || colTrees[i].getColType()==Constant.COVER_CLOUMN){
				String jsStr = "exeRequest(rootUrl+'/jsp/inc/welcome.jsp', rightDivContent);";
				String colTitle = " title=\"["+colTrees[i].getName()+"]只是封面栏目；不是文章最终文章栏目。\"";
				if(colTrees[i].getColType()==Constant.ARTICLES_CLOUMN){
					jsStr = "exeRequest(rootUrl+'/MainCtrl',rightDivContent,'page=ArticleManagePage&column_id="+colTrees[i].getId()+"');";
					colTitle = "";
				}
				display.append("<a href=\"javascript:controlTree('left_col"+colTrees[i].getId()+"');"+jsStr+"\""+colTitle+">");
				display.append("<img src=\""+req.getContextPath()+"/img/tree/plusbottom.gif\" id=\"src2_left_col"+colTrees[i].getId()+"\" border=\"0px\"/>");
				display.append("<img src=\""+req.getContextPath()+"/img/tree/folder.gif\" id=\"src_left_col"+colTrees[i].getId()+"\" border=\"0px\"/>");
				display.append(colTrees[i].getName()+"</a>");
			}else{
				if(colTrees[i].getColType()==Constant.EXTERNAL_LINK_CLOUMN){
					display.append("<a href=\""+req.getContextPath()+"/MainCtrl?page=ViewPage&col_id="+colTrees[i].getId()+"&is_popup=true\"  target=\"_blank\">");
				}
				display.append("<img src=\""+req.getContextPath()+"/img/tree/joinbottom.gif\" id=\"src2_left_col"+colTrees[i].getId()+"\" border=\"0px\"/>");
				display.append("<img src=\""+req.getContextPath()+"/img/tree/page.gif\" id=\"src_left_col"+colTrees[i].getId()+"\" border=\"0px\"/>");
				display.append(colTrees[i].getName()+"</a>");
				if(colTrees[i].getColType()==Constant.EXTERNAL_LINK_CLOUMN){
					display.append("&nbsp;<font color=\"red\">(");
					display.append("外部链接");
					display.append(")</font>");
				}
			}

			display.append("</div>");
			display.append("<div id=\"left_col"+colTrees[i].getId()+"\" style=\"display:none;\">");
			getTrees(colTrees[i].getId(),display,req);
			display.append("</div>");
		}
		return display;
	}

}

