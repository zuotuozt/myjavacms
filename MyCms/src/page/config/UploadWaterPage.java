package page.config;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MultipartRequest;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class UploadWaterPage extends HtmlPage {
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		File upFile = ((MultipartRequest) req).getFile("water_gif");
		if (upFile != null) {
			if (MultipartRequest.isMultipart(req)) {
				String oldFileName = ((MultipartRequest) req)
						.getFileName(upFile);
				String ext = oldFileName.substring(oldFileName
						.lastIndexOf(".") + 1).toLowerCase();
				if (!ext.equals("gif")) {
					PubFun.ajaxPrintUpStr("alert('水印图片只能是gif文件。');parent.contiueAll();", resp);
					return null;					
				}

				PubFun.saveAs(upFile, InitServlet.CONTENT_REALPATH + "img/watermark.gif");
			}
		}

		PubFun.ajaxPrintUpStr("alert('您已经成功的更换水印图片文件。');parent.exeRequest(parent.rootUrl+'/jsp/water_mark.jsp',parent.rightDivContent);", resp);
		return null;
	}

}
