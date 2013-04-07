package page.col;

import java.util.ArrayList;
import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.col.inc.StaticBasePage;

import table.ColumnTable;
import util.Constant;
import util.PubFun;

public class StaticColumnsPage extends StaticBasePage {
	@Override
	// 发布栏目下栏目
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			PubFun.ajaxPrintStr("页面失效，请重新登录。", resp);
			return null;
		}
		int colId = getIntParameter("col_id", 0, req);
		if (colId != 0 && ColumnTable.isExistsById(colId)) {
			Col col = ColumnTable.loadColForPreview(colId);
			ArrayList<Col> columns = new ArrayList<Col>();
			columns.add(col);
			columns = this.getChildren(colId, columns);
			for(Col c : columns){
				if(c.getColType() == Constant.ARTICLES_CLOUMN){
					this.staticList(c, req, resp, 0);//0:更新列表栏目所有页面
				}else if(c.getColType() == Constant.COVER_CLOUMN){
					this.staticIndex(c, req, resp);
				}
			}
			PubFun.ajaxPrintStr("更新栏目页面已完成。", resp);
		}else{
			PubFun.ajaxPrintStr("栏目不存在。", resp);
		}
		return null;
	}
	
}
