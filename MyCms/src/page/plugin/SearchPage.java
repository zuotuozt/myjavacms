package page.plugin;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import page.inc.HtmlPage;
import util.Constant;
import util.InitServlet;
import javabean.SearchResult;

public class SearchPage extends HtmlPage {

	@Override
	public String print(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
        File indexDir = new File(Constant.SEARCH_INDEX_PATH);
        String querrys = getStringParameter("search_txt", "", req);
	    int pageNo = getIntParameter("page_no", 1, req);
        if(querrys.equals("")){
        }else{
		    IndexReader reader = IndexReader.open(FSDirectory.open(indexDir));
		    IndexSearcher searcher = new IndexSearcher(reader); 
	        if(searcher != null){
		        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);          
		        String[] fields = {"title", "note", "content"}; 
		        String[] key = {querrys, querrys, querrys};
		        Query query = MultiFieldQueryParser.parse(Version.LUCENE_36, key, fields, analyzer);
	            TopDocs topDocs = searcher.search(query, 153866);
	            ScoreDoc[] hits = topDocs.scoreDocs;
	  	        if(hits.length > 0){
			        int sumPage = this.getTotalPage(hits.length, InitServlet.MESSAGE_PAGE_SIZE);
			        //高亮显示
			        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b style=\"color:rgb(204,102,0);\">","</b>");
			        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
	        		ArrayList<SearchResult> results = new ArrayList<SearchResult>();
	        		SearchResult s = null;
	        		String hightContent = null;
	        		Document doc = null;
	        		for(int i=(pageNo-1)*InitServlet.MESSAGE_PAGE_SIZE; i<pageNo*InitServlet.MESSAGE_PAGE_SIZE&&i<hits.length;i++){
	        			s = new SearchResult();
	        			doc = searcher.doc(hits[i].doc);
	        			//将高亮显示处理的字符串存储
	        			String content = doc.get("content");
	        			if(content==null || content.equals("")){
	        				s.setContent("");
	        				s.setCnt(0);
	        			}else{
	        				s.setCnt(content.length());
		        			TokenStream tokenStream = analyzer.tokenStream("content",  new StringReader(content));
		        			hightContent = highlighter.getBestFragment(tokenStream, content);
		        			if(hightContent==null){
		        				s.setContent(content);
		        			}else{
		        				s.setContent(hightContent);
		        			}
	        			}
	        		    s.setNote(doc.get("note"));
	        			TokenStream tokenStream = analyzer.tokenStream("title",  new StringReader(doc.get("title")));
	        			hightContent = highlighter.getBestFragment(tokenStream, doc.get("title"));
	        			if(hightContent==null){
	        				s.setTitle(doc.get("title"));
	        			}else{
	        				s.setTitle(hightContent);
	        			}
	        			s.setUrl(doc.get("url"));
	        			s.setYear(doc.get("year"));
	        			s.setMonth(doc.get("month"));
	        			s.setDay(doc.get("day"));
	        			s.setAuthor(doc.get("author"));
	        			results.add(s);
	        		}

	        		req.setAttribute("results", (SearchResult[]) results.toArray(new SearchResult[results.size()]));
					req.setAttribute("cnt", hits.length);
					req.setAttribute("sumPage", sumPage);
					req.setAttribute("pageSize", InitServlet.MESSAGE_PAGE_SIZE);
		        }
	        }
        }
        req.setAttribute("querrys", querrys);
		req.setAttribute("pageNo", pageNo);
		return "/jsp/plugin/search/search.jsp";
	}
	
}
