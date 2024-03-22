<%@ page import="com.finalprojectcoffee.entities.Address" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    @SuppressWarnings("unchecked")
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
<br><br><br><br><br><br><br><br><br><br>
<!-- home section starts  -->
<section class="products" style="padding-top: 0; min-height:100vh;">
    <div class="box-container">
        <h2 class="heading">Your Addresses</h2>
        <form action="add-address.jsp" method="get">
            <button type="submit" class="add-address-btn">Add</button>
        </form>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Street</th>
                <th>Town</th>
                <th>County</th>
                <th>EirCode</th>
                <th>Action</th>
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
                <td>
                    <form action="controller" method="post" class="action-buttons">
                        <button type="submit" name="action" value="to-update-address" class="update-btn">Update</button>
                        <button type="submit" name="action" value="delete-address" class="delete-btn">Delete</button>
                        <input type="hidden" name="addressId" value="<%=address.getId()%>" />
                    </form>
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


    <% request.getSession().removeAttribute("add-address-success");
        request.getSession().removeAttribute("errorMessage"); %>

    function updateAddress(addressId){
        $.ajax({
            url:'controller',
            type:'POST',
            data:{
                ajax: true,
                action: 'update-address',
                addressId: addressId
            }
        })
    }

    function deleteAddress(addressId){
        $.ajax({
            url:'controller',
            type:'POST',
            data:{
                ajax: true,
                action: 'delete-address',
                addressId: addressId
            },
            success: function (response) {
                if (response.success) {
                    window.location.reload();
                }
            }
        })
    }

</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
<style>
    table {
        table-layout: fixed;
        width: 100%;
    }

    th.action-column, td.action-column {
        width: 120px;
        max-width: 120px;
    }

    .add-address-btn {
        background-color: #109acb;
        color: white;
        padding: 8px 20px;
        font-size: 18px;
        border: none;
        float: right;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
    }

    .add-address-btn:hover {
        background-color: #017fbd;
    }

    .action-buttons {
        display: flex;
        justify-content: space-between;
        width: 50%;
        gap: 15px;

        .update-btn, .delete-btn {
            font-size: 14px;
            padding: 6px 12px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            line-height: 1.2;
            margin: 0;
            flex: none;
            width: 80px;
            height: 30px;
        }

        .update-btn {
            background-color: #109acb;
            color: white;
        }

        .update-btn:hover {
            background-color: #017fbd;
        }

        .delete-btn {
            background-color: #ff4d4d;
            color: white;
            transition: background-color 0.3s;
        }

        .delete-btn:hover {
            background-color: #cc0000;
        }
    }

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