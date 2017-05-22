package ru.k0r0tk0ff.main;

import ru.k0r0tk0ff.service.Settings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by k0r0tk0ff on 5/15/2017.
 */
public class Main {



    public static void main(String[] args) throws SQLException {

        /**
         * Get variables from file with settings - "parameters.properties"
         */
        Settings settings = Settings.getInstance();


        String xmlAsAString;

        System.out.println("------------delimiter------------");

        Starter starter = new Starter();
        starter.setN(Integer.parseInt(settings.getValue("n")));
        starter.setUrl(settings.getValue("jdbc.url"));
        starter.setLogin(settings.getValue("jdbc.login"));
        starter.setPassword(settings.getValue("jdbc.password"));

        /**
         * Get connection to DB
         */
        Connection connection = starter.getConnectionToDB();

        /**
         * Insert in to DB values
         */
        starter.insertDataToDB(connection);

        /**
         * Get data from DB
         */
        ResultSet resultSet = starter.getDataFromDb(connection);

        /**
         * Generate XML from result of query to DB
         */
        xmlAsAString = starter.generateXml(resultSet);

        /**
         * See xml output on screen
         */
        System.out.println("\nSee XML: ");
        System.out.println(xmlAsAString);





    }
}
