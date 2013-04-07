package timetask;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import timetask.IndexTask;

public class CMSListener implements ServletContextListener {
	public static final long DELAY = 2*1000;
	private Timer timer;   

	public void contextDestroyed(ServletContextEvent arg0) {
		 timer.cancel();
	}


	public void contextInitialized(ServletContextEvent arg0) {
		timer = new Timer("更新",true);    
	    //安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。
		timer.schedule(new IndexTask(), DELAY, 24*60*60*1000); 
	}
	
	

}
