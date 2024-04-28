<%@ page import="com.finalprojectcoffee.entities.User" %>
<header class="header fixed-top">
    <%
        User user = (User) request.getSession().getAttribute("loggedInUser");
        request.setAttribute("loggedInUser", user);
    %>
    <style>
        :root{
            --main-color:#2980b9;
            --orange:#f39c12;
            --red:#e74c3c;
            --black:#333;
            --white:#fff;
            --light-color:#666;
            --light-bg:#eee;
            --border:.2rem solid var(--black);
            --box-shadow:0 .5rem 1rem rgba(0,0,0,.1);
        }
        .btn:hover,
        .delete-btn:hover,
        .option-btn:hover{
            background-color: #d9534f;
            color: var(--white);
        }

        .btn{
            background-color: var(--main-color);
            color: var(--white);
        }

        .option-btn{
            background-color: var(--orange);
            color: var(--white);
        }

        .delete-btn{
            background-color: var(--red);
            color: var(--white);
        }

        .flex-btn{
            display: flex;
            gap:1rem;
        }
    </style>
    <div class="container">
        <div class="row align-items-center">
            <a href="#" class="logo mr-auto"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>
            <nav class="nav">
                <a href="controller?action=employee-page">Dashboard</a>
                <a href="controller?action=add-review">View Menu</a>
                <a href="controller?action=view-order-history">Order History</a>
            </nav>
            <div class="icons">
                <div class="fas fa-bars" id="menu-btn"></div>
                <a href="controller?action=viewcurrent-emp-orders">
                    <div class="fas fa-truck" id="cart-btn"><span>
                              <%=session.getAttribute("employeeDeliveryList") != null ? session.getAttribute("employeeDeliveryList") : "0"%>

                    </span>

                    </div>
                </a>
                <div id="user-btn" class="fas fa-user">
                    <a href="#"></a>
                </div>
                </a>
            </div>
            <div class="profile">
                <div class="flex-btn">
                </div>
                <a href="controller?action=viewcurrent-emp-orders" class="option-btn">Orders</a>
                <a href="controller?action=logout" class="delete-btn"
                   onclick="return confirm('Logout from the website?');">Logout</a>
            </div>
        </div>
    </div>
    </div>

</header>

<script>

</script>