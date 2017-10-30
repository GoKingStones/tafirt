package text;

import static text.TextConst.ENCODING;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class TextExtractServiceImpl implements TextExtractService {

    @Override
    public String extract(String html) {
        return TextExtractor.parse(html);
    }

    @Override
    public String extract(File file) throws IOException {
        return extract(file, ENCODING);
    }

    @Override
    public String extract(File file, String encoding) throws IOException {
        String html = extractHTML(file, encoding);
        return extract(html);
    }

    @Override
    public String extract(URL url) throws IOException {
        return extract(url, ENCODING);
    }

    @Override
    public String extract(URL url, String encoding) throws IOException {
        String html = extractHTML(url, encoding);
        return extract(html);
    }

    /**
     * 从file中以指定encoding获得html字符串
     * 
     * @param file
     * @param encoding
     * @return
     * @throws IOException
     */
    private String extractHTML(File file, String encoding) throws IOException {
        String html = extractHTML(new InputStreamReader(new FileInputStream(
                file), encoding));
        return html;
    }

    /**
     * 从URL以指定的encoding获得html字符串
     * 
     * @param url
     * @param encoding
     * @return
     * @throws IOException
     */
    private String extractHTML(URL url, String encoding) throws IOException {
        String html = extractHTML(new InputStreamReader(url.openStream(),
                encoding));
        return html;
    }

    /**
     * 从reader中以指定的encoding获得html字符串
     * 
     * @param reader
     * @return
     * @throws IOException
     */
    private String extractHTML(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String s;
        StringBuilder sb = new StringBuilder("");
        while ((s = br.readLine()) != null) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }

}
