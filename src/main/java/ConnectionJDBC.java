/**
 * Created by User on 7/14/2017.
 */
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionJDBC {

    Properties prop = new Properties();
    InputStream input = null;

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/users";
    public static final String JDBC_USER = "root";
    public static final String JDBC_PASS = "root";



    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}