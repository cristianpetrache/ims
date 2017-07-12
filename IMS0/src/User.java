import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by User on 7/11/2017.
 */



public class User {

    private String displayName;
    private String email;
    private String password;
    private Date date;

    public User(){

    }

    public ArrayList<String> dataValidation(){
        ArrayList<String> arl=new ArrayList<String>();

        if(displayName==null)
            arl.add("This is not a valid Display Name. Display name should have at least 1 char and no more than 100");
        else
            if(email==null)
                arl.add("This is not a valid email address");
            else
                if(password==null)
                    arl.add("This is not a valid password. It must be between 8 and 100 characters, can contain any character, but at least one upper case and one number");
                else
                    if(date==null)
                        arl.add("This is not a valid date of birth.");

return  arl;

    }





    public User(String displayName, String email, String password, Date date)
    {

        if(displayNameValidation(displayName))
            this.displayName = displayName;

        if(emailValidation(email))
            this.email=email;
        else return;


            this.password=password;

        if(dateValidation(date))
            this.date=date;
    }


   private boolean displayNameValidation(String name){
       if(name.length()>100 | name.length()<1)
           return false;

       return true;
    }

  private boolean emailValidation(String email){

        EmailValidator validator=new EmailValidator();

       if(validator.validate(email))
           return true;

        return false;
    }

     boolean passwordValidation(String password){

      PasswordValidator validator= new PasswordValidator();

      if(validator.validate(password))
          return true;

      return false;

    }

   static private boolean dateValidation(Date date){
     Period period;


       LocalDate dateInLocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       period = Period.between(dateInLocalDate,LocalDate.now());

        if(period.getYears()+1900==18 && period.getMonths()+1>0   )
        {
            return true;}
            else if(period.getYears()+1900>18)
                return true;

       return false;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "User{" +
                "displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                '}';
    }
}
