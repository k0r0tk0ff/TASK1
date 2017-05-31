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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import java.io.IOException;




/**
 * Class for connect to db, and generate xml file from db's data
 * @since +1.6
 *
 */

class XmlGenerator {

    /**
     * method for generate XML document
     */

    String generateDocument(ResultSet resultSet) throws SQLException, ParserConfigurationException,
            IOException, SAXException, TransformerException, XMLStreamException {

        String dataXml;


        //Convert to string db Query Result
        List<String> dbQueryResult = new ArrayList();

        //1 method (need rewrite with writer -> reader) ----------------------

/*        while (resultSet.next()) {
            dbQueryResult.add(resultSet.getString(1));
        }


        final XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream("0.xml"), "UTF-8");
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

        //for delete
        OutputStream outputStream = null;


        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new StreamSource(
                        new BufferedInputStream(new FileInputStream("0.xml"))),
                              new StreamResult(new FileOutputStream("1.xml"))
        );

        BufferedReader reader = new BufferedReader(new FileReader ("1.xml"));
        //BufferedReader reader = new BufferedReader(new FileReader ("1.xml"));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }*/

        //2 method ----------------------------------------


        while (resultSet.next()) {
            dbQueryResult.add(resultSet.getString(1));
        }

        //  Generate xml

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
        for (String aDbQueryResult : dbQueryResult) {
            Element entry = document.createElement("entry");
            Element field = document.createElement("field");

            Text text = document.createTextNode(aDbQueryResult);
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




