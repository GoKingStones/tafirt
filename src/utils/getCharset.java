package utils;


import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/*
 * 通过URL获取网页编码格式
 * 
 */
public class getCharset {
	public String getUrlCharset(String url){
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            System.out.println("Content-Type" + "--->" + map.get("Content-Type"));
            List<String> list=map.get("Content-Type");
            if (list.size()>0){
                String contentType=list.toString().toUpperCase();
                if (contentType.contains("UTF-8")){
                    return "UTF-8";
                }
                if(contentType.contains("GB2312")){
                    return "GB2312";
                }
                if (contentType.contains("GBK")){
                    return "GBK";
                }
            }

            //如果相应头里面没有编码格式,用下面这种
            CodepageDetectorProxy codepageDetectorProxy = CodepageDetectorProxy.getInstance();
            codepageDetectorProxy.add(JChardetFacade.getInstance());
            codepageDetectorProxy.add(ASCIIDetector.getInstance());
            codepageDetectorProxy.add(UnicodeDetector.getInstance());
            codepageDetectorProxy.add(new ParsingDetector(false));
            codepageDetectorProxy.add(new ByteOrderMarkDetector());
            Charset charset = codepageDetectorProxy.detectCodepage(new URL(url));
            return charset.name();
        }catch (Exception e){}
        return null;
    }
}
