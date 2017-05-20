package ru.k0r0tk0ff.main;

import ru.k0r0tk0ff.service.Settings;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by k0r0tk0ff on 5/15/2017.
 */
public class Main {

    public static void main(String[] args) {

        /**
         * Get variables from file with settings - "parameters.properties"
         */

        Settings settings = Settings.getInstance();

        System.out.println("------------delimiter------------");

        Starter starter = new Starter();
        starter.setN(Integer.parseInt(settings.getValue("n")));
        starter.setUrl(settings.getValue("jdbc.url"));
        starter.setLogin(settings.getValue("jdbc.login"));
        starter.setPassword(settings.getValue("jdbc.password"));

        /**
         * Insert in to DB values
         */
        starter.insertDataToDB();


        /**
         * Get data from DB
         */


        ResultSet resultSet = starter.getDataFromDb();

        // show data in db
/*        try {
                while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        } catch (SQLException sqlError) {
            System.out.println("Select failed !!!");
            sqlError.printStackTrace();
        }*/

        XmlGenerator xmlGenerator = new XmlGenerator();
        xmlGenerator.generateDocument(starter.getIp(), starter.getLogin(), starter.getPassword());




    }
}
