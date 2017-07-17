/**
 * Created by User on 7/13/2017.
 */
import java.sql.*;

public class LoginDB {
    public static boolean validate(String email,String pass){
        boolean status=false;
        String secret_code= "validated";
        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
             con=ConnectionJDBC.getConection();
         //           "jdbc:mysql://localhost/users","root","root");

            PreparedStatement ps=con.prepareStatement(
                    "select * from users_true where email=? and password=? and secret_code=?");
            ps.setString(1,email);
            ps.setString(2,pass);
            ps.setString(3, secret_code);

            ResultSet rs=ps.executeQuery();
            status=rs.next();

        }catch(Exception e){System.out.println(e);}
        return status;
    }
}