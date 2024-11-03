package app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if ("list".equals(action)) {
            out.println("<h2>User List</h2>");
            out.println("<a href='index.html'>Add New User</a><br><br>");
            for (User user : userDAO.getAllUsers()) {
                out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail() + " ");
                out.println("<a href='?action=edit&id=" + user.getId() + "'>Edit</a> ");
                out.println("<a href='?action=delete&id=" + user.getId() + "'>Delete</a><br>");
            }
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.getUser(id);
            out.println("<h2>Edit User</h2>");
            out.println("<form action='UserServlet' method='post'>");
            out.println("<input type='hidden' name='id' value='" + user.getId() + "'/>");
            out.println("Name: <input type='text' name='name' value='" + user.getName() + "'/><br>");
            out.println("Email: <input type='text' name='email' value='" + user.getEmail() + "'/><br>");
            out.println("<input type='submit' value='Update'/>");
            out.println("</form>");
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            userDAO.deleteUser(id);
            response.sendRedirect("UserServlet?action=list");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            User newUser = new User(0, name, email);
            userDAO.addUser(newUser);
        } else {
            int id = Integer.parseInt(idParam);
            User user = userDAO.getUser(id);
            user.setName(name);
            user.setEmail(email);
            userDAO.updateUser(user);
        }

        response.sendRedirect("UserServlet?action=list");
    }
}
