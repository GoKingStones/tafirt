//package test;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jsoup.Jsoup;
//import org.junit.Test;
//
//import simhash.SimHasher;
//import text.TextExtractor;
//import text.TextExtractor.Method;
//
//
//public class Stringtest {
//
//	@Test
//	public void testS() throws IOException{
//		//http://www.calekki.com/xwzx/newsCategoryId=9&FrontNews_list01-1490879432616_pageNo=1&FrontNews_list01-1490879432616_pageSize=10.html
//		//http://www.calekki.com/xwzx_xx/newsId=1718.html
//		//http://www.e-eiz.com/news.asp?page=2
////		String URL_LIST = "http://www\\.huaxin-agri\\.com/cn/News\\.Asp(\\?y=&Page=\\d)?";
////		String URL_POST = "http://www\\.mlqsydy\\.com/newsdefault/1923/1942/\\d+";
////		System.out.println("http://www.huaxin-agri.com/cn/News.Asp?y=&Page=2".matches(URL_LIST));
////		System.out.println("http://www.huaxin-agri.com/cn/News.Asp".matches(URL_LIST));
////		System.out.println("http://www.mlqsydy.com/newsdefault/1923/1942/23113".matches(URL_POST));
////
////		String temp = "detail.jsp?article_millseconds=1356873653468&column_no=070401";
////		String article = temp.substring(temp.indexOf("=")+1, temp.indexOf("&"));
////		String column = temp.substring(temp.lastIndexOf("=")+1);
////		System.out.println(article + " "+ column);
//
//		String newurl;
//		String tmp = "http://www.cecz.org/list_1_45.html";
//		System.out.println(tmp);
//		String a = tmp.substring(tmp.indexOf("list_1_")+7,tmp.indexOf(".html"));
//		System.out.println(a);
//		int num = Integer.valueOf(a);
//		num++;
//		System.out.println(num);
//		String b = tmp.substring(0,tmp.indexOf("list_1_")+7);
//		newurl = b+num+".html";
//		System.out.println(newurl);
//		tmp = "http://www.baidu.com.cn/s?wd=中国&pn=10&cl=3";
//		a = tmp.substring(tmp.indexOf("&pn=")+4,tmp.lastIndexOf("&"));
//		System.out.println(a);
//		num = (Integer.valueOf(a)-1)*10;
//		System.out.println(num);
//		b = tmp.substring(0,tmp.indexOf("&pn=")+4);
//		newurl = b+num;
//		System.out.println(newurl);
//		String aString = "<a href='news5.html'>[2016-08-04]聚龙印尼产业园获批为国家级境外经贸合作区</a>";
//		System.out.println(aString);
//		b=aString.replaceAll("(?is)<.*?>", "");
//		String cString = b.replaceAll("(?is)\\[.*?\\]", "");
//		System.out.println(b);
//		System.out.println(cString);
//
//		List<String> aList = new ArrayList<>();
//		aList.add("ss");
//		aList.add("下一页&gt;");
//		aList.add("23");
//		System.out.println(aList.contains("下一页"));
//		ArrayList<String> list1 = new ArrayList<String>();
//		list1.add("b");
//		list1.add("d");
//		list1.add("d");
//		list1.add("d");
////		list1.set(0,"a");//替换索引为0的值
//		list1.add(3, null);// 添加到索引为0的位置
//		System.out.println(list1);
//		for(String ac:list1)
//			if(ac == null)
//				System.out.println("true");
//		System.out.println(list1.size());
//
//		String aString2 = "<span class=' newTimeFactor_before_abs m'>2016年4月15日&nbsp;-&nbsp;</span>继“一带一路”圆桌会后,“再造中阿丝绸之路——走进<em>埃及</em>”会议15日举行。<em>中非泰达</em>投资股份有限公司相关负责人付绅在会上介绍了中埃苏伊士经贸合作区...";
//		System.out.println("true");
//		ArrayList<String> list2 = new ArrayList<String>();
//		list2.add("2017年14月12日");
//		list2.add("2017年4月12日");
//		list2.add("2017年11月12日");
//		list2.add("2017年4月13日");
//    	for(int i=0;i<list2.size();i++){
//    		String time = list2.get(i).replace("年", "-").replace("月", "-").replace("日", "");
//    		list2.set(i, time);
//    	}
//    	for(String time:list2){
//    		System.out.println(time);
//    	}
//		if(aString2.contains("newTimeFactor_before_abs m")){
//			System.out.println("true");
//			String temp = aString2.substring(aString2.indexOf("<span class=' newTimeFactor_before_abs m'>"), aString2.indexOf("</span>"));
//			System.out.println(temp);
//		}
//
//		String title = "由租地种粮到投资高端产业园 豫企出海共赢丝路 - 大家新闻网";
//		if(title.contains("--")){
//			String tmpString = title.replace(" ", "");
//			System.out.println(tmpString);
//			String source = tmpString.substring(tmpString.lastIndexOf("--")+2);
//			System.out.println(source);
//		}
//		else if(title.contains("-")){
//			String tmpString = title.replace(" ", "");
//			System.out.println(tmpString);
//			String source = tmpString.substring(tmpString.lastIndexOf("-")+1);
//			System.out.println(source);
//		}
//		else if(title.contains("_")){
//			String tmpString = title.replace(" ", "");
//			System.out.println(tmpString);
//			String source = tmpString.substring(tmpString.lastIndexOf("_")+1);
//			System.out.println(source);
//		}
//
//		List<String> ppList = new ArrayList<>();
//		System.out.println("........");
//		System.out.println(ppList.size());
//		System.out.println("*************");
//
////		String url = "http://ljip.vn/web/zh/news-center/";
////		String postHtmlStr;
////		try {
////			postHtmlStr = Jsoup.connect(url).timeout(1000).get().toString();
////			System.out.println(postHtmlStr);
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//
////		String url = "http://www.decent-china.com/index.php/Info/Index/id/3952.html";
//////		String url = "http://www.decent-china.com/index.php/Info/Index/id/3954.html";
////    	String postHtmlStr = Jsoup.connect(url).timeout(1000).get().toString();
////		Method method = Method.PunctuationDensity;
////		String txt = TextExtractor.parse(postHtmlStr, method);
////		System.out.println(txt);
////		SimHasher sh = new SimHasher(txt);
////		System.out.println(sh.getHash());
////		System.out.println(sh.getSignature());
////		BigInteger signature = sh.getSignature();
////		// simhash distance设置为3
////		List<BigInteger> subsig = sh.subByDistance(sh, 3);
////		String subsigStr = subsig.toString().replace("[", "").replace("]", "").replace(" ", "");
////		System.out.println(subsig);
////		System.out.println(subsigStr);
//	}
//}
