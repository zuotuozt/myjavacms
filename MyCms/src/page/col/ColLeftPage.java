package page.col;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import table.ColumnTable;
import page.inc.HtmlPage;
import util.Constant;
import util.PubFun;

public class ColLeftPage extends HtmlPage {

	@Override //主要任务是显示的准备工作 
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			PubFun.ajaxPrintC("session", resp);
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
				display.append("<a id=\"left_col").append(i).append("\" href=\"javascript:treeDiv(")
				.append(cols[i].getId()).append(",'left_col','TreeLeftPage');\">")
				.append("<img src=\""+req.getContextPath()+"/img/tree/base.gif\" border=\"0px\"/>")
				.append("<font style=\"font-weight:bold;font-size:12px;\">"+cols[i].getName())
				.append("</font></a><div id=\"left_col"+cols[i].getId()+"\">");
			}
		}
		StringBuilder display1 = new StringBuilder();
		TreeLeftPage treeLeftPage = new TreeLeftPage();
		display1 = treeLeftPage.getTrees(parentId, display1, req);
		display.append(display1).append("</div>");
		req.setAttribute("display", display.toString());
		/*********************************************************/

		return "/jsp/col/left_col.jsp";
	}

}
