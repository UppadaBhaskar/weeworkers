package customer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Registration1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (username != null && password != null && email != null) {
            try (Connection connection = Database.getConnection()) {
                String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, email);
                    statement.executeUpdate();
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    response.sendRedirect("customer/registration/success.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("customer/registration/error.jsp");
            }
        } else {
            response.sendRedirect("customer/registration/error.jsp");
        }
    }
}
