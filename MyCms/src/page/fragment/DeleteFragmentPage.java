package page.fragment;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.FragmentTable;
import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;

public class DeleteFragmentPage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		long fragmentId = getLongParameter("fragment_id", 0, req);
		
		delFragmentFile(fragmentId);
		FragmentTable.delFragment(fragmentId);
		
		return "/MainCtrl?page=FragmentManagePage";
	}
	
	private void delFragmentFile(long fragmentId) throws Exception {
		
		String htmlPath =( InitServlet.WEB_SITE_PATH + Constant.FRAGMENT_PATH);
		
		File file = new File(htmlPath + "/" + fragmentId + ".js");
		if(file.exists()){
			file.delete();
		}
	}
}
