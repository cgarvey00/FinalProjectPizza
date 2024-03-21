<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Address" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Address> addressList = (List<Address>) request.getSession().getAttribute("addressList");
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<body>
<%@include file="customer-nav.jsp" %>
<br><br><br><br><br><br><br><br>br><br><br>
<!-- home section starts  -->
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
            <% if(addressList !=null && !addressList.isEmpty()){
                for (Address address : addressList) {%>
            <tr>
                <td><%=address.getStreet() %>
                </td>
                <td><%=address.getTown() %>
                </td>
                <td><%=address.getCounty() %>
                </td>
                <td><%=address.getEirCode() %>
                    <% if(address.getIsDefault() == 1){ %>
                    <span class="default-address-label">Default</span>
                    <% } %>
                </td>
            </tr>
            <%}} %>
            </tbody>
        </table>
    </div>
</section>

<%@include file="footer.jsp" %>
<script>
    <%
    Boolean addAddressSuccess= (Boolean)request.getSession().getAttribute("add-address-success");
    String addAddressError=(String) request.getSession().getAttribute("errorMessage");

       if(addAddressSuccess !=null){ %>
    <% if(addAddressSuccess ){ %>
    alert("Address has been added Successfully!");
    <% }else if(addAddressError !=null)  {%>
    alert("Failure to add Address! Try again ");
    <%} %>
    <%} %>




    <%--    <% request.getSession().removeAttribute("update-product-success");--%>
    <%--        request.getSession().removeAttribute("ape-message"); %>--%>
    <% request.getSession().removeAttribute("add-address-success");
        request.getSession().removeAttribute("errorMessage"); %>
    <%--    <% request.getSession().removeAttribute("delete-product-success");--%>
    <%--     request.getSession().removeAttribute("pde-message"); %>--%>


</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
<style>
    .default-address-label {
        background-color: #4CAF50;
        color: white;
        padding: 2px 5px;
        margin-left: 10px;
        border-radius: 3px;
        font-size: 0.8em;
    }
</style>
</html>
