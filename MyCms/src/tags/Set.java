package tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Set extends TagSupport {

	private static final long serialVersionUID = -1205253350861842335L;

	private String var;  //变量名
	private String value;//变量值
	
	public void setVar(String var) {
		this.var = var;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		pageContext.setAttribute(var, value);
		return SKIP_BODY;
	}

}
