package text;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public interface TextExtractService {
	/**
	 * 从含有html标签的字符串中抽取正文文本,默认UTF-8
	 * 
	 * @param html
	 * @return
	 */
	String extract(String html);

	/**
	 * 从file中提取正文文本,默认UTF-8
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	String extract(File file) throws IOException;

	/**
	 * 从file中以指定encoding提取正文文本
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	String extract(File file, String encoding) throws IOException;

	/**
	 * 从url页面中提取正文文本,默认UTF-8
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	String extract(URL url) throws IOException;

	/**
	 * 以指定的encoding从url页面中提取正文文本
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	String extract(URL url, String encoding) throws IOException;

}
