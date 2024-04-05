<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:include page="admin-nav.jsp"/>
<br><br><br><br><br><br><br><br><br><br>
<!-- home section starts  -->
<section class="products" style="padding-top: 0; min-height:100vh;">
    <div class="box-container">
        <h2 class="heading">Employee List</h2>
        <form action="employee-register.jsp" method="get">
            <button type="submit" class="add-employee-btn">Add</button>
        </form>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 60px">ID</th>
                <th>Name</th>
                <th>Phone Number</th>
                <th style="width: 230px">Email</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty sessionScope.employeeList}">
                <c:forEach var="employee" items="${sessionScope.employeeList}">
                    <tr>
                        <td style="width: 60px"><c:out value="${employee.getId()}"/></td>
                        <td><c:out value="${employee.getUsername()}"/></td>
                        <td><c:out value="${employee.getPhoneNumber()}"/></td>
                        <td style="width: 230px"><c:out value="${employee.getEmail()}"/></td>
                        <td><c:out value="${employee.getStatus()}"/></td>
                        <td>
                            <form action="controller" method="post" class="action-buttons">
                                <button type="submit" name="action" value="to-update-employee" class="update-btn">Update</button>
                                <button type="submit" name="action" value="delete-employee" class="delete-btn">Delete</button>
                                <input type="hidden" name="employeeId" value="${employee.getId()}" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</section>

<jsp:include page="footer.jsp"/>
<script>
    function updateEmployee(employeeId){
        $.ajax({
            url:'controller',
            type:'POST',
            data:{
                ajax: true,
                action: 'update-employee',
                addressId: addressId
            }
        })
    }

    function deleteEmployee(employeeId){
        $.ajax({
            url:'controller',
            type:'POST',
            data:{
                ajax: true,
                action: 'delete-user',
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

    .add-employee-btn {
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

    .add-employee-btn:hover {
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