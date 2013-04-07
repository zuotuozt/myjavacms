package javabean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VelocityInfo{
	
	private String templateName;
	private String htmlName;
	private HttpServletResponse resp_;
	private HttpServletRequest req_;
/**********************************/	
	public String getHtmlName() {
		return htmlName;
	}
	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public HttpServletResponse getResp_() {
		return resp_;
	}
	public void setResp_(HttpServletResponse resp_) {
		this.resp_ = resp_;
	}
	public HttpServletRequest getReq_() {
		return req_;
	}
	public void setReq_(HttpServletRequest req_) {
		this.req_ = req_;
	}

}
