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

public class UploadIndexPicturePage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		String path = getStringParameter("filepath","", req);
		File upFile = ((MultipartRequest) req).getFile("upfile");
		String realPath = InitServlet.WEB_SITE_PATH + Constant.UPLOAD_PATH + "/" + user.getName() + "/image/";
		if (upFile != null) {
			if (MultipartRequest.isMultipart(req)) {
				String fileName = ((MultipartRequest) req).getFileName(upFile);
				
                    File f = new File(realPath);                   	
                    if(!f.exists()){
                    	f.mkdirs();
                    }
                    File ff  = new File(realPath + "/" + fileName);
                    if(ff.exists()){
            			req.setAttribute("inf", "该文件名已存在。");
            			return Constant.VIEW_INFO_PAGE;
                    }else{
            			String ext = "";
            			int indexDoc = fileName.lastIndexOf(".");
            			if(indexDoc != -1){
            				ext = fileName.substring(indexDoc+1).toLowerCase();
            			}
                    	if(ext.equals("jpg") || ext.equals("jpeg")
                    			|| ext.equals("gif") || ext.equals("png")){
            				if(InitServlet.IS_WATER==0){
                        		PubFun.saveAs(upFile, realPath + "/" + fileName);
            				}else{
            					if(InitServlet.IS_WATER_PIC==0){
            						if(!PubFun.pressText(upFile, realPath + "/" + fileName, ext)){
                                		PubFun.saveAs(upFile, realPath + "/" + fileName);
            				        }
            					}else{
            				        if(!PubFun.pressImage(upFile, realPath + "/" + fileName, ext)){
                                		PubFun.saveAs(upFile, realPath + "/" + fileName);
            				        }
            					}
            				}
                    	}else{
                			req.setAttribute("inf", "上传图片必须是jpg；jpeg；gif和png格式。");
                			return Constant.VIEW_INFO_PAGE;
                    	}
                    }
					
			}
		}
		File list = new File(realPath);
		req.setAttribute("filepath", path);
		req.setAttribute("files", list.listFiles());
		return "/jsp/file/select_picture.jsp";
	}

}
