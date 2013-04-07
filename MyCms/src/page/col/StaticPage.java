package page.col;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ColumnTable;
import util.Constant;
import page.col.inc.StaticBasePage;
import util.PubFun;

public class StaticPage extends StaticBasePage {

	@Override
	// 发布栏目
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if ((user == null)) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int colId = getIntParameter("col_id", Constant.TOP_CLOUMN_TREE, req);
		if (colId != Constant.TOP_CLOUMN_TREE && ColumnTable.isExistsById(colId)) {
			Col col = ColumnTable.loadColForPreview(colId);
			if (col.getColType() == Constant.ARTICLES_CLOUMN) {
				String msg = this.staticList(col, req, resp, 1);//1:只更新列表栏目首页
				if(msg.equals("")){
					PubFun.ajaxPrintStr("成功发布列表栏目首页", resp);				
				}else{
					PubFun.ajaxPrintStr(msg, resp);		
				}
			}else if(col.getColType() == Constant.COVER_CLOUMN){
				String msg = this.staticIndex(col, req, resp);
				if(msg.equals("")){
					PubFun.ajaxPrintStr("成功发布封面栏目首页", resp);				
				}else{
					PubFun.ajaxPrintStr(msg, resp);		
				}
			}
		}else{
			PubFun.ajaxPrintStr("栏目不存在", resp);
		}
		return null;
	}

}
