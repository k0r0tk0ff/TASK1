package ru.k0r0tk0ff.main;

import java.sql.*;

/**
 * Created by user on 5/15/2017.
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
    public void setN(int n) {
        this.n = n;
    }

    /**
     * Setter for property 'ip'.
     *
     * @param ip Value to set for property 'ip'.
     */
    public void setUrl(String ip) {
        this.url = ip;
    }

    /**
     * Setter for property 'login'.
     *
     * @param login Value to set for property 'login'.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Setter for property 'password'.
     *
     * @param password Value to set for property 'password'.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for property 'n'.
     *
     * @return Value for property 'n'.
     */
    public int getN() {
        return n;
    }

    /**
     * Getter for property 'ip'.
     *
     * @return Value for property 'ip'.
     */
    public String getIp() {
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
     */
	void insertDataToDB(Connection connection) {
		DbInserter dbInserter = new DbInserter();

        dbInserter.dbInsert(
            connection,
		    this.getN());
	}

    /**
     * Get Resultset from DB
     * @return resultSet
     */
	ResultSet getDataFromDb(Connection connection) {

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
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

	String generateXml (ResultSet resultSet) throws SQLException {
        XmlGenerator xmlGenerator = new XmlGenerator();
        String result = "Xml create fail!";

        try {
            result = xmlGenerator.generateDocument(resultSet);
        } catch (SQLException e) {
            System.out.println(" Generate XML error!");
            e.printStackTrace();
        }

        return result;
    }

    void saveToFile (String stringInput) {
	    XmlToFileSaver xmlToFileSaver = new XmlToFileSaver();
	    xmlToFileSaver.saveStringToXml(stringInput);
    }

    String readFileToString() {
	    XsltGenerator xsltGenerator = new XsltGenerator();
	    return xsltGenerator.loadFileToString();
    }


}
