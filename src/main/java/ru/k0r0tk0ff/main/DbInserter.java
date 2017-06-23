package ru.k0r0tk0ff.main;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by k0r0tk0ff
 * @since +1.6
 */
class DbInserter {

    /**
     * @param connection - connection to DB
     * @param n - entered value
     */

    void dbInsert(Connection connection, int n) throws SQLException {

        Statement statementForDrop;
        Statement statementCreateTable;
        Statement statementForInsertData;

        final String createTable = "create TABLE TEST (field INTEGER);";
        final String dropTable = "DROP TABLE TEST;";

	    // array n - for values from 0 to n
	    int[] array = new int[n];

        // Try drop table, if exist
        try {
            statementForDrop = connection.createStatement();
            statementForDrop.execute(dropTable);
            System.out.println("Drop success !!!");
            statementForDrop.close();
        } catch (SQLException sqlErrorForDropTable) {
            System.out.println("Drop failed !!!");
            sqlErrorForDropTable.printStackTrace();
        }

        // Try crete table
        try {
            statementCreateTable = connection.createStatement();
            statementCreateTable.execute(createTable);
            System.out.println("Create table success!!");
            statementCreateTable.close();
        } catch (SQLException sqlErrorForCreateTable) {
            System.out.println("Create table failed !!!");
            sqlErrorForCreateTable.printStackTrace();
        }

        //Create sql query for insert
        // etalon
        //final String testinserter = "INSERT into TEST (field) VALUES ('111'),('123');";

        //Insert data
	    try {
		    statementForInsertData = connection.createStatement();
            ArrayList<String> queries = new ArrayList<>();
            for (int i = 1; i<n+1; i++) {
                queries.add(String.format("INSERT into TEST (field) VALUES ('%s')", i));
            }

            for (String query : queries) {
                statementForInsertData.addBatch(query);
            }
		    statementForInsertData.executeBatch();
            System.out.println("Insert data success!!");
            statementForInsertData.close();

	    } catch (SQLException sqlErrorForInsertData) {
		    System.out.println("Insert data failed !!!");
		    sqlErrorForInsertData.printStackTrace();
	    }

        //Close connection
        try {
            connection.close();
        } catch (SQLException closeError) {
            System.out.println("Can't close connection.");
            closeError.printStackTrace();
        }
    }
}


