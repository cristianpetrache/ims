import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by UserPrototype on 7/12/2017.
 */
@WebServlet(name = "ServletAddToken")
public class ServletAddToken extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserPrototype user = (UserPrototype)this.getServletConfig().getServletContext().getAttribute("sharedUser");
        if(request.getParameter("token")!=null){
            user.setSecretCode(request.getParameter("token"));
        }
        if(user!=null){
            System.out.println("servletToken POST: "+ user.toString());
        }else System.out.println("failed to update the token");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserPrototype user = (UserPrototype)this.getServletConfig().getServletContext().getAttribute("sharedUser");
        if(user!=null){
            System.out.println("servletToken GET: "+user.toString());
        }else System.out.println("failed to transmit the user data");
    }


}
