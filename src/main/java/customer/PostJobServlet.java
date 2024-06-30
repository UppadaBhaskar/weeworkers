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

public class PostJobServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("customer/login/login.jsp");
            return;
        }

        String serviceName = request.getParameter("serviceName");
        String workName = request.getParameter("workName");
        String description = request.getParameter("description");
        String numDays = request.getParameter("numDays");
        String cost = request.getParameter("cost");
        String language = request.getParameter("language");
        String ratingStr = request.getParameter("rating");
        String teamSize = request.getParameter("teamSize");
        String location = request.getParameter("location");
        String clientUsername = (String) session.getAttribute("username");

        Integer rating = null;
        if (ratingStr != null && !ratingStr.isEmpty()) {
            try {
                rating = Integer.parseInt(ratingStr);
            } catch (NumberFormatException e) {
                // Handle the case where rating is not a valid integer
                rating = null;
            }
        }

        if (serviceName != null && workName != null && numDays != null && cost != null && language != null && teamSize != null && location != null) {
            try (Connection connection = Database.getConnection()) {
                String sql = "INSERT INTO jobs (service_name, work_name, description, num_days, cost, language, rating, team_size, location, client_username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, serviceName);
                    statement.setString(2, workName);
                    statement.setString(3, description);
                    statement.setString(4, numDays);
                    statement.setString(5, cost);
                    statement.setString(6, language);
                    if (rating != null) {
                        statement.setInt(7, rating);
                    } else {
                        statement.setNull(7, java.sql.Types.INTEGER);
                    }
                    statement.setString(8, teamSize);
                    statement.setString(9, location);
                    statement.setString(10, clientUsername);
                    statement.executeUpdate();

                    request.getRequestDispatcher("JobListServlet").forward(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("post_job_error.jsp");
            }
        } else {
            response.sendRedirect("post_job_error.jsp");
        }
    }
}
