package page.file;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;

public class FileManagePage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String filepath = getStringParameter("filepath",
				user.getUploadFileRoot(), req);
		String oldFilename = getStringParameter("old_filename", "", req);
		String newFilename = getStringParameter("filename", "newfile.txt",
				req);
		String flg = getStringParameter("flg", "0", req);
		int type = getIntParameter("type",Constant.FILE_PATH_TYPE,req);
		String msg = null;
		if ("1".equals(flg)) {// 删除
			if(type==Constant.FILE_PATH_TYPE){
				msg = delFile(InitServlet.WEB_SITE_PATH + filepath + "/"
						+ newFilename);		
			}else{
				msg = delFile(InitServlet.CONTENT_REALPATH + filepath + "/"
						+ newFilename);		
			}
				
		} else {// 改名
			msg = renameFile(filepath, oldFilename, newFilename,type);
		}
		File dir = null;
		if(msg != null){
			req.setAttribute("msg", msg);
		}
		if(type==Constant.FILE_PATH_TYPE){
			 dir = new File(InitServlet.WEB_SITE_PATH + filepath);
		}else{
			 dir = new File(InitServlet.CONTENT_REALPATH + filepath);
		}
		File[] ff = dir.listFiles();
        req.setAttribute("type", type);
		req.setAttribute("files", ff);
		req.setAttribute("filepath", filepath);
		return "/jsp/file/fileList.jsp";
	}

	private String renameFile(String filepath, String oldFileName,
			String newFilename,int type) {
		File file = null;
		if(type==Constant.FILE_PATH_TYPE){
			file = new File(InitServlet.WEB_SITE_PATH + filepath + "/"
					+ oldFileName);
		}else{
			file = new File(InitServlet.CONTENT_REALPATH + filepath + "/"
					+ oldFileName);
		}
		String msg = "";
		if (file.exists()) {
			String ext = "";
			int indexDoc = oldFileName.lastIndexOf(".");
			if(indexDoc != -1){
				ext = oldFileName.substring(indexDoc);
			}
			boolean bolRename = false;
			if(type==Constant.FILE_PATH_TYPE){
				bolRename = file.renameTo(new File(InitServlet.WEB_SITE_PATH 
						+ filepath + "/" + newFilename + ext));
			}else{
				bolRename = file.renameTo(new File(InitServlet.CONTENT_REALPATH 
						+ filepath + "/" + newFilename + ext));
			}
			
			if (bolRename) {
				msg = "改名成功。";
			} else {
				msg = "当前文件夹下可能有相同的文件或目录名存在，不能被改名。";
			}
		} else {
			msg = "文件不存在。";
		}
		return msg;
	}

	// 删除某个文件
	private String delFile(String filepath) {
		File file = new File(filepath);
		String msg = "";
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
				msg = "删除成功。";
			}else if (file.isDirectory()) {
				String[] tempList = file.list();
				if (tempList.length == 0) {
					file.delete();
					msg = "删除成功。";
				} else {
					msg = "当前文件夹下有文件存在，此目录不能被删除。";
				}
			}
		}else {
			msg = "文件或目录不存在。";
		}
		return msg;
	}

}
