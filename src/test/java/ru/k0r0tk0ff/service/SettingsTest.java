package ru.k0r0tk0ff.service;

import org.junit.Test;

import java.io.File;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static ru.k0r0tk0ff.service.Settings.getInstance;

/**
 * //TODO add comments.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class SettingsTest {

    @Test
    public void ShowFilesInRootDirectory () {
        /**
         *  Show files in direstory "/" for project
         */
        File file = new File("./");
        for (File everyFindedFile : file.listFiles()) {
            System.out.println(everyFindedFile);
        }
    }

    /**.
     * Use root directory for contain file with properties "app.properties"
     * Root directory is "src\main\resources"
     * @throws Exception
     */
/*    @Test
    public void whenClassLoader() throws Exception {
        Settings settings = new Settings();
        ClassLoader loader = Settings.class.getClassLoader();
        try (InputStream io = loader.getResourceAsStream("app.properties")) {
            settings.load(io);
        }
        String value = settings.getValue("home.path");
        assertThat(value, is("c:\\tmp\\"));
    }*/

    /**.
     * Use full path for access to file with properties "app.properties"
     * Root directory is "src\main\resources"
     * @throws Exception
     */
/*    @Test
    public void whenClassLoaderLoadFromFullPathFromFolderResources() throws Exception {

        Settings settings = getInstance();
        ClassLoader loader = Settings.class.getClassLoader();
        try (InputStream io = loader.getResourceAsStream("ru/k0r0tk0ff/service/app.properties")) {
            settings.load(io);
        }
        String value = settings.value("jdbc.password");
        assertThat(value, is("zxcvbnm"));

    }*/
}