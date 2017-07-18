

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by User on 7/10/2017.
 */
public class ConnectionJDBC {
    public static String JDBC_URL;
    public static String JDBC_USER;
    public static String JDBC_PASS;

    static {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            //inputStream = new FileInputStream("db.properties");
            properties.load(ConnectionJDBC.class.getResourceAsStream("/database.properties"));

            JDBC_URL=properties.getProperty("JDBC_URL");
            JDBC_USER=properties.getProperty("JDBC_USER");
            JDBC_PASS=properties.getProperty("JDBC_PASS");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getConection() {
        Connection connection = null;

        try {
            connection= DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}