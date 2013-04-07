package page.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.inc.HtmlPage;
import util.Constant;
import util.InitServlet;
import util.PubFun;

public class EditWaterMarkPage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		Properties prop = new Properties();
		prop.load(new InputStreamReader(new FileInputStream(InitServlet.CONTENT_REALPATH 
				+ "config.properties"), "utf-8"));
		String isWater = getStringParameter("is_water", "-1", req);
		if(!isWater.equals("-1")){
			prop.setProperty("IS_WATER", isWater);
		}
		String isWaterPic = getStringParameter("is_water_pic", "-1", req);
		if(!isWaterPic.equals("-1")){
			prop.setProperty("IS_WATER_PIC", isWaterPic);
		}
		String isWaterCenter = getStringParameter("is_water_center", "-1", req);
		if(!isWaterCenter.equals("-1")){
			prop.setProperty("IS_WATER_CENTER", isWaterCenter);
		}
		String waterText = getStringParameter("water_text", "-1", req);
		if(!waterText.equals("-1")){
			prop.setProperty("WATER_TEXT", waterText);
		}
		prop.store(new OutputStreamWriter(new FileOutputStream(InitServlet.CONTENT_REALPATH
				+ "config.properties"), "utf-8"), null);
		InitServlet.getSystemParms(prop);
		
		PubFun.ajaxPrintStr("上传图片水印参数设置成功。", resp);
		return null;
	}

}
