<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order</title>

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
<h1 style="text-align: center;">Profile Page</h1>
<c:if test="${not empty sessionScope.loggedInUser}">
    <div class="show-products">
        <div class="box-container">
            <div class="box bg-light">
                <div class="content text-dark" style="text-align: center;">
                    <h1 style="text-align: center;"><c:out value="${sessionScope.loggedInUser.getUsername()}"/></h1><br>
                    <h2>Email: <c:out value="${sessionScope.loggedInUser.getEmail()}"/></h2><br>
                    <h2>Phone Number: <c:out value="${sessionScope.loggedInUser.getPhoneNumber()}"/></h2><br>
                    <h2>Default Address:
                        <c:if test="${not empty sessionScope.addressList}">
                            <c:forEach var="address" items="${sessionScope.addressList}">
                                <c:if test="${address.getIsDefault()==1}">
                                    <c:out value="${address.getEirCode()}"/>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </h2><br>
                    <h2>Loyalty Point: <c:out value="${sessionScope.loyaltyPoints}"/></h2>
                    <div class="button-container">
                        <a href="update-profile.jsp" class="btn update-btn">Update Profile</a>
                        <a href="change-password.jsp" class="btn update-btn">Change Password</a>
                        <form action="controller" method="post" id="deleteAccountForm">
                            <button type="button" onclick="showDeleteModal()" class="btn delete-account-btn">Delete Account</button>
                            <input type="hidden" name="action" value="delete-account">
                            <input type="hidden" name="action" value="loggedInUser">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>

<!-- Delete Account Modal -->
<div id="deleteAccountModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p style="text-align: center; font-size: 20px">Please enter "DELETE" to confirm the deletion of your account.</p>
        <input type="text" id="deleteConfirm" class="input-container" placeholder="Enter DELETE" onkeyup="enableConfirmButton()">
        <button id="confirmDeleteBtn" onclick="confirmAccountDeletion()" disabled>Confirm</button>
    </div>
</div>

<script>
    var modal = document.getElementById('deleteAccountModal');
    var confirmBtn = document.getElementById('confirmDeleteBtn')
    var deleteConfirmInput = document.getElementById('deleteConfirm')

    function showDeleteModal() {
        modal.style.display = 'block';
    }

    function closeModal() {
        modal.style.display = 'none';
        deleteConfirmInput.value = '';
    }

    var span = document.getElementsByClassName("close")[0];
    span.onclick = function() {
        closeModal();
    }

    window.onclick = function(event) {
        if (event.target === modal) {
            closeModal();
        }
    }

    function enableConfirmButton(){
        var deleteConfirm = document.getElementById('deleteConfirm').value;
        confirmBtn.disabled = deleteConfirm.toUpperCase() !=="DELETE";
    }

    function  confirmAccountDeletion(){
        var deleteConfirmValue = document.getElementById('deleteConfirm').value.toUpperCase();
        if (deleteConfirmValue === "DELETE") {
            document.getElementById('deleteAccountForm').submit();
        } else {
            alert('You need to type "DELETE" to confirm.');
        }
    }
</script>

</body>
<style>
    .button-container {
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        margin-top: 20px;
        gap: 10px;
    }

    .btn {
        color: white;
        padding: 10px 50px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        min-width: 150px;
        margin: 10px;
    }

    .update-btn, .change-btn {
        background-color: #109acb;
    }

    .update-btn:hover, .change-btn:hover {
        background-color: #017fbd;
    }

    .delete-account-btn {
        background-color: #ff4d4d;
    }

    .delete-account-btn:hover {
        background-color: #cc0000;
    }

    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0,0,0);
        background-color: rgba(0,0,0,0.4);
        padding-top: 60px;
    }


    .modal-content {
        position: relative;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        background-color: #fefefe;
        margin: 5% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
    }

    .close {
        position: absolute;
        top: 0;
        right: 0;
        color: #aaa;
        font-size: 28px;
        font-weight: bold;
        padding: 10px;
        cursor: pointer;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }

    .input-container {
        justify-content: center;
        width: 300px;
        height: 25px;
        font-size: 16px;
        border: 1px solid black
    }

    #confirmDeleteBtn {
        width: 200px;
        height: 25px;
        margin-top: 20px;
        font-size: 16px;
        border: 1px solid black;
        text-align: center;
    }
</style>
</html>