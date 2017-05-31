package ru.k0r0tk0ff.main;


import java.io.*;
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


import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Class for connect to db, and generate xml file from db's data
 *
 *
 */

public class XmlGenerator {

    /**
     * method for generate XML document
     */

    String generateDocument(ResultSet resultSet) throws SQLException, ParserConfigurationException, FileNotFoundException,
            IOException, SAXException, TransformerException, XMLStreamException {

        String dataXml;

        /**
         * Convert to string db Query Result
         */
        List<String> dbQueryResult = new ArrayList();

        while (resultSet.next()) {
            dbQueryResult.add(resultSet.getString(1));
        }


        final XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream("1.xml"), "UTF-8");
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("entries");

        for (int i=0; i<dbQueryResult.size() ; i++) {
            writer.writeStartElement("entry");
            writer.writeStartElement("field");
            writer.writeCharacters(dbQueryResult.get(i));
            writer.writeEndElement();
            writer.writeEndElement();
        }

        writer.writeEndElement();
        writer.writeEndDocument();
        writer.close();


        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new StreamSource(
                        new BufferedInputStream(new FileInputStream("1.xml"))),
                new StreamResult(new FileOutputStream("2.xml"))
        );



/*        for (int i=0; i<dbQueryResult.size() ; i++) {
            Element entry = document.createElement("entry");
            Element field = document.createElement("field");

            Text text = document.createTextNode(dbQueryResult.get(i));
            entries.appendChild(entry);
            entry.appendChild(field);
            field.appendChild(text);
        }*/




        //-------------------------------------------------------------------------------

        /**
         * Generate xml
         */

        //create document
        /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();*/

        //create root element
/*        Element entries = document.createElement("entries");
        document.appendChild(entries);*/

        // for debug
        //System.out.println( String.format(("Arraylist's dimension is %s"),dbQueryResult.size()));

        //create elements with values
        //     <entry>
        //          <field>value=dbQueryResult.get(i)</field>
        //      </entry>
/*        for (int i=0; i<dbQueryResult.size() ; i++) {
            Element entry = document.createElement("entry");
            Element field = document.createElement("field");

            Text text = document.createTextNode(dbQueryResult.get(i));
            entries.appendChild(entry);
            entry.appendChild(field);
            field.appendChild(text);
        }*/

        //serialise and set encoding "UTF-8". Use DOM method
        /*DOMImplementation impl = document.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);

        LSOutput out = implLS.createLSOutput();
        out.setEncoding("UTF-8");
        System.out.println("Convert to UTF-8 encoding success.");*/

        // use BufferedOutputStream for more fast write to file
/*        out.setByteStream(new BufferedOutputStream
                (new FileOutputStream
                        (new File("1.xml"))));

        ser.write(document, out);
        System.out.println("Create file \"1.xml\" success.");
        //dataXml = ser.writeToString(document);

        return ser.writeToString(document);*/

        return "";
    }
}




