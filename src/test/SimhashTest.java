//package test;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//import org.apache.commons.io.IOUtils;
//import org.jsoup.Jsoup;
//
//import simhash.SimHasher;
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.selector.Html;
//
//public class SimhashTest {
//
//	@Test
//	public void testDistance(){
//		boolean flag=false;
//		// String str1 = readAllFile("D:/test/testin2.txt");
//		String str1 = "张志超你把我家的钥匙放在哪里了。";
//		SimHasher hash1 = new SimHasher(str1);
//		System.out.println(hash1.getSignature());
//		System.out.println(hash1.getHash());
//		List<BigInteger>list=hash1.subByDistance(hash1, 3);
//		String t1 = list.toString().replace("[", "").replace("]", "").replace(" ", "");
//		Map<Integer, ArrayList<String>> sig = new HashMap<>();
//		ArrayList<String> l1 = new ArrayList<>();
//		ArrayList<String> l2 = new ArrayList<>();
//		ArrayList<String> l3 = new ArrayList<>();
//		ArrayList<String> l4 = new ArrayList<>();
//		l1.add(t1.split(",")[0]);
//		l2.add(t1.split(",")[1]);
//		l3.add(t1.split(",")[2]);
//		l4.add(t1.split(",")[3]);
//
////		sig.put(0, t1);
//		System.out.println(t1);
//		System.out.println("============================");
//
//		// String str2 = readAllFile("D:/test/testin.txt");
//		String str2 = "张志超我家的钥匙你放在哪里了。";
//		SimHasher hash2 = new SimHasher(str2);
//		System.out.println(hash2.getSignature());
//		List<BigInteger>list2=hash2.subByDistance(hash2, 3);
//		String t2 = list2.toString().replace("[", "").replace("]", "");
//		hash1.subByDistance(hash2, 3);
//
//		l1.add(t2.split(",")[0]);
//		l2.add(t2.split(",")[1]);
//		l3.add(t2.split(",")[2]);
//		l4.add(t2.split(",")[3]);
//		System.out.println("============================");
////		if(sig.containsValue(subsig.split(",")[0]))
////			flag = true;
////		else if(sig.containsValue(subsig.split(",")[1]))
////			flag = true;
////		else if(sig.containsValue(subsig.split(",")[2]))
////			flag = true;
////		else if(sig.containsValue(subsig.split(",")[3]))
////			flag = true;
////		else {
////			flag = false;
////		}
//
//		l1.add("000");
//		l2.add("111");
//		l3.add("222");
//		l4.add("333");
//		sig.put(0, l1);
//		sig.put(1, l2);
//		sig.put(2, l3);
//		sig.put(3, l4);
//		System.out.println(sig.get(0));
//		if(sig.get(0).contains(t2.split(",")[0]))
//			flag = true;
//		else {
//			flag = false;
//		}
//
//		System.out.println("flag: "+flag);
//		System.out.println(hash1.getHammingDistance(hash2.getSignature()));
//
//		String y = "111xxx,2121x,3434";
//		System.out.println(y.split(",")[0]);
//		System.out.println(y.split(",")[1]);
//		System.out.println(y.split(",")[2]);
//		String x = "111xx2121x3434";
//		for(String c : x.split(","))
//			System.out.println(c);
//
//
//		String URL_LIST = "http://zccz\\.cnmc\\.com\\.cn/outlinetem\\.jsp\\?outlinetype=1&column_no=070401(&topage=\\d*)?";
//		String URL_POST = "http://zccz\\.cnmc\\.com\\.cn/detailtem\\.jsp\\?column_no=\\d+&article_millseconds=\\d+";
//		System.out.println("http://zccz.cnmc.com.cn/outlinetem.jsp?outlinetype=1&column_no=070401&topage=12".matches(URL_LIST));
//		System.out.println("http://zccz.cnmc.com.cn/detailtem.jsp?column_no=070401&article_millseconds=1366723975218".matches(URL_POST));
//	}
//
//	/**
//	 * 测试用
//	 * @param filename 名字
//	 * @return
//	 */
//	public static String readAllFile(String filename) {
//		String everything = "";
//		try {
//			FileInputStream inputStream = new FileInputStream(filename);
//			everything = IOUtils.toString(inputStream);
//			inputStream.close();
//		} catch (IOException e) {
//		}
//
//		return everything;
//	}
//}
