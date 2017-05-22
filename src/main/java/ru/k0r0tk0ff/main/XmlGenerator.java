package ru.k0r0tk0ff.main;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * Class for connect to db, and generate xml file from db's data
 *
 */

public class XmlGenerator {

    /**
     * method for generate XML document
     */

    String generateDocument(ResultSet resultSet) throws SQLException {

        XStream xstream = new XStream();

        List<String> entries = new ArrayList();

        while (resultSet.next()) {
            entries.add(String.valueOf(resultSet.getInt(1)));

        }

        // Change "list" to "entries"
        xstream.alias("entries", List.class);

        // Change "String" to "entry"
        xstream.alias("entry", java.lang.String.class);

        // Create XML document
        String dataXml = xstream.toXML(entries);

        return dataXml;

        //System.out.println("\nPreview of xml output: ");
        //System.out.println(dataXml);

    }


}




