package page.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class BackUpPage extends HtmlPage {

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
			backup(resp);
		}
		return  null;
	}
	
	private void backup(HttpServletResponse resp){
		BufferedReader br = null;
		PrintWriter writer = null;
		String inStr = null;
		List<String> cmd = new ArrayList<String>();
		cmd.add(InitServlet.MYSQL_DB_PATH + "/" + "mysqldump");
		cmd.add("-u" + InitServlet.MYSQL_DB_USER);
		cmd.add("-p" + InitServlet.MYSQL_DB_PASSWD);
		cmd.add(InitServlet.MYSQL_DB_NAME);
		ProcessBuilder pb = new ProcessBuilder(cmd);
		Process process;
		try {
			process = pb.start();
			br = new BufferedReader(new InputStreamReader(process
					.getInputStream(), "utf-8"));
			StringBuilder sb = new StringBuilder("");
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr).append(System.getProperty("line.separator"));
			}
			String outStr = sb.toString();
	        writer = resp.getWriter();
	    	resp.setHeader("content-disposition", "attachment;filename=" + InitServlet.MYSQL_DB_NAME +".sql");
			writer.write(outStr);
			writer.flush();
			if (writer != null)
				writer.close();
			if (br != null)
				br.close();
		} catch (IOException e) {
			try {
				PubFun.ajaxPrint("alert('mysql数据库程序目录设置不正确；请到《系统参数》中修改；或者内容中有英文单引号+中文连词。');parent.contiueAll();", resp);
			} catch (IOException e1) {
				System.out.println(e.getMessage());
			}
			System.out.println(e.getMessage());
		}
	}

}
