import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet(name = "servlet2")
public class ServletWelcome extends HttpServlet {

    PrintWriter out;
    public ArrayList<String> mistakes = new ArrayList<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();


        //   int numberOfMissingFields = 0; // is missing: (name->1) (pass->3) (date->5) (name & pass->4) (name & date->6) (pass & date->8) ( all 3 ->9)


        User user = (User) this.getServletConfig().getServletContext().getAttribute("FinalUser");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        String userEmail = user.getEmail();
        String userDisplay_name = "";
        String userCryptedPassword = null;
        LocalDate userDate = LocalDate.of(1997, 3, 15);


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


        try {
            org.json.JSONObject js = new org.json.JSONObject(jsonString);

            if (js != null) {


                NameValidator val = new NameValidator();
                try {
                    userDisplay_name = js.getString("displayName");
                    if (!userDisplay_name.equals("")) {
                        System.out.println(userDisplay_name);
                        if (val.validate(userDisplay_name)) {

                            insertName(userDisplay_name, userEmail);
                        } else
                            mistakes.add("This is not a valid Display Name. Display name should have at least 1 char and no more than 100 and may only contain alphanumeric characters ");
                    }


                } catch (org.json.JSONException e) {
                    e.printStackTrace();

                }


                PasswordValidator validator = new PasswordValidator();
                try {
                    String pass = js.getString("password");

                    if (!pass.equals("")) {
                        if (validator.validate(pass)) {
                            userCryptedPassword = Crypt.cryptPassword(pass);
                            insertPassword(userEmail, userCryptedPassword);
                        } else
                            mistakes.add("This is not a valid password. It must be between 8 and 100 characters, can contain any character, but at least one upper case and one number");

                    }
                } catch (org.json.JSONException e) {
                    e.printStackTrace();

                }


                DateValidator dateValidator = new DateValidator();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                formatter = formatter.withLocale(Locale.UK);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
                try {

                    if (!js.getString("dateOfBirth").equals("")) {
                        LocalDate date = LocalDate.parse(js.getString("dateOfBirth"), formatter);


                        if (dateValidator.validation(date)) {
                            userDate = date;
                            insertDate(userEmail, String.valueOf(userDate));
                        } else
                            mistakes.add("This is not a valid date of birth, you should be over 18.");
                    }
                } catch (org.json.JSONException e) {
                    e.printStackTrace();

                }


                if (mistakes.size() != 0) {
                    for (String m : mistakes) {
                        out.println(m);
                        out.flush();
                        System.out.println(m);
                    }
                    mistakes.clear();
                }


            } else {
                out.println("JSON EMPTY");
                out.flush();
                System.out.println("JSON EMPTY");
            }


        } catch (JSONException e) {
            out.println("JSON ERROR, NOT THE REQUIRED FORMAT");
            out.flush();
            e.printStackTrace();
        }


    }


//    private void insertName(String name, String user_email) {
//
//        // System.out.println("insert");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//
//
//            String insertSQL = "UPDATE users.users_true SET display_name=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//            ps.setString(1, name);
//            ps.setString(2, user_email);
//            ps.executeUpdate();
//            out.print("User display name updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert1(String user_email, String password1, String date1) {
//
//        // System.out.println("insert1");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET password=?,date=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//
//            ps.setString(1, password1);
//            ps.setString(2, date1);
//            ps.setString(3, user_email);
//            ps.executeUpdate();
//            out.print("User password and birth date updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert3(String name, String user_email, String date1) {
//
//        //  System.out.println("insert3");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET display_name=?,date=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//            ps.setString(1, name);
//
//            ps.setString(2, date1);
//            ps.setString(3, user_email);
//            ps.executeUpdate();
//            out.print("User display name and birth date updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert5(String name, String user_email, String password1) {
//
//        System.out.println("insert5");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET display_name=?,password=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//            ps.setString(1, name);
//            ps.setString(2, password1);
//
//            ps.setString(3, user_email);
//            ps.executeUpdate();
//            out.print("User display name and password updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    private void insertDate(String user_email, String date1) {

        //  System.out.println("insert4");
        java.sql.PreparedStatement preparedStatement;
        Connection dbConnection = ConnectionJDBC.getConection();
        try {


            String insertSQL = "UPDATE users.users_true SET date=? WHERE email=?";
            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);

            ps.setString(1, date1);
            ps.setString(2, user_email);
            ps.executeUpdate();
            out.println("User birth date updated");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insertName(String name, String user_email) {

        // System.out.println("insert8");
        java.sql.PreparedStatement preparedStatement;
        Connection dbConnection = ConnectionJDBC.getConection();
        try {


            String insertSQL = "UPDATE users.users_true SET display_name=? WHERE email=?";
            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
            ps.setString(1, name);
            ps.setString(2, user_email);
            ps.executeUpdate();
            out.println("User display name updated");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insertPassword(String user_email, String password1) {

        //  System.out.println("insert6");
        java.sql.PreparedStatement preparedStatement;
        Connection dbConnection = ConnectionJDBC.getConection();
        try {


            String insertSQL = "UPDATE users.users_true SET password=? WHERE email=?";
            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);

            ps.setString(1, password1);

            ps.setString(2, user_email);
            ps.executeUpdate();
            out.println("User password updated");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
//import org.json.JSONException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Locale;
//
//import javax.servlet.ServletException;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
////@WebServlet(name = "servlet2")
//public class ServletWelcome extends HttpServlet {
//
//    PrintWriter out;
//    public ArrayList<String> mistakes = new ArrayList<>();
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        out = response.getWriter();
//
//
//        int numberOfMissingFields = 0; // is missing: (name->1) (pass->3) (date->5) (name & pass->4) (name & date->6) (pass & date->8) ( all 3 ->9)
//
//
//        User user = (User) this.getServletConfig().getServletContext().getAttribute("FinalUser");
//
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        String userEmail = user.getEmail();
//
//        String userDisplay_name = "";
//        String userCryptedPassword = null;
//        LocalDate userDate = LocalDate.of(1997, 3, 15);
//
//
//        StringBuilder jsonBuilder = new StringBuilder();
//        String line = null;
//        try {
//            BufferedReader reader = request.getReader();
//            while ((line = reader.readLine()) != null)
//                jsonBuilder.append(line);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String jsonString = jsonBuilder.toString();
//
//
//        try {
//            org.json.JSONObject js = new org.json.JSONObject(jsonString);
//
//            if (js != null) {
//
//
//                NameValidator val = new NameValidator();
//                try {
//                    if (val.validate(js.getString("displayName")))
//                        userDisplay_name = js.getString("displayName");
//                    else
//                        mistakes.add("This is not a valid Display Name. Display name should have at least 1 char and no more than 100 and may only contain alphanumeric characters ");
//                } catch (org.json.JSONException e) {
//                    numberOfMissingFields += 1;
//
//                }
//
//
//
//
//
//                PasswordValidator validator = new PasswordValidator();
//                try {
//                    String pass = js.getString("password");
//                    if (validator.validate(pass))
//                        userCryptedPassword = Crypt.cryptPassword(pass);
//                    else
//                        mistakes.add("This is not a valid password. It must be between 8 and 100 characters, can contain any character, but at least one upper case and one number");
//                } catch (org.json.JSONException e) {
//                    numberOfMissingFields += 3;
//
//                }
//
//
//
//
//
//                DateValidator dateValidator = new DateValidator();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
//                formatter = formatter.withLocale(Locale.UK);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
//                try {
//                    LocalDate date = LocalDate.parse(js.getString("dateOfBirth"), formatter);
//
//                    if (dateValidator.validation(date))
//                        userDate = date;
//                    else
//                        mistakes.add("This is not a valid date of birth, you should be over 18.");
//                } catch (org.json.JSONException e) {
//                    numberOfMissingFields += 5;
//
//                }
//
//
//
//
//
//
//                if (mistakes.size() != 0) {
//                    for (String m : mistakes) {
//                        out.println(m);
//
//                        System.out.println(m);
//                    }
//                    mistakes.clear();
//
//
//
//
//                } else {
//
//
//                    if (numberOfMissingFields == 0)
//                        insert(userDisplay_name, userEmail, userCryptedPassword, String.valueOf(userDate));
//                    else if (numberOfMissingFields == 1)
//                        insert1(userEmail, userCryptedPassword, String.valueOf(userDate));
//                    else if (numberOfMissingFields == 3)
//                        insert3(userDisplay_name, userEmail, String.valueOf(userDate));
//                    else if (numberOfMissingFields == 5)
//                        insert5(userDisplay_name, userEmail, userCryptedPassword);
//                    else if (numberOfMissingFields == 4)
//                        insert4(userEmail, String.valueOf(userDate));
//                    else if (numberOfMissingFields == 6)
//                        insert6(userEmail, userCryptedPassword);
//                    else if (numberOfMissingFields == 8)
//                        insert8(userDisplay_name, userEmail);
//                    else if (numberOfMissingFields == 9) {
//                        out.print("All fields are empty.");
//                        out.flush();
//                        System.out.println("all fields are missing");
//                    }
//
//
//                }
//
//
//            } else {
//
//                System.out.println("JSON FAIL");
//            }
//
//
//        } catch (JSONException e) {
//            out.println("JSON ERROR, NOT THE REQUIRED FORMAT");
//            out.flush();
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//
//
//
//    private void insert(String name, String user_email, String password1, String date1) {
//
//        // System.out.println("insert");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//
//
//            String insertSQL = "UPDATE users.users_true SET display_name=?,password=?,date=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//            ps.setString(1, name);
//            ps.setString(2, password1);
//            ps.setString(3, date1);
//            ps.setString(4, user_email);
//            ps.executeUpdate();
//            out.print("User profile updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert1(String user_email, String password1, String date1) {
//
//        // System.out.println("insert1");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET password=?,date=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//
//            ps.setString(1, password1);
//            ps.setString(2, date1);
//            ps.setString(3, user_email);
//            ps.executeUpdate();
//            out.print("User password and birth date updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert3(String name, String user_email, String date1) {
//
//        //  System.out.println("insert3");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET display_name=?,date=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//            ps.setString(1, name);
//
//            ps.setString(2, date1);
//            ps.setString(3, user_email);
//            ps.executeUpdate();
//            out.print("User display name and birth date updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert5(String name, String user_email, String password1) {
//
//        System.out.println("insert5");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET display_name=?,password=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//            ps.setString(1, name);
//            ps.setString(2, password1);
//
//            ps.setString(3, user_email);
//            ps.executeUpdate();
//            out.print("User display name and password updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert4(String user_email, String date1) {
//
//        //  System.out.println("insert4");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET date=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//
//            ps.setString(1, date1);
//            ps.setString(2, user_email);
//            ps.executeUpdate();
//            out.print("User birth date updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert8(String name, String user_email) {
//
//        // System.out.println("insert8");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET display_name=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//            ps.setString(1, name);
//            ps.setString(2, user_email);
//            ps.executeUpdate();
//            out.print("User display name updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void insert6(String user_email, String password1) {
//
//        //  System.out.println("insert6");
//        java.sql.PreparedStatement preparedStatement;
//        Connection dbConnection = ConnectionJDBC.getConection();
//        try {
//
//
//            String insertSQL = "UPDATE users.users_true SET password=? WHERE email=?";
//            java.sql.PreparedStatement ps = dbConnection.prepareStatement(insertSQL);
//
//            ps.setString(1, password1);
//
//            ps.setString(2, user_email);
//            ps.executeUpdate();
//            out.print("User password updated");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}

