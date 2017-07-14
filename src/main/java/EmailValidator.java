/**
 * Created by User on 7/11/2017.
 */
import java.sql.Connection;
import java.sql.ResultSet;
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

        int repetareEmail = 0;
        try {


            String selectSQL = "SELECT COUNT(id) as total FROM users.users_true WHERE email = ?";
            java.sql.PreparedStatement ps = dbConnection.prepareStatement(selectSQL);
            ps.setString(1, email);
            ResultSet rs=  ps.executeQuery();


            while(rs.next()){
                repetareEmail= rs.getInt("total");
            }



            // System.out.println(repetareEmail);


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (repetareEmail == 0)
            return true;
        return false;
    }
}