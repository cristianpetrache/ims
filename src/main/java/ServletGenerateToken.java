import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

/**
 * Created by UserPrototype on 7/12/2017.
 */
@WebServlet(name = "ServletGenerateToken")
public class ServletGenerateToken extends HttpServlet implements  Constant{

    private final String USER_AGENT = "Mozilla/5.0";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            User user = ManageUser();
            this.getServletConfig().getServletContext().setAttribute("completeUser", user);
            request.getRequestDispatcher("/addUser").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private User ManageUser() throws Exception {

        String url = "https://validate.mybluemix.net/token/AndreiCazan30";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("UserPrototype-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("ServletGenerateToken RESPONSE: "+ response.toString());
        User user = (User)this.getServletConfig().getServletContext().getAttribute("sharedUser");
        System.out.println("servlet Generate token BEFORE: user -> "+ user.toString());


        try {

            int TokenPUTcode = user.validateToken(response.toString());
            System.out.println(TokenPUTcode);
            if(TokenPUTcode == 200) {
                JSONObject object = new JSONObject(response.toString());
                user.setToken(object.getString("token"));
                System.out.println("servlet Generate token AFTER: user -> "+ user.toString());

                UUID uuid =  UUID.randomUUID();
                GoogleMail.Send("supermega.team.0@gmail.com", "easypeasylemonsqueezy", user.getEmail(), "Team 0 verification email",
                        uuid.toString());
                user.setSecretCode(uuid.toString());
                System.out.println("Email trmis cu success!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;

    }




}
