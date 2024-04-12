<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>

<body>
<jsp:include page="admin-nav.jsp"/>
<br><br><br><br><br><br><br><br><br><br>
<form action="controller" method="post">
    <button type="submit" name="action" value="to-add-product" class="add-btn">Add</button>
</form>
<section class="show-products">
    <h1 class="heading">Product List</h1>
    <div class="box-container">
        <c:if test="${not empty sessionScope.productList}">
            <c:forEach var="product" items="${sessionScope.productList}">
                <div class="box">
                    <img src="${pageContext.request.contextPath}/images/${product.image}" alt="Product Image">
                    <div class="name">
                        <c:out value="${product.getName()}"/>
                    </div>
                    <div class="details">Category: <span>
                        <c:out value="${product.getCategory()}"/></span>
                    </div>
                    <div class="price">&euro;<span>
                        <c:out value="${product.getPrice()}"/>
                        </span>
                    </div>
                    <div class="details">Details: <span>
                          <c:out value="${product.getDetails()}"/>
                        </span>
                    </div>
                    <div class="stock">Stock: <span>
                            <c:out value="${product.getStock()}"/>
                        </span>
                    </div>
                    <form action="controller" method="post">
                        <button type="submit" name="action" value="to-update-product" class="option-btn">Update</button>
                        <input type="hidden" name="productId" value="${product.getId()}">
                    </form>
                    <form action="controller" method="post" id="deleteProductForm-${product.getId()}">
                        <button type="button" value="delete-product" class="delete-btn"
                                onclick="showDeleteModal('${product.getName()}', '${product.getId()}')">Delete
                        </button>
                        <input type="hidden" name="action" value="delete-product">
                        <input type="hidden" name="productId" value="${product.getId()}">
                    </form>
                </div>
            </c:forEach>
        </c:if>
    </div>
</section>

<jsp:include page="footer.jsp"/>

<div id="deleteProductModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p style="text-align: center; font-size: 20px">Confirm you are sure to delete <span id="productNameToDelete"></span>.</p>
        <button id="confirmDeleteBtn" onclick="confirmProductDeletion()">Confirm</button>
        <input type="hidden" id="productIdToDelete">
    </div>
</div>

<script>
    var modal = document.getElementById('deleteProductModal');
    var confirmBtn = document.getElementById('confirmDeleteBtn');

    function showDeleteModal(productName, productId) {
        document.getElementById('productIdToDelete').value = productId;
        document.getElementById('productNameToDelete').textContent = productName;
        modal.style.display = 'block';
    }

    function closeModal() {
        modal.style.display = 'none';
    }

    var span = document.getElementsByClassName("close")[0];
    span.onclick = function () {
        closeModal();
    }

    window.onclick = function (event) {
        if (event.target === modal) {
            closeModal();
        }
    }

    function confirmProductDeletion() {
        var productId = document.getElementById('productIdToDelete').value;
        var form = document.getElementById('deleteProductForm-' + productId);

        if(form) {
            form.submit();
        } else {
            console.error('Form not found for product ID: ' + productId);
        }
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

<style>
    .add-btn {
        background-color: #109acb;
        color: white;
        padding: 8px 20px;
        font-size: 18px;
        border: none;
        float: right;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
        margin-top: 20px;
        margin-right: 40px;
    }

    .add-btn:hover {
        background-color: #017fbd;
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
        background-color: rgb(0, 0, 0);
        background-color: rgba(0, 0, 0, 0.4);
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

    #confirmDeleteBtn {
        width: 300px;
        height: 25px;
        margin-top: 20px;
        font-size: 16px;
        border: 1px solid black;
        text-align: center;
    }
</style>
</html>