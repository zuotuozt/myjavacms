package tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

//父类标签
public class Choose extends SimpleTagSupport {
	private boolean isExcute;
	 
	 public boolean isExcute() {
	  return isExcute;
	 }

	 public void setExcute(boolean isExcute) {
	  this.isExcute = isExcute;
	 }

	 @Override
	 public void doTag() throws JspException, IOException {
		 this.getJspBody().invoke(null);
	 }

}


