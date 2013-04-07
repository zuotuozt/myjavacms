package page.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import table.FellingTable;
import util.PubFun;
import page.inc.HtmlPage;

public class LoadBrowseCntPage extends HtmlPage {

	@Override //显示文章浏览次数 
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		long articleId = getIntParameter("article_id", 0, req);
		long browseCnt = 0;
		if(articleId > 0) browseCnt = FellingTable.loadBrowseCnt(articleId);
		if(browseCnt == -1){
			FellingTable.insertFeeling(articleId);
		}else{
			FellingTable.updateBrowseCnt(articleId);		
		}
		PubFun.ajaxPrintC(String.valueOf(browseCnt+1), resp);

		return null;
	}

}
