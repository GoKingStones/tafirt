package text;

public interface TextConst {
    /* 中文句号 */
    String POINT_ZH = "。";

    /** 标点符号 */
    String[] PUNCTUATIONS = { ".", "。", ",", "，", "!", "！", "?", "？", ":", "：",
            "\"", "“", "”", "、" };

    /* 段落分隔符 */
    String PARA_BREAK = "\n";
    /**
     * 默认字符编码
     */
    String ENCODING = "utf-8";

    /* 当待抽取的网页正文中遇到成块的新闻标题未剔除时，只要增大此阈值即可。 */
    /* 阈值增大，准确率提升，召回率下降；值变小，噪声会大，但可以保证抽到只有一句话的正文 */
    int THRESHOLD = 60;
    /* 默认的正文行数 */
    int BLOCK_LINES = 3;

    int LINK_THRESHOLD = 60;

    int PUN_THRESHOLD = 250;
    /* 标点密度时正文行数 */
    int PUN_BLOCK_LINES = 3;

/*    String[] NOISE_WORDS = { "Copyright", "友情链接", "任何关于疾病的建议都不能替代执业医师的面对面诊断",
            "隐私条款", "媒体合作", "战略合作伙伴", "ICP备","本文仅代表作者个人观点","请读者仅作参考"};*/
    String[] NOISE_WORDS = { "Copyright", "友情链接", "版权所有",
            "隐私条款", "媒体合作", "战略合作伙伴", "ICP备","本文仅代表作者个人观点","请读者仅作参考"};

    /** 空白符表达式 */
    String SPACES_REG = "\\s+";
}
