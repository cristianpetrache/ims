import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by UserPrototype on 7/12/2017.
 */
@WebServlet(name = "ServletAddUser")
public class ServletAddUser extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {







//        User user = (User) this.getServletConfig().getServletContext().getAttribute("completeUser");
//        System.out.println("servletAddUser: "+ user.toString());
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
//        try {
//            JSONObject object = new JSONObject(jsonString);
//            if( user.getSecretCode().equals(object.getString("secretCode"))){
//                JDBCregister.insertBD(user.getDisplayName(), user.getEmail(), user.getPassword(),String.valueOf(user.getDate()), user.getToken());
//            } else{
//                System.out.println("Invalid secret code. Make sure you type it right!");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }


}
