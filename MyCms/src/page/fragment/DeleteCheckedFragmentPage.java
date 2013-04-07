package page.fragment;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.FragmentTable;
import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class DeleteCheckedFragmentPage extends HtmlPage{

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String fragments = getStringParameter("fragments", "", req);
		if(fragments.equals("")||fragments.equals("{}")){
			PubFun.ajaxPrintStr("请选择要删除的碎片。", resp);
			return null;
		}
		
		String[] strArr = fragments.split(",");
		String htmlPath = InitServlet.WEB_SITE_PATH + Constant.FRAGMENT_PATH;
		for(String e : strArr){
			File file = new File(htmlPath + "/" + e + ".js");
			if(file.exists()){
				file.delete();
			}
		}
		
		FragmentTable.delFragments(fragments);
		
		return "/MainCtrl?page=FragmentManagePage";
	}

}
