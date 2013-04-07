package page.plugin.feeling;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import table.FellingTable;
import util.PubFun;
import page.inc.HtmlPage;

public class UpdateFeelingPage extends HtmlPage {

	@Override //调文章表情打分的历史数据 
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		long articleId = getIntParameter("article_id", 0, req);
		int i = getIntParameter("i", 0, req);
		String ajaxStr = "感谢您的投票。";
		if(i > 0){
			String lastIp = "0";
			if(articleId > 0) lastIp = FellingTable.loadLastip(articleId);
			if(lastIp.equals(getRemortIP(req))){
				ajaxStr = "您已经投过票了。";
			}else{
				String sqlStr = "update plugin_feeling set face"+i+"_cnt=face"+i
						+"_cnt+1,last_ip='"+getRemortIP(req)+"' where article_id=?";
				FellingTable.updateFeeling(articleId, sqlStr);		
			}
		}else{
			ajaxStr = "请您选择要投的票。";
		}
		PubFun.ajaxPrintC(ajaxStr, resp);

		return null;
	}

}
