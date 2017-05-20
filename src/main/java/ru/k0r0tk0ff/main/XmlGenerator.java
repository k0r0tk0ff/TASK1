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

	PreparedStatement preparedStatement = null;
	Connection connection = null;
	String sqlQuery = "SELECT * FROM TEST";

	//Create the variable for Document object
	Document mapDoc = null;
	Document dataDoc = null;
	Document newDoc = null;

	ResultSet getDataFromDb(String url, String login, String password, int n) {

		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection(
					url,
					login,
					password);

			try {
				preparedStatement = connection.prepareStatement(sqlQuery);
				resultSet = preparedStatement.executeQuery();
				System.out.println("Select success !!!");

				//for debug - need to see result of sql query
				// resultSet.getInt(1) - 1 is column index

				//while (resultSet.next()) {
				//	System.out.println(resultSet.getInt(1));
				//}
			} catch (SQLException sqlErrorForDropTable) {
				System.out.println("Select failed !!!");
				sqlErrorForDropTable.printStackTrace();
			}


		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}

		/**
		 * Close connection
		 */
		try {
			connection.close();
		} catch (SQLException closeError) {
			System.out.println("Can't close connection.");
			closeError.printStackTrace();
		}

		return resultSet;
	}

    /**
     * Main class for generate XML document
!!!     * @return document
     */

	void generateDocument (String url, String login, String password) {

        XStream xstream = new XStream();


        List<String> entries = new ArrayList();

        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(
                    url,
                    login,
                    password);

            try {
                preparedStatement = connection.prepareStatement(sqlQuery);
                resultSet = preparedStatement.executeQuery();
                System.out.println("Select success !!!");

                try {
                    while (resultSet.next()) {
                        entries.add(String.valueOf(resultSet.getInt(1)));
                        //String dataXml = xstream.toXML(resultSet.getInt(1));
                        //String dataXml = xstream.toXML(entries.toString());
                        //System.out.println(dataXml);

                    }
                } catch (SQLException sqlError) {
                    System.out.println("Select failed !!!");
                    sqlError.printStackTrace();
                }

                /*for (Integer i  : entries) {
                    System.out.println(i);

                }*/

                // Change "list" to "entries"
                xstream.alias("entries", List.class);

                // Change "int" to "entry"
				xstream.alias("entry", java.lang.String.class);

				// Create XML document
                String dataXml = xstream.toXML(entries);

                System.out.println(dataXml);


            } catch (SQLException sqlErrorForDropTable) {
                System.out.println("Select failed !!!");
                sqlErrorForDropTable.printStackTrace();
            }


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }


	}


}




