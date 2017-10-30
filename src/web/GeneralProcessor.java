package web;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.enterprise.inject.New;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import controller.CrawController;

import model.PageDao;
import model.Websites;

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
import utils.getCharset;
import utils.exception.ServiceException;

import static utils.Const.GENERAL_PAGE_INSERT;
import static utils.Const.GENERAL_PAGE_UPDATE;
import static utils.Const.NO_SLEEP_TIME;
import static utils.Const.RETRY_TIMES;
import static utils.Const.DEFAULT_SIM_DISTANCE;


/**
 * general page processor
 * 
 */
public class GeneralProcessor implements PageProcessor {
	private String domain;
	private int taskId;
	private String name;
	private String source;
	private String official_url;
	
	public static final String URL_LIST = "http://www\\.baidu\\.com\\.cn/s\\?wd=.*(&pn=\\d+)?.*&tn=22073068_3_oem_dg";
	
	public static final String[] excludeList = new String[]{"baike.baidu.com","zhidao.baidu.com","www.docin.com","xueshu.baidu.com",
		"question","wenku.baidu.com","tieba.baidu.com","ganji.com","aseby.essc.fengj.com/","doc.mbalib.com",
		"jobui.com","company.zhaopin.com"};

	public GeneralProcessor(String domain, String charset, int taskId, String name, String source, String official_url) {
		super();
		this.domain = domain;
		this.taskId = taskId;
		this.name = name;
		this.source = source;
		this.official_url = official_url;
		
		this.site = Site.me().setCharset(charset).setRetryTimes(RETRY_TIMES)
				.setSleepTime(NO_SLEEP_TIME).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) " +
						"AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");;
	}

	private Site site;

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page){
		System.out.println("in processing...");

		Html html = page.getHtml();

		// 列表页
		// 控制搜索结果的爬取页数
		int page_num = 0; 
		int page_MAX = 20;
		int page_size = 10;		
		if (page.getUrl().regex(URL_LIST).match()) {
			System.out.println("in list");
			List<String> linkurls = html.xpath("//div[@id='page']/a").replace("(?is)<.*?>", "").all();
//			for(String l:linkurls)
//				System.out.println(l);
						
			if(linkurls.contains("下一页&gt;")){
				System.out.println("new page");
				String newurl = "";
				String tmp = page.getUrl().toString();	
//				System.out.println(tmp);
								
				if(!tmp.contains("&pn=")){
					String a = tmp.substring(0,tmp.lastIndexOf("&"));
					newurl = a+"&pn=10&tn=22073068_3_oem_dg";
					System.out.println(newurl);
				}
				else{		
					int a = Integer.valueOf(tmp.substring(tmp.indexOf("&pn=")+4,tmp.lastIndexOf("&")));
					page_num = (a/page_size)+1;
					if(page_num<page_MAX){
//						System.out.println(page_num);
						int num = page_num*page_size;		
//						System.out.println(num);
						String b = tmp.substring(0,tmp.indexOf("&pn=")+4);
						newurl = b+num+"&tn=22073068_3_oem_dg";
						System.out.println(newurl);
					}
				}
				page.addTargetRequest(newurl);
			}
			
			// xpath不支持属性值中间有空格，所以用正则表达式解决无法选取的问题
			// 时间的div所在的位置不固定，所以用'//'
	    	List<String> links = html.xpath("//div[@class~='result.{1}c-container.{1}']/h3/a").links().all();
	    	List<String> abstracts = html.xpath("//div[@class~='result.{1}c-container.{1}']//div[@class='c-abstract']").all();
	    	List<String> timeall = html.xpath("//div[@class~='result.{1}c-container.{1}']//div[@class='c-abstract']/span").regex(("\\d{4}年\\d{1,2}月\\d{1,2}日")).all();
	    	List<String> nameall = html.xpath("//div[@class~='result.{1}c-container.{1}']/h3/a").replace("(?is)<.*?>", "").all();
	    	//处理时间格式
	    	for(int i=0;i<timeall.size();i++){
	    		String time = timeall.get(i).replace("年", "-").replace("月", "-").replace("日", "");
	    		timeall.set(i, time);
	    	}
	    	//处理时间可能不存在的问题
	    	for(int i=0;i<abstracts.size();i++){
	    		if(!abstracts.get(i).contains("newTimeFactor_before_abs m")){
	    			timeall.add(i,null);
	    		}
	    	}
	    	
	    	List<String> urls = new ArrayList<String>();
	    	List<String> names = new ArrayList<String>();
	    	List<String> times = new ArrayList<String>();
	    	List<String> official = new ArrayList<String>();
	    	// 获取官网网址
			Websites web = new Websites();			
			List<Websites> list = web.selectAll();
			for(Websites li:list){
				String urlString = li.getStr("cooperation_url");
				if(urlString!=null && urlString!=""){
					official.add(CrawController.getDomain(urlString));
				}
			}
					    	
	    	//处理链接加密问题以及去除部分不需要的链接
	    	for(int i=0;i<links.size();i++){
	        	Connection.Response res;
				try {
					res = Jsoup.connect(links.get(i)).timeout(60000).method(Connection.Method.GET).followRedirects(false).execute();
					String str= res.header("Location");
					boolean flag = false;
					for(String t:excludeList){
						if(str.contains(t)){
							flag = false;
							break;
						}
						else{
							flag = true;
						}
					}
					// 只有在链接不包含上述域名的时候才有必要做这步，如果不做这个判断会修改flag值
					if(flag){
						for(String t:official){
							if(str.contains(t)){
								flag = false;
								break;
							}
							else{
								flag = true;
							}
						}
					}
					if(flag){
						urls.add(str);
						times.add(timeall.get(i));
						names.add(nameall.get(i));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       	
	    	}

			System.out.println(urls.size());
			System.out.println(times.size());
			System.out.println(names.size());
			try {
				if(urls.size() != times.size() && names.size() != times.size())				
					throw new ServiceException("在中非泰达产业园的新闻爬取中出现了问题");
			} catch (ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
//			for(int i=0;i<urls.size();i++){			
//				System.out.println(times.get(i));
//				System.out.println(names.get(i));
//				System.out.println(urls.get(i));
//				System.out.println("===========================");
//			}
			List<PageDao> insertList = new ArrayList<>();
			List<PageDao> updateList = new ArrayList<>();
			
			for(int i=0;i<urls.size();i++){

//				System.out.println(urls.get(i));	
							
				PageDao Page = new PageDao();
				int num=0;
				
				boolean flag = Page.dao().select(urls.get(i));
				if(flag){
					System.out.println(urls.get(i) + " already crawed!");
					continue;
				}
				else{
					try {
						PageDao generalPage = new PageDao();
//						System.out.println("new url");
						
						String postHtmlStr = Jsoup.connect(urls.get(i)).timeout(1000).get().toString();
						
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
						 * 
						 */
						String title = names.get(i);
						SourceNER sourceNER = new SourceNER();
						List<String> resultSource = new ArrayList<>();
						resultSource = sourceNER.source(title);
						String source = resultSource.toString().replace("[", "").replace("]", "").replace(" ", "");
						generalPage.set("SourceApartment", source);
						List<PageDao> result = Page.dao.selectPage(subsigStr, sh);
						if(!result.isEmpty()){
							/*
							 * 存在则执行update语句，num + 1
							 */
							System.out.println("same page have already been crawed!");
							for(PageDao ExPage : result){
								num = ExPage.get("num");
//								System.out.println(num);
								num = num+1;
								generalPage.set("num", num);
								generalPage.set("id", ExPage.get("id"));
//								System.out.println(generalPage.get("id"));
								updateList.add(generalPage);
							}
						}
						else{
							/*
							 * 不存在则执行insert语句，num = 1
							 */
							num = 1;
							generalPage.set("num", num);
							
//							System.out.println(generalPage.get("signature"));
//							System.out.println(generalPage.get("subsig"));
//							System.out.println("当前page："+generalPage.getStr("SourceURL")+":--------"+generalPage.getStr("NewsTitle"));
//							System.out.println(i);
//							System.exit(0);
							
							insertList.add(generalPage);
						}	
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					page.putField(GENERAL_PAGE_UPDATE, updateList);
					page.putField(GENERAL_PAGE_INSERT, insertList);
//					System.out.println("insert size "+ insertList.size());
//					System.out.println("update size "+ updateList.size());
				}
				
//				if(i==5)
//					break;
			}
		}
	}

	@Override
	public Site getSite() {
		return this.site;
	}

}
