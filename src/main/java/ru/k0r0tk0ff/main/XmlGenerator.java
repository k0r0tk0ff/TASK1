package ru.k0r0tk0ff.main;

import java.sql.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


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

	void generateDocument (ResultSet resultSet) {

		try {

			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
			//mapDoc = docbuilder.parse("mapping.xml");
			mapDoc = docbuilder.parse("mapping.xml");
			dataDoc = docbuilder.newDocument();

			newDoc = docbuilder.newDocument();
		} catch (Exception errorCreateDocument) {
			System.out.println("Problem creating document: ");
            errorCreateDocument.printStackTrace();
		}

		//Retrieve the root element
		Element mapRoot = mapDoc.getDocumentElement();
		//Retrieve the (only) data element and cast it to Element

        //Node dataNode = mapRoot.getElementsByTagName("data").item(0);
		Node dataNode = mapRoot.getElementsByTagName("entries").item(0);
		Element dataElement = (Element)dataNode;

        ResultSetMetaData resultMetadata = null;

        //Create a new element called "data"
        Element dataRoot = dataDoc.createElement("data");

        try {
            //Get the ResultSet information
            resultMetadata = resultSet.getMetaData();
            //Determine the number of columns in the ResultSet
            int numCols = resultMetadata.getColumnCount();

            while (resultSet.next()) {
                //For each row of data
                //Create a new element called "row"
                Element rowEl = dataDoc.createElement("row");
                for (int i=1; i <= numCols; i++) {
                    //For each column index, determine the column name
                    String colName = resultMetadata.getColumnName(i);
                    //Get the column value
                    String colVal = resultSet.getString(i);
                    //Determine if the last column accessed was null
                    if (resultSet.wasNull()) {
                        colVal = "and up";
                    }
                    //Create a new element with the same name as the column
                    Element dataEl = dataDoc.createElement(colName);
                    //Add the data to the new element
                    dataEl.appendChild(dataDoc.createTextNode(colVal));
                    //Add the new element to the row
                    rowEl.appendChild(dataEl);
                }
            }
        } catch (SQLException sqlError) {
            System.out.println("SQL Error! ");
            sqlError.printStackTrace();
        }

        dataDoc.appendChild(dataRoot);
        //Retrieve the root element (also called "root")
        Element newRootInfo = (Element)mapRoot.getElementsByTagName("root").item(0);
        //Retrieve the root and row information
        String newRootName = newRootInfo.getAttribute("name");
        String newRowName = newRootInfo.getAttribute("rowName");
        //Retrieve information on elements to be built in the new document
        NodeList newNodesMap = mapRoot.getElementsByTagName("element");

        //Create the final root element with the name from the mapping file
        Element newRootElement = newDoc.createElement(newRootName);

        //Retrieve all rows in the old document
        NodeList oldRows = dataRoot.getElementsByTagName("row");
        for (int i=0; i < oldRows.getLength(); i++){

            //Retrieve each row in turn
            Element thisRow = (Element)oldRows.item(i);

            //Create the new row
            Element newRow = newDoc.createElement(newRowName);

            for (int j=0; j < newNodesMap.getLength(); j++) {

                //For each node in the new mapping, retrieve the information
                //First the new information...
                Element thisElement = (Element)newNodesMap.item(j);
                String newElementName = thisElement.getAttribute("name");

                //Then the old information
                Element oldElement = (Element)thisElement.getElementsByTagName("content").item(0);
                String oldField = oldElement.getFirstChild().getNodeValue();


                //Get the original values based on the mapping information
                Element oldValueElement = (Element)thisRow.getElementsByTagName(oldField).item(0);
                String oldValue = oldValueElement.getFirstChild().getNodeValue();

                //Create the new element
                Element newElement = newDoc.createElement(newElementName);
                newElement.appendChild(newDoc.createTextNode(oldValue));

                //Retrieve list of new elements
                NodeList newAttributes = thisElement.getElementsByTagName("attribute");
                for (int k=0; k < newAttributes.getLength(); k++) {
                    //For each new attribute
                    //Get the mapping information
                    Element thisAttribute = (Element)newAttributes.item(k);
                    String oldAttributeField = thisAttribute.getFirstChild().getNodeValue();
                    String newAttributeName = thisAttribute.getAttribute("name");

                    //Get the original value
                    oldValueElement = (Element)thisRow.getElementsByTagName(oldAttributeField).item(0);
                    String oldAttributeValue = oldValueElement.getFirstChild().getNodeValue();

                    //Create the new attribute
                    newElement.setAttribute(newAttributeName, oldAttributeValue);
                }

                //Add the new element to the new row
                newRow.appendChild(newElement);

            }
            //Add the new row to the root
            newRootElement.appendChild(newRow);
        }
        //Add the new root to the document
        newDoc.appendChild(newRootElement);

        System.out.println(newRootElement.toString());

	}


}




