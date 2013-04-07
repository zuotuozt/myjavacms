package page.article;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class ArticleThumbnailsPage extends HtmlPage{
	public String print(HttpServletRequest req, HttpServletResponse resp)
	throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			PubFun.ajaxPrintC("session", resp);
			return null;
		}
		String fileRoot = InitServlet.WEB_SITE_PATH + Constant.UPLOAD_PATH + "/" + user.getName() + "/";
		//源图片名称
		String oldfilepath = getStringParameter("oldfilepath", "", req);
		oldfilepath=fileRoot+oldfilepath.substring(oldfilepath.lastIndexOf("image"));
		//源图片路径名称
		String fileName = getStringParameter("fileName", "", req);
		//剪切图片存放路径名称
		String fileDirectory = fileRoot+"/image/thumbnails"+"/"+PubFun.getDateTime("yyyy-MM", null);
		File f = new File(fileDirectory);
		if(!f.exists()){
			f.mkdirs();
		}
		// 剪切点y坐标
		int top= getIntParameter("imgtop",0,req);
		//剪切点x坐标 
		int left= getIntParameter("imgleft",0,req);
		// 剪切点宽度
		int width= getIntParameter("imgwidth",0,req);
		// 剪切点高度
		int height= getIntParameter("imgheight",0,req);
		
		String nameWithoutExt = fileName.substring(0, fileName
				.lastIndexOf("."));
		if(nameWithoutExt.indexOf("(") != -1){
			nameWithoutExt = fileName.substring(0, fileName
					.indexOf("("));
		}
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		File pathToSave = new File(fileDirectory, fileName);
		int counter = 1;
		while (pathToSave.exists()) {
			fileName = nameWithoutExt + "(" + counter + ")" + "."
					+ ext;
			pathToSave = new File(fileDirectory, fileName);
			counter++;
		}
		PubFun.cut(oldfilepath, fileDirectory + "/" + fileName, left, top, width, height);
		
		PubFun.ajaxPrintC("/thumbnails"+"/"+PubFun.getDateTime("yyyy-MM", null) + "/" + fileName, resp);

		return null;
	}
	
}


