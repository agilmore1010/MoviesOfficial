<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Database</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        .container {
            max-width: 900px;
            margin: 20px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #007bff;
        }

        .link {
            display: inline-block;
            margin: 10px 0;
            padding: 10px 20px;
            color: #fff;
            background-color: #007bff;
            text-decoration: none;
            border-radius: 4px;
        }

        .movie-title {
            font-size: 1.5em;
            margin: 20px 0;
            color: #333;
        }

        .movie-list {
            list-style-type: none;
            padding: 0;
        }

        .movie-list li {
            margin: 10px 0;
        }

        .movie-list li a {
            color: #007bff;
            text-decoration: none;
        }


    </style>
</head>
<body>
	<a href="/logout"> Logout</a> 
    <div class="container">
        <h1>Hi ${user.lastName} Family! Welcome to Your Movie Database!</h1>
        <ul class="movie-list">
            <c:forEach var="movie" items="${movies}">
                <li class="movie-title">
                    <a href="view/movies/${movie.id}">${movie.title}</a>
                </li>
            </c:forEach>
        </ul>
        <a class="link" href="/new/movies">Add a New Movie</a>
    </div>
</body>
</html>
