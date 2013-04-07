package javabean;

//插件：文章读后感表plugin_feeling
public class Feeling{
	
	private long articleId; //文章ID
	private long face1Cnt; //表情1
	private long face2Cnt; //表情2
	private long face3Cnt; //表情3
	private long face4Cnt; //表情4
	private long face5Cnt; //表情5
	private long face6Cnt; //表情6
	private String lastIp; //最后一次打分的IP地址
	
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	public long getFace1Cnt() {
		return face1Cnt;
	}
	public void setFace1Cnt(long face1Cnt) {
		this.face1Cnt = face1Cnt;
	}
	public long getFace2Cnt() {
		return face2Cnt;
	}
	public void setFace2Cnt(long face2Cnt) {
		this.face2Cnt = face2Cnt;
	}
	public long getFace3Cnt() {
		return face3Cnt;
	}
	public void setFace3Cnt(long face3Cnt) {
		this.face3Cnt = face3Cnt;
	}
	public long getFace4Cnt() {
		return face4Cnt;
	}
	public void setFace4Cnt(long face4Cnt) {
		this.face4Cnt = face4Cnt;
	}
	public long getFace5Cnt() {
		return face5Cnt;
	}
	public void setFace5Cnt(long face5Cnt) {
		this.face5Cnt = face5Cnt;
	}
	public long getFace6Cnt() {
		return face6Cnt;
	}
	public void setFace6Cnt(long face6Cnt) {
		this.face6Cnt = face6Cnt;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	
	
}
