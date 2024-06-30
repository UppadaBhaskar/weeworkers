<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vendor Home</title>
    <style>
        .job-block {
            border: 1px solid #ccc;
            padding: 10px;
            margin: 10px 0;
            background-color: #f9f9f9;
        }
        .pagination {
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <h2>Your Job Posts</h2>
    <%
        List<List<Object>> jobs = (List<List<Object>>) request.getAttribute("jobs");
        if (jobs != null && !jobs.isEmpty()) {
            for (List<Object> job : jobs) {
    %>
                <div class="job-block">
                    <h3><%= job.get(0) %></h3> <!-- service_name -->
                    <p><strong>Work Name:</strong> <%= job.get(1) %></p> <!-- work_name -->
                    <p><strong>Description:</strong> <%= job.get(2) %></p> <!-- description -->
                    <p><strong>Number of Days:</strong> <%= job.get(3) %></p> <!-- num_days -->
                    <p><strong>Cost:</strong> $<%= job.get(4) %></p> <!-- cost -->
                    <p><strong>Language Preferred:</strong> <%= job.get(5) %></p> <!-- language -->
                    <p><strong>Rating:</strong> <%= job.get(6) %></p> <!-- rating -->
                    <p><strong>Team Size:</strong> <%= job.get(7) %></p> <!-- team_size -->
                    <p><strong>Location:</strong> <%= job.get(8) %></p> <!-- location -->
                </div>
    <%
            }
        } else {
    %>
            <p>No job posts found.</p>
    <%
        }
    %>
    <div class="pagination">
        <form action="VendorJobListServlet" method="get">
            <input type="hidden" name="page" value="<%= (Integer) request.getAttribute("currentPage") - 1 %>">
            <button type="submit" <%= ((Integer) request.getAttribute("currentPage") <= 1) ? "disabled" : "" %>>Previous</button>
        </form>
        <form action="VendorJobListServlet" method="get">
            <input type="hidden" name="page" value="<%= (Integer) request.getAttribute("currentPage") + 1 %>">
            <button type="submit" <%= ((Boolean) request.getAttribute("hasNextPage")) ? "" : "disabled" %>>Next</button>
        </form>
    </div>
</body>
</html>
