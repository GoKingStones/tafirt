package utils;

import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

public class SourceNER {
	public List<String> source(String text){
		Segment segment = HanLP.newSegment().enableCustomDictionary(true).enableOrganizationRecognize(true);
		CustomDictionary.add("长江日报");
		CustomDictionary.add("解放日报");
		CustomDictionary.add("荆楚网");
		List<Term> termList = segment.seg(text);
		List<String> result = new ArrayList<>();
		for(Term term:termList){
//			System.out.println(term.word+" "+term.nature+" "+term.getFrequency());
			if(term.nature.toString()=="nt" || term.nature.toString()=="nz")
//				System.out.println(term.word+" "+term.nature);
				result.add(term.word);
		}
		return result;
	}
	
	public static void main(String[] args){
		String[] testCase = new String[]{
		        "我在上海林原科技有限公司兼职工作，",
		        "同时在上海外国语大学日本文化经济学院学习经济与外语。",
		        "我经常在台川喜宴餐厅吃饭，",
		        "偶尔去地中海影城看电影。",
		        "中乌建立发展战略伙伴关系要闻-解放日报",
		        "大使李立出席毛里求斯晋非经贸合作区伊，中华人民共和国外交部",
		        "...大使李立出席毛里求斯晋非经贸合作区伊..._中华人民共和国外交部",
		        "中乌建立发展战略伙伴关系 01-要闻-解放日报",
		        "中乌农业合作将全面启动 十月有望达成具体成果_同花顺财经",
		        "济南杨铭宇餐饮管理有限公司是由杨先生创办的餐饮企业",
		        "北京华安通信科技有限公司",
		        " 汉网-长江日报：来汉推介莱基自贸区",
		        " 荆楚网-湖北日报：水深浪大鱼多，赴非“捕鱼”前景几何",
		        "印尼一周经济新闻导读（2016/4/25-2016/4/30）"
		};
		
		for (String sentence : testCase)
		{

			SourceNER sourceNER = new SourceNER();
			List<String> result = new ArrayList<>();
			result = sourceNER.source(sentence);
			String source = result.toString().replace("[", "").replace("]", "").replace(" ", "");
			System.out.println(source);
		}
	}
}
