package ru.k0r0tk0ff.main;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by k0r0tk0ff
 */

public class XmlToFileSaver {

    void saveStringToXml(String input) {
        try {
            FileUtils.writeStringToFile(new File("1.xml"), input, "UTF-8");
            System.out.println("Create file \"1.xml\" success.");
        } catch (IOException e) {
            System.out.println("Create file \"1.xml\" error!");
            e.printStackTrace();
        }
    }

}
