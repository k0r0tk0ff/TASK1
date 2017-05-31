package ru.k0r0tk0ff.main;

import java.io.*;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by k0r0tk0ff
 * @since +1.6
 */
class XsltGenerator {

    void generateXmlWithUseXslt(String path) throws IOException, TransformerException, SAXException, ParserConfigurationException {
        File stylesheet = new File("Transform.xslt");
        File datafile = new File(path);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder2 = factory.newDocumentBuilder();
        Document document2 = builder2.parse(datafile);

        // Use a Transformer for output
        TransformerFactory tFactory = TransformerFactory.newInstance();
        StreamSource stylesource = new StreamSource(stylesheet);

        Transformer transformer = tFactory.newTransformer(stylesource);

        //transformer.setParameter("format-pretty-print", true);
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
        transformer.setOutputProperty(OutputKeys.STANDALONE,"yes");


        DOMSource source = new DOMSource(document2);
        StreamResult result = new StreamResult(System.out);

        //for show on screen
        transformer.transform(source, result);

        //for write to file
        transformer.transform(source, new StreamResult(new FileOutputStream("2.xml")));
    }
}
