package tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

//子类标签
public class When extends SimpleTagSupport {
	private boolean test;

	public void setTest(boolean test) {
		this.test = test;
	}

	@Override
	public void doTag() throws JspException, IOException {
		Choose parent = (Choose) this.getParent(); // 获得父类标签对象
		if (test && !parent.isExcute()) { // 判断父类为false那么自身为true才输出
			this.getJspBody().invoke(null);
			parent.setExcute(true); // 把父类设置为true
		}
	}
}
