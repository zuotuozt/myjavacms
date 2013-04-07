package util;

public class Constant {
	/***数据库连接类型****/
	public static final int CONNECTION_READONLY = 0; //selcet sql查询（速度快）
	public static final int CONNECTION_NONE_TRANSZACTION = 1; //update sql（速度快）
	public static final int CONNECTION_TRANSZACTION = 2; //update 多条sql（速度慢）

	/***信息页面*** */
	public static final String REDIRECT_LOGIN_PAGE = "MainCtrl?page=LogoutPage"; //doGet到登录页面(response跳转)
	public static final String ORGINAL_LOGIN_PAGE = "/sign_in.jsp"; //doPost到登录页面(request跳转)
	public static final String VIEW_INFO_PAGE = "/view_info.jsp"; //插件模块中显示信息页面
	
	/***用户信息***/
	public final static String SUPER_USER = "admin";		//系统管理员
	public final static String CHIEF_EDITOR = "chief_editor";	//总编辑
	
	/***栏目信息***/
	public final static int ARTICLES_CLOUMN = 0;		//最终列表栏目（在本栏目发布文档，并生成文档列表）
	public final static int COVER_CLOUMN = 1;			//栏目封面（栏目本身不允许发布文档）
	public final static int EXTERNAL_LINK_CLOUMN = 2;   //外部连接（填写链接网址）
	
	public final static int TOP_CLOUMN_TREE = 0;		//查顶级栏目树
	public final static int HOME_CLOUMN = 1;		    //首页栏目树
	
	public final static int PREVIEW = 0;			            //预览静态化页面
	public final static int STATIC = 1;				            //发布静态化页面
	public final static int VIEW = 2;                           //查看静态化页面
	public final static String TEMPLATE_BASEPATH = "template";	//模板文件的根目录
	public final static String SEARCH_INDEX_PATH = InitServlet.CONTENT_REALPATH + "lucene";//lucene站内搜索的索引文件位置
	
	/***其它信息***/
	public final static String FRAGMENT_PATH = "/fragment";	//碎片根目录
	public final static String UPLOAD_PATH = "/upload"; //上传文件根目录
	public final static String DOWNLOAD_PATH = "/download"; //下载文件根目录
	public final static String DB_PATH = "/db"; //数据库根目录

	/***文件信息***/
	public final static int FILE_PATH_TYPE = 0; //文件管理
	public final static int TEMPLATE_PATH_TYPE = 1; //模板管理

}
