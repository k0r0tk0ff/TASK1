package ru.k0r0tk0ff.main;

import ru.k0r0tk0ff.service.Settings;

/**
 * Created by user on 5/15/2017.
 */
public class Main {




    public static void main(String[] args) {

        Settings settings = Settings.getInstance();

        System.out.println("------------delimiter------------");

        Starter starter = new Starter();
        starter.setN(Integer.parseInt(settings.getValue("n")));
        starter.setUrl(settings.getValue("jdbc.url"));
        starter.setLogin(settings.getValue("jdbc.login"));
        starter.setPassword(settings.getValue("jdbc.password"));


        DbInserter dbInserter = new DbInserter();
        dbInserter.dbConnectAndInsert(
             starter.getIp(),
                  starter.getLogin(),
                      starter.getPassword()
        );

        System.out.println("------------delimiter------------");

    }
}
