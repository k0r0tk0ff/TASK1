package ru.k0r0tk0ff.main;


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

        String dataXml;

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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element entries = document.createElement("entries");
        document.appendChild(entries);

        // for debug
        //System.out.println( String.format(("Arraylist's dimension is %s"),dbQueryResult.size()));

        for (int i=0; i<dbQueryResult.size() ; i++) {
            Element entry = document.createElement("entry");
            Element field = document.createElement("field");

            Text text = document.createTextNode(dbQueryResult.get(i));
            entries.appendChild(entry);
            entry.appendChild(field);
            field.appendChild(text);
        }


        DOMImplementation impl = document.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS)impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);

        LSOutput out = implLS.createLSOutput();
        out.setEncoding("UTF-8");
        System.out.println("Convert to UTF-8 encoding success.");
        out.setByteStream(new FileOutputStream( new File("1.xml")));
        System.out.println("Create file \"1.xml\" success.");
        ser.write(document, out);

        String a = ser.writeToString(document);

        System.out.println("SEE SUBSTRING");
        System.out.println("-------------------------------------");
        System.out.println(a.substring(a.indexOf(System.getProperty("line.separator"))+1));
        System.out.println("-------------------------------------");

        //dataXml = ser.writeToString(document);
        // dataXml = a.substring(a.indexOf(System.getProperty("line.separator"))+1);
         dataXml = a.substring(2);



        //------------------------------

       /* XStream xstream = new XStream();

        List<String> entries = new ArrayList();

        while (resultSet.next()) {
            entries.add(String.valueOf(resultSet.getInt(1)));

        }*/

        // Change "list" to "entries"
        //xstream.alias("entries", List.class);

        // Change "String" to "entry"
        //xstream.alias("entry", java.lang.String.class);

        // Create XML document
        //String dataXml = xstream.toXML(entries);

        return dataXml;

        //System.out.println("\nPreview of xml output: ");
        //System.out.println(dataXml);

    }


}




