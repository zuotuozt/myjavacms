package page.file;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import util.PubFun;
import page.inc.HtmlPage;
import util.InitServlet;

public class IndexPictureFileList extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			PubFun.ajaxPrint("alert('页面失效；请重新登陆。');opener.location='"
					+ req.getContextPath() + "/" + Constant.REDIRECT_LOGIN_PAGE
					+ "';close();", resp);
			return null;
		}
		String fileRoot = InitServlet.WEB_SITE_PATH + Constant.UPLOAD_PATH + "/" + user.getName() 
						+ "/image";
		String path = getStringParameter("filepath", "", req);
		String flg = getStringParameter("flg", "0", req);
		if(flg.equals("1")){
			if(!path.equals("")){
				path = path.substring(0, path.lastIndexOf("/"));
			}
		}else if(!flg.equals("0")){
			path = path + "/" + flg;
		}
		File f = new File(fileRoot + "/" + path);
	    if(!f.exists()){
	    	f.mkdirs();
	    }
	    req.setAttribute("files", f.listFiles());
    	req.setAttribute("filepath", path);
		return "/jsp/file/select_picture.jsp";
	}

}
