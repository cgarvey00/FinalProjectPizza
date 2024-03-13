<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Order" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Admin".equals(loggedInUser.getUserType()) || !"Employee".equals(loggedInUser.getUserType())
            || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }


%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/userdetails.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>

<body>

<%--<%if ("Admin".equals(loggedInUser.getUserType())) { %>--%>
<%--<%@include file="admin-nav.jsp" %>--%>
<%--<% } else if ("Customer".equals(loggedInUser.getUserType())) {%>--%>
<%--<%@include file="customer-nav.jsp" %>--%>
<%--<% } else if ("Employee".equals(loggedInUser.getUserType())) {%>--%>
<%--<%@include file="employee-nav.jsp" %>--%>
<%--#--%>
<%--<% } %>--%>
<section class="edit-details">
    </br>
    </br>
    </br>
    </br>
    <h1 class="heading">Update Profile Details</h1>

<%--    <% if ("Admin".equals(loggedInUser.getUserType()) || "Employee".equals(loggedInUser.getUserType())--%>
<%--            || "Customer".equals(loggedInUser.getUserType())) {--%>
<%--        response.sendRedirect("index.jsp");--%>
<%--    } %>--%>
    <form action="controller" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="update-profile">
        <input type="hidden" name="user_id" value="<%=loggedInUser.getId() %>">
        <input type="hidden" name="old_image" value="<%=loggedInUser.getImage() %>">
        <div class="image-container">
            <div class="main-image">
                <center><img src="../uploaded-images/<%=loggedInUser.getId() %>" class="rounded-circle img-fluid"
                             alt="" style="height:300px; width:300px;"></center>
            </div>
        </div>
        <span>Update Username</span>
        <input type="text" name="name" required class="box" maxlength="100" placeholder="Enter Username"
               value="<%=loggedInUser.getUsername() %>">
        <span>Update Email</span>
        <input type="text" name="email" required class="box" maxlength="100" placeholder="Enter Email"
               value="<%=loggedInUser.getEmail() %>">
        <span>Update PhoneNumber</span>
        <input type="number" name="phoneNumber" required class="box" maxlength="100" placeholder="Enter Email"
               value="<%=loggedInUser.getPhoneNumber() %>">

        <span>Update Street </span>
        <input type="text" class="box" required maxlength="100" placeholder="Enter Street" name="street">

        <span>Town </span>
        <input type="text" class="box" required maxlength="100" placeholder="Enter Town" name="town">

        <span>County</span>
        <input type="text" minlength="0" class="box" required maxlength="100" placeholder="Enter County"
               onkeypress="if(this.value.length == 20) return false;" name="county">

        <span>EirCode</span>
        <textarea name="eirCode" type="text" placeholder="Enter EirCode" class="box" required
                  maxlength="500" cols="30" rows="10"></textarea>


        <%--        <span>Update Address </span>--%>
        <%--        <input type="text" name="address" required class="box" maxlength="100" placeholder="enter Address"--%>
        <%--               value="<?= $fetch_users['address']; ?>">--%>
        <span>Update Image </span>
        <%--        <input type="file" name="image" accept="image/jpg, image/jpeg, image/png, image/webp" class="box">--%>
        <input type="text" name="image" required class="box" maxlength="100" placeholder="Enter Image" --%>
        value="<%=loggedInUser.getImage() %>">
        <div class="flex-btn">
            <input type="submit" name="update-profile" class="btn" value="update">
            <a href="../" class="option-btn">go back</a>
        </div>
    </form>


</section>
<%@include file="footer.jsp" %>
<script>
    document.getElementById('toggleAddressForm').addEventListener('click', function () {
        var addressForm = document.getElementById('addressForm');
        addressForm.style.display = (addressForm.style.display === 'none' || addressForm.style.display === '') ? 'block' : 'none';
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

</html>







