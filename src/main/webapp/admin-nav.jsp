<header class="header fixed-top">
    <div class="container">
        <div class="row align-items-center">
            <a href="#" class="logo mr-auto"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>
            <nav class="nav">
                <a href="controller?action=admin-dashboard">Dashboard</a>
                <a href="controller?action=view-admin-orders">View Orders</a>
                <a href="controller?action=view-products">View Products</a>
            </nav>
            <div class="icons">
                <div class="fas fa-bars" id="menu-btn"></div>
                <div id="user-btn" class="fas fa-user">
                    <a href="#"></a>
                </div>
            </div>
            <div class="profile">
                <a href="controller?action=view-admin-orders" class="option-btn">Orders</a>
                <a href="controller?action=logout" class="delete-btn"
                   onclick="return confirm('Logout from the website?');">Logout</a>
            </div>
        </div>
    </div>
    </div>
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
        }

        .option-btn{
            background-color: var(--orange);
        }

        .delete-btn{
            background-color: var(--red);
        }

        .flex-btn{
            display: flex;
            gap:1rem;
        }
    </style>
</header>


