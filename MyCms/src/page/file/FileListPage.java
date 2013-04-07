package page.file;

import java.io.File;
import javabean.UserInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;

public class FileListPage extends HtmlPage {
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int type = getIntParameter("type", Constant.FILE_PATH_TYPE, req);
		int template = getIntParameter("new",0,req);
		String templates = getStringParameter("template","",req);
		String fileRoot = Constant.TEMPLATE_BASEPATH;
		String realPath = InitServlet.WEB_SITE_PATH;
		if(type == Constant.FILE_PATH_TYPE){
			fileRoot = user.getUploadFileRoot();
			realPath = InitServlet.WEB_SITE_PATH;
		}else if(type == Constant.TEMPLATE_PATH_TYPE){
			fileRoot = Constant.TEMPLATE_BASEPATH;
			realPath = InitServlet.CONTENT_REALPATH;
		}
		String path = getStringParameter("filepath", fileRoot, req);
		String flg = getStringParameter("flg", "0", req);
		if(flg.equals("1")){
			if(!path.equals(fileRoot)){
				path = path.substring(0, path.lastIndexOf("/"));
			}
		}else if(!flg.equals("0")){
			path += "/" + flg;		
		}
		
		File f = new File(realPath + path);
		if (!f.exists()) {
			f.mkdirs();
		}
		File[] ff = f.listFiles();
		req.setAttribute("files", ff);
		req.setAttribute("type", type);
		req.setAttribute("filepath", path);
		if(template!=0){
			req.setAttribute("template", templates);
			return "/jsp/file/new_fileList.jsp";
		}else{
			return "/jsp/file/fileList.jsp";
		}
	}

}
