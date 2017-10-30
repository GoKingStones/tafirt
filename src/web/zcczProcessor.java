package web;

import static utils.Const.DEFAULT_SIM_DISTANCE;
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


/**
 * 赞比亚中国经贸合作区 page processor
 * 
 */
public class zcczProcessor implements PageProcessor {
	private String domain;
	private int taskId;
	private String name;
	private String source;
	
	// 注意正则表达式中的.和？,以及网页中链接和URL的区别
	public static final String URL_LIST = "http://zccz\\.cnmc\\.com\\.cn/outlinetem\\.jsp\\?outlinetype=1&column_no=070401(&topage=\\d*)?";
	public static final String URL_POST = "detail\\.jsp\\?article_millseconds=\\d+&column_no=\\d+";

	public zcczProcessor(String domain, String charset, int taskId, String name, String source) {
		super();
		this.domain = domain;
		this.taskId = taskId;
		this.name = name;
		this.source = source;
		
		this.site = Site.me().setCharset(charset).setRetryTimes(RETRY_TIMES)
				.setSleepTime(NO_SLEEP_TIME);
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
			
			List<String> linkurls = html.xpath("//div[@class='rightSec']//td[@class='page']/a").replace("(?is)<.*?>", "").all();
			if(linkurls.contains("下一页")){
				System.out.println("new page");
				String newurl = "";
				String tmp = page.getUrl().toString();	
				System.out.println(tmp);
				if(!tmp.contains("topage")){
					newurl = tmp +"&topage=2";
//					System.out.println(newurl);
				}
				else{					
					String a = tmp.substring(tmp.lastIndexOf("=")+1);
					int num = Integer.valueOf(a);
					num++;
					String b = tmp.substring(0, tmp.lastIndexOf("=")+1);
					newurl = b+num;
					System.out.println(newurl);
				}
				page.addTargetRequest(newurl);
			}
			
	    	List<String> urls = html.xpath("//div[@class='rightSec']//td[@align='left']").links().regex(URL_POST).all();
	    	List<String> times = html.xpath("//div[@class='rightSec']//td").regex("\\d{4}-\\d{1,2}-\\d{1,2}").all();
	    	List<String> names = html.xpath("//div[@class='rightSec']//td[@align='left']/a").replace("(?is)<.*?>", "").all();
//	    	System.out.println(urls.size());
//	    	System.out.println(times.size());
//	    	System.out.println(names.size());
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
				// detail.jsp?article_millseconds=1356873653468&column_no=070401
				// http://zccz.cnmc.com.cn/detailtem.jsp?column_no=070401&article_millseconds=1356873653468
				String temp = urls.get(i);
				String article = temp.substring(temp.indexOf("=")+1, temp.indexOf("&"));
				String column = temp.substring(temp.lastIndexOf("=")+1);
				String url = "http://zccz.cnmc.com.cn/detailtem.jsp?column_no="+column+"&article_millseconds="+article;
							
				PageDao Page = new PageDao();
				int num=0;
				
				boolean flag = Page.dao().select(url);
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
						generalPage.set("SourceURL", url);
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
//				if(i==2)
//					break;
			}
		}
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
