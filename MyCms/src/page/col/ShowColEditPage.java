package page.col;

import java.io.File;

import javabean.Col;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.ColumnTable;
import util.Constant;
import util.InitServlet;
import page.col.inc.TreeColPage;

public class ShowColEditPage extends TreeColPage {

	@Override
	/**
	 * 根据增加顶级栏目或者次级栏目的不同，返回页面不同的值
	 * **/
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if((user == null)) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		int colId = getIntParameter("colId",0,req);
		int parentId = getIntParameter("parentId", -1, req);
		Col col = new Col();
        if(parentId == -1){
        	col = ColumnTable.loadColForModify(colId);
        }else{//增加栏目
    		StringBuilder display = new StringBuilder("<div>&nbsp;</div>");
    		display = getTrees(Constant.TOP_CLOUMN_TREE, display, req);
    		req.setAttribute("selTxt2", display.append("<div class=\"floatline\"></div><div>&nbsp;</div>").toString());
       		col = ColumnTable.loadParentInfo(parentId);
       		col.setColType(-1);
        	col.setParentid(parentId);
        }
        req.setAttribute("col", col);
		StringBuilder display = new StringBuilder("<div>&nbsp;</div>");
		File f = new File(InitServlet.CONTENT_REALPATH + Constant.TEMPLATE_BASEPATH);
		display = getTrees(f, display, req);
		req.setAttribute("selTxt", display.append("<div class=\"floatline\"></div><div>&nbsp;</div>").toString());
        return "/jsp/col/edit_col.jsp";
     
	}
	
	protected StringBuilder getTrees(File f,StringBuilder display,HttpServletRequest req) throws Exception{
		File[] files = f.listFiles();
		String templatePath;
		for(int i = 0;i < files.length; i++){
			templatePath = getFilePath(files[i].getAbsolutePath());
			display.append("<div style=\"text-align:left;font-size:12px;\">");
			for(int j=0; j<this.getlevel(templatePath); j++){
				display.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			if(files[i].isDirectory()){
				display.append("<a href=\"javascript:controlTree('"+files[i].hashCode()+"','file');\"> ");
				display.append("<img src=\""+req.getContextPath()+"/img/tree/plusbottom.gif\" id=\"src2_"+files[i].hashCode()+"\" border=\"0px\"/>");
				display.append("<img src=\""+req.getContextPath()+"/img/tree/folder.gif\" id=\"src_"+files[i].hashCode()+"\" border=\"0px\"/>");
				display.append(files[i].getName()+"</a>");				
				display.append("</div><div id=\""+files[i].hashCode()+"\" style=\"display:none;\">");
				getTrees(files[i],display,req);
				display.append("</div>");
			}else{
				display.append("<a href=\"javascript:;\"> ");
				display.append("<img src=\""+req.getContextPath()+"/img/tree/joinbottom.gif\" border=\"0px\"/>");
				display.append("<img src=\""+req.getContextPath()+"/img/tree/page.gif\" border=\"0px\"/>");
				display.append(files[i].getName()+"</a>");
				display.append("&nbsp;&nbsp;<input type=\"checkbox\"/ ");
				display.append("onclick=\"selectColumnTemplate('"+templatePath+"',this);return false;\" ></div>");
			}
		}
		return display;
	}
	
	private String getFilePath(String absolutePath){
		String path = absolutePath;
		path = path.replaceAll("\\\\", "/");
		path = path.substring(path.indexOf(Constant.TEMPLATE_BASEPATH.substring(1))-1);
		return path;
	}
	
	protected StringBuilder getTrees(int parentId,StringBuilder display,HttpServletRequest req) throws Exception{
		//取出id的子频道
		Col[] colTrees = null;
		UserInfo user = getSessionUser(req);
		if(user.getName().equals(Constant.CHIEF_EDITOR) || user.getName().equals(Constant.SUPER_USER)
				|| parentId != Constant.HOME_CLOUMN){
			colTrees = ColumnTable.loadColumnsForTree(parentId);
		}else{
			colTrees = ColumnTable.loadColumnsForHomeTree(user.getId());
		}
		for(int i = 0;i < colTrees.length; i++){
			if(colTrees[i].getColType()!=Constant.EXTERNAL_LINK_CLOUMN){
				colTrees[i].setLevel(this.getlevel(colTrees[i].getHtmlPath()));
				display.append("<div style=\"text-align:left;font-size:12px;\">");
				for(int j=0; j<colTrees[i].getLevel(); j++){
					display.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				}
				if(colTrees[i].getColType()==Constant.ARTICLES_CLOUMN || colTrees[i].getColType()==Constant.COVER_CLOUMN){
					display.append("<a href=\"javascript:controlTree('"+colTrees[i].getId()+"');\">");
					display.append("<img src=\""+req.getContextPath()+"/img/tree/plusbottom.gif\" id=\"src2_"+colTrees[i].getId()+"\" border=\"0px\"/>");
					display.append("<img src=\""+req.getContextPath()+"/img/tree/folder.gif\" id=\"src_"+colTrees[i].getId()+"\" border=\"0px\"/>");
					display.append(colTrees[i].getName()+"</a>");
				}else{
					display.append("<img src=\""+req.getContextPath()+"/img/tree/joinbottom.gif\" id=\"src2_"+colTrees[i].getId()+"\"/>");
					display.append("<img src=\""+req.getContextPath()+"/img/tree/page.gif\" id=\"src_"+colTrees[i].getId()+"\"/>");
					display.append(colTrees[i].getName());				
				}
	
				display.append("&nbsp;&nbsp;<input type=\"checkbox\" ");
				display.append("onclick=\"selectColumnPublish("+colTrees[i].getId()+",'"+colTrees[i].getName()+"');return false;\" ");
				display.append("id=\"cb" + colTrees[i].getId()+"\" /></div>");
				display.append("<div id=\""+colTrees[i].getId()+"\" style=\"display:none;\">");
				getTrees(colTrees[i].getId(),display,req);
				display.append("</div>");
			}
		}
		return display;
	}

}
