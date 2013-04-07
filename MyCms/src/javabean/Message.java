package javabean;

import java.util.Date;

//插件：留言板表plugin_Message
public class Message{
	
	private long id; //主键ID
	private String title; //留言标题
	private String message; //留言信息
	private String reply; //回复信息
	private Date creatime; //留言时间
	private String ip; //留言来自的ip
	private Date replytime; //回复时间
	private int replyer; //回复人
	private boolean replied; //回复状态
	
	/******* 计算字段 ********/	
	private String replyName; // 回复人名字
	
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public Date getCreatime() {
		return creatime;
	}
	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getReplytime() {
		return replytime;
	}
	public void setReplytime(Date replytime) {
		this.replytime = replytime;
	}
	public int getReplyer() {
		return replyer;
	}
	public void setReplyer(int replyer) {
		this.replyer = replyer;
	}
	public boolean isReplied() {
		return replied;
	}
	public void setReplied(boolean replied) {
		this.replied = replied;
	}
	
		
}
