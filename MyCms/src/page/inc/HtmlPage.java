package page.inc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import table.ColumnTable;
import util.InitServlet;

import javabean.UserInfo;

public abstract class HtmlPage {

	public abstract String print(HttpServletRequest req,
			HttpServletResponse resp) throws Exception;

	// 得到登录用户存在session的信息
	protected UserInfo getSessionUser(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		return (UserInfo) session.getAttribute("userInfo");
	}

	/****** 封装request值开始 *****/
	protected boolean getBooleanParameter(String key, boolean v,
			HttpServletRequest req) {
		String str = req.getParameter(key);
		if (str == null)
			return v;
		return Boolean.valueOf(str.trim()).booleanValue();
	}

	protected String getStringParameter(String key, String defStr,
			HttpServletRequest req){
		String str = req.getParameter(key);
		if ((str == null) || (str.trim().length() == 0)) {
			return defStr;
		} else {
			return str.trim();
		}
	}

	protected int getIntParameter(String key, int i, HttpServletRequest req)
			throws NumberFormatException {
		String str = req.getParameter(key);
		if ((str == null) || (str.trim().length() == 0) || str.equals("null")) {
			return i;
		} else {
			str = str.trim();
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException exc) {
			throw new NumberFormatException("值：'" + str + "' 不是真正的整型。");
		}
	}

	protected long getLongParameter(String key, long i, HttpServletRequest req)
			throws NumberFormatException {
		String str = req.getParameter(key);
		if ((str == null) || (str.trim().length() == 0)) {
			return i;
		}
		try {
			return Long.parseLong(str.trim());
		} catch (NumberFormatException exc) {
			throw new NumberFormatException(key + ":不是真正的长整型.");
		}
	}

	/****** 封装request值结束 *****/
	// 分页函数
	protected int page(int pageNo, int cnt, HttpServletRequest req) {
		int sumPage = 1;
		int moCnt = cnt % InitServlet.PAGE_SIZE;
		if (moCnt > 0) {
			sumPage = cnt / InitServlet.PAGE_SIZE + 1;
		} else {
			sumPage = cnt / InitServlet.PAGE_SIZE;
		}
		if (sumPage < 1) {
			sumPage = 1;
		}
		if (sumPage < pageNo) {
			pageNo = sumPage;
		}
		 String[] all = null;
		  	if(sumPage<=9){
		  		all = new String[9];
				for(int i=0;i<9;i++){
					if(i<sumPage&&cnt!=0){
						all[i] = ""+(i+1);
					}else{
						all[i] = "";
					}	
				}	
		  	}else{
		  		all = new String[11];
		  		all[0] = "1";
		  		all[1] = "2";
		  		all[9] = ""+(sumPage-1);
		  		all[10] = ""+sumPage;
		  		if(pageNo<=4){
						all[3] = "3";
		  			all[4] = "4";
		  			all[5] = "5";
		  			all[6] = "6";
		  			all[7] = "7";
					}else{
						if(pageNo>=(sumPage-3)){
							all[3] = ""+(sumPage-6);
			      			all[4] = ""+(sumPage-5);
			      			all[5] = ""+(sumPage-4);
			      			all[6] = ""+(sumPage-3);
			      			all[7] = ""+(sumPage-2);
						}else{
							all[3] = ""+(pageNo-2);
			      			all[4] = ""+(pageNo-1);
			      			all[5] = ""+pageNo;
			      			all[6] = ""+(pageNo+1);
			      			all[7] = ""+(pageNo+2);
						}
					}
		  		if(!all[3].equals("3")){
		  			all[2] = "..."; 
		  		}else{
		  			all[2] = ""; 	
		  		}
		  		if(!all[7].equals(""+(sumPage-2))){
		  			all[8] = "..."; 
		  		}else{
		  			all[8] = ""; 	
		  		}
		  	}
		req.setAttribute("pageNo", pageNo);
		req.setAttribute("cnt", cnt);
		req.setAttribute("sumPage", sumPage);
		req.setAttribute("allPages", all);
		req.setAttribute("pageSize", InitServlet.PAGE_SIZE);
		return pageNo;
	}
	
	protected int getTotalPage(int cnt, int pageSize) {
		int sumPage = 1;
		int moCnt = cnt % pageSize;
		if (moCnt > 0) {
			sumPage = cnt / pageSize + 1;
		} else {
			sumPage = cnt / pageSize;
		}
		if (sumPage < 1) {
			sumPage = 0;
		}
		return sumPage;
	}
	// 把字符串生成外部文件（当文件不存在时会自动生成新文件,但文件真实路径不能自动生成）
	protected void stringToFile(String fileName, String content) throws IOException {
		BufferedWriter out = 
			new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
		out.write(content);
		out.close();
	}
	//删除目录下的所有子目录和文件
	protected void delDirs(String filepath) throws IOException{
	     File f = new File(filepath);//定义文件路径        
	     if(f.exists()){//判断是否存在
	    	 if(f.isDirectory()){//判断是文件还是目录
		         if(f.listFiles().length==0){//若目录下没有文件则直接删除
		             f.delete();//删除目录
		         }else{//若有则把文件放进数组，并判断是否有下级目录
		             File delFile[]=f.listFiles();
		             int i =f.listFiles().length;
		             for(int j=0;j<i;j++){
		                 if(delFile[j].isDirectory()){
		                	 delDirs(delFile[j].getAbsolutePath());//递归调用delDirs方法并取得子目录路径
		                 }else{
		                	 delFile[j].delete();//删除文件
		                 }
		             }
		             f.delete();//删除目录
		         }
		     }else{
		    	f.delete();//删除文件
		     }
	     }
	}
	
	protected int getTop(int id) throws Exception{
		int top = 0;
		int parentId = ColumnTable.loadTop(id);
		if(parentId==0){
			top = id;
		}else{
			top = parentId;
			while(ColumnTable.loadTop(top)!=0){
				top = ColumnTable.loadTop(top);
			}
		}
		return top;
	}
	
	protected String getRemortIP(HttpServletRequest request) {
	  if (request.getHeader("x-forwarded-for") == null) {
		  return request.getRemoteAddr();
	  } else {
		  return request.getHeader("x-forwarded-for");
	  }
	}

}
