<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");


    if (request.getSession(false) == null || loggedInUser == null || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Review</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        label {
            font-weight: bold;
            margin-bottom: 5px;
        }

        textarea,
        select {
            width: 100%;
            padding: 10px;
            margin: 5px 0 20px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            font-size: 16px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            background-color: #4caf50;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        /* Rating system styles */
        .rating {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }

        .rating span {
            font-size: 36px;
            color: #ccc;
            cursor: pointer;
            transition: color 0.3s;
        }

        .rating span:hover,
        .rating span:hover ~ span {
            color: #ff9800;
        }

        .rating span.active {
            color: #ff9800;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Write a Review</h1>
    <form action="controller?action=add-review" method="post">
        <label for="comment">Comment:</label><br>
        <textarea id="comment" name="comment" rows="4" cols="50"></textarea><br>
        <label for="stars">Stars:</label>
        <div class="rating" id="ratingStars" data-rating="0">
            <span data-value="5">&#x2605;</span>
            <span data-value="4">&#x2605;</span>
            <span data-value="3">&#x2605;</span>
            <span data-value="2">&#x2605;</span>
            <span data-value="1">&#x2605;</span>
        </div>
        <input type="hidden" id="stars" name="stars" value="0">
        <input type="submit" value="Submit Review">
    </form>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const ratingStars = document.querySelectorAll('.rating > span');

        ratingStars.forEach(function (star) {
            star.addEventListener('click', function () {
                const ratingValue = this.getAttribute('data-value');
                // const ratingContainer = this.parentElement;
                // const stars = ratingContainer.getAttribute('data-rating');
                document.getElementById('stars').value = ratingValue;

                // document.getElementById('stars').value = ratingValue;

                ratingStars.forEach(s => s.classList.remove('active'));
                for (let i = ratingStars.length-ratingValue; i < ratingStars.length; i++) {
                    ratingStars[i].classList.add('active');
                }

            });
        });
    })
    ;
</script>

</body>
</html>
