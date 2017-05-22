package ru.k0r0tk0ff.main;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by k0r0tk0ff
 */
public class XsltGenerator {

    /**
     * read file to string
     */
    String loadFileToString() {

        String dataFromFile = null;
        File file = new File("1.xml");

        try {
            //File file =
            dataFromFile = FileUtils.readFileToString(file, "UTF-8");
            System.out.println("Read file \"1.xml\" success.");
            //System.out.println(String.format("\n Data from file: %s\n", dataFromFile));
        } catch (IOException e) {
            System.out.println("Read file \"1.xml\" error!");
            e.printStackTrace();
        }
        return dataFromFile;
    }

    /**
     * Convert data
     */
    String convertDataWithXSLT(String input) {
        String out = null;

        XStream xstream = new XStream();


        return out;
    }
}
