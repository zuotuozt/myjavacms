package page.file;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class ShowEditFile extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		//获取路径参数
		String path = getStringParameter("filepath","",req);
		String filename = getStringParameter("filename","",req);
		int type = getIntParameter("type",Constant.FILE_PATH_TYPE,req);
		String contents = "";
		//获取文件内容
		if(type==Constant.FILE_PATH_TYPE){
			contents = PubFun.getJsFilterValue(PubFun.getFileContent(InitServlet.WEB_SITE_PATH + path, filename));
		}else{
			if(filename.equals("")){
				contents = "<%@ page contentType=\"text/html;charset=utf-8\" %>\n<%@ taglib uri=\"/51java\" prefix=\"51java\" %>";
			}else{
				contents = PubFun.getJsFilterValue(PubFun.getFileContent(InitServlet.CONTENT_REALPATH + path, filename));
			}
		}
		
		req.setAttribute("content", contents);
		req.setAttribute("filepath", path);
		req.setAttribute("filename", filename);
		req.setAttribute("type", type);
		return "/jsp/file/edit_file.jsp";
	}
}
