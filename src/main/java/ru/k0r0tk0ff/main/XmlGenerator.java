package ru.k0r0tk0ff.main;

import java.sql.*;

/**
 * Class for connect to db, and generate xml file from db's data
 *
 */

public class XmlGenerator {

	PreparedStatement preparedStatement = null;
	Connection connection = null;
	String sqlQuery = "SELECT * FROM TEST";

	void getDataFromDbAndGenerateXmlFile(String url, String login, String password, int n) {

		try {
			connection = DriverManager.getConnection(
					url,
					login,
					password);

			// Try drop table, if exist

			try {
				preparedStatement = connection.prepareStatement(sqlQuery);
				ResultSet resultSet = preparedStatement.executeQuery();
				System.out.println("Select success !!!");
			} catch (SQLException sqlErrorForDropTable){
				System.out.println("Select failed !!!");
				sqlErrorForDropTable.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}

	}

	void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
