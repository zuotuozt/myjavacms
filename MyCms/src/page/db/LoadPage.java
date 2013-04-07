package page.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MultipartRequest;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class LoadPage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			PubFun.ajaxPrint("alert('页面失效；请重新登陆。');parent.location.reload();",
					resp);
			return null;
		}
		if(InitServlet.MYSQL_DB_PASSWD.equals("")){
			PubFun.ajaxPrint("alert('为了安全起见，请输入数据库密码。');parent.contiueAll();", resp);
		}else{
			File file = ((MultipartRequest) req).getFile("upfile"); 
			if(file!=null){
				load(file.getPath(), resp);
				PubFun.ajaxPrint("alert('恢复数据库成功。');parent.contiueAll();", resp);
			}
		}
		return null;
	}
	
	private void load(String file, HttpServletResponse resp) throws IOException {
		OutputStream out = null;
		BufferedReader br = null;
		String inStr = null;
		OutputStreamWriter writer = null;
		StringBuilder sb = new StringBuilder("");
		List<String> cmd = new ArrayList<String>();
		cmd.add(InitServlet.MYSQL_DB_PATH + "/" + "mysql");
		cmd.add("-u" + InitServlet.MYSQL_DB_USER);
		cmd.add("-p" + InitServlet.MYSQL_DB_PASSWD);
		cmd.add(InitServlet.MYSQL_DB_NAME);
		ProcessBuilder pb = new ProcessBuilder(cmd);
		try{
			Process child = pb.start();
			out = child.getOutputStream();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "utf-8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + System.getProperty("line.separator"));
			}
			writer = new OutputStreamWriter(out, "utf-8");
			writer.write(sb.toString());
			writer.flush();
			writer.close();
			br.close();
			out.close();
		} catch (IOException e) {
			try {
				PubFun.ajaxPrint("alert('mysql数据库程序目录设置不正确；请到《系统参数》中修改。');parent.contiueAll();", resp);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
		}
	}


}
