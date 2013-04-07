package page.plugin.feeling;

import javabean.Feeling;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import table.FellingTable;
import util.PubFun;
import page.inc.HtmlPage;

public class LoadFeelingPage extends HtmlPage {

	@Override //调文章表情打分的历史数据 
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		long articleId = getIntParameter("article_id", 0, req);
		Feeling feeling = null;
		if(articleId > 0) feeling = FellingTable.loadFeeling(articleId);
		String ajaxStr = "0,0,0,0,0,0";
		if(feeling == null){
			FellingTable.insertFeeling(articleId);
		}else{
			ajaxStr = feeling.getFace1Cnt() + "," + feeling.getFace2Cnt() + ","
					+ feeling.getFace3Cnt() + "," + feeling.getFace4Cnt() + ","
					+ feeling.getFace5Cnt() + "," + feeling.getFace6Cnt();			
		}
		PubFun.ajaxPrintC(ajaxStr, resp);

		return null;
	}

}
