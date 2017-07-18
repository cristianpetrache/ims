import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet(name = "servlet1")
public class ServletLoginCheck extends HttpServlet {
    PrintWriter out;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        out = response.getWriter();
        HttpSession httpSession = request.getSession();


        if(httpSession.getAttribute("attempts")==null) {
            httpSession.setAttribute("attempts", 0);
        }
        if(httpSession.getAttribute("timeout")==null) {
            httpSession.setAttribute("timeout",new Date());
        }


        final String queryCheck = "SELECT * from users_true WHERE email = ?";

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
        JSONObject jsonObject = new JSONObject(jsonString);
        Crypt crypter = new Crypt();
        String cryptedPass = crypter.cryptPassword(jsonObject.getString("password"));


        if (httpSession.getAttribute("email")==null) {
            Date timeoutCheck = (Date) httpSession.getAttribute("timeout");
            if (timeoutCheck.before(new Date())) {
                httpSession.setAttribute("attempts", 0);
            }
            if ((Integer) httpSession.getAttribute("attempts") <= 4) {

                if (LoginDB.validate(jsonObject.getString("email"), cryptedPass)) {
                    System.out.println("LOG IN SUCCESSFUL!");
                    out.print("Log in successful!");
                    out.flush();
                    httpSession.setAttribute("email", jsonObject.getString("email"));
                    httpSession.setMaxInactiveInterval(15*60);
                    Connection connection = ConnectionJDBC.getConection();
                    final PreparedStatement ps;
                    try {
                        ps = connection.prepareStatement(queryCheck);
                        ps.setString(1, jsonObject.getString("email"));
                        final ResultSet resultSet = ps.executeQuery();
                        if (resultSet.next()) {
                            User user = new User();
                            user.setDisplayName(resultSet.getString("display_name"));
                            user.setEmail(resultSet.getString("email"));
                            user.setPassword(resultSet.getString("password"));

                            this.getServletConfig().getServletContext().setAttribute("FinalUser", user);
                            request.getRequestDispatcher("/ServletWelcome").forward(request, response);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }


                } else {

                    System.out.println("LOG IN UNSUCCESFUL!");
                    out.print("LOG IN UNSUCCESSFUL!");
                    out.flush();
                    System.out.println("USERNAME / PASSWORD INCORRECT");
                    out.print("USERNAME / PASSWORD INCORRECT");
                    out.flush();

                    int attempts = (Integer) httpSession.getAttribute("attempts");
                    httpSession.setAttribute("attempts", attempts + 1);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                    out.append("Login attempts: "+ httpSession.getAttribute("attempts").toString());

                    Date actual = new Date();
                    Date timeout = new Date(actual.getTime() + 15 * 60000);
                    httpSession.setAttribute("timeout", timeout);


                }

            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.append(" Failed to type in the correct password! You've been locked!");
                Date timeout = (Date) httpSession.getAttribute("timeout");
                long actualTime = timeout.getTime() - (new Date().getTime());
                out.append("Come back after " + (TimeUnit.MILLISECONDS.toSeconds(actualTime))/60 + " minutes");
            }
        }else{
            out.append("Email: " + httpSession.getAttribute("email").toString() +" already is use!");
        }
    }

}