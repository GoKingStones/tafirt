package text;

public class StringUtil {
    /** 标点符号 */
    public static final String PUNCTUATIONS = "[,，.。?？!！:：“”;；——]";

    /**
     * 取得指定子串在字符串中出现的次数。
     * <p/>
     * <p>
     * 如果字符串为<code>null</code>或空，则返回<code>0</code>。
     * 
     * <pre>
     * StringUtil.countMatches(null, *)       = 0
     * StringUtil.countMatches("", *)         = 0
     * StringUtil.countMatches("abba", null)  = 0
     * StringUtil.countMatches("abba", "")    = 0
     * StringUtil.countMatches("abba", "a")   = 2
     * StringUtil.countMatches("abba", "ab")  = 1
     * StringUtil.countMatches("abba", "xxx") = 0
     * </pre>
     * 
     * </p>
     * 
     * @param str
     *            要扫描的字符串
     * @param subStr
     *            子字符串
     * @return 子串在字符串中出现的次数，如果字符串为<code>null</code>或空，则返回<code>0</code>
     */
    public static int countMatches(String str, String subStr) {
        if ((str == null) || (str.length() == 0) || (subStr == null)
                || (subStr.length() == 0)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }
        return count;
    }

    /**
     * 统计字符串中的标点符号数
     * 
     * @param str
     * @return
     */
    public static int countPunctuations(String str) {
        if (str == null) {
            return 0;
        }
        int originLength = str.length();
        if (0 == originLength) {
            return 0;
        }
        str = str.replaceAll("[.。,，!！?？:：“”、]", "");
        int newLength = str.length();
        return originLength - newLength;
    }

    public static void main(String[] args) {
        String s = "           不改革 军队是打不了仗的 更是打不了胜仗的2015-11-23 15:28            日防相妄批中国南海行动“自以为是且无法容忍”2015-11-23 13:18            专家：售华苏35或为中国定制版 两国联合改进2015-11-23 13:11 ";
        System.out.println(StringUtil.countPunctuations(s));
    }

    /**
     * remove js/css/comment from html
     * 
     * @param html
     * @return
     */
    public static String preProcess(String html) {
    	/*
    	 * (?i) 表示所在位置右侧的表达式开启忽略大小写模式
    	 * (?s) 表示所在位置右侧的表达式开启单行模式, 更改句点字符 (.) 的含义，以使它与每个字符（而不是除 \n之外的所有字符）匹配。
    	 * 注意：(?s)通常在匹配有换行的文本时使用
    	 */
        html = html.replaceAll("(?is)<!DOCTYPE.*?>", "");
        html = html.replaceAll("(?is)<!--.*?-->", ""); // remove html comment
        html = html.replaceAll("(?is)<script.*?>.*?</script>", ""); // remove
                                                                    // javascript
        html = html.replaceAll("(?is)<style.*?>.*?</style>", ""); // remove css
        html = html.replaceAll("&.{2,5};|&#.{2,5};", " "); // remove special
                                                           // char
        html = html.replaceAll("(?is)<.*?>", "");// remove all dom-tags
        // <!--[if !IE]>|xGv00|9900d21eb16fa4350a3001b3974a9415<![endif]-->
        return html;
    }

    /**
     * 保留HTML标签,主要是为了保留<a>超文本</a>标签
     * 
     * @param html
     * @return
     */
    public static String preProcessExceptHtmlTags(String html) {
        html = html.replaceAll("(?is)<!DOCTYPE.*?>", "");
        html = html.replaceAll("(?is)<!--.*?-->", ""); // remove html comment
        html = html.replaceAll("(?is)<script.*?>.*?</script>", ""); // remove
                                                                    // javascript
        html = html.replaceAll("(?is)<style.*?>.*?</style>", ""); // remove css
        html = html.replaceAll("&.{2,5};|&#.{2,5};", " "); // remove special
                                                           // char
        // <!--[if !IE]>|xGv00|9900d21eb16fa4350a3001b3974a9415<![endif]-->
        return html;
    }
}
