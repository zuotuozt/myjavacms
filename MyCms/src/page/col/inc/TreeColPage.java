package page.col.inc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import page.inc.HtmlPage;
import util.PubFun;

import javabean.UserInfo;

public abstract class TreeColPage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			PubFun.ajaxPrintC("session", resp);
			return null;
		}
		int parentId = getIntParameter("colId", 0, req);
		StringBuilder display = new StringBuilder();
		display = getTrees(parentId, display, req);
		PubFun.ajaxPrintC(display.toString(), resp);
		return null;
	}
	protected abstract StringBuilder getTrees(int parentId,StringBuilder display,HttpServletRequest req) throws Exception;
	
	protected int getlevel(String htmlPath){
		int level = 0 ;
		int index = 0 ;
		if(htmlPath!=null&&!htmlPath.equals("")){
            while(htmlPath.indexOf("/",index)!=-1){
            	index = htmlPath.indexOf("/",index);
            	index++;
            	level++;
            }
		}
		return level;
	}

}

