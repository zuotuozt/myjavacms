package page.col;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ColumnTable;
import util.Constant;
import page.inc.HtmlPage;
import util.PubFun;

public class ColEditPage extends HtmlPage {

	@Override //增加时有可能出现什么异常
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if(user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int parentId = getIntParameter("parentId", Constant.HOME_CLOUMN, req); 
		int colId = getIntParameter("colId", 0, req);
		String colName = getStringParameter("colName", "", req);
		if(colName.equals("")){
			PubFun.ajaxPrintStr("请输入栏目名称", resp);
			return null;
		}
		String htmlPath = getStringParameter("htmlPath", "", req);
		if(parentId!=0 && htmlPath.equals("")){
			PubFun.ajaxPrintStr("请输入文件保存目录", resp);
			return null;
		}
		int colType = getIntParameter("colType", 0, req);
		String colIntro = getStringParameter("colIntro", "", req);
		boolean isParent = getBooleanParameter("is_parent", false, req);
		String seoTitle = getStringParameter("seoTitle", "", req);
		boolean isParentSeoTitle = getBooleanParameter("is_parent_seo_title", false, req);
		String seoKeywords = getStringParameter("seoKeywords", "", req);
		boolean isParentSeoKeywords = getBooleanParameter("is_parent_seo_keywords", false, req);
		String seoDescription = getStringParameter("seoDescription", "", req);
		boolean isParentSeoDesc = getBooleanParameter("is_parent_seo_desc", false, req);
		if(parentId > 0){
			Col column = ColumnTable.loadColIntro(parentId);
			if(isParent){
				colIntro = column.getColIntro();
			}
			if(isParentSeoTitle){
				seoTitle = column.getSeoTitle();
			}
			if(isParentSeoKeywords){
				seoKeywords = column.getSeoKeywords();
			}
			if(isParentSeoDesc){
				seoDescription = column.getSeoDescription();
			}
		}
		String link = getStringParameter("link", "", req);
		String indexTemplate = getStringParameter("indexTemplate", "", req);
		String listTemplate = getStringParameter("listTemplate", "", req);
		String articleTemplate = getStringParameter("articleTemplate", "", req);
		Col col = new Col();
		col.setName(colName);
		if(parentId == 0){
			link = "/index.html";
		}else{
			Col parentCol = ColumnTable.loadParentInfo(parentId);
			htmlPath = parentCol.getLastHtmlpath() + "/" + htmlPath;
			if(colType == Constant.ARTICLES_CLOUMN){
				link = htmlPath + "/pages/1.html";
			}else if(colType == Constant.COVER_CLOUMN){
				link = htmlPath + "/index.html";
			}
		}
		col.setHtmlPath(htmlPath);
		col.setColIntro(colIntro);
		col.setSeoTitle(seoTitle);
		col.setSeoKeywords(seoKeywords);
		col.setSeoDescription(seoDescription);
		col.setColType(colType);
		col.setIndexTemplate(indexTemplate);
		col.setListTemplate(listTemplate);
		col.setArticleTemplate(articleTemplate);
		col.setLink(link);
		if(colId==0){//增加栏目
		   if(ColumnTable.isExistsByHtmlPath(htmlPath)){
				PubFun.ajaxPrintStr("该文件保存目录已经存在，请重新填写", resp);
				return null;
			}
			col.setParentid(parentId);
			ColumnTable.addColumn(col);
		}else{//修改栏目
			col.setId(colId);
			ColumnTable.updateColumn(col);
		}
		resp.sendRedirect("MainCtrl?page=ColManagePage&parentId=" + parentId);
		return null;
	}

}
