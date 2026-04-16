import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final List<String> users = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        resp.setContentType("text/html");

        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try (PrintWriter out = resp.getWriter()) {
            if (id < 0 || id >= users.size()) {
                out.println("<html><body><h1>Такой пользователь не найден</h1></body></html>");
                return;
            }
            out.println("<html><body><h1>" + users.get(id) + "</h1></body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name.isEmpty()) {
            try (PrintWriter out = resp.getWriter()) {
                out.println("Невозможно добавить пользователя с пустым именем");
            }
            return;
        }

        users.add(name);
        resp.sendRedirect("/user?id=" + (users.size() - 1));
    }
}
