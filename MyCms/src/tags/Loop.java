package tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Loop extends SimpleTagSupport{
      private int cnt; 

      public void setCnt(int cnt) {
		this.cnt = cnt;
	}

      @Override
      public void doTag() throws JspException, IOException {
          JspFragment body=getJspBody();
          for(int i=0; i<this.cnt; i++){
        	  this.getJspContext().setAttribute("loopcnt", i+1);  //当前循环次数
              body.invoke(null);
          }
      }     

}

