import java.util.Date;

/**
 * Created by UserPrototype on 7/11/2017.
 */
public class UserPrototype {
    private String displayName;
    private Date dateOfBirth;
    private String email;
    private String password;
    private String secretCode;

    public UserPrototype(){

    }
    public UserPrototype(String displayName, Date dateOfBirth, String email, String password, String secretCode) {
        this.displayName = displayName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.secretCode = secretCode;
    }

    public UserPrototype(String displayName, Date dateOfBirth, String email, String password) {
        this.displayName = displayName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.secretCode=null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    @Override
    public String toString() {
        return "UserPrototype{" +
                "displayName='" + displayName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", secretCode='" + secretCode + '\'' +
                '}';
    }
}
