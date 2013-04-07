package page.fragment;

import java.io.File;
import java.sql.Connection;

import javabean.Fragment;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.FragmentTable;
import util.Constant;
import util.InitServlet;
import util.PubFun;
import page.inc.TransactionPage;

public class EditFragmentPage  extends TransactionPage{

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {		
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String fragmentTitle = getStringParameter("fragment_title", "", req);
		if(fragmentTitle.equals("")){
			PubFun.ajaxPrintStr("请您输入 广告名称。", resp);
			return null;
		}
		String content = getStringParameter("content", "", req);
		if(content.equals("")){
			PubFun.ajaxPrintStr("请您输入广告内容。", resp);
			return null;
		}
		content = PubFun.setJsFilterValue(content);
		int fragmentId = getIntParameter("fragment_id", 0, req);
		int columnId = getIntParameter("column_id", -1, req);
		
		Fragment fragment = new Fragment();
		fragment.setTitle(fragmentTitle);
		fragment.setContent(content);
		fragment.setColumnId(columnId);
		if(fragmentId == 0){
			Object[] args = new Object[1];
			args[0] = fragment;
			if(transactionConn(args)){
			}else{
				PubFun.ajaxPrintStr("增加广告失败。", resp);
				return null;
			}
		}else{
			toJsFile(content, fragmentId);
			fragment.setId(fragmentId);
			FragmentTable.updateFragment(fragment);
		}
		
		return "/MainCtrl?page=FragmentManagePage&fragment_id=" + fragmentId;

	}

	private void toJsFile(String content, long fragmentId) throws Exception {
		if(!content.equals("")){
			File f = new File(InitServlet.WEB_SITE_PATH + Constant.FRAGMENT_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}
			String filePrefix = InitServlet.WEB_SITE_PATH + Constant.FRAGMENT_PATH
				+ "/" + fragmentId;
			stringToFile(filePrefix + ".js", "document.write('" 
					+ PubFun.getHtmlFilterValue(content) + "');");
		}
	}

	@Override
	protected void handleTransaction(Connection conn, Object[] args)
			throws Exception {
		Fragment fragment = (Fragment)args[0];
		FragmentTable.insertFragment(conn, fragment);
		long fragmentId = FragmentTable.loadLastId(conn);
		toJsFile(fragment.getContent(), fragmentId);		
	}
	
}
