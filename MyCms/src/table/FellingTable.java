package table;

import java.sql.Connection;
import java.util.List;

import table.inc.DBTable;
import javabean.Feeling;

//插件：文章读后感表plugin_feeling
public class FellingTable extends DBTable {

	public static void insertFeeling(long articleId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "insert into plugin_feeling values(?,0,0,0,0,0,0,'',1)";
		args[PARAM_ERROR] = "FellingTable===============insertFeeling has error";

		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	
	public static void updateFeeling(long articleId, String sqlStr) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sqlStr;
		args[PARAM_ERROR] = "FellingTable===============updateFeeling has error";

		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	
	public static Feeling loadFeeling(long articleId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select face1_cnt,face2_cnt,face3_cnt,face4_cnt,face5_cnt,face6_cnt" +
				" from plugin_feeling where article_id=?";
		args[PARAM_ERROR] = "FellingTable============loadFeeling has error";
		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		Feeling feeling = null;
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			feeling = new Feeling();
			feeling.setFace1Cnt((Long)list2[0]);
			feeling.setFace2Cnt((Long)list2[1]);
			feeling.setFace3Cnt((Long)list2[2]);
			feeling.setFace4Cnt((Long)list2[3]);
			feeling.setFace5Cnt((Long)list2[4]);
			feeling.setFace6Cnt((Long)list2[5]);
		}
		return feeling;
	}
	
	public static String loadLastip(long articleId) throws Exception {
		String ip = "0";
		
		String sql = "select last_ip from plugin_feeling where article_id=?";		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "FellingTable===============loadLastip has error";
		
		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			ip = (String)list2[0];
		}	
	
		return ip;
	}
	
	public static void delFeeling(Connection conn, long articleId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "delete from plugin_feeling where article_id=?";
		args[PARAM_ERROR] = "FellingTable===============delFeeling has error";

		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}
	
	public static void delFeelings(Connection conn, String articles) throws Exception {
		Object[] args = new Object[2];		
		args[PARAM_SQL] = "delete from plugin_feeling where article_id in(" + articles + ")";
		args[PARAM_ERROR] = "FellingTable===============delFeelings has error";
		
		updateTranscation(conn, args);
	}
	
	public static void delFeelingsByColumnId(Connection conn, int columnId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "delete from plugin_feeling where exists(select id from cms_article where plugin_feeling.article_id=id and col_id=?)";
		args[PARAM_ERROR] = "FellingTable===============delFeelingsByColumnId has error";

		Object[] params = new Object[1];
		params[0] = columnId;
		args[PARAM_ARGS] = params;
		
		updateTranscation(conn, args);
	}
	
	public static long loadBrowseCnt(long articleId) throws Exception {
		long browseCnt = -1;
		
		String sql = "select browse_cnt from plugin_feeling where article_id=?";
		Object[] args = new Object[3];		
		args[PARAM_SQL] = sql;
		args[PARAM_ERROR] = "FellingTable===============loadBrowseCnt has error";
		
		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		List<Object> lists = select(args);	
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			browseCnt = (Long)list2[0];
		}	
	
		return browseCnt;
	}
	
	public static void updateBrowseCnt(long articleId) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "update plugin_feeling set browse_cnt=browse_cnt+1 where article_id=?";
		args[PARAM_ERROR] = "FellingTable===============updateBrowseCnt has error";

		Object[] params = new Object[1];
		params[0] = articleId;
		args[PARAM_ARGS] = params;
		
		update(args);
	}

}
