package page.file;

import java.io.File;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MultipartRequest;
import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class UploadFilePage extends HtmlPage {
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		String path = getStringParameter("filepath", user.getUploadFileRoot(),
				req);
		String currentDirPath = InitServlet.WEB_SITE_PATH + path;

		int type = getIntParameter("type", Constant.FILE_PATH_TYPE, req);
		req.setAttribute("type", type);
		String fileName = "";
		String msg = "alert('您已经成功的上传文件。');";

		File upFile = ((MultipartRequest) req).getFile("upfile");
		if (upFile != null) {
			if (MultipartRequest.isMultipart(req)) {
				String oldFileName = ((MultipartRequest) req)
						.getFileName(upFile);
				String ext = oldFileName.substring(oldFileName
						.lastIndexOf(".") + 1);
				if (type == Constant.TEMPLATE_PATH_TYPE && !ext.equals("jsp")) {
					PubFun.ajaxPrintUpStr("alert('上传模板文件只能是jsp文件。');parent.contiueAll();", resp);
					return null;					
				}
				fileName = ((MultipartRequest) req)
						.getParameter("upfile_name");
				if (fileName.trim().equals("")) {// 更改文件名
					fileName = oldFileName;
				} else {
					fileName += oldFileName.substring(oldFileName
							.lastIndexOf("."));
				}
				String nameWithoutExt = fileName.substring(0, fileName
						.lastIndexOf("."));
				File pathToSave = new File(currentDirPath, fileName);
				int counter = 1;
				while (pathToSave.exists()) {
					fileName = nameWithoutExt + "(" + counter + ")"
							+ "." + ext;
					pathToSave = new File(currentDirPath, fileName);
					counter++;
					msg = "alert('被上传文件名已存在；文件名更改为：\"" + fileName
							+ "\"');";
				}

				// 保存照片
				if (type == Constant.FILE_PATH_TYPE) {
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
					PubFun.saveAs(upFile, InitServlet.CONTENT_REALPATH
							+ path + "/" + fileName);
				}
				if (type == Constant.FILE_PATH_TYPE) {
					msg += "parent.exeRequest(parent.rootUrl+'/MainCtrl',parent.rightDivContent,'page=FileListPage&filepath="
							+ path + "');";
				} else {
					msg += "parent.exeRequest(parent.rootUrl+'/MainCtrl',parent.rightDivContent,'page=FileListPage&filepath="
							+ path + "&type=+" + type + "');";
				}
			}
		}

		PubFun.ajaxPrintUpStr(msg, resp);
		return null;
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
