package page.user;

import javabean.Col;
import javabean.UserInfo;
import javabean.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ColumnTable;
import table.DepartmentTable;
import table.UserInfoTable;
import table.UserRoleTable;
import util.Constant;
import page.inc.HtmlPage;

public class UserInfoPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String returnStr = "/jsp/user/create_user.jsp";
		req.setAttribute("departmentList", DepartmentTable.loadDepartment());
		
		StringBuilder display = new StringBuilder("<div id=\"selectSub\" class=\"selectSub\">");
		Col[] colTrees = ColumnTable.loadColumnsForTree(Constant.HOME_CLOUMN);
		for(int i=0;i<colTrees.length;i++){	    
			display.append("<input type=\"checkbox\" onclick=\"addPreItem('selectSub','previewItem')\" ");
			display.append("value=\"").append(colTrees[i].getId());
			display.append("\" title=\"").append(colTrees[i].getName());
			display.append("\" />").append(colTrees[i].getName()).append("&nbsp;");
		}	    
		req.setAttribute("selTxt", display.append("</div>").toString());
		
		int userId = getIntParameter("user_id", 0, req);
		if(userId > 0){
			req.setAttribute("user", UserInfoTable.loadUserInfoByID(userId));
			returnStr = "/jsp/user/edit_user.jsp";
			StringBuilder display2 = new StringBuilder("");
			UserRole[] userRoleList = UserRoleTable.loadUserRoleList(userId);
			for(int i=0;i<userRoleList.length;i++){
				display2.append("<input type=\"checkbox\" checked onclick=\"copyItem('makeSureItem','previewItem','selectSub');same(this,'selectSub');\" ");
				display2.append("value=\"").append(userRoleList[i].getColumnId());
				display2.append("\" title=\"").append(userRoleList[i].getColName());
				display2.append("\" />").append(userRoleList[i].getColName()).append("&nbsp;");
			}
			req.setAttribute("selTxt2", display2.toString());
		}
		
		return returnStr;
	}
}