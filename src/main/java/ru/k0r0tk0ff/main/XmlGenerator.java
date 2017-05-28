package ru.k0r0tk0ff.main;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Class for connect to db, and generate xml file from db's data
 *
 */

public class XmlGenerator {

    /**
     * method for generate XML document
     */

    String generateDocument(ResultSet resultSet) throws SQLException, ParserConfigurationException, FileNotFoundException {

        //String dataXml;

        /**
         * Convert to string db Query Result
         */
        List<String> dbQueryResult = new ArrayList();

        while (resultSet.next()) {
            dbQueryResult.add(resultSet.getString(1));
        }

        /**
         * Generate xml
         */

        //create document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        //create root element
        Element entries = document.createElement("entries");
        document.appendChild(entries);

        // for debug
        //System.out.println( String.format(("Arraylist's dimension is %s"),dbQueryResult.size()));

        //create elements with values
        //     <entry>
        //          <field>value=dbQueryResult.get(i)</field>
        //      </entry>
        for (int i=0; i<dbQueryResult.size() ; i++) {
            Element entry = document.createElement("entry");
            Element field = document.createElement("field");

            Text text = document.createTextNode(dbQueryResult.get(i));
            entries.appendChild(entry);
            entry.appendChild(field);
            field.appendChild(text);
        }

        //serialise and set encoding "UTF-8". Use DOM method
        DOMImplementation impl = document.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);

        LSOutput out = implLS.createLSOutput();
        out.setEncoding("UTF-8");
        System.out.println("Convert to UTF-8 encoding success.");

        // use BufferedOutputStream for more fast write to file
        out.setByteStream(new BufferedOutputStream
                (new FileOutputStream
                        (new File("1.xml"))));
        System.out.println("Create file \"1.xml\" success.");
        ser.write(document, out);

        //dataXml = ser.writeToString(document);

        return ser.writeToString(document);
    }
}




