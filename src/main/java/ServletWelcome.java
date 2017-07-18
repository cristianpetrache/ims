import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet(name = "servlet2")
public class ServletWelcome extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3);
        User user = (User)this.getServletConfig().getServletContext().getAttribute("FinalUser");

        System.out.println(user.getDisplayName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());


    }



    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       // User user = (User)this.getServletConfig().getServletContext().getAttribute("FinalUser");

       // System.out.println(user.getDisplayName());
       // System.out.println(user.getEmail());
       // System.out.println(user.getPassword());


    }




}