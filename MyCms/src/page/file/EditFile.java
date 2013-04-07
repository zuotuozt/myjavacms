package page.file;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class EditFile extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String filepath = getStringParameter("filepath","",req);
		String name = getStringParameter("filename","",req);
		String content = getStringParameter("content","",req);
		int type = getIntParameter("type",Constant.FILE_PATH_TYPE,req);
		content = PubFun.getFilterValue(content);
	    File file = null; 
	    if(type==Constant.FILE_PATH_TYPE){
	    	 PubFun.writeFile(InitServlet.WEB_SITE_PATH + filepath, name, content);
	    	 file = new File(InitServlet.WEB_SITE_PATH + filepath);
	    }else{
	    	 file = new File(InitServlet.CONTENT_REALPATH + filepath + "/" + name);
	    	 if(!file.exists()){
	    		 file.createNewFile();
	    	 }
	    	 PubFun.writeFile(InitServlet.CONTENT_REALPATH+filepath, name, content);
	    	 file = new File(InitServlet.CONTENT_REALPATH+filepath);
	    } 
	    File[] ff = file.listFiles();
	    req.setAttribute("filepath", filepath);
	    req.setAttribute("files", ff);
	    req.setAttribute("type", type);
		return "/jsp/file/fileList.jsp";
	}

}
