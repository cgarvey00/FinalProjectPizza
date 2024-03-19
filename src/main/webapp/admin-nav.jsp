<header class="header fixed-top">
    <div class="container">
        <div class="row align-items-center">
            <a href="#" class="logo mr-auto"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>
            <nav class="nav">
                <a href="controller?action=admin-dashboard">Dashboard</a>
                <a href="controller?action=admin-product">View Orders</a>
                <a href="controller?action=admin-product">View Products</a>
            </nav>
            <div class="icons">
                <div class="fas fa-bars" id="menu-btn"></div>
                <div id="user-btn" class="fas fa-user">
                        <a href="#"></a>
                    </div>
            </div>
            <div class="profile">
                <a href="controller?action=view-orders-customer" class="option-btn">Orders</a>
                <form action="controller" method="POST">
                    <input type="hidden" name="action" value="view-reset-password">
                    <a><button type="submit" name="view-reset-password" class="option-btn">Change Password</button></a>
                </form>
                <a href="controller?action=logout" class="delete-btn"
                   onclick="return confirm('Logout from the website?');">Logout</a>
            </div>
        </div>
    </div>
    </div>

</header>


