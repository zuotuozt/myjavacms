import java.io.File;
import java.io.IOException;

import javabean.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.InitServlet;
import util.MultipartRequest;
import util.Constant;
import util.PubFun;

public class SimpleUploaderCtrl extends HttpServlet {

	private static final long serialVersionUID = 1L;
//ckeditor上传文件
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setHeader("Cache-Control","no-cache");
		HttpSession session = req.getSession(true);
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		if (user == null) {
			PubFun.ajaxPrintUpStr("alert('页面失效；请重新登陆。');parent.location='"
					+ req.getContextPath() + "/" + Constant.REDIRECT_LOGIN_PAGE + "';", resp);
			return;
		}		

		String currentPath = Constant.UPLOAD_PATH + "/" + user.getName() + "/"
				+ req.getParameter("type") + "/" + PubFun.getDateTime("yyyy-MM", null);
		String currentDirPath = InitServlet.WEB_SITE_PATH + currentPath;
		File current = new File(currentDirPath);
		if (!current.exists()) {
			current.mkdirs();
		}
		currentPath = InitServlet.WEB_SITE_URL + currentPath;

		String fileUrl = "";

		try {
			req = new MultipartRequest(req, user.getId());
			File upFile = ((MultipartRequest) req).getFile("upload");
			if (upFile != null) {
				String fileName = ((MultipartRequest) req).getFileName(upFile);
				String nameWithoutExt = fileName.substring(0, fileName
						.lastIndexOf("."));
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				File pathToSave = new File(currentDirPath, fileName);
				fileUrl = currentPath + "/" + fileName;
				int counter = 1;
				while (pathToSave.exists()) {
					fileName = nameWithoutExt + "(" + counter + ")" + "."
							+ ext;
					fileUrl = currentPath + "/" + fileName;
					pathToSave = new File(currentDirPath, fileName);
					counter++;
				}
				String fileType = getFileType(ext);
				if(fileType.equals("not image") || InitServlet.IS_WATER==0){
					PubFun.saveAs(upFile, currentDirPath + "/" + fileName);
				}else{
					if(InitServlet.IS_WATER_PIC==0){
						if(!PubFun.pressText(upFile, currentDirPath + "/" + fileName, fileType)){
				        	PubFun.saveAs(upFile, currentDirPath + "/" + fileName);
				        }
					}else{
				        if(!PubFun.pressImage(upFile, currentDirPath + "/" + fileName, fileType)){
				        	PubFun.saveAs(upFile, currentDirPath + "/" + fileName);
				        }
					}
				}
			} else {
				PubFun.ajaxPrintUpStr("alert('请选择上传文件。');", resp);
			}
		} catch (Exception ex) {
			PubFun.ajaxPrintUpStr("alert('上传文件失败。');", resp);
		} finally {
			if (req instanceof MultipartRequest) {
				((MultipartRequest) req).deleteTemporaryFile();
			}
		}
		
		String str = "CKEDITOR.tools.callFunction("  
                + req.getParameter("CKEditorFuncNum") + ",'" + fileUrl + "',''" + ")";

		PubFun.ajaxPrintUpStr(str, resp);
	}
	
    private String getFileType(String ext){
        String type = ext.toLowerCase();
        if(type.equals("jpeg")){
            type = "jpg";
        }
        if(ext.equals("jpg") || ext.equals("png") || ext.equals("gif") || ext.equals("bmp")){        	
        }else{
        	type="not image";
        }
        return type;
    }

}

