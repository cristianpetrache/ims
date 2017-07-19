//import com.mysql.cj.api.jdbc.JdbcConnection;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by UserPrototype on 7/12/2017.
 */
@WebServlet(name = "ServletAddUser")
public class ServletAddUser extends HttpServlet {
    PrintWriter out;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out=response.getWriter();

        User user = (User) this.getServletConfig().getServletContext().getAttribute("User");

        Connection connection = ConnectionJDBC.getConection();
        final String queryCheck = "SELECT * from users_true WHERE secret_code = ?";

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


        try {
            if(user.getSecretCode().equals(jsonObject.getString("secretCode"))) {


                final PreparedStatement ps = connection.prepareStatement(queryCheck);
                ps.setString(1, user.getSecretCode());
                final ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id_user");
                    PreparedStatement updateEXP = connection.prepareStatement("update `users_true` set `secret_code` = 'validated'  where `id_user` = '" + id + "'");
                    int affectedRows = updateEXP.executeUpdate();
                    System.out.println("Validation successful!!! ");
                    out.print("Validation successful!");
                    out.flush();



                }else{
                    System.out.println("NU EXISTA IN BD ACEST SECRET CODE!");
                    out.print("Secret code doesn't exist in BD");
                    out.flush();
                }
            }else{
                System.out.println("SECRET CODE INCORECT!");
                out.print("Incorrect secret code!");
                out.flush();
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


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }


}