package table;

import table.inc.DBTable;

//数据库操作
public class GlobalTable extends DBTable {
	//恢复数据库
	public static void loadDB(String dbName, String path) throws Exception {
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "restore database " + dbName + " from disk=? with replace";
		args[PARAM_ERROR] = "恢复数据库失败。";
		
		Object[] params = new Object[1];
		params[0] = path;
		args[PARAM_ARGS] = params;
		
		update(args);
	}
	
}
