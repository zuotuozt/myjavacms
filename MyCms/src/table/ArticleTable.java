package table;

import java.sql.Connection;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import table.inc.DBTable;
import util.InitServlet;
import util.PubFun;
import javabean.Article;

//文章表cms_article
public class ArticleTable extends DBTable {
	
	/*****************新*****************/
	public static Article[] loadWholeArticlesByCols(String cols,
													String dateStr,
													int pageBegin,
													int pageSize) throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		String sql = "select a.id,creatime,title,author,a.content,b.html_path,b.article_template," +
				"b.id,b.col_name,b.seo_title,b.seo_keywords,b.seo_description,source,a.note" +
				" from cms_article a,cms_column b where a.col_id=b.id and b.id in (" 
				+ cols + ") " + dateStr + " limit ?,?";
		Object[] args = new Object[3];
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadWholeArticlesByCols has error";
		
		Object[] params = new Object[2];
		params[0] = pageBegin;
		params[1] = pageSize;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			Article article = new Article();
			article.setId((Long)list2[0]);
			article.setCreatime((Date)list2[1]);
			article.setTitle((String)list2[2]);
			article.setAuthor((String)list2[3]);
			article.setContent((String)list2[4]);
			//得到文章的真实访问的url地址
			String htmlPath = (String)list2[5];
			article.setHtmlPath(htmlPath);
			if(htmlPath != null && !htmlPath.equals("")){
				htmlPath = InitServlet.WEB_SITE_URL + htmlPath + "/" 
						+ PubFun.getDateTime("yyyy-MM-dd", (Date)list2[1]) +
						"/" + (Long)list2[0] + ".html";
				article.setUrl(htmlPath);
			}
			article.setArticleTemplate((String)list2[6]);
			article.setColumnid((Integer)list2[7]);
			article.setColumnname((String)list2[8]);
			article.setSeoTitle((String)list2[9]);
			article.setSeoKeywords((String)list2[10]);
			article.setSeoDescription((String)list2[11]);
			article.setSource((String)list2[12]);
			article.setNote((String)list2[13]);
			articleList.add(article);
		}	

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}
	
	//所有指定日期之后的文章
	public static Article[] loadWholeArticlesByCreatetime(int columnId,String dateTime) throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		String sql = "select a.id,creatime,title,author,a.content,b.html_path,b.article_template from " +
			"cms_article a, cms_column b where a.col_id=b.id and b.id=? and creatime > ?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadWholeArticlesByColumnId has error";
		
		Object[] params = new Object[2];
		params[0] = columnId;
		params[1] = dateTime;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			Article article = new Article();
			article.setId((Long)list2[0]);
			article.setCreatime((Date)list2[1]);
			article.setTitle((String)list2[2]);
			article.setAuthor((String)list2[3]);
			article.setContent((String)list2[4]);
			article.setHtmlPath(((String)list2[5]));
			article.setArticleTemplate((String)list2[6]);
			articleList.add(article);
		}	

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}
	
	public static boolean isExistsById(long articleId) throws Exception {
		boolean isExists = false;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_article where id=?";
		args[PARAM_ERROR] = "ArticleTable============isExistsById has error";

		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		if(lists.size() > 0){
			isExists = true;
		}
		return isExists;
	}
	
	public static Article loadArticleForModify(long articleId) throws Exception {
		String sql = "select a.col_id,creatime,title,author,content,b.html_path,b.article_template,source,note," +
				"picture,is_top from cms_article a,cms_column b where a.col_id=b.id and a.id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadArticleForModify has error";
		
		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		Article article = null;
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setId(articleId);
			article.setColumnid((Integer)list2[0]);
			article.setCreatime((Date)list2[1]);
			article.setTitle((String)list2[2]);
			article.setAuthor((String)list2[3]);
			article.setContent((String)list2[4]);
			article.setHtmlPath((String)list2[5]);
			article.setArticleTemplate((String)list2[6]);
			article.setSource((String)list2[7]);
			article.setNote((String)list2[8]);
			article.setPicture((String)list2[9]);
			article.setTop((Boolean)list2[10]);
		}	

		return article;
	}
	
	public static int loadCntByTitle(int columnId, String title, int userId) throws Exception {
		int num = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select count(id) from cms_article where col_id=? and title like ? and ? in (1,2,creator)";
		args[PARAM_ERROR] = "ArticleTable============loadCntByTitle has error";
		
		Object[] params = new Object[3];
		params[0] = columnId;
		params[1] = "%" + title + "%";
		params[2] = userId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			num = ((Long)list2[0]).intValue();
		}
		return num;
	}
//文章列表页	
	public static Article[] loadArticles(int columnId,
			String title,
			int pageNo,
			int userId) throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		String sql ="select a.id,title,creatime,case b.alias when '' then b.name else b.alias end," +
				"is_top,ordercnt from cms_article a,cms_user b where b.id=creator and ? in (1,2,b.id) " +
				"and col_id=? and title like ? order by is_top desc,ordercnt desc,a.id desc limit ?,?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadArticles has error";
		
		Object[] params = new Object[5];
		params[0] = userId;
		params[1] = columnId;
		params[2] = "%" + title + "%";
		params[3] = (pageNo - 1) * InitServlet.PAGE_SIZE;
		params[4] = InitServlet.PAGE_SIZE;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		Article article = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setId((Long)list2[0]);
			article.setTitle((String)list2[1]);
			article.setCreatime((Date)list2[2]);
			article.setCreatorName((String)list2[3]);
			article.setTop((Boolean)list2[4]);
			article.setOrdercnt((Integer)list2[5]);
			articleList.add(article);
		}		

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}

	private static Object[] getInsertArticleArgs(Article article) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "insert into cms_article(title,author,content,creatime,col_id,note,source," +
				"picture,creator,is_top,ordercnt) values(?,?,?,now(),?,?,?,?,?,?,?)";
		args[PARAM_ERROR] = "ArticleTable===============insertArticle has error";

		Object[] params = new Object[10];
		params[0] = article.getTitle();
		params[1] = article.getAuthor();
		params[2] = article.getContent();
		params[3] = article.getColumnid();
		params[4] = article.getNote();
		params[5] = article.getSource();
		params[6] = article.getPicture();
		params[7] = article.getCreator();
		params[8] = article.isTop();
		params[9] = article.getOrdercnt();
		args[PARAM_ARGS] = params;
		return args;
	}
	
	public static void insertArticle(Article article) throws Exception {
		Object[] args = getInsertArticleArgs(article);
		update(args);
	}
	
	public static void insertTransArticle(Connection conn, Article article) throws Exception {
		Object[] args = getInsertArticleArgs(article);
		updateTranscation(conn, args);
	}
	
	private static Object[] getUpdateArticleArgs(Article article) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_article set title=?,author=?,content=?,note=?," +
						"source=?,picture=?,col_id=?,is_top=?,ordercnt=? where id=?";
		args[PARAM_ERROR] = "ArticleTable===============updateArticle has error";
		Object[] params = new Object[10];
		params[0] = article.getTitle();
		params[1] = article.getAuthor();
		params[2] = article.getContent();
		params[3] = article.getNote();
		params[4] = article.getSource();
		params[5] = article.getPicture();
		params[6] = article.getColumnid();
		params[7] = article.isTop();
		params[8] = article.getOrdercnt();
		params[9] = article.getId();
		args[PARAM_ARGS] = params;
		
		return args;
	}
	
	public static void updateArticle(Article article) throws Exception {
		Object[] args = getUpdateArticleArgs(article);
		update(args);
	}
	
	public static void updateTransArticle(Connection conn, Article article) throws Exception {
		Object[] args = getUpdateArticleArgs(article);
		updateTranscation(conn, args);
	}
    //取消所有文章栏目置顶	
	public static void cancelTop(Connection conn, int columnId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_article set is_top=0 where is_top=1 and col_id=?";
		args[PARAM_ERROR] = "ArticleTable===============cancelTop has error";
		
		Object[] params = new Object[1];
		params[0] = columnId;
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}
	
	public static void delArticle(Connection conn, long articleId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "delete from cms_article where id=?";
		args[PARAM_ERROR] = "ArticleTable===============delArticle has error";

		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}
	
	public static Article loadArticleForDelete(long articleId) throws Exception {
		Article article = null;
		
		String sql = "select creatime,b.html_path from cms_article a,cms_column b where a.col_id=b.id and a.id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadArticleForDelete has error";
		
		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setCreatime((Date)list2[0]);
			article.setHtmlPath((String)list2[1]);
		}	

		return article;
	}
	
	public static Article loadArticleForStaticPage(long articleId) throws Exception {
		Article article = null;
		
		String sql = "select creatime,b.html_path from cms_article a,cms_column b where a.col_id=b.id and a.id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadArticleForStaticPage has error";
		
		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setCreatime((Date)list2[0]);
			article.setHtmlPath((String)list2[1]);
		}	

		return article;
	}
	
	public static Article loadWholeArticleById(long articleId) throws Exception {
		Article article = null;
		
		String sql = "select title,creatime,a.content,a.author,b.html_path,b.article_template," +
				"b.id,source,note,b.col_name,b.seo_title,b.seo_keywords,b.seo_description" +
				" from cms_article a,cms_column b where a.col_id=b.id and a.id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadWholeArticleById has error";
		
		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setId(articleId);
			article.setTitle((String)list2[0]);
			article.setCreatime((Date)list2[1]);
			article.setContent((String)list2[2]);
			article.setAuthor((String)list2[3]);
			//得到文章的真实访问的url地址
			String htmlPath = (String)list2[4];
			article.setHtmlPath(htmlPath);
			if(htmlPath != null && !htmlPath.equals("")){
				htmlPath = InitServlet.WEB_SITE_URL + htmlPath + "/" 
						+ PubFun.getDateTime("yyyy-MM-dd", (Date)list2[1]) +
						"/" + articleId + ".html";
				article.setUrl(htmlPath);
			}
			article.setArticleTemplate((String)list2[5]);
			article.setColumnid((Integer)list2[6]);
			article.setSource((String)list2[7]);
			article.setNote((String)list2[8]);
			article.setColumnname((String)list2[9]);
			article.setSeoTitle((String)list2[10]);
			article.setSeoKeywords((String)list2[11]);
			article.setSeoDescription((String)list2[12]);
		}	

		return article;
	}
	
	public static Article[] loadWholeArticleByIds(String articles) throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		
		String sql = "select a.id,title,creatime,a.content,a.author,b.html_path,b.article_template," +
				"b.id,source,note,b.col_name,b.seo_title,b.seo_keywords,b.seo_description" +
				" from cms_article a,cms_column b where a.col_id=b.id and a.id in("
			+ articles + ")";		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadWholeArticleByIds has error";
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			Article article = new Article();
			article.setId((Long)list2[0]);
			article.setTitle((String)list2[1]);
			article.setCreatime((Date)list2[2]);
			article.setContent((String)list2[3]);
			article.setAuthor((String)list2[4]);
			//得到文章的真实访问的url地址
			String htmlPath = (String)list2[5];
			article.setHtmlPath(htmlPath);
			if(htmlPath != null && !htmlPath.equals("")){
				htmlPath = InitServlet.WEB_SITE_URL + htmlPath + "/" 
						+ PubFun.getDateTime("yyyy-MM-dd", (Date)list2[2]) +
						"/" + (Long)list2[0] + ".html";
				article.setUrl(htmlPath);
			}
			article.setArticleTemplate((String)list2[6]);
			article.setColumnid((Integer)list2[7]);
			article.setSource((String)list2[8]);
			article.setNote((String)list2[9]);
			article.setColumnname((String)list2[10]);
			article.setSeoTitle((String)list2[11]);
			article.setSeoKeywords((String)list2[12]);
			article.setSeoDescription((String)list2[13]);
			articleList.add(article);
		}	

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}
	
	public static void delArticles(Connection conn, String articles) throws Exception {
		Object[] args = new Object[2];		
		args[PARAM_SQL] = "delete from cms_article where id in(" + articles + ")";
		args[PARAM_ERROR] = "ArticleTable===============delArticles has error";
		
		updateTranscation(conn, args);
	}
	
	public static Article[] loadSimpleArticleByIds(String articles) throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		
		String sql = "select a.id,creatime,b.html_path from " +
			"cms_article a, cms_column b where a.col_id=b.id and a.id in("
			+ articles + ")";		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadSimpleArticleByIds has error";
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			Article article = new Article();
			article.setId((Long)list2[0]);
			article.setCreatime((Date)list2[1]);
			article.setHtmlPath((String)list2[2]);
			articleList.add(article);
		}	

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}
	
	public static void delArticlesByColumnId(Connection conn, int columnId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "delete from cms_article where col_id=?";
		args[PARAM_ERROR] = "ArticleTable===============delArticlesByColumnId has error";

		Object[] params = new Object[1];
		params[0] = columnId;
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}
	//lucense定时搜索
	public static Article[] loadArticlesForSearchIndex() throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		String sql = "select a.id,title,creatime,note,b.html_path,a.content,a.author" +
				" from cms_article a,cms_column b where a.col_id=b.id";		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadArticlesForSearchIndex has error";
		List<Object> lists = select(args);	
		Article article = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setId((Long)list2[0]);
			article.setTitle((String)list2[1]);
			article.setCreatime((Date)list2[2]);
			String note = (String)list2[3];
			if(note==null) note="";
			article.setNote(note);
			article.setHtmlPath((String)list2[4]);
			article.setContent((String)list2[5]);
			article.setAuthor((String)list2[6]);
			articleList.add(article);	
		}

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}

	public static Article loadArticleForStatic(long articleId) throws Exception {
	Article article = null;
	
	String sql = "select title,author,creatime,content,picture,note from cms_article where id=?";		
	Object[] args = new Object[3];		
	args[PARAM_SQL] = sql;
	args[PARAM_ERROR] = "ArticleTable===============loadArticleForStatic has error";
	
	Object[] params = new Object[1];
	params[0] = articleId;
	args[PARAM_ARGS] = params;
	
	List<Object> lists = select(args);	
	for(Object list : lists){
		Object[] list2 = (Object[]) list;
		article = new Article();
		article.setId(articleId);
		article.setTitle((String)list2[0]);
		article.setAuthor((String)list2[1]);
		article.setCreatime((Date)list2[2]);
		article.setContent((String)list2[3]);
		article.setPicture((String)list2[4]);
		article.setNote((String)list2[5]);
	}	

	return article;
}
	public static int loadAllCnt(int columnId) throws Exception {
		int num = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select count(id) from cms_article where col_id=?";
		args[PARAM_ERROR] = "ArticleTable============loadAllCnt has error";
		
		Object[] params = new Object[1];
		params[0] = columnId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			num = ((Long)list2[0]).intValue();
		}
		return num;
	}
	
	public static long loadAllCntByUserId(int userId) throws Exception {
		long cnt = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select count(id) from cms_article where creator=?";
		args[PARAM_ERROR] = "ArticleTable============loadAllCntByUserId has error";
		
		Object[] params = new Object[1];
		params[0] = userId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			cnt = (Long)list2[0];
		}
		return cnt;
	}
	
	public static long loadLastIdById() throws Exception {
		long lastId = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select max(id) from cms_article";
		args[PARAM_ERROR] = "ArticleTable============loadLastIdById has error";
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			lastId = (Long)list2[0];
		}
		return lastId;
	}

//获得所有文章的总数
	public static long loadArticleSum(String searchName) throws Exception{
		long sum = 0;
		String sql = "select count(id) from cms_article where title like ?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadArticleSum has error";
		Object[] params = new Object[1];
		params[0] = "%" + searchName + "%";
		args[PARAM_ARGS] = params;
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			sum = (Long)list2[0];
		}
		return sum;
	}

	//根据文章id获得栏目id
	public static int loadArticleCol(long id) throws Exception{
		int colId = 0;
		String sql = "select col_id from cms_article where id = ?";		
		Object[] args = new Object[3];
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadArticleCol has error";
		Object[] params = new Object[1];
		params[0] = id;
		args[PARAM_ARGS] = params;
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			colId = (Integer)list2[0];
		}
		return colId;
	}
	/********自定义文章的标记语言开始**************/
	//arlist列表标签
	public static Article[] loadArticlesForStatic(int begin,
			int pageSize, int columnId, String orderby,
			int noteLen, int titleLen) throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		
		String sql = "select a.id,title,creatime,author,note,b.html_path,a.picture,is_top,b.id,b.col_name " +
				"from cms_article a,cms_column b where col_id=? and a.col_id=b.id order by " +
				orderby + " limit ?,?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============loadArticlesForStatic has error";
		
		Object[] params = new Object[3];
		params[0] = columnId;
		params[1] = begin;
		params[2] = pageSize;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		Article article = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setId((Long)list2[0]);
			//判断文章标题的长度显示
			String title = (String)list2[1];
			if(titleLen > 0 && title.length() > titleLen){
				title = title.substring(0, titleLen);
			}
			article.setTitle(title);
			
			article.setCreatime((Date)list2[2]);
			article.setAuthor((String)list2[3]);
			//判断文章摘要的长度显示
			String note = (String)list2[4];
			if(noteLen > 0 && note.length() > noteLen){
				note = note.substring(0, noteLen);
			}
			article.setNote(note);
			//得到文章的真实访问的url地址
			String htmlPath = (String)list2[5];
			if(htmlPath != null && !htmlPath.equals("")){
				htmlPath = InitServlet.WEB_SITE_URL + htmlPath + "/" 
						+ PubFun.getDateTime("yyyy-MM-dd", (Date)list2[2]) +
						"/" + (Long)list2[0] + ".html";
				article.setUrl(htmlPath);
			}			
			article.setPicture((String)list2[6]);
			article.setTop((Boolean)list2[7]);
			article.setColumnid((Integer)list2[8]);
			article.setColumnname((String)list2[9]);
			articleList.add(article);		
		}

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}
	/********自定义文章的标记语言结束**************/
	public static int searchCntByTitle(String title) throws Exception {
		int num = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select count(id) from cms_article where title like ?";
		args[PARAM_ERROR] = "ArticleTable============searchCntByTitle has error";
		
		Object[] params = new Object[1];
		params[0] = "%" + title + "%";
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			num = ((Long)list2[0]).intValue();
		}
		return num;
	}
	public static Article[] searchArticles(String title,int pageNo) throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		String sql ="select a.id,title,creatime,case b.alias when '' then b.name else b.alias end," +
				"col_name from cms_article a,cms_user b,cms_column c where b.id=creator and " +
				"col_id=c.id and title like ? order by a.id desc limit ?,?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============searchArticles has error";
		
		Object[] params = new Object[3];
		params[0] = "%" + title + "%";
		params[1] = (pageNo - 1) * InitServlet.PAGE_SIZE;
		params[2] = InitServlet.PAGE_SIZE;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		Article article = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setId((Long)list2[0]);
			article.setTitle((String)list2[1]);
			article.setCreatime((Date)list2[2]);
			article.setCreatorName((String)list2[3]);
			article.setColumnname((String)list2[4]);
			articleList.add(article);
		}		

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}
	public static int advancedSearchCnt(String title, String dateStr, String author, String editor) throws Exception {
		int num = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select count(a.id) from cms_article a, cms_user b  where " +
				"a.creator=b.id and a.title like ? and a.author like ? and case b.alias when '' then b.name else b.alias end like ?" 
				+ dateStr;
		args[PARAM_ERROR] = "ArticleTable============advancedSearchCnt has error";
		
		Object[] params = new Object[3];
		params[0] = "%" + title + "%";
		params[1] = "%" + author + "%";
		params[2] = "%" + editor + "%";
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			num = ((Long)list2[0]).intValue();
		}
		return num;
	}
	public static Article[] advancedSearch(String title,String dateStr,String author,String editor,int pageNo) throws Exception {
		ArrayList<Article> articleList = new ArrayList<Article>();
		String sql ="select a.id,title,creatime,case b.alias when '' then b.name else b.alias end," +
				"col_name from cms_article a,cms_user b,cms_column c where b.id=creator and col_id=c.id and " +
				"title like ? and a.author like ? and case b.alias when '' then b.name else b.alias end like ?" 
				+ dateStr +	"order by a.id desc limit ?,?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ArticleTable===============searchArticles has error";
		
		Object[] params = new Object[5];
		params[0] = "%" + title + "%";
		params[1] = "%" + author + "%";
		params[2] = "%" + editor + "%";
		params[3] = (pageNo - 1) * InitServlet.PAGE_SIZE;
		params[4] = InitServlet.PAGE_SIZE;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		Article article = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			article = new Article();
			article.setId((Long)list2[0]);
			article.setTitle((String)list2[1]);
			article.setCreatime((Date)list2[2]);
			article.setCreatorName((String)list2[3]);
			article.setColumnname((String)list2[4]);
			articleList.add(article);
		}		

		return (Article[]) articleList.toArray(new Article[articleList.size()]);
	}
}
