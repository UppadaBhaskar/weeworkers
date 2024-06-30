package vendor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VendorLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && password != null) {
            try (Connection connection = Database.getConnection()) {
                String sql = "SELECT * FROM vendor WHERE username = ? AND password = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            HttpSession session = request.getSession();
                            session.setAttribute("vendorUsername", username);
                            request.getRequestDispatcher("VendorJobListServlet").forward(request, response);
                        } else {
                            response.sendRedirect("vendor/login/vendor_login.jsp");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("vendor_login_error.jsp");
            }
        } else {
            response.sendRedirect("vendor_login_error.jsp");
        }
    }
}
