package ru.k0r0tk0ff.main;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by k0r0tk0ff
 * Class for contain main execute methods
 */
public class Starter {
    int n;
    String url;
    String login;
    String password;


    /**
     * Setter for property 'n'.
     *
     * @param n Value to set for property 'n'.
     */
    void setN(final int n) {
        this.n = n;
    }

    /**
     * Setter for property 'ip'.
     *
     * @param enteredUrl Value to set for property 'ip'.
     */
    void setUrl(final String enteredUrl) {
        this.url = enteredUrl;
    }

    /**
     * Setter for property 'login'.
     *
     * @param login Value to set for property 'login'.
     */
    void setLogin(final String login) {
        this.login = login;
    }

    /**
     * Setter for property 'password'.
     *
     * @param password Value to set for property 'password'.
     */
    void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Getter for property 'n'.
     *
     * @return Value for property 'n'.
     */
    int getN() {
        return n;
    }

    /**
     * Getter for property 'ip'.
     *
     * @return Value for property 'ip'.
     */
    String getUrl() {
        return url;
    }

    /**
     * Getter for property 'login'.
     *
     * @return Value for property 'login'.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Getter for property 'password'.
     *
     * @return Value for property 'password'.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method for connection to DB
     * @return connection
     */

    Connection getConnectionToDB () {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    url,
                    login,
                    password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Insert data to DB
     * @param connection
     * connect to our DB
     */
	void insertDataToDB(Connection connection) {
		DbInserter dbInserter = new DbInserter();

        try {
            dbInserter.dbInsert(
                connection,
                this.getN());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Resultset from DB
     * @return resultSet
     */
	ResultSet getDataFromDb(Connection connection) {

        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        String sqlQuery = "SELECT * FROM TEST";
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


        //Close connection
        try {
            connection.close();
        } catch (SQLException closeError) {
            System.out.println("Can't close connection.");
            closeError.printStackTrace();
        }

        return resultSet;
    }

	void generateXml (final ResultSet resultSet) throws SQLException, XMLStreamException {
        XmlGenerator xmlGenerator = new XmlGenerator();
        String result = "Xml create fail!";

        try {
            result = xmlGenerator.generateDocument(resultSet);
        } catch (SQLException e) {
            System.out.println(" Generate XML error!");
            e.printStackTrace();
        } catch (ParserConfigurationException | IOException | TransformerException | SAXException error) {
            error.printStackTrace();
        }
    }

    void xsltTransform(final String path) {

        XsltGenerator xsltGenerator = new XsltGenerator();

        try {
            xsltGenerator.generateXmlWithUseXslt(path);
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    void xmlParserToArrayListAndSum(final String path) {
	    int sum = 0;
        XmlFileParserAndSumCounter xmlFileParser = new XmlFileParserAndSumCounter();
        ArrayList<Integer> arrayForSum = xmlFileParser.parseXmlFileToArrayList(path);

        System.out.println("Parse file to array success.");

        for (int i: arrayForSum ) {
            sum = sum + i;
        }

        System.out.println(String.format("Sum is %s",sum));
    }
}
