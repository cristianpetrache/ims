/**
 * Created by User on 7/13/2017.
 */
import java.sql.*;

public class LoginDB {
    public static boolean validate(String email,String pass){
        boolean status=false;
        int confirmed=1;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost/users","root","root");

            PreparedStatement ps=con.prepareStatement(
                    "select * from users_true where email=? and password=? and confirmed=?");
            ps.setString(1,email);
            ps.setString(2,pass);
            ps.setInt(3,confirmed);

            ResultSet rs=ps.executeQuery();
            status=rs.next();

        }catch(Exception e){System.out.println(e);}
        return status;
    }
}