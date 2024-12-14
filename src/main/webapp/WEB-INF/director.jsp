<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<head>
    <title>Movies directed by ${director}</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
</head>
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
        margin-bottom: 20px;
    }

    .link {
        display: inline-block;
        margin: 10px 0;
        padding: 10px 20px;
        color: #fff;
        background-color: #007bff;
        text-decoration: none;
        border-radius: 4px;
        font-size: 1.2em;
        transition: background-color 0.3s ease;
    }

    .link:hover {
        background-color: #0056b3;
    }

    .movie-title {
        font-size: 1.5em;
        margin: 20px 0;
        color: #333;
        transition: color 0.3s ease;
    }

    .movie-title a {
        color: #007bff;
        text-decoration: none;
    }

    .movie-title a:hover {
        color: #0056b3;
    }

    .director-list {
        list-style-type: none;
        padding: 0;
        margin: 0;
    }

    .director-list li {
        margin: 10px 0;
    }

    .text-center {
        text-align: center;
    }

    .btn-custom {
        background-color: #007bff;
        color: #fff;
        border-radius: 4px;
        padding: 10px 20px;
        text-decoration: none;
        font-size: 1.2em;
        transition: background-color 0.3s ease;
        display: inline-block;
        margin-top: 10px;
    }

    .btn-custom:hover {
        background-color: #0056b3;
    }

    .btn-danger {
        background-color: #dc3545;
        color: #fff;
        border-radius: 4px;
        padding: 10px 20px;
        text-decoration: none;
        font-size: 1.2em;
        transition: background-color 0.3s ease;
        display: inline-block;
        margin-top: 10px;
    }

    .btn-danger:hover {
        background-color: #c82333;
    }

    .count {
        color: gray;
        margin-top: 20px; /* Added margin for spacing */
    }
</style>

<body>
    <div class="container">
        <h2>Movies directed by ${director}</h2>
        <ul class="director-list">
            <c:set var="currentLetter" value="" />  <!-- Initialize currentLetter outside the loop -->
            <c:forEach var="movie" items="${movies}">
                <c:set var="adjustedTitle" value="${fn:trim(fn:replace(movie.title, 'The ', ''))}" />
                <c:set var="firstLetter" value="${adjustedTitle.substring(0, 1).toUpperCase()}" />
        
                <c:if test="${firstLetter ne currentLetter}">  <!-- Only display if the letter changes -->
                    <li class="movie-title"><strong>${firstLetter}</strong></li>
                    <c:set var="currentLetter" value="${firstLetter}" />  <!-- Update currentLetter -->
                </c:if>
        
                <li class="movie-title">
                    <a href="${pageContext.request.contextPath}/view/movies/${movie.id}">${movie.title}</a>
                </li>
            </c:forEach>
        </ul>
        

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/new/movies" class="btn-custom">Add a New Movie</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn-danger mt-3">Logout</a>
            <a href="${pageContext.request.contextPath}/genres" class="btn-custom">Back to Genres</a>
            <a href="${pageContext.request.contextPath}/view/movies/${previousMovieId}" class="btn-custom">Back to Previous Movie</a>
        </div>
    </div>
    
    <div class="count">
        <p>Number of movies directed by ${director}: ${movieCount}</p>
        <!-- Optionally show the movie count for a specific genre if applicable -->
        <p>Total number of movies: ${totalMovieCount}</p>
    </div>
</body>
</html>
