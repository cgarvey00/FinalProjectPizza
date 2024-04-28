<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Order" %>
<%@ page import="com.finalprojectcoffee.entities.Review" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
  User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

  List<User> userList = (List<User>) request.getSession().getAttribute("userList");
  //
  //Checking to ensure the User is logged in or not
  if (request.getSession(false) == null || loggedInUser == null || !"Admin".equals(loggedInUser.getUserType())) {
    response.sendRedirect("index.jsp");
  }


%>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User List</title>

  <!-- font awesome cdn link  -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
  <!-- bootstrap cdn link  -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
  <!-- styles css link  -->
  <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/customer.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
  <%--    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">--%>
  <!-- AJAX Link -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"></script>
</head>

<style>

  .alert {
    padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #a94442;
    background-color: #f2dede;
    border-color: #ebccd1;
  }


  .success {
    padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;
    color: #27942b;
    background-color: #affdb2;
    border-color: #b0ff93;
  }
</style>
<body>
<%@include file="admin-nav.jsp" %>
<section class="products" style="padding-top: 0; min-height:100vh;">
  <br><br><br><br><br><br><br><br><br>

  <div class="box-container">
    <h2 class="heading">Users</h2>
    <br><br><br><br><br><br><br><br><br>
    <table class="table table-bordered">
      <thead>
      <tr>
        <th>UserName</th>
        <th>Email Address</th>
        <th>Phone Number</th>
        <th>User Type</th>
      </tr>
      </thead>
      <tbody>
      <%
        if (userList != null && !userList.isEmpty()) {
          for (User user : userList) {
      %>
      <tr>
        <td><%=user.getUsername()%>
        </td>
        <td><%=user.getEmail() %>
        </td>
        <td>
          <%=user.getPhoneNumber() %>
        </td>
        <td>
          <%=user.getUserType()%>
        </td>
      </tr>
      <%}} %>
      </tbody>
    </table>

  </div>
</section>

<%@include file="footer.jsp" %>

<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>

</body>
</html>
