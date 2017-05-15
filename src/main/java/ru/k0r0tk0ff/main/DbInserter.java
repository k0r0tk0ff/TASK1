package ru.k0r0tk0ff.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by k0r0tk0ff on 5/15/2017.
 */
public class DbInserter {

    private Connection connection = null;
    private PreparedStatement preparedStatement;

    final String createTable = "create TABLE TEST (field INTEGER);";


    public void dbConnect() {

    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


}
