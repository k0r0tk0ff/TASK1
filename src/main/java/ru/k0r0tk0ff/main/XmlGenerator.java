package ru.k0r0tk0ff.main;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import org.w3c.dom.Document;


/**
 * Class for connect to db, and generate xml file from db's data
 *
 */

public class XmlGenerator {





    /**
     * Main class for generate XML document
     */

    void generateDocument(ResultSet resultSet) throws SQLException {

        XStream xstream = new XStream();

        List<String> entries = new ArrayList();

        while (resultSet.next()) {
            entries.add(String.valueOf(resultSet.getInt(1)));

        }

        // Change "list" to "entries"
        xstream.alias("entries", List.class);

        // Change "int" to "entry"
        xstream.alias("entry", java.lang.String.class);

        // Create XML document
        String dataXml = xstream.toXML(entries);

        System.out.println("\nPreview of xml output: ");
        System.out.println(dataXml);

    }


}




