package test;

import org.apache.http.io.BufferInfo;

import java.io.*;

public class DataTeset {

    public void main(String[] args) throws IOException {

        File in=new File("/Users/kingstones/Desktop/埃及/粗钢产量.csv");
        File out=new File("/Users/kingstones/Desktop/aiji/粗钢产量.csv");
        BufferedReader bufferedReader=new BufferedReader(new FileReader(in));
        String tmp=null;
        tmp=bufferedReader.readLine();
        while (tmp!=null){

        }
        writeToFile("/Users/kingstones/Desktop/aiji/粗钢产量.csv");

    }

    public static void writeToFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream out = new FileOutputStream(file, true);
        for (int i = 0; i < 100; i++) {
            StringBuffer sb = new StringBuffer();
            // 写入的内容最后加上一个空格用于拆分成行
            sb.append("这是第" + i + "行：Java文件写入读取测试 ");
            out.write(sb.toString().getBytes("utf-8"));
        }
        out.close();
    }
}