import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "servlet1")
public class ServletLoginCheck extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

Crypt crypter=new Crypt();
        String email=request.getParameter("email");
        String password=crypter.cryptPassword( request.getParameter("userpass"));

        if(LoginDB.validate(email, password)){
            RequestDispatcher rd=request.getRequestDispatcher("ServletWelcome");
            rd.forward(request,response);

        }
        else{
            out.print("Sorry username or password error");
            RequestDispatcher rd=request.getRequestDispatcher("index.html");
            rd.include(request,response);
        }

        out.close();
    }



}