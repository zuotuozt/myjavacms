package tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import util.InitServlet;

public class WebRoot extends TagSupport {

	private static final long serialVersionUID = -8163591616901328926L;

	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(InitServlet.WEB_SITE_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
}
