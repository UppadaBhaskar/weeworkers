package vendor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VendorRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (username != null && password != null && email != null) {
            try (Connection connection = Database.getConnection()) {
                String sql = "INSERT INTO vendor (username, password, email) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, email);
                    statement.executeUpdate();

                    response.sendRedirect("vendor/login/vendor_login.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("vendor_register_error.jsp");
            }
        } else {
            response.sendRedirect("vendor_register_error.jsp");
        }
    }
}
