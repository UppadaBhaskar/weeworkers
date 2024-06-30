<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Post Job</title>
</head>
<body>
    <%
        
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("customer/login/login.jsp");
            return;
        }
    %>
    <h2>Post a Job</h2>
    <form action="PostJobServlet" method="post">
        <label for="serviceName">Service Name:</label>
        <select id="serviceName" name="serviceName" required>
            <option value="">Select a Service</option>
            <%
                List<String> services = (List<String>) request.getAttribute("services");
                if (services != null) {
                    for (String service : services) {
                        out.println("<option value=\"" + service + "\">" + service + "</option>");
                    }
                } else {
                    out.println("<option value=\"\">No Services Available</option>");
                }
            %>
        </select>
        <br>
        <label for="workName">Actual Work Name:</label>
        <input type="text" id="workName" name="workName" required>
        <br>
        <label for="description">Description of the Work:</label>
        <textarea id="description" name="description"></textarea>
        <br>
        <label for="numDays">Number of Days of Work:</label>
        <input type="number" id="numDays" name="numDays" required>
        <br>
        <label for="cost">Cost of Work:</label>
        <input type="number" id="cost" name="cost" required>
        <br>
        <label for="language">Language Preferred:</label>
        <input type="text" id="language" name="language" required>
        <br>
        <label for="rating">Rating of the Vendor/Freelancer (optional):</label>
        <input type="number" id="rating" name="rating">
        <br>
        <label for="teamSize">Team Size:</label>
        <input type="number" id="teamSize" name="teamSize" required>
        <br>
        <label for="location">Location:</label>
        <input type="text" id="location" name="location" required>
        <br>
        <button type="submit">Post Job</button>
    </form>
    <br>
    <form action="JobListServlet" method="get">
        <button type="submit">View My Job Posts</button>
    </form>
</body>
</html>
