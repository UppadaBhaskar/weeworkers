package customer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class JobListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("customer/login/login.jsp");
            return;
        }

        String clientUsername = (String) session.getAttribute("username");
        List<List<Object>> jobs = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT service_name, work_name, description, num_days, cost, language, rating, team_size, location FROM jobs WHERE client_username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, clientUsername);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        List<Object> job = new ArrayList<>();
                        job.add(resultSet.getString("service_name"));
                        job.add(resultSet.getString("work_name"));
                        job.add(resultSet.getString("description"));
                        job.add(resultSet.getInt("num_days"));
                        job.add(resultSet.getDouble("cost"));
                        job.add(resultSet.getString("language"));
                        job.add(resultSet.getInt("rating"));
                        job.add(resultSet.getInt("team_size"));
                        job.add(resultSet.getString("location"));
                        jobs.add(job);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("jobs", jobs);
        request.getRequestDispatcher("job_list.jsp").forward(request, response);
    }
}
