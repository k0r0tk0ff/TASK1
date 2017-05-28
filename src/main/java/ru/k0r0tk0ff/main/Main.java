package ru.k0r0tk0ff.main;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;
import ru.k0r0tk0ff.service.Settings;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.xml.crypto.dsig.Transform.XSLT;

/**
 * Created by k0r0tk0ff on 5/15/2017.
 */
public class Main {



    public static void main(String[] args) throws SQLException, TransformerConfigurationException {

        /**
         * Get variables from file with settings - "parameters.properties"
         */
        Settings settings = Settings.getInstance();


        String xmlAsAString;

        System.out.println("------------delimiter------------");

        Starter starter = new Starter();
        starter.setN(Integer.parseInt(settings.getValue("n")));
        starter.setUrl(settings.getValue("jdbc.url"));
        starter.setLogin(settings.getValue("jdbc.login"));
        starter.setPassword(settings.getValue("jdbc.password"));

        /**
         * Get connection to DB
         */
        Connection connection = starter.getConnectionToDB();

        /**
         * Insert in to DB values
         */
        starter.insertDataToDB(connection);

        /**
         * Get data from DB
         */
        ResultSet resultSet = starter.getDataFromDb(connection);

        /**
         * Generate XML from result of query to DB
         */
        xmlAsAString = starter.generateXml(resultSet);

        /**
         * See xml output on screen and create XML file "1.xml"
         */
        System.out.println("\nSee XML: ");
        System.out.println(xmlAsAString);

        /**
         * Read XML file "1.xml" to String variable
         */
        String outOfFile = starter.readFileToString();
        System.out.println(outOfFile);

        //

      /*  XStream xstream = new XStream();
        String outOfFile = (String)xstream.fromXML(outOfFile);
      */
        /*        String xslt = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:output method=\"xml\" omit-xml-declaration=\"yes\" indent=\"no\"/>\n" +
                "  <xsl:template match=\"/cat\">\n" +
                "    <xsl:copy>\n" +
                "      <xsl:apply-templates select=\"mName\"/>\n" +
                "    </xsl:copy>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";*/


/*        XStream xstream = new XStream();
        xstream.alias("cat", Cat.class);

        TraxSource traxSource = new TraxSource(new Cat(4, "Garfield"), xstream);
        Writer buffer = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer(
                new StreamSource(new StringReader(XSLT)));
        transformer.transform(traxSource, new StreamResult(buffer));*/

    }
}
