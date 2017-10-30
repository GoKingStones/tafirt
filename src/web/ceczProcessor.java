package web;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import model.PageDao;

import simhash.SimHash;
import simhash.SimHasher;
import text.TextExtractor;
import text.TextExtractor.Method;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import utils.SourceNER;
import utils.exception.ServiceException;

import static utils.Const.GENERAL_PAGE_INSERT;
import static utils.Const.GENERAL_PAGE_UPDATE;
import static utils.Const.NO_SLEEP_TIME;
import static utils.Const.RETRY_TIMES;
import static utils.Const.DEFAULT_SIM_DISTANCE;


/**
 * 中俄现代农业产业合作区 page processor
 * 
 */
public class ceczProcessor implements PageProcessor {
	private String domain;
	private int taskId;
	private String name;
	private String source;
	
	// 注意正则表达式中的.和？,以及网页中链接和URL的区别
	public static final String URL_LIST = "http://www\\.cecz\\.org/list_1(_\\d+)?\\.html";
	public static final String URL_POST = "http://www\\.cecz\\.org/article_1_\\d+\\.html";

	public ceczProcessor(String domain, String charset, int taskId, String name, String source) {
		super();
		this.domain = domain;
		this.taskId = taskId;
		this.name = name;
		this.source = source;     
        
		this.site = Site.me().setCharset(charset).setRetryTimes(RETRY_TIMES).setSleepTime(NO_SLEEP_TIME)
				.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	}

	private Site site;

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page){
		System.out.println("in processing...");

		Html html = page.getHtml();
		String htmlStr = html.toString();
//		System.out.println(htmlStr);

		// 列表页
//		System.out.println(page.getUrl());
		if (page.getUrl().regex(URL_LIST).match()) {
			System.out.println("in list");
//			System.out.println(htmlStr);
			List<String> linkurls = html.xpath("//div[@id='page']/a").replace("(?is)<.*?>", "").all();
//			for(String l:linkurls)
//				System.out.println(l);
			if(linkurls.contains("下一页")){
				System.out.println("new page");
				String newurl = "";
				String tmp = page.getUrl().toString();	
//				System.out.println(tmp);
				if(!tmp.contains("list_1_")){
					newurl = "http://www.cecz.org/list_1_2.html";
					System.out.println(newurl);
				}
				else{					
					String a = tmp.substring(tmp.indexOf("list_1_")+7,tmp.indexOf(".html"));
					int num = Integer.valueOf(a);
					num++;
					String b = tmp.substring(0,tmp.indexOf("list_1_")+7);
					newurl = b+num+".html";
					System.out.println(newurl);
				}
				page.addTargetRequest(newurl);
			}
			
	    	List<String> urls = html.xpath("//div[@id='right_content']//li/a").links().regex(URL_POST).all();
	    	// 要注意时间的格式！
	    	List<String> times = html.xpath("//div[@id='right_content']//li/span").regex("\\d{4}-\\d{1,2}-\\d{1,2}").all();
	    	List<String> names = html.xpath("//div[@id='right_content']//li/a").replace("(?is)<.*?>", "").all();
	    	System.out.println(urls.size());
	    	System.out.println(times.size());
	    	System.out.println(names.size());
//	    	System.exit(0);
	    	
			List<PageDao> insertList = new ArrayList<>();
	    	
//			System.out.println(urls.size());
			try {
				if(urls.size() != times.size() && names.size() != times.size())				
					throw new ServiceException("在 "+this.name+" 的新闻爬取中出现了问题");
			} catch (ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
			for(int i=0;i<urls.size();i++){

//				System.out.println(urls.get(i));
				String temp = urls.get(i);
//				System.out.println(temp);
				String url = temp;
				System.out.println(url);
//				System.exit(0);
				
				PageDao Page = new PageDao();
				int num=0;
				
				boolean flag = Page.dao().select(temp);
				if(flag){
					System.out.println(url + " already crawed!");
					continue;
				}
				else{
					try {
						PageDao generalPage = new PageDao();
//						System.out.println("new url");
						
						String postHtmlStr = Jsoup.connect(url).timeout(1000).get().toString();
						
						// 抽取算法
						Method method = Method.PunctuationDensity;
						String txt = TextExtractor.parse(postHtmlStr, method);
//						System.out.println(txt);
						if (!"".equals(txt)) {
//							System.out.println("begin setting");
							generalPage.set("body", txt);
//							System.out.println("end setting");
						}
						generalPage.set("SourceURL", urls.get(i));
						generalPage.set("NewsTitle", names.get(i));
						generalPage.set("task", this.taskId);
						generalPage.set("ZoneName", this.name);
						generalPage.set("source", this.source);
						generalPage.set("NewsTime", times.get(i));
						
//						System.out.println(generalPage.get("SourceURL"));
//						System.out.println(generalPage.get("NewsTitle"));
//						System.out.println(generalPage.get("NewsTime"));
//						System.out.println(generalPage.get("task"));
//						System.out.println(generalPage.get("body"));
						
						SimHasher sh = new SimHasher(txt);
						BigInteger signature = sh.getSignature();
						// simhash distance设置为3
						List<BigInteger> subsig = sh.subByDistance(sh, DEFAULT_SIM_DISTANCE);
						String subsigStr = subsig.toString().replace("[", "").replace("]", "").replace(" ", "");
//						System.out.println(subsig);
//						System.out.println(subsigStr);
						generalPage.set("signature", signature);
						generalPage.set("subsig", subsigStr);
//						System.out.println(generalPage.get("subsig"));
						
						/*
						 * 插入signature去重的代码和CRF的代码
						 */
						String title = names.get(i);
						SourceNER sourceNER = new SourceNER();
						List<String> result = new ArrayList<>();
						result = sourceNER.source(title);
						String source = result.toString().replace("[", "").replace("]", "").replace(" ", "");
						generalPage.set("SourceApartment", source);
						/*
						 * 不存在则执行insert语句，num = 1
						 */
						num = 1;
						generalPage.set("num", num);
						
//						System.out.println(generalPage.get("signature"));
//						System.out.println(generalPage.get("subsig"));
//						System.out.println("当前page："+generalPage.getStr("SourceURL")+":--------"+generalPage.getStr("NewsTitle"));
//						System.out.println(i);
//						System.exit(0);
						
						insertList.add(generalPage);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				page.putField(GENERAL_PAGE_INSERT, insertList);
//				System.out.println("insert size "+ insertList.size());
			}
		}
	}

	@Override
	public Site getSite() {
		return this.site;
	}
}
