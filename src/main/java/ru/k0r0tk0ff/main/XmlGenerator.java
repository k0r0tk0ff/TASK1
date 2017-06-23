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

        //Convert to string db Query Result
        List<String> dbQueryResult = new ArrayList<>();
        String line;

        while (resultSet.next()) {
            dbQueryResult.add(resultSet.getString(1));
        }

        final XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream("0.xml"), "UTF-8");
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("entries");
        for (String aDbQueryResult : dbQueryResult) {
            writer.writeStartElement("entry");
            writer.writeStartElement("field");
            writer.writeCharacters(aDbQueryResult);
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
                        new BufferedInputStream(new FileInputStream("0.xml"))),
                              new StreamResult(new FileOutputStream("1.xml"))
        );

        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try (BufferedReader reader = new BufferedReader(new FileReader("1.xml"))) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        }
    }
}




