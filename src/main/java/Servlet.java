
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import java.util.UUID;

/**
 * Created by UserPrototype on 7/11/2017.
 */
@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet implements Constant {
    PrintWriter out;

    public ArrayList<String> mistakes = new ArrayList<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out=response.getWriter();

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

            if(object!=null){
                String password = object.getString("password");
                PasswordValidator validator = new PasswordValidator();
                if(validator.validate(password)) {

                    String cryptedPass = Crypt.cryptPassword(password);


                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                    formatter = formatter.withLocale(Locale.GERMANY );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
                    LocalDate date = LocalDate.parse(object.getString("dateOfBirth"), formatter);

                    user = new User(object.getString("displayName"), object.getString("email"),
                            cryptedPass, date);
                    user.setToken(object.getString("token"));

                    JSONObject token = new JSONObject("{ token: "+object.getString("token")+ "}");

                    int responseCode = user.validateToken(token.toString());
                    if(responseCode == 200){

                        mistakes = user.dataValidation();
                        if(mistakes.size() != 0){
                            for(String mistake: mistakes){
                                System.out.println(mistake);
                                out.print(mistake);
                                out.flush();
                            }
                        }else {

                            user.setToken(object.getString("token"));

                            //generate and send secretCode
                            UUID uuid =  UUID.randomUUID();
                            GoogleMail.Send("supermega.team.0@gmail.com", "easypeasylemonsqueezy", user.getEmail(), "Team 0 verification email",
                                    uuid.toString());
                            user.setSecretCode(uuid.toString());
                            System.out.println("Email trmis cu success!");
                            out.print("Email trmis cu success!");
                            out.flush();

                            JDBCregister.insertBD(user.getDisplayName(), user.getEmail(), user.getPassword(),String.valueOf(user.getDate()), user.getSecretCode());

                            System.out.println("servlet POST: " + user.toString());
                            this.getServletConfig().getServletContext().setAttribute("User", user);
                            request.getRequestDispatcher("/addUser").forward(request, response);
                        }



                    }else{
                        System.out.println("The token has not been validated!");
                        out.print("The token has not been validated!");
                        out.flush();
                    }


                }else{
                    System.out.println("PAROLA INVALIDA");
                    out.print("PAROLA INVALIDA");
                    out.flush();
                }
            }else{
                System.out.println("---FAIL TO CONVERT JSON---");
                out.print("---FAIL TO CONVERT JSON---");
                out.flush();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }


}
