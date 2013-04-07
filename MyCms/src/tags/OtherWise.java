package tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

//子类标签
public class OtherWise extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		Choose parent = (Choose) this.getParent(); // 获得父类标签对象
		if (!parent.isExcute()) { // 判断父类为false才输出
			this.getJspBody().invoke(null);
			parent.setExcute(false); // 把父类设置为false
		}
	}
}
