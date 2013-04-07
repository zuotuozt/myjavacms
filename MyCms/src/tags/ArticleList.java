package tags;

import java.io.IOException;

import javabean.Article;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import table.ArticleTable;
import util.InitServlet;

public class ArticleList extends BodyTagSupport {

	private static final long serialVersionUID = -4901630247234341946L;
	private String columnid;//表示栏目ID，在列表模板和文档模板中一般不需要指定；默认为当前的栏目。
	private String pageno;//表示开始显示的页数，默认值：1；
	private String begin;//表示开始显示数，从1开始；和pageno只能有一个生效；如果有值，pageno参数将失效；
	private String pagesize;//表示显示数据条数（默认为5）；
	private String notelen;//表示摘要长度，也就是文章简介；最多255个汉字。默认为总长度
	private String titlelen;//表示标题长度；默认为总长度
	private String orderby;//表示排序方式，默认值是creatime排列。格式：orderby="creatime,title desc"
	private Article[] articles;
	private int i = 0;

	@Override
	public int doAfterBody() throws JspException {
		int pz = Integer.parseInt(pagesize);
		if(articles.length>0){
			if(i<pz&&i<articles.length){
				pageContext.setAttribute("article", articles[i]);
				i++;
				pageContext.setAttribute("currentcnt", i);
				return EVAL_BODY_AGAIN;
		    }else{
		    	return SKIP_BODY;	
		    }
		}else{
			return SKIP_BODY;
		}       
	}
	
	@Override
	public int doEndTag() throws JspException {
		if(articles.length>0){
			try {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}			
		articles = null;
		columnid= null;
		i=0;
		pageno = null;
		pagesize = null;
		orderby = null;
		notelen = null;
		begin = null;
		titlelen = null;

		return SKIP_BODY; 
	}
	
	@Override
	public int doStartTag() throws JspException {
        int ci = 0;
		if(columnid==null||columnid.equals("")){
			ci = (Integer)pageContext.getRequest().getAttribute("columnid");
		}else{
			ci = Integer.parseInt(columnid);
		}
		if(pagesize==null||pagesize.equals("")){
			pagesize=String.valueOf(InitServlet.PAGE_SIZE);
		}
		if(notelen==null||notelen.equals("")){
			notelen="0";
		}
		if(titlelen==null||titlelen.equals("")){
			titlelen="0";	
		}
		if(orderby==null||orderby.equals("")){
			orderby="is_top desc,ordercnt desc,creatime desc";
		}else{
			orderby = orderby.replaceAll("id", "a.id").replaceAll("top", "is_top");
		}
		if(begin==null||begin.equals("")){
			begin="0";
		}

        int pz = Integer.parseInt(pagesize);
        int il = Integer.parseInt(notelen);
        int tl = Integer.parseInt(titlelen);
        int pn = Integer.parseInt(begin);
        if(pn>0){
        	pn-=1;
        }else{
    		if(pageno==null||pageno.equals("")){
    			if(pageContext.getRequest().getAttribute("pageno")==null){
    				pn = 0;
    			}else{
        			pn = ((Integer)pageContext.getRequest().getAttribute("pageno")-1)*pz;   				
    			}
    		}else{
    			pn=(Integer.parseInt(pageno)-1)*pz;
    		}
        }         
  
        if(ci>0&&pz>0&&il>=0&&tl>=0){
        	try {
				articles = ArticleTable.loadArticlesForStatic(pn,pz,ci,orderby,il,tl);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		
		if(articles.length>0){
			if(i<pz&&i<articles.length){
				pageContext.setAttribute("article", articles[i]);
				i++;
				pageContext.setAttribute("currentcnt", i);
		    }
			return EVAL_BODY_AGAIN;
		}else{
			return SKIP_BODY; 
		}		
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}
	public void setPageno(String pageno) {
		this.pageno = pageno;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public void setNotelen(String notelen) {
		this.notelen = notelen;
	}
	public void setTitlelen(String titlelen) {
		this.titlelen = titlelen;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public void setArticles(Article[] articles) {
		this.articles = articles;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}	
	
	
}
