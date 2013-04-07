package timetask;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;

import javabean.Article;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import table.ArticleTable;
import util.Constant;
import util.PubFun;

public class IndexTask extends TimerTask {

	private static boolean isRunning = true;  
	@Override
	public void run() {
		if(isRunning){
			try {
				indexF();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	 private void indexF() throws Exception {
	    File indexDir = new File(Constant.SEARCH_INDEX_PATH);
	    if(!indexDir.exists()) indexDir.mkdir();
	   	IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36));
	    conf.setOpenMode(OpenMode.CREATE);
	    IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), conf);
        Article[] articles  = ArticleTable.loadArticlesForSearchIndex();
        System.out.println("articles 共有 "+articles.length+" 条");
        long startTime = new Date().getTime();  
        //增加document到索引去 
        for (int i = 0; i < articles.length; i++){
            indexWriter.addDocument(PubFun.getLuceneDoc(articles[i]));   
        }
        indexWriter.forceMerge(1);
        indexWriter.close();
          
        //测试一下索引的时间  
        long endTime = new Date().getTime();  
        System.out.println("这花费了"  
                        + (endTime - startTime)  
                        + " 毫秒来把文档增加到索引里面去!" );
	}

}
