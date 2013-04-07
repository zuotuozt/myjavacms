package table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javabean.Message;
import table.inc.DBTable;
import util.InitServlet;

//插件：留言板表plugin_Message
public class MessageTable extends DBTable {
	//后段cms显示
	public static int loadCntByTitle(String title) throws Exception {
		int num = 0;
		
		Object[] args = new Object[3];		
		args[PARAM_SQL] = "select count(id) from plugin_message where title like ?";
		args[PARAM_ERROR] = "MessageTable============loadCntByTitle has error";
		
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
	//前段显示
	public static int loadCnt() throws Exception {
		int num = 0;
		
		Object[] args = new Object[2];		
		args[PARAM_SQL] = "select count(id) from plugin_message where is_replied=1";
		args[PARAM_ERROR] = "MessageTable============loadCnt has error";
		
		List<Object> lists = select(args);
		for(Object list : lists){
			Object[] list2 = (Object[]) list;
			num = ((Long)list2[0]).intValue();
		}
		return num;
	}	
	//留言列表页后端cms显示	
		public static Message[] loadMessages(String title,	int pageNo) throws Exception {
			ArrayList<Message> messageList = new ArrayList<Message>();
			String sql ="select a.id,title,creatime,ip,is_replied,replytime,case b.alias when '' then b.name else b.alias end" +
					" from plugin_message a left join cms_user b on b.id=replyer where title like ? order by is_replied, a.id desc limit ?,?";
			Object[] args = new Object[3];		
			args[PARAM_SQL] = sql;
			args[PARAM_ERROR] = "MessageTable===============loadMessages has error";
			
			Object[] params = new Object[3];
			params[0] = "%" + title + "%";
			params[1] = (pageNo - 1) * InitServlet.PAGE_SIZE;
			params[2] = InitServlet.PAGE_SIZE;
			args[PARAM_ARGS] = params;
			
			List<Object> lists = select(args);
			Message message = null;
			for(Object list : lists){
				Object[] list2 = (Object[]) list;
				message = new Message();
				message.setId((Long)list2[0]);
				message.setTitle((String)list2[1]);
				message.setCreatime((Date)list2[2]);
				message.setIp((String)list2[3]);
				message.setReplied((Boolean)list2[4]);
				message.setReplytime((Date)list2[5]);
				message.setReplyName((String)list2[6]);
				messageList.add(message);
			}		

			return (Message[]) messageList.toArray(new Message[messageList.size()]);
		}
		//前段显示
		public static Message[] loadMessageList(int pageNo) throws Exception {
			ArrayList<Message> messageList = new ArrayList<Message>();
			String sql ="select title,message,creatime,reply from plugin_message where is_replied=1 order by id desc limit ?,?";
			Object[] args = new Object[3];		
			args[PARAM_SQL] = sql;
			args[PARAM_ERROR] = "MessageTable===============loadMessageList has error";
			
			Object[] params = new Object[2];
			params[0] = (pageNo - 1) * InitServlet.MESSAGE_PAGE_SIZE;
			params[1] = InitServlet.MESSAGE_PAGE_SIZE;
			args[PARAM_ARGS] = params;
			
			List<Object> lists = select(args);
			Message message = null;
			for(Object list : lists){
				Object[] list2 = (Object[]) list;
				message = new Message();
				message.setTitle((String)list2[0]);
				message.setMessage((String)list2[1]);
				message.setCreatime((Date)list2[2]);
				message.setReply((String)list2[3]);
				messageList.add(message);
			}		

			return (Message[]) messageList.toArray(new Message[messageList.size()]);
		}
		//发布留言
		public static void insertMessage(Message message) throws Exception{
			Object[] args = new Object[3];		
			args[PARAM_SQL] = "insert into plugin_message(title,message,creatime,ip) values(?,?,now(),?)";
			args[PARAM_ERROR] = "MessageTable===============insertMessage has error";

			Object[] params = new Object[3];
			params[0] = message.getTitle();
			params[1] = message.getMessage();
			params[2] = message.getIp();
			args[PARAM_ARGS] = params;
			
			update(args);
		}
		//判断留言是否存在
		public static boolean isExistsById(long id) throws Exception {
			boolean isExists = false;
			
			Object[] args = new Object[3];		
			args[PARAM_SQL] = "select id from plugin_message where id=?";
			args[PARAM_ERROR] = "MessageTable============isExistsById has error";

			Object[] params = new Object[1];
			params[0] = id;
			args[PARAM_ARGS] = params;
			
			List<Object> lists = select(args);
			if(lists.size() > 0){
				isExists = true;
			}
			return isExists;
		}
		//显示留言内容
		public static Message loadMessage(long messageId) throws Exception {
			Message message = null;
			
			String sql = "select title,message,reply from plugin_message where id=?";		
			Object[] args = new Object[3];		
			args[PARAM_SQL] = sql;
			args[PARAM_ERROR] = "MessageTable===============loadMessage has error";
			
			Object[] params = new Object[1];
			params[0] = messageId;
			args[PARAM_ARGS] = params;
			
			List<Object> lists = select(args);	
			for(Object list : lists){
				Object[] list2 = (Object[]) list;
				message = new Message();
				message.setId(messageId);
				message.setTitle((String)list2[0]);
				message.setMessage((String)list2[1]);
				message.setReply((String)list2[2]);
			}	

			return message;
		}
		//回复留言
		public static void replyMessage(Message message) throws Exception {
			Object[] args = new Object[3];		
			args[PARAM_SQL] = "update plugin_message set title=?,message=?,reply=?,replytime=now(),is_replied=1,replyer=? where id=?";
			args[PARAM_ERROR] = "MessageTable===============replyMessage has error";
			Object[] params = new Object[5];
			params[0] = message.getTitle();
			params[1] = message.getMessage();
			params[2] = message.getReply();
			params[3] = message.getReplyer();
			params[4] = message.getId();
			args[PARAM_ARGS] = params;
			
			update(args);
		}
		
		public static void delMessage(long messageId) throws Exception {
			Object[] args = new Object[3];		
			args[PARAM_SQL] = "delete from plugin_message where id=?";
			args[PARAM_ERROR] = "MessageTable===============delMessage has error";

			Object[] params = new Object[1];
			params[0] = messageId;
			args[PARAM_ARGS] = params;
			
			update( args);
		}
		
		public static void delMessages( String messages) throws Exception {
			Object[] args = new Object[2];		
			args[PARAM_SQL] = "delete from plugin_message where id in(" + messages + ")";
			args[PARAM_ERROR] = "MessageTable===============delMessages has error";
			
			update(args);
		}
	
}
