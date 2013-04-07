package page.col;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.col.inc.StaticBasePage;

import table.ArticleTable;
import table.ColumnTable;
import util.Constant;
import util.InitServlet;
import util.PubFun;

public class PreviewPage extends StaticBasePage {

	@Override //预览栏目 //没有考虑模板不存在的情况
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if((user == null)) {
			PubFun.ajaxPrint("alert('页面失效；请重新登陆。');if(opener) opener.location='"
					+ req.getContextPath() + "/" + Constant.REDIRECT_LOGIN_PAGE
					+ "';close();", resp);
			return null;
		}
		int colId = getIntParameter("col_id",0,req);
		String jspStr = "";
		if(colId!=0&&ColumnTable.isExistsById(colId)) {
			Col column = ColumnTable.loadColForPreview(colId);
	        if(column.getColType() == Constant.ARTICLES_CLOUMN){
	        	int cnt = ArticleTable.loadAllCnt(column.getId());
	        	if(cnt != 0){
	        		File f = new File(InitServlet.CONTENT_REALPATH + column.getListTemplate());
	        		if(f.exists() && !f.isDirectory()){
	    				int pageSize = InitServlet.PAGE_SIZE;
	    				if (this.getThatProperty(f, "pagesize")!= null) {
	    					pageSize = Integer.parseInt(this.getThatProperty(f, "pagesize"));
	    				}
	    				int totalPage = this.getTotalPage(cnt, pageSize);
	        			req.setAttribute("path", InitServlet.WEB_SITE_URL
								+ column.getHtmlPath() + "/pages/" );
	        			req.setAttribute("columnid", column.getId());
						req.setAttribute("seotitle", column.getSeoTitle());
						req.setAttribute("seokeywords", column.getSeoKeywords());
						req.setAttribute("seodescription", column.getSeoDescription());
						req.setAttribute("pageno", 1);
						req.setAttribute("totalcnt", cnt);
						req.setAttribute("totalpage", totalPage);
						req.setAttribute("pagesize", pageSize);
						ArrayList<Col> cols = new ArrayList<Col>();
						cols.add(column);
						getParents(column, cols);
						Collections.reverse(cols);
						req.setAttribute("parentcols", cols);
	        		}else{
	        			PubFun.ajaxPrint("alert('无法预览；请到\"模板管理\" 选定模板文件。');close();", resp);
	        			return null;
	        		}
	        	}else{
	        		PubFun.ajaxPrint("alert('此列表栏目下没有可以预览的文章。');close();", resp);
        			return null;
	        	}
    			jspStr = column.getListTemplate() + "?id=" + colId + "&pageno=1";
	        }else if(column.getColType() == Constant.COVER_CLOUMN){
	        	File f = new File(InitServlet.CONTENT_REALPATH + column.getIndexTemplate());
	        	if(f.exists() && !f.isDirectory()){
	        		jspStr = column.getIndexTemplate() + "?id=" + colId;
	        	}
	        }
		}else{
			PubFun.ajaxPrint("alert('此栏目不存在；请确认 。');close();", resp);
		}
		if(jspStr.equals("")){
			PubFun.ajaxPrint("alert('无法预览；请到\"模板管理\" 选定模板文件。');close();", resp);
			return null;
		}else{
			return jspStr;
		}
	}

}
