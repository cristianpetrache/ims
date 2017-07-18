/**
 * Created by User on 7/11/2017.
 */
//import com.mysql.cj.api.mysqla.result.Resultset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {
       // Matcher matcher;
        matcher = pattern.matcher(hex);
        if( matcher.matches() && uniqueEmail(hex))
        return true;

        return false;



    }


    public static boolean uniqueEmail(String email) {
        Connection dbConnection = ConnectionJDBC.getConection();
        String selectSQL = "SELECT * FROM users.users_true WHERE email = '" + email+"'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectSQL);
            if(rs.next()){
                System.out.println("EMAIL ALREADY EXISTS");
                return false;
            }else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}