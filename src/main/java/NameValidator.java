import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 7/13/2017.
 */
public class NameValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String NAME_PATTERN="^[_A-Za-z0-9]*$";

    public NameValidator(){
        pattern=Pattern.compile(NAME_PATTERN);
    }

    private boolean displayNameValidation(String name){
        if(name.length()>100 | name.length()<1)
            return false;

        return true;
    }

    public  boolean validate(final String hex) {
        //   Matcher matcher;
        matcher = pattern.matcher(hex);
        if(displayNameValidation(hex) && matcher.matches())
            return true;

        return false;
    }
}
