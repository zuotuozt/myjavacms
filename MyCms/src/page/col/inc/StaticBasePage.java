package page.col.inc;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javabean.Article;
import javabean.Col;
import javabean.VelocityInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import table.ArticleTable;
import table.ColumnTable;
import page.inc.HtmlPage;
import util.Constant;
import util.InitServlet;
import util.PubFun;

public abstract class StaticBasePage extends HtmlPage {
	//发布封面栏目	
	protected String staticIndex(Col col, HttpServletRequest req, HttpServletResponse resp)
	throws IOException,	ServletException {
		String msg = "";
		File ff = new File(InitServlet.CONTENT_REALPATH + col.getIndexTemplate());
		if(!col.getIndexTemplate().equals("") && ff.exists()){
			VelocityInfo info = new VelocityInfo();
			info.setReq_(req);
			info.setResp_(resp);
			info.setTemplateName(col.getIndexTemplate() + "?id=" + col.getId());
			info.setHtmlName(InitServlet.WEB_SITE_PATH + col.getLink());
			File f = new File(InitServlet.WEB_SITE_PATH + col.getHtmlPath());
			if (!f.exists()) {
				f.mkdirs();
			}
			req.setAttribute("columnid", col.getId());
			req.setAttribute("seotitle", col.getSeoTitle());
			req.setAttribute("seokeywords", col.getSeoKeywords());
			req.setAttribute("seodescription", col.getSeoDescription());
			this.service(info);
		}else{
			msg = "发布失败；请到\"模板管理\" 选定封面模板文件。";
		}
		return msg;
	}
	//发布列表栏目所有分页; pageCnt: 0更新列表栏目所有页面,1只更新列表栏目首页。
	protected String staticList(Col col, HttpServletRequest req,
			HttpServletResponse resp, int pageCnt) throws Exception {
		VelocityInfo info = new VelocityInfo();
		info.setReq_(req);
		info.setResp_(resp);
		File f = new File(InitServlet.CONTENT_REALPATH + col.getListTemplate());
		File ff = new File(InitServlet.WEB_SITE_PATH	+ col.getHtmlPath() + "/pages");
		if (!ff.exists()) {
			ff.mkdirs();
		}
		String msg = "";
		if (f.exists()&&(!f.isDirectory())) {
			int pageSize = InitServlet.PAGE_SIZE;
			if (this.getThatProperty(f, "pagesize")!= null) {
				pageSize = Integer.parseInt(this.getThatProperty(f, "pagesize"));
			}
			int cnt = ArticleTable.loadAllCnt(col.getId());
			int totalPage = this.getTotalPage(cnt, pageSize);
			if (totalPage > 0) {
				ArrayList<Col> cols = new ArrayList<Col>();
				cols.add(col);
				getParents(col, cols);
				Collections.reverse(cols);
				req.setAttribute("parentcols", cols);
				int j = totalPage;
				if(pageCnt != 0 && pageCnt < totalPage) j = pageCnt;
				for (int i = 1; i <= j; i++) {
					info.setTemplateName(col.getListTemplate());
					info.setHtmlName(InitServlet.WEB_SITE_PATH
							+ col.getHtmlPath() + "/pages/" + i + ".html");
					req.setAttribute("path", InitServlet.WEB_SITE_URL
							+ col.getHtmlPath()+"/pages/");
					req.setAttribute("columnid", col.getId());
					req.setAttribute("seotitle", col.getSeoTitle());
					req.setAttribute("seokeywords", col.getSeoKeywords());
					req.setAttribute("seodescription", col.getSeoDescription());
					req.setAttribute("pageno", i);
					req.setAttribute("totalcnt", cnt);
					req.setAttribute("totalpage", totalPage);
					req.setAttribute("pagesize", pageSize);
					this.service(info);
				}
			}else{
				msg = "此列表栏目下没有可以发布的文章。";
				ff.delete();
			}
		}else{
			msg = "发布失败；请到\"模板管理\" 选定列表模板文件。";
		}
		return msg;
	}
	//发布文章
	protected String staticArticle(Article article,HttpServletRequest req,HttpServletResponse resp) throws Exception{
		VelocityInfo info = new VelocityInfo();
		info.setReq_(req);
		info.setResp_(resp);
		File ff = new File(InitServlet.WEB_SITE_PATH	+ article.getHtmlPath()
				+  "/" + PubFun.getDateTime("yyyy-MM-dd", article.getCreatime()));
		if(!ff.exists()){
			ff.mkdirs();
		}
		String msg = "";
		if((!article.getArticleTemplate().equals("")) && 
				new File(InitServlet.CONTENT_REALPATH + article.getArticleTemplate()).exists()){
			info.setTemplateName(article.getArticleTemplate()
					+ "?id=" + article.getId());
			info.setHtmlName(InitServlet.WEB_SITE_PATH
					+ article.getHtmlPath() + "/" 
					+ PubFun.getDateTime("yyyy-MM-dd", article.getCreatime())
					+ "/" + article.getId()+ ".html");
			req.setAttribute("article", article);
			req.setAttribute("columnid", article.getColumnid());
			req.setAttribute("seotitle", article.getSeoTitle());
			req.setAttribute("seokeywords", article.getSeoKeywords());
			req.setAttribute("seodescription", article.getSeoDescription());
			this.service(info);
		}else{
			msg = "发布失败；请到\"模板管理\" 选定文章模板文件。";
		}
		return msg;
	}
	protected void indexF(Article article) throws Exception {
	    File indexDir = new File(Constant.SEARCH_INDEX_PATH);
	    if(!indexDir.exists()) indexDir.mkdir();
	   	IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36));
	    conf.setOpenMode(OpenMode.APPEND);
	    IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), conf);
        indexWriter.updateDocument(new Term("id", String.valueOf(article.getId())),
        		PubFun.getLuceneDoc(article));
        indexWriter.forceMerge(1);
        indexWriter.close();
	}
	
	protected void indexF(Article[] articles) throws Exception {
	    File indexDir = new File(Constant.SEARCH_INDEX_PATH);
	    if(!indexDir.exists()) indexDir.mkdir();
	   	IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36));
	    conf.setOpenMode(OpenMode.APPEND);
	    IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), conf);
        for (int i = 0; i < articles.length; i++) {
            indexWriter.updateDocument(new Term("id", String.valueOf(articles[i].getId())),
            		PubFun.getLuceneDoc(articles[i]));
        }
        indexWriter.forceMerge(1);
        indexWriter.close();
	}
	
	protected ArrayList<Col> getParents(Col col,ArrayList<Col> cols) throws Exception{
		Col c = ColumnTable.loadColByIdForStatic(col.getParentid());
		if(c!=null){
			cols.add(c);
			getParents(c,cols);
		}
		return cols;
	}
	
	protected ArrayList<Col> getParentsCol(Col col,ArrayList<Col> cols) throws Exception{
		Col c = ColumnTable.loadColForPreview(col.getParentid());
		if(c!=null){
			cols.add(c);
			getParents(c,cols);
		}
		return cols;
	}
	
	protected String getThatProperty(File f,String property) throws IOException{
		String prop = this.getProperty(this.readFile(f), property);
		return prop;
	}
	
	private String readFile(File f) throws IOException {
		String ret = null;
		BufferedReader br = null;
		if(f.exists()&&(!f.isDirectory())){
			try {
				br = new BufferedReader(new FileReader(f));
				String line = null;
				StringBuilder sb = new StringBuilder((int) f.length());
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				ret = sb.toString();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (Exception e) {
					}
				}
			}
		}
		return ret;
	}
	
	private String getProperty(String s, String property) {
		String value = null;
		if (property != null && s != null) {
			int index = s.indexOf(property);
			int begin = s.indexOf("\"", index);
			int end = s.indexOf("\"", begin + 1);
			if (index != -1 && begin != -1 && end != -1) {
				value = s.substring(begin + 1, end);
			}
		}
		return value;
	}
	//模板jsp文件静态化成html文件
	private void service(VelocityInfo info)
	throws IOException, ServletException{
		int index = info.getTemplateName().indexOf("?");
		String name = index==-1?info.getTemplateName():info.getTemplateName().substring(0, index);
		File f = new File(InitServlet.CONTENT_REALPATH + name);
		if(f.exists()){
			info.getResp_().setContentType("text/html; charset=utf-8");
			RequestDispatcher rd = info.getReq_().getRequestDispatcher(info.getTemplateName());
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ServletOutputStream stream = new ServletOutputStream() {
				public void write(byte[] data, int offset, int length) {
					os.write(data, offset, length);
				}
			
				public void write(int b) throws IOException {
					os.write(b);
				}
			};
			final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"utf-8"));
			HttpServletResponse rep = new HttpServletResponseWrapper(info.getResp_()) {
				public ServletOutputStream getOutputStream() {
					return stream;
				}
			
				public PrintWriter getWriter() {
					return pw;
				}
			};
				rd.include(info.getReq_(), rep);
				pw.flush();
				FileOutputStream fos = new FileOutputStream(info.getHtmlName());
				os.writeTo(fos);
				fos.close();
				os.close();
				pw.close();
				stream.close();
		}		
	}
	
	protected ArrayList<Col> getChildren(int colId,ArrayList<Col> columns) throws Exception{
		ArrayList<Col> cols = ColumnTable.loadChildrenForStatic(colId);
		for(Col col:cols){
			columns.add(col);
			getChildren(col.getId(),columns);
		}
		
		return columns;
	}

}
