<%@ page import="com.finalprojectcoffee.entities.User" %>
<header class="header fixed-top">
<%
    User user = (User) request.getSession().getAttribute("loggedInUser");
    request.setAttribute("loggedInUser",user);
%>
    <div class="container">
        <div class="row align-items-center">
            <a href="#" class="logo mr-auto"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>
            <nav class="nav">
                <a href="controller?action=customer-page">Home</a>
                <a href="controller?action=view-menu">Menu</a>
                <a href="controller?action=add-review">Review</a>
            </nav>
            <div class="icons">
                <div class="fas fa-bars" id="menu-btn"></div>
                <a href="controller?action=view-search">
                    <div class="fas fa-search" id="search-btn"></div>
                </a>
                <a href="controller?action=view-cart">
                    <div class="fas fa-shopping-cart" id="cart-btn"><span></span>
                    </div>
                    <div id="user-btn" class="fas fa-user">
                        <a href="#"></a>
                    </div>
                </a>
            </div>
            <div class="profile">
                <div class="flex-btn">
                    <form action="controller" method="POST">
                        <input type="hidden" name="action" value="view-user-profile">
                        <a><button type="submit" name="view-user-profile" class="option-btn">Profile</button></a>
                    </form>
                    <a href="controller?action=view-address" class="option-btn">Address</a>
                </div>
                <a href="controller?action=view-order-customer" class="option-btn">Orders</a>
                <form action="controller" method="POST">
                    <input type="hidden" name="action" value="view-changepassword">
                    <a><button type="submit" name="view-changepassword" class="option-btn">Change Password</button></a>
                </form>
                <a href="controller?action=logout" class="delete-btn"
                   onclick="return confirm('Logout from the website?');">Logout</a>
            </div>
        </div>
    </div>
    </div>

</header>

<script>

</script>