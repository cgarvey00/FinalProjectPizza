<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit a Review</title>
</head>
<body>
<h2>Submit a Review</h2>
<form action="submitReview" method="post">
    <label for="username">Your Username:</label>
    <input type="text" id="username" name="username"><br><br>

    <label>Star Rating:</label><br>
    <input type="radio" id="star1" name="stars" value="1"><label for="star1">1</label>
    <input type="radio" id="star2" name="stars" value="2"><label for="star2">2</label>
    <input type="radio" id="star3" name="stars" value="3"><label for="star3">3</label>
    <input type="radio" id="star4" name="stars" value="4"><label for="star4">4</label>
    <input type="radio" id="star5" name="stars" value="5"><label for="star5">5</label><br><br>

    <label for="comment">Your Review:</label><br>
    <textarea id="comment" name="comment" rows="4" cols="50"></textarea><br><br>

    <input type="submit" value="Submit Review">
</form>
</body>
</html>
