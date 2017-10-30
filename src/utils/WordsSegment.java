package utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档分词
 * 
 */
public final class WordsSegment {
	private static final Logger logger = LoggerFactory.getLogger(WordsSegment.class);

	private WordsSegment() {
	}

	/**
	 * 分词
	 * 
	 * @param str 字符串
	 * @return
	 */
	public static List<String> getCutWords(String str) {
		//创建分词对象  
		Analyzer analyzer = new IKAnalyzer(true);
		Reader r = new StringReader(str);
		//分词
		TokenStream ts = analyzer.tokenStream("searchValue", r);
		ts.addAttribute(CharTermAttribute.class);

		//遍历分词数据  
		List<String> list = new ArrayList<String>();
		try {
			while (ts.incrementToken()) {
				CharTermAttribute ta = ts.getAttribute(CharTermAttribute.class);
				String word = ta.toString();
				list.add(word);
			}
		} catch (IOException e) {
			logger.error("分词IO错误：" + e.getMessage());
		}
		return list;
	}
}