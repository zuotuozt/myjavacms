package page.col;
import java.sql.Connection;
import java.util.ArrayList;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.inc.TransactionPage;
import table.ArticleTable;
import table.ColumnTable;
import table.FellingTable;
import util.Constant;
import util.InitServlet;
import util.PubFun;


public class DelColPage extends TransactionPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int colId = getIntParameter("column_id", 0, req);
		if(colId>1 && ColumnTable.isExistsById(colId)){
			Col col = ColumnTable.loadColForDel(colId);
			Object[] args = new Object[2];
			args[0] = colId;
			args[1] = col;
			if(transactionConn(args)){//删除文件
				return "/MainCtrl?page=ColManagePage&parentId="+col.getParentid();
			}else{
				PubFun.ajaxPrintStr("删除栏目失败。", resp);
				return null;
			}
		}else{
			PubFun.ajaxPrintStr("要删除的栏目不存在。", resp);
			return null;
		}

	}
	
	@Override
	protected void handleTransaction(Connection conn, Object[] args)
			throws Exception {
		int colId = (Integer)args[0];
		Col col = (Col)args[1];
		ArrayList<Col> all = new ArrayList<Col>();
		all = this.getAllChildrenForDel(colId, all);
		all.add(col);
		for(Col column : all){
			ColumnTable.deleteColumn(conn, column.getId());
			FellingTable.delFeelingsByColumnId(conn, column.getId());
			ArticleTable.delArticlesByColumnId(conn, column.getId());
			delDirs(InitServlet.WEB_SITE_PATH + col.getHtmlPath());
		}
	}
	
    //获取所有子栏目
	private ArrayList<Col> getAllChildrenForDel(int id,ArrayList<Col> all) throws Exception{
		Col[] col = ColumnTable.loadChildrenByIdForDel(id);
		for(int i = 0; i < col.length; i++){
			all.add(col[i]);
			if(ColumnTable.hasChildren(col[i].getId())){
				this.getAllChildrenForDel(col[i].getId(), all);
			}
		}
		return all;
	}


	
	
}
