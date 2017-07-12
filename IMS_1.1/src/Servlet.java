
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by UserPrototype on 7/11/2017.
 */
@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet implements Constant {

    public ArrayList<String> mistakes = new ArrayList<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder jsonBuilder = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jsonBuilder.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonString = jsonBuilder.toString();

        User user = new User();
        try {
            org.json.JSONObject object = new org.json.JSONObject(jsonString);
           // System.out.println("DATA NASTERII ESTE: "+ object.getString("dateOfBirth"));
            if(object!=null){
                String password = object.getString("password");
                PasswordValidator validator = new PasswordValidator();
                if(validator.validate(password)) {
                    String cryptedPass = Crypt.cryptPassword(password);
//                    user = new User(object.getString("displayName"), object.getString("email"),
//                            object.getString("password"), simpleDateFormat.parse(object.getString("dateOfBirth")));

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                    formatter = formatter.withLocale(Locale.GERMANY );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
                    LocalDate date = LocalDate.parse(object.getString("dateOfBirth"), formatter);

                    user = new User(object.getString("displayName"), object.getString("email"),
                            cryptedPass, date);

                    mistakes = user.dataValidation();
                    if(mistakes.size() != 0){
                        for(String mistake: mistakes){
                            System.out.println(mistake);
                        }
                    }else {

                        System.out.println("servlet POST: " + user.toString());
                        this.getServletConfig().getServletContext().setAttribute("sharedUser", user);
                        request.getRequestDispatcher("/getToken").forward(request, response);
                    }
                }else{
                    System.out.println("PAROLA INVALIDA");
                }
            }else{
                System.out.println("---FAIL TO CONVERT JSON---");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }


}
