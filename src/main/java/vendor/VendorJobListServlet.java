package vendor;

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

public class VendorJobListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int JOBS_PER_PAGE = 5;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("vendorUsername") == null) {
            response.sendRedirect("vendor/login/vendor_login.jsp");
            return;
        }

        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<List<Object>> jobs = new ArrayList<>();
        boolean hasNextPage = false;
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT service_name, work_name, description, num_days, cost, language, rating, team_size, location " +
                         "FROM jobs LIMIT ? OFFSET ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, JOBS_PER_PAGE + 1);
                statement.setInt(2, (page - 1) * JOBS_PER_PAGE);
                try (ResultSet resultSet = statement.executeQuery()) {
                    int count = 0;
                    while (resultSet.next()) {
                        if (count < JOBS_PER_PAGE) {
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
                        } else {
                            hasNextPage = true;
                        }
                        count++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("jobs", jobs);
        request.setAttribute("currentPage", page);
        request.setAttribute("hasNextPage", hasNextPage);
        request.getRequestDispatcher("vendor/home/vendor_home.jsp").forward(request, response);
    }
}
