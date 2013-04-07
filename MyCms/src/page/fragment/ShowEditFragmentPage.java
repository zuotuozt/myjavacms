package page.fragment;

import javabean.Col;
import javabean.Fragment;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ColumnTable;
import table.FragmentTable;
import util.Constant;
import page.col.inc.TreeColPage;
import util.PubFun;

public class ShowEditFragmentPage extends TreeColPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int fragmentId = getIntParameter("fragment_id", 0, req);
		if(fragmentId != 0){
			if(!FragmentTable.isExistsById(fragmentId)){
				PubFun.ajaxPrintStr("没有此广告。", resp);
				return null;
			}
			req.setAttribute("fragment", FragmentTable.loadFragmentById(fragmentId));
		}
		StringBuilder display = new StringBuilder("<select id=\"column_id\"><option value=\"-1\" style=\"background:#dcdcdc;\">所有栏目</option>");
		display = getTrees(0, display, req);
		req.setAttribute("selTxt", display.append("</select>").toString());
		
		return "/jsp/fragment/edit_fragment.jsp";
	}
	
	protected StringBuilder getTrees(int parentId,
									StringBuilder display,
									HttpServletRequest req) throws Exception{
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
			if(colTrees[i].getColType()==Constant.ARTICLES_CLOUMN || colTrees[i].getColType()==Constant.COVER_CLOUMN){
				colTrees[i].setLevel(this.getlevel(colTrees[i].getHtmlPath()));
				display.append("<option value=\""+colTrees[i].getId()+"\"");
				if(colTrees[i].getColType()==Constant.COVER_CLOUMN){
					display.append(" style=\"background:#dcdcdc;\"");
				}
				Fragment fragment = (Fragment)req.getAttribute("fragment");
				if(fragment != null && colTrees[i].getId()==fragment.getColumnId()){
					display.append(" selected=\"selected\"");
				}
				display.append(">");
				for(int j=0; j<colTrees[i].getLevel()+1; j++){
					if(colTrees[i].getId()!=Constant.HOME_CLOUMN){
						display.append("—");
					}
				}
				display.append(colTrees[i].getName()).append("</option>");
				getTrees(colTrees[i].getId(),display,req);
			}
		}
		return display;
	}

}
