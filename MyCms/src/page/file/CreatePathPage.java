package page.file;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;

public class CreatePathPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String path = getStringParameter("filepath",
				user.getUploadFileRoot(), req);
		int type = getIntParameter("type",Constant.FILE_PATH_TYPE,req);
		
		String mkName = getStringParameter("newpath", "newfile", req);
		File dir = null;
        if(type==Constant.FILE_PATH_TYPE){
        	 dir = new File(InitServlet.WEB_SITE_PATH + path + "/" + mkName);
        }else{
        	 dir = new File(InitServlet.CONTENT_REALPATH + path + "/" + mkName);
        }
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File[] ff = dir.getParentFile().listFiles();
        req.setAttribute("type", type);
		req.setAttribute("files", ff);
		req.setAttribute("filepath", path);
		return "/jsp/file/fileList.jsp";

	}

}
