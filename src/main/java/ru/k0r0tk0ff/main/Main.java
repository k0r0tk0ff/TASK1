package ru.k0r0tk0ff.main;

import ru.k0r0tk0ff.service.Settings;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by user on 5/15/2017.
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

        DbInserter dbInserter = new DbInserter();

        dbInserter.dbConnectAndInsert(
             starter.getIp(),
                  starter.getLogin(),
                      starter.getPassword(),
                          starter.getN());
        dbInserter.closeConnection();
        System.out.println("------------delimiter------------");

    }
}
