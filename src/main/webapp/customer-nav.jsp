<%@ page import="com.finalprojectcoffee.entities.User" %>
<header class="header fixed-top">
    <%
        User user = (User) request.getSession().getAttribute("loggedInUser");
        request.setAttribute("loggedInUser", user);
    %>
    <style>
        :root {
            --main-color: #2980b9;
            --orange: #f39c12;
            --red: #e74c3c;
            --black: #333;
            --white: #fff;
            --light-color: #666;
            --light-bg: #eee;
            --border: .2rem solid var(--black);
            --box-shadow: 0 .5rem 1rem rgba(0, 0, 0, .1);
        }

        .btn:hover,
        .delete-btn:hover,
        .option-btn:hover {
            background-color: #d9534f;
            color: var(--white);
        }

        .btn {
            background-color: var(--main-color);
        }

        .option-btn {
            background-color: var(--orange);
            color: var(--white);
        }

        .delete-btn {
            background-color: var(--red);
            color: var(--white);
        }

        .flex-btn {
            display: flex;
            gap: 1rem;
        }
    </style>
    <div class="container">
        <div class="row align-items-center">
            <a href="#" class="logo mr-auto"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>
            <nav class="nav">
                <a href="controller?action=customer-page">Home</a>
                <a href="controller?action=view-menu">Menu</a>
                <a href="controller?action=view-review">Review</a>
            </nav>
            <div class="icons">
                <div class="fas fa-bars" id="menu-btn"></div>
                <a href="controller?action=view-search">
                    <div class="fas fa-search" id="search-btn"></div>
                </a>
                <a href="controller?action=view-cart">
                    <div class="fas fa-shopping-cart" id="cart-btn"><span>
                        <%=session.getAttribute("cartLists") != null ? session.getAttribute("cartLists") : "0"%>
                    </span>
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
                        <a>
                            <button type="submit" name="view-user-profile" class="option-btn">Profile</button>
                        </a>
                    </form>
                    <%--                    <a href="controller?action=view-address" class="option-btn">Address</a>--%>
                    <form action="controller" method="POST">
                        <input type="hidden" name="action" value="view-address">
                        <a>
                            <button type="submit" name="view-address" class="option-btn">Address</button>
                        </a>
                    </form>

                </div>
                <%--                <a href="controller?action=view-order-customer" class="option-btn">Orders</a>--%>
                <form action="controller" method="POST">
                    <input type="hidden" name="action" value="view-order-customer">
                    <a>
                        <button type="submit" name="view-order-customer" class="option-btn">
                            Orders
                        </button>
                    </a>
                </form>
                <form action="controller" method="POST">
                    <input type="hidden" name="action" value="view-changepassword">
                    <a>
                        <button type="submit" name="view-changepassword" class="option-btn">Change Password</button>
                    </a>
                </form>
                <%--                <a href="controller?action=logout" class="delete-btn"--%>
                <%--                   onclick="return confirm('Logout from the website?');">Logout</a>--%>
                <form action="controller" method="POST">
                    <input type="hidden" name="action" value="logout">
                    <a>
                        <button type="submit" name="logout" class="delete-btn"
                                onclick="return confirm('Logout from the website?');">Logout
                        </button>
                    </a>
                </form>
            </div>
        </div>
    </div>
    </div>

</header>

<script>

</script>