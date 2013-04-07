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

public class ConfigManagePage extends HtmlPage {

	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		String value = getStringParameter("edit_value", "", req);
		String key = getStringParameter("edit_id", "", req);
		if(!key.equals("")){
			Properties prop = new Properties();
			prop.load(new InputStreamReader(new FileInputStream(InitServlet.CONTENT_REALPATH 
					+ "config.properties"), "utf-8"));
			prop.setProperty(key, value.toLowerCase());
			prop.store(new OutputStreamWriter(new FileOutputStream(InitServlet.CONTENT_REALPATH
					+ "config.properties"), "utf-8"), null);
			InitServlet.getSystemParms(prop);
		}

		return "/jsp/sys_config.jsp";
	}

}
