package ru.k0r0tk0ff.main;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


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

	Document generateDocument () {

		//InputStream input = null;
		//FileInputStream fileInputStream = null;
		//input = this.getClass().getClassLoader().getResourceAsStream("mapping.xml");

		try {

			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
			//mapDoc = docbuilder.parse("mapping.xml");
			mapDoc = docbuilder.parse("mapping.xml");
			dataDoc = docbuilder.newDocument();

			newDoc = docbuilder.newDocument();
		} catch (Exception e) {
			System.out.println("Problem creating document: "+e.getMessage());
		}

		return newDoc;
	}


}




