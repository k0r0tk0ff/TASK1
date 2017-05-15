package ru.k0r0tk0ff.main;

import java.sql.*;

/**
 * Created by k0r0tk0ff on 5/15/2017.
 */
public class DbInserter {

    //private PreparedStatement preparedStatement;




    public void dbConnectAndInsert(String url, String login, String password) {
        Connection connection = null;
        Statement statementForDrop;
        Statement statementForInsert;
        final String createTable = "create TABLE TEST (field INTEGER);";
        final String dropTable = "DROP TABLE TEST;";


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
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }
}
