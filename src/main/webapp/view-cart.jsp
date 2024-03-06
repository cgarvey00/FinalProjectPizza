<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
</head>
<body>
<br>
<br>
<br>
</br>
<br>
<br>
<br>
</br>
<section class="products" id="products">
    <h3 class="heading">Shopping Cart</h3>

    <div class="box-container">
<%

%>
        <%--        <?php--%>
        <%--         $grand_total = 0;--%>
        <%--         $select_cart = $db->prepare("SELECT * FROM `cart` WHERE user_id = ?");--%>
        <%--        $select_cart->execute([$user_id]);--%>
        <%--        if ($select_cart->rowCount() > 0) {--%>
        <%--        while ($fetch_cart = $select_cart->fetch(PDO::FETCH_ASSOC)) {--%>
        <%--        ?>--%>
        <div class="box">
            <form action="." method='POST'>
                <%--                <input type='hidden' name='p_id' value='<?php echo $fetch_cart['p_id']; ?>'>--%>
                <%--                <input type='hidden' name='id' value='<?php echo $fetch_cart['id']; ?>'>--%>
                <input type="hidden" name="name" value="<?= $fetch_cart['name']; ?>">
                <input type="hidden" name="price" value="<?= $fetch_cart['price']; ?>">
                <input type="hidden" name="qty" value="<?= $fetch_cart['quantity']; ?>">
                <input type='hidden' name='action' value='add-to-checkout'>
                <img src="../uploaded-images/<?= $fetch_cart['image']; ?>" alt="">
                <div class="name" name="name">
                    <?= $fetch_cart['name']; ?>
                </div>
                <div class="flex">
                    <div class="price" step="0.01" name="price">sub total : <span>&euro;
                        <?= $sub_total = ($fetch_cart['price'] * $fetch_cart['quantity']); ?>
                           </span></div>
                    <form action="." method='POST'>
                        <%--                        <input type='hidden' name='p_id' value='<?php echo $fetch_cart['p_id']; ?>'>--%>
                        <input type="hidden" name="qty" value="<?= $fetch_cart['quantity']; ?>">
                        <input type='hidden' name='action' value='update-cart'>
                        <div><span> <input type="number" name="qty" class="qty" min="1" max="99"
                                           onkeypress="if(this.value.length == 2) return false;"
                                           value="<?= $fetch_cart['quantity']; ?>"></span></div>

                        <button type="submit" class="fas fa-edit" name="update-cart"></button>

                    </form>
                </div>

            </form>
            <form action="." method='POST'>
                <input type='hidden' name='action' value='delete-cartitem'>
                <%--                <input type="hidden" name="id" value='<?php echo $fetch_cart['id']; ?>'>--%>

                <input type="submit" name="delete-cartitem" class="delete-btn" value="delete"
                       onclick="return confirm('delete this product?');">

            </form>
        </div>

        <%--        <?php--%>
        <%--               $grand_total += $sub_total;--%>
        <%--            }--%>
        <%--         } else {--%>
        <%--            echo '<p class="empty">no products added yet!</p>';--%>
        <%--        }--%>
        <%--        ?>--%>
    </div>
    <form action="." method='POST'>
        <div class="cart-total">
            <input type="hidden" name="userID" value='<?php echo $user_id; ?>'>
            <p>grand total : <span>&euro;
                <?= $grand_total; ?>
               </span></p>
            <a href="?action=customer-view" style="text-decoration:none;" class="option-btn">continue shopping</a>
            <a href="?action=delete-all-cart" style="text-decoration:none;"
               class="delete-btn <?=($grand_total > 1) ? '' : 'disabled'; ?>"
               onclick="return confirm('delete all from cart?');">delete all item</a>
            <a href="?action=view-checkout" style="text-decoration:none;"
               class="btn <?=($grand_total > 1) ? '' : 'disabled'; ?>">proceed to checkout</a>
        </div>
    </form>
</section>
<section class="footer container">

    <a href="#" class="logo"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>

    <p class="credit"> created by <span>Conor,Tom and Matthew</span> | all rights reserved! © 2024 </p>

    <div class="share">
        <a href="#" class="fab fa-facebook-f"></a>
        <a href="#" class="fab fa-linkedin"></a>
        <a href="#" class="fab fa-twitter"></a>
        <a href="#" class="fab fa-github"></a>
    </div>

</section>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

</html>