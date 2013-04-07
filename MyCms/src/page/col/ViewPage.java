package page.col;

import java.io.File;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ColumnTable;
import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class ViewPage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if((user == null)) {
			PubFun.ajaxPrint("alert('页面失效；请重新登陆。');opener.location='"
					+ req.getContextPath() + "/" + Constant.REDIRECT_LOGIN_PAGE
					+ "';close();", resp);
			return null;
		}
		int colId = getIntParameter("col_id", 0, req);
		if(colId != 0 && ColumnTable.isExistsById(colId)){
			Col col = ColumnTable.loadColForPreview(colId);
			String url = col.getLink();
			if(col.getColType()==2){
	        	resp.sendRedirect(url);
	        }else if(new File(InitServlet.WEB_SITE_PATH + url).exists()){
				resp.sendRedirect(InitServlet.WEB_SITE_URL + url);
			}else{
				PubFun.ajaxPrint("alert('网页文件不存在；请点击\"发布\"按钮 。');close();", resp);
			}
		}else{
			PubFun.ajaxPrint("alert('此栏目不存在；请确认 。');close();", resp);
		}
		return null;
	}

}
