package page.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.inc.HtmlPage;
import util.Constant;
import util.InitServlet;
import util.PubFun;

public class DownLoadFilePage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String names = req.getParameter("name");
		File file = new File(InitServlet.WEB_SITE_PATH + Constant.DOWNLOAD_PATH + "/" + names);
		if(!file.exists()){
			PubFun.ajaxPrintC("文件不存在。", resp);
			return null;
		}
		resp.setContentType("application/force-download");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        resp.setHeader("Content-length", String.valueOf(in.available()));
		resp.setHeader("content-disposition", "attachment;filename=" + names);
		BufferedOutputStream out = new BufferedOutputStream(resp
				.getOutputStream());
		readAndWrite(in, out);
		/*****记录下载次数开始********/
		Properties prop = new Properties();
		prop.load(new InputStreamReader(new FileInputStream(InitServlet.CONTENT_REALPATH 
				+ "config.properties"), "utf-8"));
		prop.setProperty("DOWNLOAD_CNT", String.valueOf(Integer.valueOf(prop.getProperty("DOWNLOAD_CNT"))+1));
		prop.store(new OutputStreamWriter(new FileOutputStream(InitServlet.CONTENT_REALPATH
				+ "config.properties"), "utf-8"), null);
		InitServlet.getSystemParms(prop);
		/*****记录下载次数结束********/

		return null;
	}

	private void readAndWrite(InputStream in, OutputStream out)
			throws IOException {
		byte[] read = new byte[1024];
		int readByte = 0;
		while (-1 != (readByte = in.read(read, 0, read.length))) {
			out.write(read, 0, readByte);
		}
		in.close();
		out.close();
	}

}
