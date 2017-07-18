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
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "servlet1")
public class ServletLoginCheck extends HttpServlet {
    PrintWriter out;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        out= response.getWriter();

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
        String cryptedPass = crypter.cryptPassword( jsonObject.getString("password"));

        if(LoginDB.validate(jsonObject.getString("email"), cryptedPass)){
            System.out.println("LOG IN SUCCESSFUL!");
            out.print("Log in successful!");
            out.flush();
            Connection connection = ConnectionJDBC.getConection();
            final PreparedStatement ps;
            try {
                ps = connection.prepareStatement(queryCheck);
                ps.setString(1, jsonObject.getString("email"));
                final ResultSet resultSet = ps.executeQuery();
                if(resultSet.next()){
                    User user = new User();
                    user.setDisplayName(resultSet.getString("display_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));

                    this.getServletConfig().getServletContext().setAttribute("FinalUser", user);
                    request.getRequestDispatcher("/ServletWelcome").forward(request,response);
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }





        }else{
            System.out.println("LOG IN UNSUCCESFUL!");
            out.print("Log in succesfull!");
            out.flush();
            System.out.println("USERNAME / PASSWORD INCORRECT");
            out.print("USERNAME / PASSWORD INCORRECT");
            out.flush();
        }

    }



}