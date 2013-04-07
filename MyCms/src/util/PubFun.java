package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javabean.Article;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import page.inc.HtmlPage;

public class PubFun {

	/** *web startup initialize*** */
	private static ComboPooledDataSource ds = null;
	private static Map<String, HtmlPage> pageMap = null; //cms核心page
	private static Map<String, HtmlPage> pluginPageMap = null; //插件page

	public static Connection getConn(int flg) throws NamingException,
			SQLException, ClassNotFoundException {
		Connection conn = ds.getConnection();
		if (flg == Constant.CONNECTION_READONLY) {
			conn.setReadOnly(true);
		} else {
			conn.setReadOnly(false);
			if (flg == Constant.CONNECTION_TRANSZACTION) {
				conn.setAutoCommit(false);
			} else {
				conn.setAutoCommit(true);
			}
		}
		return conn;
	}
	public static void initConnection() throws PropertyVetoException {
		ds = new ComboPooledDataSource(); 
	    ds.setDriverClass("com.mysql.jdbc.Driver");
	    String jdbcUrl = InitServlet.MYSQL_DB_IP + ":" + InitServlet.MYSQL_DB_PORT + "/" + InitServlet.MYSQL_DB_NAME;
	    ds.setJdbcUrl("jdbc:mysql://" + jdbcUrl + "?useUnicode=true&characterEncoding=utf-8&autoreconnect=true"); 
	    ds.setUser(InitServlet.MYSQL_DB_USER); 
	    ds.setPassword(InitServlet.MYSQL_DB_PASSWD); 
	    ds.setMaxPoolSize(150);
	    ds.setMinPoolSize(10);
	}
	public static void setPageMap(Map<String, HtmlPage> pageMap) {
		PubFun.pageMap = pageMap;
	}
	public static Map<String, HtmlPage> getPageMap() {
		return pageMap;
	}
	public static Map<String, HtmlPage> getPluginPageMap() {
		return pluginPageMap;
	}
	public static void setPluginPageMap(Map<String, HtmlPage> pluginPageMap) {
		PubFun.pluginPageMap = pluginPageMap;
	}
	/*********** ajax字符串输出开始 ****************/
	public static void ajaxPrintStr(String ajaxStr, HttpServletResponse resp) throws IOException {
		PrintWriter pw = resp.getWriter();
		pw.print("cmsinfo:alert('" + getHtmlFilterValue(ajaxStr) + "');");
		pw.close();
	}
	public static void ajaxPrint(String ajaxStr, HttpServletResponse resp) throws IOException  {
		PrintWriter pw = resp.getWriter();
		pw.print("<script language=\"javascript\">" + ajaxStr + "</script>");
		pw.close();
	}	
	public static void ajaxPrintC(String ajaxStr, HttpServletResponse resp) throws IOException {
		PrintWriter pw = resp.getWriter();
		pw.print(ajaxStr);
		pw.close();
	}
	public static void ajaxPrintUpStr(String ajaxStr, HttpServletResponse resp)
	throws IOException {
		PrintWriter pw = resp.getWriter();
		pw.print("<script language=\"javascript\">parent." + ajaxStr + "</script>");
		pw.close();
	}
	public static String getHtmlFilterValue(String str) {
		if (str != null) {
			str = str.replaceAll("\\\\", "\\\\\\\\");
			str = str.replaceAll("\n", "\\\\n");
			str = str.replaceAll("\r", "\\\\r");
			str = str.replaceAll("\\'", "\\\\'");
		}
		return str;
	}
	/*********** ajax字符串输出结束   ****************/
	/***********   处理日期字符串开始   ****************/
	public static String getDateTime(String format, Date date) {
		String result = null;
		Calendar c = Calendar.getInstance();
		if(date != null) c.setTime(date);
		SimpleDateFormat f = new SimpleDateFormat(format);
		result = f.format(c.getTime());
		return result;
	}
	
	public static String getDateString(long dateTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
		Date d=new Date(dateTime);
		String dateStr = sdf.format(d);
		return dateStr;
	}
	
	public static Date getDateByString(String format, String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);			
		return sdf.parse(dateStr);
	}
	/***********   处理日期字符串结束   ****************/
	/***********   上传文件开始   ****************/
	public static void saveAs(File upFile, String filePath) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(upFile);
			int len = fis.available();
			byte[] by = new byte[len];
			fis.read(by);
			fos = new FileOutputStream(filePath);
			fos.write(by);
		} catch (IOException e) {
			throw new Exception("系统配置模块中：《服务器代码真实路径》参数设置有误。");
		} finally {
			if (fos != null){
				fos.flush();
				fos.close();
			}
			if (fis != null)
				fis.close();
		}
	}
	/***********   上传文件结束   ****************/	
	
	/***********   文件读取开始    ****************/	
	public static String getFileContent(String path,String name) throws Exception {
		File file = new File(path+"/"+name);
		FileInputStream in = null;
	    byte[] content = null;
		String contents = "";
		if(file.exists()&&!file.isDirectory()){
			try {
			    in = new FileInputStream(file);	
			    content = new byte[in.available()];
			    in.read(content);
			    contents = new String(content,"utf-8");
			} catch (IOException e) {
				throw new Exception("系统配置模块中：《服务器代码真实路径》参数设置有误。");
			}finally{
				if(in!=null)
				in.close();
			}
		}
		return contents;
		
	}
	
	public static void writeFile(String path,String name,String content) throws Exception {	
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"/"+name),"UTF-8"));
		out.write(content);
		out.close();
	}
	/***********   文件读取结束   ****************/	
	public static String getJsFilterValue(String str) {
		if (str != null) {
			str = str.replaceAll(InitServlet.WEB_SITE_URL, "<51java:webroot/>");
			str = str.replaceAll("</textarea>", "<fm:/textarea>");
			str = str.replaceAll("</TEXTAREA>", "<fm:/textarea>");
			str = str.replaceAll("&lt;/textarea&gt;", "fm:textarea");
			str = str.replaceAll("&lt;/TEXTAREA&gt;", "fm:textarea");
		}
		return str;
	}
	
	public static String setJsFilterValue(String str) {
		if (str != null) {
			str = str.replaceAll("<51java:webroot/>", InitServlet.WEB_SITE_URL);
			str = str.replaceAll("<fm:/textarea>", "</TEXTAREA>");
			str = str.replaceAll("<fm:/textarea>", "</textarea>");
		    str = str.replaceAll("fm:textarea", "&lt;/textarea&gt;");
			str = str.replaceAll("<fm:/textarea>", "</textarea>");
		}
		return str;
	}
	
	public static String getFilterValue(String str) {
		if (str != null) {
			str = str.replaceAll("<fm:/textarea>", "</TEXTAREA>");
			str = str.replaceAll("<fm:/textarea>", "</textarea>");
		    str = str.replaceAll("fm:textarea", "&lt;/textarea&gt;");
			str = str.replaceAll("<fm:/textarea>", "</textarea>");
		}
		return str;
	}
	
	//将存入数据库的文章中的图片地址用统一符号替换
	public static String getDbContentFliterValue(String str){
		if(str!=null&&!str.equals("")){
			str = str.replaceAll(InitServlet.WEB_SITE_URL, "<51java:webroot/>");
		}
		return str;
	}
	
	public static String getArContentValue(String str){
		if(str!=null&&!str.equals("")){
			str = str.replaceAll("<51java:webroot/>", InitServlet.WEB_SITE_URL);
		}
		return str;
		
	}

	/***********   生成缩略图   ****************/	
		/** 
	     * 对图片裁剪，并把裁剪完的新图片保存 。 
	     */  
	public static void cut(String srcpath ,String subpath ,int x, int y, int width, int height) throws IOException {
	    	 /** 
             * @param srcpath		源图片路径名称如:c:\1.JPG   
             * @param subpath       剪切图片存放路径名称.如:c:\2.JPG   
             * @param x				剪切点x坐标
             * @param y				剪切点y坐标
             * @param width			剪切点宽度
             * @param height		剪切点高度  
             */
	    	FileInputStream is = null;  
	        ImageInputStream iis = null;
	        String imagestyle=srcpath.substring(srcpath.lastIndexOf(".")+1);
	        try {
	        	/** 读取图片文件 **/
	            is = new FileInputStream(srcpath);
	            
	            /** 
	             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。 
	             * 参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "tiff"）等 。 
	             */
	            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(imagestyle);
	            
	            ImageReader reader = it.next();
	            /** 获取图片流 **/
	            iis = ImageIO.createImageInputStream(is);
	            
	            /** 
	             * iis:读取源.true:只向前搜索.将它标记为 ‘只向前搜索’。 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 
	             * reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。 
	             */
	            reader.setInput(iis, true);
	            /** 
	             * <p> 
	             * 描述如何对流进行解码的类 
	             * <p> 
	             * .用于指定如何在输入时从 Java Image I/O 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 
	             * ImageReader 实现的 getDefaultReadParam 方法中返回 ImageReadParam 的实例。 
	             */
	            ImageReadParam param = reader.getDefaultReadParam();
	            /** 
	             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象 
	             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。 
	             */
	            Rectangle rect = new Rectangle(x, y, width, height);
	            /** 提供一个 BufferedImage，将其用作解码像素数据的目标。**/
	            param.setSourceRegion(rect);
	            /** 
	             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的 
	             * BufferedImage 返回。 
	             */
	            BufferedImage bi = reader.read(0, param);
	            /** 保存新图片 **/ 
	            ImageIO.write(bi,imagestyle, new File(subpath));
	            }
	        finally {
	        	if (is != null)
	            	is.close();
	            if (iis != null)
	            	iis.close();
	            }
	        }
	
	// 从words.properties过滤文章敏感字
	public static String filterWords(String content) throws IOException {
		StringBuilder patternBuf = new StringBuilder("");
		File file = new File(InitServlet.CONTENT_REALPATH, "words.properties");
		InputStream in = new FileInputStream(file);
		Properties pro = new Properties();
		pro.load(in);
		Enumeration<?> enu = pro.propertyNames();
		patternBuf.append("(");
		while (enu.hasMoreElements()) {
			patternBuf.append((String) enu.nextElement() + "|");
		}
		patternBuf.deleteCharAt(patternBuf.length() - 1);
		patternBuf.append(")");
		Pattern pattern = Pattern.compile(new String(patternBuf.toString()
				.getBytes("ISO-8859-1"), "UTF-8"));
		Matcher m = pattern.matcher(content);
		content = m.replaceAll("");
		return content;
	}
	
	/***上传图片水印开始***/
	public static boolean pressText(File oImage, String newImageName, String fileType) throws IOException{
        BufferedImage originalImage = ImageIO.read(oImage);
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);
        if (originalWidth < 50 || originalHeight < 50){
            return false;
        }
        if(InitServlet.WATER_TEXT==null || InitServlet.WATER_TEXT.trim().equals("")){
        	return false;
        }
        BufferedImage newImage = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(originalImage, 0, 0, originalWidth, originalHeight, null);
        g.setColor(Color.RED);
        int fontSize = 13;
        g.setFont(new Font("宋体", Font.PLAIN, fontSize)); 
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
        int len = InitServlet.WATER_TEXT.length();
        if(InitServlet.IS_WATER_CENTER==0){
        	g.drawString(InitServlet.WATER_TEXT, originalWidth-len*fontSize/2-6,originalHeight-6);
        }else{
        	g.drawString(InitServlet.WATER_TEXT, (originalWidth-(len*fontSize))/2, (originalHeight-fontSize)/2);
        }
        g.dispose();
        saveImage(newImage, newImageName, fileType);
        return true;
    }    
	public static boolean pressImage(File oImage, String newImageName, String fileType) throws IOException{
        File waterMarkImage = new File(InitServlet.CONTENT_REALPATH + "img/watermark.gif");
        if (!waterMarkImage.exists()) {
        	return false;
        }
        BufferedImage originalImage = ImageIO.read(oImage);
        BufferedImage watermarkImage = ImageIO.read(waterMarkImage);
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);
        int watermarkWidth = watermarkImage.getWidth(null);
        int watermarkHeight = watermarkImage.getHeight(null);
        if (originalWidth <= watermarkWidth || originalHeight <= watermarkHeight || originalWidth < 50 || originalHeight < 50) {
        	return false;
        }
        BufferedImage newImage = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(originalImage, 0, 0, originalWidth, originalHeight, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f)); 
        if(InitServlet.IS_WATER_CENTER==0){
        	g.drawImage(watermarkImage, originalWidth-watermarkWidth-10, originalHeight-watermarkHeight-10, watermarkWidth, watermarkHeight, null);
        }else{
        	g.drawImage(watermarkImage, (originalWidth-watermarkWidth)/2, (originalHeight-watermarkHeight)/2, watermarkWidth, watermarkHeight, null);
        }
        g.dispose();
        saveImage(newImage, newImageName, fileType);
        return true;
    }	
    private static void saveImage(BufferedImage bi, String savePath, String fileType) throws IOException {
        FileOutputStream out = new FileOutputStream(savePath);
        ImageIO.write(bi, fileType, out);// 存盘
        out.flush();
        out.close();
    }
    /***上传图片水印结束***/
    // 去掉所有html元素
    private static String splitAndFilterString(String input) {  
        if (input == null || input.trim().equals("")) {  
            return "";  
       }  
    String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
              "<[^>]*>", "");  
      str = str.replaceAll("[(/>)<]", "");  
       return str;  
   }
 // 得到lucene的docment内容
    public static Document getLuceneDoc(Article article) {
		Document document = new Document(); 
		Field fieldId = new Field("id", String.valueOf(article.getId()), Field.Store.YES, 
		        Field.Index.NOT_ANALYZED);
		Field fieldTitle = new Field("title", article.getTitle(), Field.Store.YES, 
		        Field.Index.ANALYZED,
		        Field.TermVector.WITH_POSITIONS_OFFSETS);  
		Field fieldNote = new Field("note", article.getNote(), Field.Store.YES,  
		        Field.Index.ANALYZED,
		        Field.TermVector.WITH_POSITIONS_OFFSETS);
		Field fieldContent = new Field("content", splitAndFilterString(article.getContent()), Field.Store.YES,  
		        Field.Index.ANALYZED,
		        Field.TermVector.WITH_POSITIONS_OFFSETS); 
		Calendar c = Calendar.getInstance();
		c.setTime(article.getCreatime());
		Field fieldYear = new Field("year", String.valueOf(c.get(Calendar.YEAR)),
				Field.Store.YES,Field.Index.NO);
		Field fieldMonth = new Field("month", String.valueOf(c.get(Calendar.MONTH)+1),
				Field.Store.YES,Field.Index.NO);
		Field fieldDay = new Field("day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)),
				Field.Store.YES,Field.Index.NO);
		Field fieldUrl = new Field("url", InitServlet.WEB_SITE_URL + article.getHtmlPath()
				+ "/" + PubFun.getDateTime("yyyy-MM-dd", article.getCreatime())
				+ "/" + article.getId()+".html", Field.Store.YES,
				Field.Index.NO);
		Field fieldAuthor = new Field("author", article.getAuthor()==null?"":article.getAuthor(),
				Field.Store.YES, Field.Index.NO);
		document.add(fieldId);
		document.add(fieldTitle); 
		document.add(fieldNote); 
		document.add(fieldAuthor); 
		document.add(fieldContent);
		document.add(fieldUrl);
		document.add(fieldYear);
		document.add(fieldMonth);
		document.add(fieldDay);
		return document;
	}

}
