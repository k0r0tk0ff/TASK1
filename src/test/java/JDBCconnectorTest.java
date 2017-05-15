/**.
 *
 * Created by mkyong
 *
 * https://www.mkyong.com/jdbc/how-do-connect-to-postgresql-with-jdbc-driver-java/
 */

import ru.k0r0tk0ff.service.Settings;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class JDBCconnectorTest {

    public static void main(String[] argv) throws Exception {
        /**.
         *  Check for install JDBC driver
         */
        System.out.println("--- PostgreSQL JDBC Connection Testing ----");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Install PostgreSQL JDBC Driver!");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");

        /**.
         *  Test connection
         */

        Settings settings = Settings.getInstance();
        assertThat(settings.getValue("jdbc.username"), is("postgres"));

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    settings.getValue("jdbc.url"),
                    settings.getValue("jdbc.login"),
                    settings.getValue("jdbc.password"));
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println(" Connect successful !");
        } else {
            System.out.println(" Connection Failed!");
        }
    }

}