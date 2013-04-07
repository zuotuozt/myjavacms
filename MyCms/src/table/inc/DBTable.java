package table.inc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Constant;
import util.PubFun;

public class DBTable {
	protected final static String TABLE_ERROR_INFO = "操作数据库出错：";
	protected final static int PARAM_SQL = 0;   //参数：sql语句的
	protected final static int PARAM_ERROR = 1; //参数：错误信息
	protected final static int PARAM_ARGS = 2;  //参数：函数参数
	
	//(修改表数据；有DB事物处理操作；共用connection)
	protected static void updateTranscation(Connection conn, Object[] args) throws Exception {
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement((String)args[PARAM_SQL]);
			if(args.length == PARAM_ARGS+1){
				Object[] params = (Object[])args[PARAM_ARGS];
				if(params != null){
					int i = 1;
					for(Object e : params){
						pstm.setObject(i++, e);
					}
				}
			}
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new Exception(TABLE_ERROR_INFO + (String)args[PARAM_ERROR]);
		} finally {
			if (pstm != null) {
				try{
					pstm.close();
				} catch (Exception e) {
				}
			}
		}
	}
	//(修改表数据；无DB事物处理操作)
	protected static void update(Object[] args) throws Exception {
		Connection conn = null;
		try {
			conn = PubFun.getConn(Constant.CONNECTION_NONE_TRANSZACTION);
			updateTranscation(conn, args);
		} finally {
			if (conn != null) {
				try{
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
	//(查询表数据；无DB事物处理操作)
	protected static List<Object> select(Object[] args) throws Exception {
		Connection conn = null;
		List<Object> list = new ArrayList<Object>();
		try {
			conn = PubFun.getConn(Constant.CONNECTION_READONLY);
			list = selectTransction(conn, args);
		} finally {
			if (conn != null) {
				try{
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return list;
	}
	//(查询表数据；有DB事物处理操作；共用connection)	
	protected static List<Object> selectTransction(Connection conn, Object[] args) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<Object>();
		try {
			pstm = conn.prepareStatement((String)args[PARAM_SQL]);
			if(args.length == PARAM_ARGS+1){
				Object[] params = (Object[])args[PARAM_ARGS];
				if(params != null){
					int i = 1;
					for(Object e : params){
						pstm.setObject(i++, e);
					}
				}
			}
			rs = pstm.executeQuery();
		    int columnsCount = rs.getMetaData().getColumnCount();  
		    while (rs.next()) {
		    	Object[] columns = new Object[columnsCount];
		    	for(int i = 0; i < columnsCount; i++){
		    		columns[i] = rs.getObject(i+1);
				}
		    	list.add(columns);
		    }
		} catch (SQLException e) {
			throw new Exception(TABLE_ERROR_INFO + (String)args[PARAM_ERROR]);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (pstm != null) {
				try{
					pstm.close();
				} catch (Exception e) {
				}
			}
		}
		return list;
	}
	
}
