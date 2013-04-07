package table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javabean.Col;
import table.inc.DBTable;
import util.Constant;
import util.InitServlet;

//栏目表cms_column
public class ColumnTable extends DBTable {
	
	/*****新 ********/
	//获得上级栏目的保存目录和名称
	public static Col loadParentInfo(int parentId) throws Exception{
		Col column = null;
		
		String sql = "select html_path,col_name from cms_column where id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadParentInfo has error";
		
		Object[] params = new Object[1];
		params[0] = parentId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);		
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			column = new Col();
			column.setLastHtmlpath((String)list2[0]);
			column.setParentName((String)list2[1]);
		}
		return column;
	}
	
	
	public static Col loadColForModify(int colId) throws Exception{
		Col column = null;
		String sql = "select col_name,html_path,col_type,index_template,list_template,article_template," +
				"col_intro,link,parent_id,seo_title,seo_keywords,seo_description from cms_column where id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColForModify has error";
		
		Object[] params = new Object[1];
		params[0] = colId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);		
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			column = new Col();
		    column.setId(colId);
			column.setName((String)list2[0]);
			String htmlPath =(String)list2[1];
			int index = htmlPath.lastIndexOf("/");
			if(index==-1){
				column.setHtmlPath(htmlPath);
			}else{
				column.setHtmlPath(htmlPath.substring(index+1));
			}
            column.setColType((Integer)list2[2]);
            column.setIndexTemplate((String)list2[3]);
            column.setListTemplate((String)list2[4]);
            column.setArticleTemplate((String)list2[5]);
            column.setColIntro((String)list2[6]);
            column.setLink((String)list2[7]);
            int parentId = (Integer)list2[8];
            column.setParentid(parentId);
        	Col parentCol = new Col();
        	if(parentId > 0){
        		parentCol = ColumnTable.loadParentInfo(parentId);
        	}
            column.setLastHtmlpath(parentCol.getLastHtmlpath());
            column.setParentName(parentCol.getParentName());
            column.setSeoTitle((String)list2[9]);
            column.setSeoKeywords((String)list2[10]);
            column.setSeoDescription((String)list2[11]);
		}
		return column;
	}
	
	public static Col loadColForPreview(int colId) throws Exception{
		Col column = null;
		String sql = "select col_type,link,index_template,list_template,article_template,html_path," +
				"parent_id,col_name,seo_title,seo_keywords,seo_description from cms_column where id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColForPreview has error";
		
		Object[] params = new Object[1];
		params[0] = colId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);		
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			column = new Col();
		    column.setId(colId);
            column.setColType((Integer)list2[0]);
            column.setLink((String)list2[1]);
            column.setIndexTemplate((String)list2[2]);
            column.setListTemplate((String)list2[3]);
            column.setArticleTemplate((String)list2[4]);
            column.setHtmlPath((String)list2[5]);
            column.setParentid((Integer)list2[6]);
            column.setName((String)list2[7]);
            column.setSeoTitle((String)list2[8]);
            column.setSeoKeywords((String)list2[9]);
            column.setSeoDescription((String)list2[10]);
		}
		return column;
	}
	
	public static void addColumn(Col column) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "insert into cms_column(parent_id,col_name,html_path," +
				"link,col_type,index_template,list_template,article_template," +
				"col_intro,seo_title,seo_keywords,seo_description) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		args[PARAM_ERROR] = "ColumnTable===============addColumn has error";

		Object[] params = new Object[12];
		params[0] = column.getParentid();
		params[1] = column.getName();
		params[2] = column.getHtmlPath();
		params[3] = column.getLink();
		params[4] = column.getColType();
		params[5] = column.getIndexTemplate();
		params[6] = column.getListTemplate();
		params[7] = column.getArticleTemplate();
		params[8] = column.getColIntro();
		params[9] = column.getSeoTitle();
		params[10] = column.getSeoKeywords();
		params[11] = column.getSeoDescription();
	            
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	
	public static void updateColumn(Col column) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_column set col_name=?,html_path=?,index_template=?,list_template=?," +
				          "article_template=?,col_intro=?,link=?,seo_title=?,seo_keywords=?,seo_description=? where id=?";
		args[PARAM_ERROR] = "ColumnTable===============updateColumn has error";
		Object[] params = new Object[11];
		params[0] = column.getName();
		params[1] = column.getHtmlPath();
		params[2] = column.getIndexTemplate();
		params[3] = column.getListTemplate();
		params[4] = column.getArticleTemplate();
		params[5] = column.getColIntro();
		params[6] = column.getLink();
		params[7] = column.getSeoTitle();
		params[8] = column.getSeoKeywords();
		params[9] = column.getSeoDescription();
		params[10] = column.getId();
		
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	//查栏目树型结构
	public static Col[] loadColumnsForTree(int parentId) throws Exception {
		ArrayList<Col> columnList = new ArrayList<Col>();
		
		String sql = "select id,html_path,col_name,col_type from cms_column where parent_id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColumnsForTree has error";
		Object[] params = new Object[1];
		params[0] = parentId;
		args[PARAM_ARGS] = params;
		List<Object> lists = select(args);	
		Col column = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			column = new Col();
			column.setId((Integer)list2[0]);
	        column.setHtmlPath((String)list2[1]);
	        column.setName((String)list2[2]);
	        column.setColType((Integer)list2[3]);
			columnList.add(column);
		}
		return (Col[]) columnList.toArray(new Col[columnList.size()]);
	}
	
	//查栏目树型结构
	public static Col[] loadColumnsForHomeTree(int userId) throws Exception {
		ArrayList<Col> columnList = new ArrayList<Col>();
		
		String sql = "select a.id,html_path,col_name,col_type from cms_column a, cms_role b " +
				"where a.id=b.column_id and parent_id=? and b.user_id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColumnsForHomeTree has error";
		Object[] params = new Object[2];
		params[0] = Constant.HOME_CLOUMN;
		params[1] = userId;
		args[PARAM_ARGS] = params;
		List<Object> lists = select(args);	
		Col column = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			column = new Col();
			column.setId((Integer)list2[0]);
	        column.setHtmlPath((String)list2[1]);
	        column.setName((String)list2[2]);
	        column.setColType((Integer)list2[3]);
			columnList.add(column);
		}
		return (Col[]) columnList.toArray(new Col[columnList.size()]);
	}
	
	public static int loadTop(int id) throws Exception {
		int top = 0 ; 
		String sql = "select parent_id from cms_column where id=" + id;		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadTop has error";
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			top = (Integer)list2[0];	
		}		

		return top;
	}
	
	public static Col loadColById(int id) throws Exception {
		Col col = null;
		String sql = "select parent_id, col_name from cms_column where id=" + id;		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColById has error";
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			col = new Col();
			col.setId(id);
			col.setParentid((Integer)list2[0]);
			col.setName((String)list2[1]);	
		}		

		return col;
	}
	
	public static Col loadColByIdForStatic(int id) throws Exception {
		Col col = null;
		String sql = "select parent_id,col_name,link from cms_column where id=" + id;		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColById has error";
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			col = new Col();
			col.setId(id);
			col.setParentid((Integer)list2[0]);
			col.setName((String)list2[1]);
			col.setLink(InitServlet.WEB_SITE_URL + (String)list2[2]);
		}		

		return col;
	}
	
	public static Col[] loadChildrenByIdForDel(int id) throws Exception {
		ArrayList<Col> columnList = new ArrayList<Col>();
		
		String sql = "select id,parent_id,html_path from cms_column where parent_id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadChildrenByIdForDel has error";
		Object[] params = new Object[1];
		params[0] = id;
		args[PARAM_ARGS] = params;
		List<Object> lists = select(args);	
		Col column = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			column = new Col();
			column.setId((Integer)list2[0]);
	        column.setParentid((Integer)list2[1]);
	        column.setHtmlPath((String)list2[2]);
            columnList.add(column);
		}		

		return (Col[]) columnList.toArray(new Col[columnList.size()]);
	}
	
	public static boolean isExistsById(int id) throws Exception {
		boolean isExists = false;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_column where id=?";
		args[PARAM_ERROR] = "ColumnTable============isExistsById has error";

		Object[] params = new Object[1];
		params[0] = id;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		if(lists.size() > 0){
			isExists = true;
		}
		return isExists;
	}
	
	public static boolean hasChildren(int id) throws Exception {
		boolean isExists = false;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_column where parent_id=?";
		args[PARAM_ERROR] = "ColumnTable============hasChildren has error";

		Object[] params = new Object[1];
		params[0] = id;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		if(lists.size() > 0){
			isExists = true;
		}
		return isExists;
	}
	
	public static Col loadColForDel(int colId) throws Exception{
		Col column = null;
		String sql = "select parent_id,html_path from cms_column where id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColForDel has error";
		
		Object[] params = new Object[1];
		params[0] = colId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);		
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			column = new Col();
		    column.setId(colId);
			column.setParentid((Integer)list2[0]);
			column.setHtmlPath((String)list2[1]);
		}
		return column;
	}
	
	public static void deleteColumn(Connection conn, int columnId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "delete from cms_column where id=?";
		args[PARAM_ERROR] = "ColumnTable===============deleteColumn has error";

		Object[] params = new Object[1];
		params[0] = columnId;
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}
	
	public static boolean isExistsByHtmlPath(String htmlPath) throws Exception {
		boolean isExists = false;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_column where html_path=?";
		args[PARAM_ERROR] = "ColumnTable============isExistsByHtmlPath has error";

		Object[] params = new Object[1];
		params[0] = htmlPath;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);
		if(lists.size() > 0){
			isExists = true;
		}
		return isExists;
	}
	
	public static ArrayList<Col> loadChildrenForStatic(int colId) throws Exception{
		ArrayList<Col> columns = new ArrayList<Col>();
		String sql = "select col_type,link,index_template,list_template,article_template,html_path," +
				"id,parent_id,col_name,seo_title,seo_keywords,seo_description from cms_column where parent_id=? ";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadChildrenForStatic has error";
		
		Object[] params = new Object[1];
		params[0] = colId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);		
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			Col column = new Col();
            column.setColType((Integer)list2[0]);
            column.setLink((String)list2[1]);
            column.setIndexTemplate((String)list2[2]);
            column.setListTemplate((String)list2[3]);
            column.setArticleTemplate((String)list2[4]);
            column.setHtmlPath((String)list2[5]);
            column.setId((Integer)list2[6]);
            column.setParentid((Integer)list2[7]);
            column.setName((String)list2[8]);
            column.setSeoTitle((String)list2[9]);
            column.setSeoKeywords((String)list2[10]);
            column.setSeoDescription((String)list2[11]);
            columns.add(column);
		}
		return columns;
	}
	public static Col loadColIntro(int parentid) throws Exception {
		Col column = new Col();
		String sql = "select col_intro,seo_title,seo_keywords,seo_description from cms_column where id="+parentid;		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColIntro has error";
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
            column.setColIntro((String)list2[0]);
            column.setSeoTitle((String)list2[1]);
            column.setSeoKeywords((String)list2[2]);
            column.setSeoDescription((String)list2[3]);
		}		
		return column;
	}
	public static String loadContent(int id) throws Exception {
		String colContent = "" ; 
		String sql = "select col_content from cms_column where id=" + id;		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadContent has error";
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			colContent = (String)list2[0];	
		}		
		return colContent;
	}
	
	//获得所有最终列表栏目的栏目集合
	public static Col[] loadArticleCols() throws Exception {
		ArrayList<Col> colList = new ArrayList<Col>();
		
		String sql = "select id,col_name from cms_column where col_type=" + Constant.ARTICLES_CLOUMN;	
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadArticleCols has error";
		
		List<Object> lists = select(args);	
		Col col = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			col = new Col();
			col.setId((Integer)list2[0]);
			col.setName((String)list2[1]);
			colList.add(col);
		}		
	  
		return (Col[]) colList.toArray(new Col[colList.size()]);
	}
	
	//获得所有顶级栏目的集合
	public static Col[] loadColTree() throws Exception {
		ArrayList<Col> colList = new ArrayList<Col>();
		
		String sql = "select id,col_name from cms_column where parent_id=" + Constant.TOP_CLOUMN_TREE;		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColTree has error";
		
		List<Object> lists = select(args);	
		Col col = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			col = new Col();
			col.setId((Integer)list2[0]);
			col.setName((String)list2[1]);
			colList.add(col);
		}		
	  
		return (Col[]) colList.toArray(new Col[colList.size()]);
	}
	
	//获得所有指定id号的栏目
	public static Col[] loadCheckedCols(String sqlParam) throws Exception {
		ArrayList<Col> columns = new ArrayList<Col>();
		
		String sql = "select col_type,link,index_template,list_template,article_template,html_path,id from cms_column where id in("+sqlParam+") ";		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadCheckedCols has error";
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			Col column = new Col();
            column.setColType((Integer)list2[0]);
            column.setLink((String)list2[1]);
            column.setIndexTemplate((String)list2[2]);
            column.setListTemplate((String)list2[3]);
            column.setArticleTemplate((String)list2[4]);
            column.setHtmlPath((String)list2[5]);
            column.setId((Integer)list2[6]);
            columns.add(column);
		}		
	  
		return (Col[]) columns.toArray(new Col[columns.size()]);
	}
	//该parentsid 下的所有下级栏目集合  
	public static Col[] loadColumnsByParentid(String parentid) throws Exception {
		ArrayList<Col> columnList = new ArrayList<Col>();
		String sql = "select id,parent_id,col_name,html_path,link,create_time,col_type,index_template,list_template,article_template from cms_column where parent_id=?";		
		Object[] args = new Object[3];
		Object[] params = new Object[1];
		params[0] = parentid;
		args[PARAM_ARGS] = params;
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColumnsByParentid has error";
		List<Object> lists = select(args);	
		Col column = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			column = new Col();
			column.setId((Integer)list2[0]);
	        column.setParentid((Integer)list2[1]);
	        column.setName((String)list2[2]);
	        column.setHtmlPath((String)list2[3]);
	        column.setLink((String)list2[4]);
	        column.setColType((Integer)list2[5]);
	        column.setIndexTemplate((String)list2[6]);
	        column.setListTemplate((String)list2[7]);
	        column.setArticleTemplate((String)list2[8]);
			columnList.add(column);
		}		

		return (Col[]) columnList.toArray(new Col[columnList.size()]);
	}
//得到栏目下的文章数（包括子栏目）	
	public static long loadChildrenCntByCols(String cols) throws Exception{
		long articleCnt = 0;
		String sql = "select count(id) from cms_article where col_id in ("+cols+")";		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadChildrenCntByCols has error";
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			articleCnt = (Long)list2[0];
		}
		return articleCnt;
	}
	//得到单页面栏目的文章内容的标记语言
	public static String loadColContent(int colId) throws Exception{
		String content = null;
		String sql = "select content from cms_column where id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "ColumnTable===============loadColContent has error";
		
		Object[] params = new Object[1];
		params[0] = colId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);		
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			content = (String)list2[0];
		}
		return content;
	}
}
