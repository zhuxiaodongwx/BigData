package cn.xiaodong.fileMaker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * @description: wordCount文件生成器
 * @author: xiaodong
 * @create: 2021-03-31 08:13
 **/

public class WordCountFileMaker {

    /**
     * 文件行数
     * 1亿行，最终生成文件约10GB
     */
    private static int fileLineNumber = 10000000;

    /**
     * 文件列数（每一行有几个单词）
     */
    private static int fileColNumber = 15;

    private static final String[] myWords = new String[]{"Hadoop", "Java", "Scala", "Spark", "Hive", "VMware", "Docker",
            "Flnk", "MySQL", "Shell", "Linux", "Spring", "MyBatis", "SpringMVC", "Oracle"};

    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter("D:/tmp/wordCount2.txt");
            BufferedWriter out = new BufferedWriter(fileWriter);

            for (int i = 0; i <= fileLineNumber; i++) {
                for (int j = 0; j <= fileColNumber; j++) {
                    int wordNum = (int) (Math.random() * myWords.length);
                    String word = myWords[wordNum];
                    out.write(word);
                    // 每个单词的分隔符
                    out.write(" ");
                }

                out.write("\n");
            }

            out.close();
        } catch (IOException e) {
        }
    }
}
