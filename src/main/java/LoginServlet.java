import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect("/firstServlet/welcome");
            return;
        }
        resp.setContentType("text/html");
       try (PrintWriter out = resp.getWriter()) {
           out.println("<form method=\"POST\">");
           out.println("<input name=\"username\" />");
           out.println("<input name=\"password\" />");
           out.println("<button type=\"submit\">submit</button>");
           out.println("</form>");
       }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username.equals("admin") && password.equals("admin")) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);
            resp.sendRedirect("/firstServlet/welcome");
            return;
        }

        resp.sendRedirect("/firstServlet/login");
    }
}
