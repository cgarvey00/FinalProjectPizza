<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Address" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    List<Address> addressList = (List<Address>) request.getSession().getAttribute("addressList");
    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<body>
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
</style>
<%@include file="customer-nav.jsp" %>
<!-- home section starts  -->
<section class="add-products">
    </br>
    </br>
    </br>
    </br>  <br>
    <br>
    <br>

    <%if (session.getAttribute("invalidAddress") != null) { %>
    <div class="alert alert-danger" role="alert">
        <h3>Address could not be verified</h3>

    </div>
    <%
            session.removeAttribute("invalidAddress");
        }%>

    <%if (session.getAttribute("failedAddAddress") != null) { %>
    <div class="alert alert-danger" role="alert">
        <h3>Failed to update address. Please try again</h3>

    </div>
    <%
            session.removeAttribute("failedAddAddress");
        }%>

    <%if (session.getAttribute("outOfRange") != null) { %>
    <div class="alert alert-danger" role="alert">
        <h3>Address is out of range. please try again</h3>

    </div>
    <%
            session.removeAttribute("outOfRange");
        }%>

    <h1 class="heading">Add Address</h1>
    <form action="controller" method="POST">
        <input type="hidden" name="action" value="add-address">
        <input type="hidden" name="uID" value="<%=loggedInUser.getId()%>">
        <div class="flex">
            <div class="inputBox">
                <span>Street (required)</span>
                <input type="text" class="box" required maxlength="100" placeholder="Enter Street" name="street">
            </div>
            <div class="inputBox">
                <span>Town (required)</span>
                <input type="text" class="box" required maxlength="100" placeholder="Enter Town" name="town">
            </div>
            <div class="inputBox">
                <span>County (required)</span>
                <input type="text" minlength="0" class="box" required maxlength="100" placeholder="Enter County"
                       onkeypress="if(this.value.length == 20) return false;" name="county">
            </div>
            <div class="inputBox">
                <span>EirCode</span>
                <textarea name="eirCode" type="text" placeholder="Enter EirCode" class="box" required
                          maxlength="500"
                          cols="30" rows="10"></textarea>
            </div>
        </div>
        <input type="submit" value="add address" class="btn" name="add-address">
    </form>

</section>
<section class="products" style="padding-top: 0; min-height:100vh;">
    <div class="box-container">
        <h2 class="heading">Your Addresses</h2>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Street</th>
                <th>Town</th>
                <th>County</th>
                <th>EirCode</th>
            </tr>
            </thead>
            <tbody>
            <% if (addressList != null && !addressList.isEmpty()) {
                for (Address address : addressList) {%>
            <tr>
                <td><%=address.getStreet() %>
                </td>
                <td><%=address.getTown() %>
                </td>
                <td><%=address.getCounty() %>
                </td>
                <td><%=address.getEirCode() %>
                </td>
            </tr>

            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</section>


<%@include file="footer.jsp" %>
<script>
    function validateAddress() {
        var street = document.forms["addressForm"]["street"].value;
        var town = document.forms["addressForm"]["town"].value;
        var county = document.forms["addressForm"]["county"].value;
        var eirCode = document.forms["addressForm"]["eirCode"].value;

        if (street.trim() === "" || town.trim() === "" || county.trim() === "") {
            alert("Street, Town and County are required");
            return false;
        }

        if (!eirCode.match(/^[A-Za-z0-9 ]+$/)) {
            alert("Enter a valid EirCode");
            return false;
        }

        return true;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

</html>
