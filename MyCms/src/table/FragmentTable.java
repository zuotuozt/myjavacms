package table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javabean.Fragment;
import table.inc.DBTable;
import util.InitServlet;
import util.PubFun;

public class FragmentTable extends DBTable{

	public static Fragment[] loadFragments() throws Exception {
		ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
		
		String sql = "select id,title,content,create_time from cms_fragment";		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "FragmentTable===============loadFragments has error";
		
		List<Object> lists = select(args);	
		Fragment fragment = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			fragment = new Fragment();
			fragment.setId((Integer)list2[0]);
			fragment.setTitle((String)list2[1]);
			fragment.setContent((String)list2[2]);
			fragment.setCreateTime((Date)list2[3]);
			fragmentList.add(fragment);
		}		

		return (Fragment[]) fragmentList.toArray(new Fragment[fragmentList.size()]);
	}
	
	public static Fragment[] loadFragmentsForGenerate() throws Exception {
		ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
		
		String sql = "select id,content from cms_fragment";		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "FragmentTable===============loadFragments has error";
		
		List<Object> lists = select(args);	
		Fragment fragment = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			fragment = new Fragment();
			fragment.setId((Integer)list2[0]);
			fragment.setContent((String)list2[1]);
			fragmentList.add(fragment);
		}		

		return (Fragment[]) fragmentList.toArray(new Fragment[fragmentList.size()]);
	}
	
	public static void insertFragment(Connection conn, Fragment fragment) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "insert into cms_fragment(id,title,content,column_id,create_time) " +
				"values(?,?,?,?,now())";
		args[PARAM_ERROR] = "FragmentTable===============insertFragment has error";
		Object[] params = new Object[4];
		params[0] = fragment.getId();
		params[1] = fragment.getTitle();
		params[2] = fragment.getContent();
		params[3] = fragment.getColumnId();
		args[PARAM_ARGS] = params;
		updateTranscation(conn, args);
	}
	
	public static long loadLastId(Connection conn) throws Exception {
		long id = 0;
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "SELECT LAST_INSERT_ID()";
		args[PARAM_ERROR] = "FragmentTable===============insertFragment has error";
		List<Object> lists = selectTransction(conn, args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			id = ((Long)list2[0]).intValue();
		}
		return id;
	}
	
	public static void updateFragment(Fragment fragment) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update cms_fragment set title=?,content=?,column_id=? where id=?";
		args[PARAM_ERROR] = "FragmentTable===============updateFragment has error";
		Object[] params = new Object[4];
		params[0] = fragment.getTitle();
		params[1] = fragment.getContent();
		params[2] = fragment.getColumnId();
		params[3] = fragment.getId();
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	
	public static void delFragment(long fragmentId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "delete from cms_fragment where id=?";
		args[PARAM_ERROR] = "FragmentTable===============delFragment has error";

		Object[] params = new Object[1];
		params[0] = fragmentId;
		args[PARAM_ARGS] = params;
		update(args);
	}
	
	public static void delFragments(String fragments) throws Exception {
		Object[] args = new Object[2];		
		args[PARAM_SQL] = "delete from cms_fragment where id in(" + fragments + ")";
		args[PARAM_ERROR] = "FragmentTable===============delFragments has error";
		update(args);
	}
	
	public static Fragment loadSimpleColById(int fragmentId) throws Exception {
		Fragment fragment = null;
		
		String sql = "select title,content,create_time from cms_fragment where id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "FragmentTable===============loadSimpleColById has error";
		
		Object[] params = new Object[1];
		params[0] = fragmentId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			fragment = new Fragment();
			fragment.setId(fragmentId);
			fragment.setTitle((String)list2[0]);
			fragment.setContent(PubFun.getJsFilterValue((String)list2[1]));
			fragment.setCreateTime((Date)list2[2]);
		}	

		return fragment;
	}
	
	public static int loadCntByTitle(String title) throws Exception {
		int num = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select count(id) from cms_fragment where title like ?";
		args[PARAM_ERROR] = "FragmentTable============loadCntByTitle has error";
		
		Object[] params = new Object[1];
		params[0] = "%"	+ title + "%";
		args[PARAM_ARGS] = params;

		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			num = ((Long)list2[0]).intValue();
		}
		return num;
	}
	
	public static Fragment[] loadFragments(String title, int pageNo) throws Exception {
		ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
		
		String sql = "select a.id,a.title,a.column_id,b.col_name from cms_fragment a,cms_column b " +
				"where a.column_id=b.id and title like ? and a.column_id!=-1 " +
				"union select id,title,column_id,'所有栏目' from cms_fragment where column_id =-1 limit ?,?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "FragmentTable===============loadFragments has error";
		
		Object[] params = new Object[3];
		params[0] = "%"	+ title + "%";
		params[1] = (pageNo - 1) * InitServlet.PAGE_SIZE;
		params[2] = InitServlet.PAGE_SIZE;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		Fragment fragment = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			fragment = new Fragment();
			fragment.setId((Integer)list2[0]);
			fragment.setTitle((String)list2[1]);
			fragment.setColumnId((Integer)list2[2]);
			fragment.setColumnName((String)list2[3]);
			fragmentList.add(fragment);
		}		

		return (Fragment[]) fragmentList.toArray(new Fragment[fragmentList.size()]);
	}
	
	public static boolean isExistsById(long id) throws Exception {
		boolean isExists = false;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select id from cms_fragment where id=?";
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
	
	public static Fragment loadFragmentById(int fragmentId) throws Exception {
		Fragment fragment = null;
		
		String sql = "select title,content,column_id from cms_fragment where id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "FragmentTable===============loadFragmentById has error";
		
		Object[] params = new Object[1];
		params[0] = fragmentId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			fragment = new Fragment();
			fragment.setTitle((String)list2[0]);
			fragment.setContent((String)list2[1]);
			fragment.setColumnId((Integer)list2[2]);
			fragment.setId(fragmentId);
		}	

		return fragment;
	}

}
