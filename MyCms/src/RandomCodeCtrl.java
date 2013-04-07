import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.RandomCode;
 
public class RandomCodeCtrl extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 protected void doGet(HttpServletRequest req,
	      HttpServletResponse resp) throws ServletException, IOException {
		  resp.setContentType("image/jpeg");
		  resp.setHeader("Pragma","No-cache");
		  resp.setHeader("Cache-Control","no-cache");
		  resp.setDateHeader("Expires", 0);
		  RandomCode rc = new RandomCode();
		  try{
			  rc.getRandcode(req,resp);
		  }catch(Exception e){
			  System.err.println(e);
		  }
	 }
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {
		 doGet(request, response);
	 }
}