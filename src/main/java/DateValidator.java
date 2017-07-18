import java.time.LocalDate;

/**
 * Created by User on 7/13/2017.
 */
public class DateValidator {


    public boolean validation(LocalDate date) {
        boolean isValid = true;
        if (date.isAfter(LocalDate.now().minusYears(18))) {
            isValid = false;

        }
        return isValid;
    }

}
