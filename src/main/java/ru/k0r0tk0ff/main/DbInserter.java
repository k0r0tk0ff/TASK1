package ru.k0r0tk0ff.main;

import com.sun.javafx.binding.StringFormatter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by k0r0tk0ff on 5/15/2017.
 */
public class DbInserter {
    private Connection connection = null;

    //private PreparedStatement preparedStatement;




    void dbConnectAndInsert(String url, String login, String password, int n) {


        Statement statementForDrop;
        Statement statementForInsert;
        Statement statementForInsertData;

        final String createTable = "create TABLE TEST (field INTEGER);";
        final String dropTable = "DROP TABLE TEST;";

	    // array n - for values from 0 to n
	    int[] array = new int[n];
	    StringBuilder sb = new StringBuilder();

	     /**
         * Connect to DB, create table (drop if exist)
         */

        try {
            connection = DriverManager.getConnection(
                    url,
                    login,
                    password);

            // Try drop table, if exist

            try {
                statementForDrop = connection.createStatement();
                statementForDrop.execute(dropTable);
                System.out.println("Drop success !!!");
            } catch (SQLException sqlErrorForDropTable){
                System.out.println("Drop failed !!!");
                sqlErrorForDropTable.printStackTrace();
            }

            try {
                    statementForInsert = connection.createStatement();
                    statementForInsert.execute(createTable);
                System.out.println("Create table success!!");

            } catch (SQLException sqlErrorForInsertTable) {
                System.out.println("Create table failed !!!");
                sqlErrorForInsertTable.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        /**
         * Create sql query for insert
         */

        // etalon
        //final String testinserter = "INSERT into TEST (field) VALUES ('111'),('123');";

        String varStringForInsert = "INSERT into TEST (field) VALUES";

	    //for debug
        //System.out.println("N = " + n);

	    sb.append(varStringForInsert);

	    for (int i = 0; i<n; i++) {
		    array[i] = (int) (Math.floor(Math.random()*1000));
		    if (i != (n-1)) {
			    sb.append(String.format("(%s),", array[i]));
		    }
	    }

	    // add last value without ","
	    sb.append(String.format("(%s)", array[n-1]));

		// add ;"
	    sb.append(String.format("%s", ";"));

		// for debug
        //System.out.println(sb.toString());
        //System.out.println("----------------------");

	    /**
	     * Insert data
	     */

	    try {
		    statementForInsertData = connection.createStatement();
		    statementForInsertData.execute(sb.toString());
		    System.out.println("Insert data success!!");

	    } catch (SQLException sqlErrorForInsertTable) {
		    System.out.println("Insert data failed !!!");
		    sqlErrorForInsertTable.printStackTrace();
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


