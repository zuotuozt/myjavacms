package page.col;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import table.ColumnTable;
import util.Constant;
import page.inc.HtmlPage;

public class ColManagePage extends HtmlPage {

	@Override //主要任务是显示的准备工作 
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		//parentId是最顶级的id 
        int parentId = getIntParameter("parentId",Constant.HOME_CLOUMN,req);
        if(parentId < Constant.HOME_CLOUMN){
        	parentId = Constant.HOME_CLOUMN;
        }else{
        	parentId = getTop(parentId);
        }
    	req.setAttribute("parentId", parentId);
        Col[] cols = ColumnTable.loadColumnsForTree(Constant.TOP_CLOUMN_TREE);
		StringBuilder display = new StringBuilder();
		if(cols!= null){
			for(int i=0;i<cols.length;i++){
				display.append("<tr class=\"tablelist\" onmouseout=\"showThisLinkOut(this);\" onmouseover=\"showThisLinkOver(this);\">")
				.append("<td class=\"tablelisttext3rt\">"+cols[i].getId()+"</td><td class=\"tablelisttext3rt\" align=\"left\">")
				.append("<a id=\"col"+i+"\" href=\"javascript:treeDiv("+cols[i].getId()+",'col','TreePage');\" style=\"font-weight:bold;\">")
				.append("<img src=\""+req.getContextPath()+"/img/tree/base.gif\" border=\"0px\"/>"+cols[i].getName()+"</a></td>")
				.append("<td class=\"tablelisttext3rt\">")
				.append("<a href=\""+req.getContextPath()+"/MainCtrl?page=PreviewPage&col_id="+cols[i].getId()+"&is_popup=true\" target=\"_blank\">预览</a>&nbsp;&nbsp;");
				if(cols[i].getColType()!=Constant.EXTERNAL_LINK_CLOUMN){
					display.append("<a href=\"javascript:exeRequest('"+req.getContextPath()+"/MainCtrl?page=StaticPage&col_id="+cols[i].getId()+"',rightDivContent);\">发布</a>&nbsp;&nbsp;")
						   .append("<a href=\""+req.getContextPath()+"/MainCtrl?page=ViewPage&col_id="+cols[i].getId()+"&is_popup=true\" target=\"_blank\">查看</a>&nbsp;&nbsp;");
				}
				display.append("<a href=\"javascript:exeRequest('"+req.getContextPath()+"/MainCtrl',rightDivContent,'page=ShowColEditPage&colId="+cols[i].getId()+"');\">编辑</a>&nbsp;&nbsp;");
				if(cols[i].getParentid()!=Constant.TOP_CLOUMN_TREE){
					display.append("<a href=\"javascript:delColumn("+cols[i].getId()+");\">删除</a>&nbsp;&nbsp;");
	       		}
				display.append("<a href=\"javascript:exeRequest('"+req.getContextPath()+"/MainCtrl', rightDivContent, 'page=ShowColEditPage&parentId="+cols[i].getId()+"');\">添加</a>&nbsp;&nbsp;");
	            if(cols[i].getColType()==Constant.ARTICLES_CLOUMN){
		            if(user.getName().equals(Constant.SUPER_USER) || user.getName().equals(Constant.CHIEF_EDITOR) || user.isArticleRole()){
		            	display.append("<a href=\"#\" onclick=\"changeSheet('"+req.getContextPath()+"/MainCtrl?page=ColLeftPage&parentId=1','"
		            			+req.getContextPath()+"/MainCtrl?page=ArticleManagePage&column_id="+cols[i].getId()
		            			+"','文章编辑',$('menu_sheet_1'));\">文章管理</a>&nbsp;&nbsp;");
		            }
	            }
	            if(user.getName().equals(Constant.SUPER_USER) || user.getName().equals(Constant.CHIEF_EDITOR) || user.isPublishRole()){
	            	display.append("<a href=\"javascript:staticColumnsHtml("+cols[i].getId()+",true);\">发布所有</a>");
	            }
	            display.append("</td></tr><tr class=\"tablelist\"><td colspan=\"5\"><div id=\"col"+cols[i].getId()+"\">");
	         }
		}
		StringBuilder display1 = new StringBuilder();
		TreePage treePage = new TreePage();
		display1 = treePage.getTrees(parentId, display1, req);
		display.append(display1).append("</div></td></tr>");
		req.setAttribute("display", display.toString());
        
        
		/*********************************************************/

		return "/jsp/col/col.jsp";
	}
	
	

	

}
