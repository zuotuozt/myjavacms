package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import page.article.*;
import page.col.*;
import page.config.*;
import page.db.*;
import page.file.*;
import page.user.*;
import page.fragment.*;
import page.message.*;
import page.publish.*;
import page.plugin.*;
import page.plugin.feeling.*;
import page.plugin.message.*;
import page.inc.HtmlPage;
import util.PubFun;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static String CONTENT_REALPATH; //服务器程序代码的真实根目录
	
	/** 从配置文件 ：config.properties文件中读取系统参数 开始 **/
	//数据库配置参数
	public static String MYSQL_DB_IP = "127.0.0.1"; //mysql数据库ip地址
	public static String MYSQL_DB_PORT = "3306"; //mysql数据库端口
	public static String MYSQL_DB_NAME = "51javacms"; //mysql数据库名
	public static String MYSQL_DB_USER = "root";
	public static String MYSQL_DB_PASSWD = ""; //mysql数据库名
	public static String MYSQL_DB_PATH = "D:/wamp/bin/mysql/mysql5.1.32/bin";//mysql数据库程序目录
	//cms系统配置参数
	public static String WEB_SITE_URL = "http://127.0.0.1/cms";//网站地址url
	public static String WEB_SITE_PATH = "d:/cms";//服务器网站的真实目录
	public static String WEB_SITE_NAME = "";//网站名称
	public static int PAGE_SIZE = 15;// 分页显示条数
	public static String INIT_PASSWD = "123456";// 用户初始化密码
	public static int DOWNLOAD_CNT = 0;// 安装程序下载次数
	public static int MESSAGE_PAGE_SIZE = 6;// 网站留言板和搜索分页显示条数
	//图片水印配置参数
	public static int IS_WATER = 0;
	public static int IS_WATER_PIC = 1;
	public static int IS_WATER_CENTER = 1;
	public static String WATER_TEXT = "51javacms";
	/** 从配置文件 ：config.ini中读取系统参数 结束 **/
	
	public void init() {
		try {
			CONTENT_REALPATH = getServletContext().getRealPath("/");
			File iniFile = new File(CONTENT_REALPATH + "config.properties");
			if(iniFile.exists()){
				getProperty();
				PubFun.initConnection();
				setPageMap();
				setPluginPageMap();
			}else{
				System.out.println( "系统配置文件（config.properties）未找到。"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setPageMap() {//cms模块
		//切记page类中不能写类变量；否则会发生线程冲突
		
		Map<String, HtmlPage> pageMap = new HashMap<String, HtmlPage>();
		/**** 用户模块开始 ***/
		pageMap.put("LoginPage", new LoginPage());
		pageMap.put("LogoutPage", new LogoutPage());
		pageMap.put("ChangePasswordPage", new ChangePasswordPage());
		pageMap.put("UserManagePage", new UserManagePage());
		pageMap.put("InsertUserInfoPage", new InsertUserInfoPage());
		pageMap.put("UserInfoPage", new UserInfoPage());
		pageMap.put("EditUserInfoPage", new EditUserInfoPage());
		pageMap.put("DeleteUserInfoPage", new DeleteUserInfoPage());
		pageMap.put("DeleteCheckedUsersPage", new DeleteCheckedUsersPage());
		pageMap.put("DepManagePage", new DepManagePage());
		pageMap.put("EditDepartmentPage", new EditDepartmentPage());
		pageMap.put("DelDepartmentPage", new DelDepartmentPage());
		pageMap.put("CheckUserNamePage", new CheckUserNamePage());
		pageMap.put("InitPasswordPage", new InitPasswordPage());
		/**** 用户模块结束 ***/

		/**** 文件模块开始 ***/
		pageMap.put("EditFile", new EditFile());
		pageMap.put("ShowEditFile", new ShowEditFile());
		pageMap.put("FileListPage", new FileListPage());
		pageMap.put("ToCreateFilePage", new ToCreateFilePage());
		pageMap.put("CreatePathPage", new CreatePathPage());
		pageMap.put("FileManagePage", new FileManagePage());
		pageMap.put("ToFileManagePage", new ToFileManagePage());
		pageMap.put("IndexPictureFileList", new IndexPictureFileList());
		/**** 文件模块结束 ***/

		/**** 配置管理模块开始 ***/
		pageMap.put("ConfigManagePage", new ConfigManagePage());
		pageMap.put("EditWordsPage", new EditWordsPage());
		pageMap.put("ShowEditWordsPage", new ShowEditWordsPage());
		pageMap.put("EditWaterMarkPage", new EditWaterMarkPage());
		pageMap.put("UploadWaterPage", new UploadWaterPage());
		/**** 配置管理模块结束 ***/
		
		/**** 文章发布模块开始 ***/
		pageMap.put("PublishIndexPage", new PublishIndexPage());
		pageMap.put("PublishArticlesPage", new PublishArticlesPage());
		/**** 文章发布模块结束 ***/

		/**** 栏目管理模块开始 new ***/
		pageMap.put("ShowColEditPage", new ShowColEditPage());
		pageMap.put("ColEditPage", new ColEditPage());
		pageMap.put("ColManagePage", new ColManagePage());
		pageMap.put("ColLeftPage", new ColLeftPage());
		pageMap.put("TreePage", new TreePage());
		pageMap.put("TreeLeftPage", new TreeLeftPage());
		pageMap.put("DelColPage", new DelColPage());
		pageMap.put("PreviewPage", new PreviewPage());
		pageMap.put("StaticPage", new StaticPage());
		pageMap.put("ViewPage", new ViewPage());
		pageMap.put("StaticArticlesPage", new StaticArticlesPage());
		pageMap.put("StaticColumnsPage", new StaticColumnsPage());
		/**** 栏目管理模块结束 new ***/

		/**** 数据库管理模块开始 ***/
		pageMap.put("LoadPage", new LoadPage());
		pageMap.put("BackUpPage", new BackUpPage());
		pageMap.put("DownLoadPage", new DownLoadPage());
		/**** 数据库管理模块开始 ***/

		/**** 文章管理模块开始 ***/
		pageMap.put("ArticleManagePage", new ArticleManagePage());
		pageMap.put("ShowEditArticlePage", new ShowEditArticlePage());
		pageMap.put("EditArticlePage", new EditArticlePage());
		pageMap.put("DeleteArticlePage", new DeleteArticlePage());
		pageMap.put("DeleteCheckedArticlesPage", new DeleteCheckedArticlesPage());
		pageMap.put("StaticArticlePage", new StaticArticlePage());
		pageMap.put("StaticArticleListPage", new StaticArticleListPage());
		pageMap.put("ArticleThumbnailsPage", new ArticleThumbnailsPage());
		pageMap.put("SearchArticlePage", new SearchArticlePage());
		pageMap.put("AdvancedSearchPage", new AdvancedSearchPage());
		/**** 文章管理模块结束 ***/

		/**** 广告模块开始 ***/
		pageMap.put("FragmentManagePage", new FragmentManagePage());
		pageMap.put("EditFragmentPage", new EditFragmentPage());
		pageMap.put("ShowEditFragmentPage", new ShowEditFragmentPage());
		pageMap.put("DeleteFragmentPage", new DeleteFragmentPage());
		pageMap.put("DeleteCheckedFragmentPage", new DeleteCheckedFragmentPage());
		pageMap.put("ShowJsFragmentPage", new ShowJsFragmentPage());
		pageMap.put("GenerateAllFragments", new GenerateAllFragments());
		/**** 广告模块结束 ***/
		
		/**** 上传文件开始 ***/
		pageMap.put("UploadFilePage", new UploadFilePage());
		pageMap.put("LoadPage", new LoadPage());
		pageMap.put("UploadIndexPicturePage", new UploadIndexPicturePage());
		/**** 上传文件结束 ***/
		
		/***留言板开始****/
		pageMap.put("MessageManagePage", new MessageManagePage());
		pageMap.put("ReplyMessagePage", new ReplyMessagePage());
		pageMap.put("ShowReplyMessagePage", new ShowReplyMessagePage());
		pageMap.put("DeleteMessagePage", new DeleteMessagePage());
		pageMap.put("DeleteCheckedMessagesPage", new DeleteCheckedMessagesPage());
		/***留言板结束****/
		PubFun.setPageMap(pageMap);
	}
	
	private void setPluginPageMap() {//插件模块
		//切记page类中不能写类变量；否则会发生线程冲突
		
		Map<String, HtmlPage> pluginPageMap = new HashMap<String, HtmlPage>();

		pluginPageMap.put("SearchPage", new SearchPage());//lucnen搜索
		pluginPageMap.put("DownLoadFilePage", new DownLoadFilePage());//下载文件
		/***文章表情开始****/
		pluginPageMap.put("LoadFeelingPage", new LoadFeelingPage());
		pluginPageMap.put("UpdateFeelingPage", new UpdateFeelingPage());
		/***文章表情结束****/
		/***留言板开始****/
		pluginPageMap.put("PublishMessagePage", new PublishMessagePage());
		pluginPageMap.put("ShowMessagePage", new ShowMessagePage());
		/***留言板结束****/
		pluginPageMap.put("LoadBrowseCntPage", new LoadBrowseCntPage());//显示文章浏览次数

		PubFun.setPluginPageMap(pluginPageMap);
	}
	
  //得到配置文件 ：config.properties的内容
   private static void getProperty(){ 
           Properties prop = new Properties(); 
           try{
   				prop.load(new InputStreamReader(new FileInputStream(InitServlet.CONTENT_REALPATH 
					+ "config.properties"), "utf-8"));
        	   getSystemParms(prop);
           }catch(FileNotFoundException e){ 
               System.out.println( "系统配置文件（config.properties）未找到。"); 
           }catch(IOException e){ 
               e.getMessage(); 
           } 
   }

	public static void getSystemParms(Properties prop)
			throws UnsupportedEncodingException {
	    //数据库配置参数
		   MYSQL_DB_IP = prop.getProperty("MYSQL_DB_IP");
		   MYSQL_DB_PORT = prop.getProperty("MYSQL_DB_PORT");
		   MYSQL_DB_NAME = prop.getProperty("MYSQL_DB_NAME");
		   MYSQL_DB_USER = prop.getProperty("MYSQL_DB_USER");
		   MYSQL_DB_PASSWD = prop.getProperty("MYSQL_DB_PASSWD");
		   MYSQL_DB_PATH = prop.getProperty("MYSQL_DB_PATH");
		   
		   //cms系统配置参数
		   WEB_SITE_URL = prop.getProperty("WEB_SITE_URL");
		   WEB_SITE_PATH = prop.getProperty("WEB_SITE_PATH");
		   WEB_SITE_NAME = prop.getProperty("WEB_SITE_NAME");
		   PAGE_SIZE = Integer.valueOf(prop.getProperty("PAGE_SIZE"));
		   INIT_PASSWD = prop.getProperty("INIT_PASSWD");
		   DOWNLOAD_CNT = Integer.valueOf(prop.getProperty("DOWNLOAD_CNT"));
		   MESSAGE_PAGE_SIZE = Integer.valueOf(prop.getProperty("MESSAGE_PAGE_SIZE"));
		   
			//图片水印配置参数
			IS_WATER = Integer.valueOf(prop.getProperty("IS_WATER"));
			IS_WATER_PIC = Integer.valueOf(prop.getProperty("IS_WATER_PIC"));
			IS_WATER_CENTER = Integer.valueOf(prop.getProperty("IS_WATER_CENTER"));
			WATER_TEXT = prop.getProperty("WATER_TEXT");
	}

}