/**
 * Created by User on 7/11/2017.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private Pattern pattern;
     private Matcher matcher;

    private static final String PASSWORD_PATTERN ="\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])\\S{8,100}\\z";
//            "^" +           //inceput -->
//                    "(?=.*[0-9])" + //minim o cifra
//                    "(?=.*[a-z])" + //minim o litera mica
//                    "(?=.*[A-Z])" + //minim o litera mare
//                    "(?=\\S+$)" +   //spatiile nu sunt permise
//                    ".{8,100}" +       //minim 8 caractere
//                    "$ ";          //<-- sfarsit

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);

    }

    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public  boolean validate(final String hex) {
     //   Matcher matcher;
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
}
