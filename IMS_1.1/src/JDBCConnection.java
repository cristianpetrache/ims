import java.sql.*;

public class JDBCConnection {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/users";


    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    public static void insertBD(String displayName, String email, String password, String dateOfBirth, String token) {


        Connection conn = null;
        PreparedStatement stmt = null;
        try{

            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");



            stmt = conn.prepareStatement("INSERT INTO users_true( display_name,email, password, date, token) VALUES ( ?, ?, ?, ?, ?)");

            stmt.setString(1, displayName);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, dateOfBirth);
            stmt.setString(5, token);

            stmt.executeUpdate();


            System.out.println("Inserted records into the table...");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end JDBCExample