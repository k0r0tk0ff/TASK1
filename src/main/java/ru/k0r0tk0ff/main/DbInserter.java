package ru.k0r0tk0ff.main;

import java.sql.*;

/**
 * Created by k0r0tk0ff
 * @since +1.6
 */
class DbInserter {

    /**
     * @param connection - connection to DB
     * @param n - entered value
     */

    void dbInsert(Connection connection, int n) {

        Statement statementForDrop;
        Statement statementCreateTable;
        Statement statementForInsertData;

        final String createTable = "create TABLE TEST (field INTEGER);";
        final String dropTable = "DROP TABLE TEST;";

	    // array n - for values from 0 to n
	    int[] array = new int[n];
	    StringBuilder sb = new StringBuilder();

        // Try drop table, if exist
        try {
            statementForDrop = connection.createStatement();
            statementForDrop.execute(dropTable);
            System.out.println("Drop success !!!");
        } catch (SQLException sqlErrorForDropTable) {
            System.out.println("Drop failed !!!");
            sqlErrorForDropTable.printStackTrace();
        }

        // Try crete table
        try {
            statementCreateTable = connection.createStatement();
            statementCreateTable.execute(createTable);
            System.out.println("Create table success!!");
        } catch (SQLException sqlErrorForCreateTable) {
            System.out.println("Create table failed !!!");
            sqlErrorForCreateTable.printStackTrace();
        }


        //Create sql query for insert

        // etalon
        //final String testinserter = "INSERT into TEST (field) VALUES ('111'),('123');";

        String varStringForInsert = "INSERT into TEST (field) VALUES";

	    //for debug
        //System.out.println("N = " + n);

	    sb.append(varStringForInsert);

	    //for (int i = 0; i<n-1; i++) {
	    for (int i = 1; i<n; i++) {
		    //for random values
	        //array[i] = (int) (Math.floor(Math.random()*1000));

            array[i] = i;
            sb.append(String.format("(%s),", array[i]));
		}

	    // add last value without ","
	    sb.append(String.format("(%s)", n));

		// add ;"
	    sb.append(String.format("%s", ";"));

		// for debug
        //System.out.println(sb.toString());
        //System.out.println("----------------------");


        //Insert data
	    try {
		    statementForInsertData = connection.createStatement();
		    statementForInsertData.execute(sb.toString());
		    System.out.println("Insert data success!!");

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


