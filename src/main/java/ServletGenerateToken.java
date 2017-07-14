import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

/**
 * Created by UserPrototype on 7/12/2017.
 */
@WebServlet(name = "ServletGenerateToken")
public class ServletGenerateToken extends HttpServlet implements  Constant{

    private final String USER_AGENT = "Mozilla/5.0";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Am ajuns aici!!!");
        try {
            ManageToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void ManageToken() throws Exception {

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
        System.out.println(response.toString());
        User user = (User)this.getServletConfig().getServletContext().getAttribute("sharedUser");
        System.out.println("servlet Generate token: user -> "+ user.toString());


        try {
            GoogleMail.Send("supermega.team.0@gmail.com", "easypeasylemonsqueezy", user.getEmail(), "Team 0 verification email", response.toString());

            System.out.println("Email trmis cu success!");

           // JSONObject jsonToken = new JSONObject(response.toString());
            int TokenPUTcode = user.validateToken(response.toString());
            System.out.println(TokenPUTcode);
            if(TokenPUTcode == 200) {
                JDBCConnection.insertBD(user.getDisplayName(), user.getEmail(), user.getPassword(),String.valueOf(user.getDate()), response.toString());
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

//    public void executePUT(JSONObject jsonObject) throws IOException, JSONException {
//        URL url = new URL("https://validate.mybluemix.net/token");
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        System.out.println("connection established!");
//        Gson gson = new Gson();
//        conn.setDoOutput(true);
//        conn.setRequestMethod("PUT");
//        conn.addRequestProperty("Content-Type", "application/json");
//       // OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
//        OutputStream os = conn.getOutputStream();
//        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
//        System.out.println("outputstream established!");
//        System.out.println(jsonObject.toString());
//       // out.write(gson.toJson(jsonObject));
//        osw.write(jsonObject.toString());
//
//        if(conn.getResponseCode() == 200){
//            System.out.println("connection response code: 200");
//            UserPrototype user = (UserPrototype)this.getServletConfig().getServletContext().getAttribute("sharedUser");
//            user.setSecretCode(jsonObject.getString("token"));
//            System.out.println("UserPrototype creat cu success!: " + user.toString());
//        }
//        osw.close();
//    }




}
