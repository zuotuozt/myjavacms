package page.fragment;

import java.io.File;

import javabean.Fragment;
import javabean.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.FragmentTable;
import util.Constant;
import page.inc.HtmlPage;
import util.InitServlet;
import util.PubFun;

public class GenerateAllFragments extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserInfo user = getSessionUser(req);
		if (user == null) {
			resp.sendRedirect(Constant.REDIRECT_LOGIN_PAGE);
			return null;
		}
		Fragment[] frag = FragmentTable.loadFragmentsForGenerate();
		if(frag.length>0){
			String path = InitServlet.WEB_SITE_PATH + Constant.FRAGMENT_PATH ;
			File f = new File(path);
			if(!f.exists()){
				f.mkdir();
			}
			for(int i=0;i<frag.length;i++){
				stringToFile(path + "/" + frag[i].getId() + ".js", "document.write('" 
						+ PubFun.getHtmlFilterValue(frag[i].getContent()) + "');");	
			}
			PubFun.ajaxPrintStr("成功生成所有碎片。", resp);
		}else{
			PubFun.ajaxPrintStr("没有可生成的碎片。", resp);
		}
		
		return null;
	}
    
}
