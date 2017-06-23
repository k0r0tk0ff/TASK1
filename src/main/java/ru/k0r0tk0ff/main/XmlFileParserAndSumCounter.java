package ru.k0r0tk0ff.main;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by k0r0tk0ff
 * Class for parse and print sum of elements
 */

public class XmlFileParserAndSumCounter {

    ArrayList parseXmlFileToArrayList(String inputPath)  {
        ArrayList<Integer> arrayAfterParse = new ArrayList();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = null;
        try {
            parser = factory.createXMLStreamReader(
                    new BufferedInputStream(new FileInputStream(inputPath)));
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        try {
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (parser.getLocalName().equals("entry")) {
                        String intValueInAttribute = parser.getAttributeValue(null, "field");
                        if (intValueInAttribute != null) {
                            //System.out.println(intValueInAttribute);
                            arrayAfterParse.add(Integer.parseInt(intValueInAttribute));
                        }
                    }

                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return arrayAfterParse;
    }
}

