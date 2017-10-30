package controller;

import static utils.Const.DEFAULT_THREADS_NUM;
import static utils.Const.ERR;
import static utils.Const.OK;
import static utils.Const.URL_REGEX;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;

import model.TaskDao;
import model.Websites;


import service.TaskService;
import service.TaskServiceImpl;
import us.codecraft.webmagic.Spider;
import utils.getCharset;
import utils.exception.ServiceException;
import web.GeneralPipeline;
import web.TestPipeline;
import web.GeneralProcessor;
import web.TestPipeline;
import web.calekkiProcessor;
import web.ceczProcessor;
import web.decentProcessor;
import web.eeizProcessor;
import web.huaxinProcessor;
import web.jlaiczProcessor;
import web.mdjlyProcessor;
import web.mlqsProcessor;
import web.pengshengProcessor;
import web.setcProcessor;
import web.sinothaiProcessor;
import web.ssezProcessor;
import web.testProcessor;
import web.zcczProcessor;
import web.zfnigProcessor;

/**
 * crawler task
 * 
 */
public class CrawController extends Controller implements Runnable{
	/**
	 * 从url中获取域名;<br/>
	 * 从https://www.baidu.com/得到baidu.com<br/>
	 * 
	 * @return
	 */
	public static String getDomain(String url) {
		if (!url.endsWith("/")) {
			url += "/";
		}
		int doubleSlashesIndex = url.indexOf("//");
		int start = doubleSlashesIndex + 2;
		int slashIndex = url.indexOf("/", start);
		return url.substring(start, slashIndex);
	}

	/**
	 * 定向网址抓取 ##输入一个或者是多个目标网址的域名,全站抓取网站的正文(如果页面内的链接不在该网站内,则不会抓取);
	 * 
	 * @param seeds
	 * @return
	 * @throws ServiceException 
	 */
	public void index() throws ServiceException{
//		System.out.println("going into run");
		run();
		renderText("OK");
	}

	@Override
	public void run() {
//		System.out.println("in run");
		
		Websites web = new Websites();
		
		getCharset gc = new getCharset();
		List<Websites> list = web.selectAll();
		
		GeneralPipeline generalPipeline = new GeneralPipeline();
		TestPipeline testPipeline = new TestPipeline();
		TaskService taskService = new TaskServiceImpl();
		//百度
		for(int i = 0;i<list.size();i++){
			Websites tempWebsites = list.get(i);
			String zone =  tempWebsites.getStr("cooperation_name");
			String official_url = tempWebsites.getStr("cooperation_url");
			String url = "http://www.baidu.com.cn/s?wd=" + zone + "&tn=22073068_3_oem_dg";
			
			TaskDao task = new TaskDao();
			String source = "百度";
			task.set("name", zone);
			task.set("url", url);
			Date date = new Date();//获得系统时间.
			String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.
			Timestamp time = Timestamp.valueOf(nowTime);//把时间转换
			task.set("time", time);
			boolean flag;
			try {
//				System.out.println("going into task save ");
				flag = taskService.save(task);
//				System.out.println("task save complete");

				if(flag){
					int taskId = taskService.find(task);
					System.out.println(taskId);
//					String target = task.getStr("url");
//					String name = task.getStr("name");
					String domain = getDomain(url);				
					String charset = gc.getUrlCharset(url);
					Spider.create(
							new GeneralProcessor(domain, charset,
									taskId, zone, source, official_url))
							.addUrl(url)
							.addPipeline(generalPipeline)
							.thread(DEFAULT_THREADS_NUM).run();
				}
			}
			catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		


		for(int i=0;i<list.size();i++){
			if(list.get(i).get("news_url")!=null){
				Websites temp = list.get(i);
				String url = temp.getStr("news_url");
				for(String tmpurl:url.split(",")){
					// 网页打开速度太慢，严重超时
					if(tmpurl.equals("http://ljip.vn/web/zh/news-center/"))
						continue;
					TaskDao task = new TaskDao();
					String source = "官方网站";
		//			System.out.println(temp.getStr("news_url"));
		//			System.out.println(temp.getStr("cooperation_name"));
					task.set("name", temp.getStr("cooperation_name"));
					task.set("url", tmpurl);
					Date date = new Date();//获得系统时间.
					String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.
					Timestamp time = Timestamp.valueOf(nowTime);//把时间转换
		//			Date time= new java.sql.Date(new java.util.Date().getTime());
					task.set("time", time);
					System.out.println("name: "+ task.get("name"));
					System.out.println("time: "+ task.get("time"));
					
					boolean flag;
					try {
		//				System.out.println("going into task save ");
						flag = taskService.save(task);
		//				System.out.println("task save complete");
		
						if(flag){
							int taskId = taskService.find(task);
		//					System.out.println(taskId);
							String target = task.getStr("url");
							String name = task.getStr("name");
							String domain = getDomain(target);				
							String charset = gc.getUrlCharset(target);
		//					System.out.println(charset);
							
							if(tmpurl.equals("http://www.setc-zone.com/xwzx/"))
								Spider.create(
										new setcProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://zccz.cnmc.com.cn/outlinetem.jsp?outlinetype=1&column_no=070401"))
								Spider.create(
										new zcczProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.e-eiz.com/news.asp?pagetitle=%E5%9B%AD%E5%8C%BA%E6%96%B0%E9%97%BB"))
								Spider.create(
										new eeizProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.calekki.com/xwzx/newsCategoryId=9.html"))
								Spider.create(
										new calekkiProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.zfnig.com/cn/News.aspx?page=1"))
								Spider.create(
										new zfnigProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.mlqsydy.com/newslist/1923"))
								Spider.create(
										new mlqsProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.ssez.com/News.asp?None=3"))
								Spider.create(
										new ssezProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.matches("http://www\\.sinothaizone\\.com/news\\.php\\?cid=\\d+"))
								Spider.create(
										new sinothaiProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.jlaicz.com/news.html"))
								Spider.create(
										new jlaiczProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.decent-china.com/index.php/Channel/Index/id/179.html"))
								Spider.create(
										new decentProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.mdjly.cn/index.php?p=news_list&lanmu=4"))
								Spider.create(
										new mdjlyProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.huaxin-agri.com/cn/News.Asp"))
								Spider.create(
										new huaxinProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.matches("http://www\\.pengshenguz\\.com/news/.+News/"))
								Spider.create(
										new pengshengProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							if(tmpurl.equals("http://www.cecz.org/list_1.html"))
								Spider.create(
										new ceczProcessor(domain, charset,
												taskId, name, source))
										.addUrl(target)
										.addPipeline(generalPipeline)
										.thread(DEFAULT_THREADS_NUM).run();
							
							else 
								break;						
						}
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		
	}
}

