package test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.PageDao;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import controller.CrawController;

import simhash.SimHasher;
import text.TextExtractor;
import text.TextExtractor.Method;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import utils.exception.ServiceException;


public class CrawTest implements PageProcessor {

	public static final String URL_LIST = "http://www\\.zfnig\\.com/cn/News\\.aspx\\?page=\\d+";
	public static final String URL_POST = "http://www\\.zfnig\\.com/cn/NewsInfo\\.aspx\\?Id=\\d+";
    public static final String url ="http://www.zfnig.com/cn/News.aspx?page=1";

    private Site site = Site
            .me()
//            .setDomain("blog.sina.com.cn")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
//    public void process(Page page) {
//        //列表页
//        if (page.getUrl().regex(URL_LIST).match()) {
//            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST).all());
//            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
//            //文章页
//        } else {
//            page.putField("title", page.getHtml().xpath("//div[@class='articalTitle']/h2"));
//            page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
//            page.putField("date",
//                    page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
//        }
//    }
    public void process(Page page) {

//      if (page.getUrl().regex(URL_LIST).match()) {
//    	 System.out.println(page.getHtml().xpath("//div[@class=\"articleList\"]//span[@class='atc_title']").replace("(?is)<.*?>", ""));
//      }
    	Html html = page.getHtml();
    	List<String> urls = html.xpath("//div[@class='main']//ul[@class='list_news']/li/a").links().regex(URL_POST).all();
    	List<String> times = html.xpath("//div[@class='main']//ul[@class='list_news']/li/span").regex("\\d{4}-\\d{1,2}-\\d{1,2}").all();
    	List<String> names = html.xpath("//div[@class='main']//ul[@class='list_news']/li/a").replace("(?is)<.*?>", "").all();
    	System.out.println(urls.size());
    	System.out.println(times.size());
    	System.out.println(names.size());
    	
		for(int i=0;i<urls.size();i++){			
			System.out.println(times.get(i));
			System.out.println(names.get(i));
			System.out.println(urls.get(i));
			System.out.println("===========================");
		}
    	
    	
//    	System.out.println(html); 
    	
/*    	String[] excludeList = new String[]{"baike.baidu.com","zhidao.baidu.com","www.docin.com","xueshu.baidu.com",
    		"question","wenku.baidu.com","tieba.baidu.com","ganji.com","aseby.essc.fengj.com/","doc.mbalib.com",
    		"jobui.com","company.zhaopin.com"};
    	
//    	List<String>tList=html.xpath("//div[@id='content_left']/div[@class~='result.{1}c-container.{1}']/h3/a").all();
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
    	
    	ArrayList<String> urls = new ArrayList<String>();
    	ArrayList<String> names = new ArrayList<String>();
    	ArrayList<String> times = new ArrayList<String>();
//    	ArrayList<String> txt = new ArrayList<String>();
    	//处理链接加密问题
    	for(int i=0;i<links.size();i++){
        	Connection.Response res;
			try {
				res = Jsoup.connect(links.get(i)).timeout(60000).method(Connection.Method.GET).followRedirects(false).execute();
				String str= res.header("Location");
//				String charset = res.charset();
//				String body = Jsoup.connect(str).timeout(60000).get().toString();
//				System.out.println(charset);
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
				if(flag){
					urls.add(str);
//					txt.add(body);
					times.add(timeall.get(i));
					names.add(nameall.get(i));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	
    	}

//		for(int i=0;i<abstracts.size();i++){
//			System.out.println(urls.get(i));
//			System.out.println(times.get(i));
//			System.out.println(names.get(i));
//		}
//    	System.out.println(tList.size());
//    	for(String s:tList)
//    		System.out.println(s);
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
		for(int i=0;i<urls.size();i++){			
			System.out.println(times.get(i));
			System.out.println(names.get(i));
			System.out.println(urls.get(i));
//			System.out.println(txt.get(i));
			System.out.println("===========================");
		}
		
		System.out.println(getDomain("http://www.huaxin-agri.com/cn/index.Asp"));*/
		
//    	List<String> aa = html.xpath("//td[@class='weiruan16']/strong").links().all();
//    	List<String> cc = html.xpath("//td[@class='weiruan16']/strong").replace("(?is)<.*?>", "").all();
//    	List<String> bb = html.xpath("//td[@class='weiruan14']").regex("\\d{4}-\\d{2}-\\d{2}").all();
//    	System.out.println(aa.size());
//    	System.out.println(bb.size());
//    	for(int i=0;i<aa.size();i++){
//    		String name = cc.get(i);
//    		String url = aa.get(i);
//    		System.out.println(name);
//    		System.out.println(url);
//    		System.out.println(bb.get(i));
//    		if(i==3)
//    			break;
//    	}
//    	System.out.println(aa.get(120));
//		System.out.println(bb.get(120));
    	
//    	
//    	List<String> urlsInPageAll = html.xpath("//td[@class='weiruan16']").links().regex(URL_POST).all();
//    	List<String> urlsInPage = new ArrayList(new HashSet<String>(urlsInPageAll));
//		System.out.println(urlsInPage.size());
//		for(String url : urlsInPage){
//			if(url.matches(URL_POST)){
//				System.out.println(url);
//				url = "http://www.setc-zone.com/system/2017/07/28/011263588.shtml";
//				String fea = url.substring(url.lastIndexOf(getDomain(url))+1, url.indexOf(".shtml"));
//				System.out.println(getDomain(url));
//				System.out.println(fea);
//				String a = "'011263588'";
//				System.out.println(a);
//				System.out.println(html.xpath("//strong/a[contains(@href,'http://www.setc-zone.com/system/2017/07/28/011263588.shtml')]"));
//				System.out.println(html.xpath("//strong/a[contains(@href, url]"));
//				System.out.println(html.xpath("//strong/a[contains(@href, a)]"));
//				System.exit(0);
//			}
//		}
    	
    }

    @Override
    public Site getSite() {
        return site;
    }

	private static String getDomain(String url) {
		if (!url.endsWith("/")) {
			url += "/";
		}
		int doubleSlashesIndex = url.indexOf("//");
		int start = doubleSlashesIndex + 2;
		int slashIndex = url.indexOf("/", start);
		return url.substring(start, slashIndex);
	}
	
    public static void main(String[] args) {
//        Spider.create(new CrawTest()).addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
//                .run();
        Spider.create(new CrawTest()).addUrl(url).run();
    }
}
