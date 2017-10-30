package text;

import static text.TextConst.BLOCK_LINES;
import static text.TextConst.ENCODING;
import static text.TextConst.LINK_THRESHOLD;
import static text.TextConst.NOISE_WORDS;
import static text.TextConst.PARA_BREAK;
import static text.TextConst.PUN_BLOCK_LINES;
import static text.TextConst.PUN_THRESHOLD;
import static text.TextConst.SPACES_REG;
import static text.TextConst.THRESHOLD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import text.TextExtractor.Method;

public class TextExtractor {
    /**
     * 抽取正文的方法
     * 
     */
    public enum Method {
        All("all", "联合所有的密度"), TextDensity("textDensity", "按照文本密度"), LinkDensity(
                "linkDensity", "联合超链接密度和文本密度"), PunctuationDensity(
                "punctuationDensity", "联合标点符号密度和文本密度");
        private String name;
        private String desc;

        Method(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public String getName() {
            return name;
        }

    }

    /**
     * 抽取网页正文，不判断该网页是否是目录型。即已知传入的肯定是可以抽取正文的主题类网页。
     * 
     * @param html
     *            网页HTML字符串
     * 
     * @return 网页正文
     */
    public static String parse(String html) {
        return parse(html, ENCODING);
    }

    /**
     * 按照文本密度抽取正文
     * 
     * @param html
     * @param encoding
     * @return
     */
    public static String parse(String html, String encoding) {
        // 默认按照文本密度
        return parse(html, encoding, Method.TextDensity, false);
    }
    
    /**
     * 制定正文抽取的方法
     * 
     * @param htmlStr
     *			 网页HTML字符串
     * @param method
     * 
     * @return 网页正文
     * 
     */
	public static String parse(String htmlStr, Method method) {
		 return parse(htmlStr, ENCODING, method, false);
	}

    /**
     * 判断传入HTML，若是主题类网页，则抽取正文；否则输出<b>"unkown"</b>。
     * 
     * @param html
     *            网页HTML字符串
     * @param flag
     *            true进行主题类判断, 省略此参数则默认为false;<br/>
     *            暂时未用到
     * 
     * @return 网页正文string
     */
    public static String parse(String html, String encoding, Method method,
            boolean flag) {
        if (method == Method.TextDensity) {
            String htmlStr = StringUtil.preProcess(html);
            return getText(htmlStr, THRESHOLD, BLOCK_LINES);
        }
        Map<Integer, Integer> links = new HashMap<>();
        Map<Integer, Integer> punctuations = new HashMap<>();
        // 去除非超链接标签;但超链接里面可能包含了标点符号
        html = StringUtil.preProcessExceptHtmlTags(html);
        countLinks(links, html, BLOCK_LINES);
        String htmlStr = StringUtil.preProcess(html);
        countPunctuations(punctuations, htmlStr, BLOCK_LINES);
        if (method == Method.LinkDensity) {
            return getTextWithLinksDensity(links, htmlStr, BLOCK_LINES);
        }
        if (method == Method.PunctuationDensity)
//            return getTextWithPunctuationsDensity(links, htmlStr,
//                    PUN_BLOCK_LINES);
            return getTextWithPunctuationsDensity(punctuations, htmlStr,
                    PUN_BLOCK_LINES);
        return getTextWithAllDensity(links, punctuations, htmlStr, BLOCK_LINES);
    }

    /**
     * 统计每个block(从第i行到第[i+blockWidth-1]行)中的超链接数
     * 
     * @param links
     *            block的超链接数
     * @param html
     *            原始的文档html字符串,包含html标签
     * @param blockWidth
     *            block的宽度
     */
    public static void countLinks(Map<Integer, Integer> links, String html,
            int blockWidth) {
        if (StringUtils.isBlank(html) || links == null) {
            return;
        }
        String[] lines = html.split(PARA_BREAK);
        int lineNum = lines.length;
        for (int i = 0; i < lineNum - blockWidth; i++) {
            int link = 0;
            for (int j = i; j < i + blockWidth; j++) {
                String line = lines[j].trim();
                // 该行中出现的超链接的次数
                link += StringUtil.countMatches(line, "<a");
            }
            links.put(i, link);
        }
    }

    /**
     * 统计每个block(从第i行到第[i+blockWidth-1]行)中的标点符号数
     * 
     * @param punctuations
     * @param html
     * @param blockWidth
     */
    public static void countPunctuations(Map<Integer, Integer> punctuations,
            String html, int blockWidth) {
        if (StringUtils.isBlank(html) || punctuations == null) {
            return;
        }
        String[] lines = html.split(PARA_BREAK);
        int lineNum = lines.length;
        for (int i = 0; i < lineNum - blockWidth; i++) {
            int punctuation = 0;
            for (int j = i; j < i + blockWidth; j++) {
                String line = lines[j].trim();
                // 该行中出现的标点符号的次数
                punctuation += StringUtil.countPunctuations(line);
            }
            punctuations.put(i, punctuation);
        }
    }

    /**
     * 根据文本密度,提取网页正文
     * 
     * @param html
     * @param threshold
     * @param blocksWidth
     * @return
     */
    public static String getText(String html, int threshold, int blocksWidth) {
        String[] lines = html.split(PARA_BREAK);
        ArrayList<Integer> indexDistribution = new ArrayList<Integer>();

        int lineNum = lines.length;
        for (int i = 0; i < lineNum - blocksWidth; i++) {
            int wordsNum = 0;
            for (int j = i; j < i + blocksWidth; j++) {
                // 去除空字符
                String line = lines[j].replaceAll(SPACES_REG, "");
                wordsNum += line.length();
            }
            indexDistribution.add(wordsNum);
        }
        
//        for(int i=0;i<indexDistribution.size();i++){
//            System.out.println(indexDistribution.get(i));
//        }

        int start = -1;
        int end = -1;
        boolean boolstart = false, boolend = false;
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < indexDistribution.size() - 1; i++) {
            if (indexDistribution.get(i) > threshold && !boolstart) {
                if (indexDistribution.get(i + 1).intValue() != 0
                        || indexDistribution.get(i + 2).intValue() != 0
                        || indexDistribution.get(i + 3).intValue() != 0) {
                    boolstart = true;
                    start = i;
                    continue;
                }
            }
            if (boolstart) {
                if (indexDistribution.get(i).intValue() == 0
                        || indexDistribution.get(i + 1).intValue() == 0) {
                    end = i;
                    boolend = true;
                }
            }
            StringBuilder tmp = new StringBuilder();
            if (boolend) {
                for (int ii = start; ii <= end; ii++) {
                    String line = lines[ii].trim();
                    /* 块长度小于5则不认为其是正文内容 */
                    if (line.length() < 5)
                        continue;
                    tmp.append(line + PARA_BREAK);
                }
                String str = tmp.toString();
                /* 除噪 */
                for (String word : NOISE_WORDS) {
                    if (str.contains(word))
                    	str = str.replace(word,"");
                    else
                        continue;
                }
                text.append(str);
                boolstart = boolend = false;
            }
        }
        return text.toString();
    }

    /**
     * 结合超链接密度和文本密度来抽取正文
     * 
     * @param html
     * @param flag
     * @param blocksWidth
     * @return
     */
    private static String getTextWithLinksDensity(Map<Integer, Integer> links,
            String html, int blocksWidth) {
        // 分成多行
        String[] lines = html.split(PARA_BREAK);
        // 总行数
        int count = lines.length;
        int textLength = 0;
        ArrayList<Double> indexDistribution = new ArrayList<>(count);

        for (int i = 0; i < count - blocksWidth; i++) {
            int wordsNum = 0;
            for (int j = i; j < i + blocksWidth; j++) {
                // 去除空字符
                String line = lines[j].replaceAll(SPACES_REG, "");
                wordsNum += line.length();
                textLength += line.length();
            }
            int link = links.get(i);
            // 联合密度
            double co = 1.0 * blocksWidth / (link + 1) * wordsNum;
            indexDistribution.add(co);
        }
        // 求每行的平均长度
        // int threshold = 2 * textLength / count;
        int threshold = LINK_THRESHOLD;
        System.out.println(threshold + "-------------------------");

        int start = -1;
        int end = -1;
        boolean boolstart = false, boolend = false;
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < indexDistribution.size() - 1; i++) {
            if (indexDistribution.get(i) > threshold && !boolstart) {
                if (indexDistribution.get(i + 1).intValue() != 0
                        || indexDistribution.get(i + 2).intValue() != 0
                        || indexDistribution.get(i + 3).intValue() != 0) {
                    boolstart = true;
                    start = i;
                    continue;
                }
            }
            if (boolstart) {
                if (indexDistribution.get(i).intValue() == 0
                        || indexDistribution.get(i + 1).intValue() == 0) {
                    end = i;
                    boolend = true;
                }
            }
            StringBuilder tmp = new StringBuilder();
            if (boolend) {
                for (int ii = start; ii <= end; ii++) {
                    String line = lines[ii].trim();
                    if (line.length() < 5)
                        continue;
                    tmp.append(line + PARA_BREAK);
                }
                String str = tmp.toString();
              /* 除噪 */
              for (String word : NOISE_WORDS) {
                  if (str.contains(word))
                  	str = str.replace(word,"");
                  else
                      continue;
              }
                text.append(str);
                boolstart = boolend = false;
            }
        }
        return text.toString();
    }

    /**
     * 结合标点符号密度和文本密度来抽取正文
     * 
     * @param html
     * @param flag
     * @param blocksWidth
     * @return
     */
    private static String getTextWithPunctuationsDensity(
            Map<Integer, Integer> punctuations, String html, int blocksWidth) {
        // 分成多行
        String[] lines = html.split(PARA_BREAK);
        // 总行数
        int count = lines.length;
        int textLength = 0;
        ArrayList<Integer> indexDistribution = new ArrayList<>(count);

        for (int i = 0; i < count - blocksWidth; i++) {
            int wordsNum = 0;
            for (int j = i; j < i + blocksWidth; j++) {
                // 去除空字符
                String line = lines[j].replaceAll(SPACES_REG, "");
                wordsNum += line.length();
                textLength += line.length();
            }
            int punctuation = punctuations.get(i);
            // 联合密度
            int co = (punctuation + 1) * wordsNum;
            indexDistribution.add(co);
        }
        // 求每行的平均长度
        // int threshold = 2 * textLength / count;
        int threshold = PUN_THRESHOLD;

        int start = -1;
        int end = -1;
        boolean boolstart = false, boolend = false;
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < indexDistribution.size() - 1; i++) {
            if (indexDistribution.get(i) > threshold && !boolstart) {
                if (indexDistribution.get(i + 1).intValue() != 0
                        || indexDistribution.get(i + 2).intValue() != 0
                        || indexDistribution.get(i + 3).intValue() != 0) {
                    boolstart = true;
                    start = i;
                    continue;
                }
            }
            if (boolstart) {
                if (indexDistribution.get(i).intValue() == 0
                        || indexDistribution.get(i + 1).intValue() == 0) {
                    end = i;
                    boolend = true;
                }
            }
            StringBuilder tmp = new StringBuilder();
            if (boolend) {
                for (int ii = start; ii <= end; ii++) {
                    String line = lines[ii].trim();
                    if (line.length() < 5)
                        continue;
                    tmp.append(line + PARA_BREAK);
                }
                String str = tmp.toString();

              /* 除噪 */
              for (String word : NOISE_WORDS) {
                  if (str.contains(word))
                  	str = str.replace(word,"");
                  else
                      continue;
              }
                text.append(str);
                boolstart = boolend = false;
            }
        }
        return text.toString();
    }

    /**
     * 结合标点符号密度、文本密度、超链接来抽取正文
     * 
     * @param links
     * @param punctuations
     * @param html
     * @param blocksWidth
     * @return
     */
    private static String getTextWithAllDensity(Map<Integer, Integer> links,
            Map<Integer, Integer> punctuations, String html, int blocksWidth) {
        // 分成多行
        String[] lines = html.split(PARA_BREAK);
        // 总行数
        int count = lines.length;
        ArrayList<Integer> indexDistribution = new ArrayList<>(count
                - blocksWidth);

        for (int i = 0; i < count - blocksWidth; i++) {
            int wordsNum = 0;
            for (int j = i; j < i + blocksWidth; j++) {
                // 去除空字符
                String line = lines[j].replaceAll(SPACES_REG, "");
                wordsNum += line.length();
            }
            indexDistribution.add(wordsNum);
        }
        int threshold = THRESHOLD;

        int start = -1;
        int end = -1;
        boolean boolstart = false, boolend = false;
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < indexDistribution.size() - 1; i++) {
            // 块的文本长度超过了阈值
            if (indexDistribution.get(i) > threshold) {
                int linkNum = links.get(i);
                int punctuationNum = punctuations.get(i);
                // System.out.println("--------------" + punctuationNum+
                // "-------" + linkNum + "---" + lines[i]);
                if ((punctuationNum + 1) * 1.0 / (linkNum + 1) <= 5) {
                    continue;
                }
                if (!boolstart) {
                    if (indexDistribution.get(i + 1).intValue() != 0
                            || indexDistribution.get(i + 2).intValue() != 0
                            || indexDistribution.get(i + 3).intValue() != 0) {
                        boolstart = true;
                        start = i;
                        continue;
                    }
                }
            }
            if (boolstart) {
                if (indexDistribution.get(i).intValue() == 0
                        || indexDistribution.get(i + 1).intValue() == 0) {
                    end = i;
                    boolend = true;
                }
            }
            StringBuilder tmp = new StringBuilder();
            if (boolend) {
                for (int ii = start; ii <= end; ii++) {
                    String line = lines[ii].trim();
                    if (line.length() < 5)
                        continue;
                    tmp.append(line + PARA_BREAK);
                }
                String str = tmp.toString();
              /* 除噪 */
              for (String word : NOISE_WORDS) {
                  if (str.contains(word))
                  	str = str.replace(word,"");
                  else
                      continue;
              }
                text.append(str);
                boolstart = boolend = false;
            }
        }
        return text.toString();
    }

    /**
     * 提取页面的Html内容
     * 
     * @param strURL
     * @return
     * @throws IOException
     */
    public static String getHTML(String strURL, String encoding)
            throws IOException {
        Document doc = Jsoup.connect(strURL).timeout(3000).get();
        String html = doc.html();
        return html;
    }
}
