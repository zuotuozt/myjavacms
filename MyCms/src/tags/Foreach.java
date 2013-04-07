package tags;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Foreach extends SimpleTagSupport  {

	 private Object items; //定义对象
	 private String var;
	 
	 public void setItems(Object items) {
	    this.items = items;
	 }
	 public void setVar(String var) {
	    this.var = var;
	 }
	 
	 @SuppressWarnings("unchecked")
	@Override
	 public void doTag() throws JspException, IOException {
		 List<Object> list = (List<Object>) items;
		 Iterator<Object> it = list.iterator();   //获得迭代器
		 int currentcnt = 0;
		 this.getJspContext().setAttribute("cnt", list.size());  //写出:循环次数
		 while(it.hasNext()){
			 currentcnt++;
			 Object obj = it.next();
			 this.getJspContext().setAttribute(var, obj);  //每遍历出数据后写出
			 this.getJspContext().setAttribute("currentcnt", currentcnt);  //每遍历出数据后写出:当前循环次数
			 this.getJspBody().invoke(null);
		}
	 }


	
}
