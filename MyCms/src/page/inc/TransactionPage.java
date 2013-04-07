package page.inc;

import java.sql.Connection;
import java.sql.SQLException;

import util.Constant;
import page.inc.HtmlPage;
import util.PubFun;

public abstract class TransactionPage extends HtmlPage{

	protected abstract void handleTransaction(Connection conn, Object[] args) throws Exception;

	protected boolean transactionConn(Object[] args)
	throws Exception {
		Connection conn = null;
		try {
			conn = PubFun.getConn(Constant.CONNECTION_TRANSZACTION);
			handleTransaction(conn, args);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new Exception("conn.rollback() falied");
			}
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return true;
	}
}