package tags;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class FormatDateStr extends TagSupport {

	private static final long serialVersionUID = -1205253350861842335L;

	private String datetime;
	private String format;

	 public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	 
	public int doStartTag() throws JspException {
		if(format == null || format.equals("")){
			format = "yyyy-MM-dd HH:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			if(datetime == null || datetime.equals("")){
				Calendar c = Calendar.getInstance();
				datetime = sdf.format(c.getTime());
			}else{
				datetime = sdf.format(sdf.parse(datetime));
			}
			pageContext.getOut().write(sdf.format(sdf.parse(datetime)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

}
