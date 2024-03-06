<header class="header fixed-top">
    <%--    <section class="flex">--%>
    <div class="container">
        <div class="row align-items-center">
            <a href="#" class="logo mr-auto"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>
            <nav class="nav">
                <a href="controller?action=customer-page">Home</a>
                <a href="controller?action=view-menu">Menu</a>
                <a href="controller?action=create-order">Create Order</a>
                <a href="controller?action=logout">Logout</a>
            </nav>
            <div class="icons">
                <div class="fas fa-bars" id="menu-btn"></div>
                <a href="controller?action=view-search">
                    <div class="fas fa-search" id="search-btn"></div>
                </a>
                <a href="controller?action=show-cart">
                    <div class="fas fa-shopping-cart" id="cart-btn"><span></span>
                    </div>
                    <div id="user-btn" class="fas fa-user">
                        <a href="#"></a>
                    </div>
                </a>
            </div>
            <div class="profile">
                <div class="flex-btn">
                    <a href="../admin/register_admin.php" class="option-btn">register</a>
                    <a href="../admin/admin_login.php" class="option-btn">login</a>
                </div>
                <a href="../components/admin_logout.php" class="delete-btn"
                   onclick="return confirm('logout from the website?');">logout</a>
            </div>
        </div>
    </div>
    </div>
    <%--    </section>--%>
</header>

<script>

</script>