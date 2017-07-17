import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@WebServlet(name = "servlet2")
public class ServletWelcome extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User user = (User)this.getServletConfig().getServletContext().getAttribute("UserLogIn");
       // String n=request.getParameter("username");

        out.println("Welcome");
        out.println(user.getDisplayName());
        out.println(user.getDate());
        out.println(user.getEmail());
        out.println(user.getPassword());

        out.close();
    }

}