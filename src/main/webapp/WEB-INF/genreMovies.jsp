<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${genre.name} Movies</title>
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

.movie-list {
	list-style-type: none;
	padding: 0;
	margin: 0;
}

.movie-list li {
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
.count{
	color:gray
}
</style>
</head>
<body>
	<div class="container">
		<h1>Welcome to The ${genre.name} Database!</h1>
		<ul class="movie-list">
			<c:set var="currentLetter" value="" />
			<c:forEach var="movie" items="${movies}">
				<c:set var="adjustedTitle" value="${fn:trim(fn:replace(movie.title, 'The ', ''))}" />

				<c:choose>
					<c:when test="${adjustedTitle.substring(0, 1) >= '0' && adjustedTitle.substring(0, 1) <= '9'}">
						<c:set var="firstLetter" value="#" />
					</c:when>
					<c:otherwise>
						<c:set var="firstLetter" value="${adjustedTitle.substring(0, 1).toUpperCase()}" />
					</c:otherwise>
				</c:choose>
				
				<c:if test="${firstLetter ne currentLetter}">
					<li class="movie-title"><strong>${firstLetter}</strong></li>
					<c:set var="currentLetter" value="${firstLetter}" />
				</c:if>

				<li class="movie-title"><a
					href="${pageContext.request.contextPath}/view/movies/${movie.id}">${movie.title}</a>
				</li>
			</c:forEach>
		</ul>
		<div class="text-center mt-3">
			<a href="${pageContext.request.contextPath}/new/movies"
				class="btn-custom">Add a New Movie</a> <a
				href="${pageContext.request.contextPath}/logout"
				class="btn-danger mt-3">Logout</a> <a
				href="${pageContext.request.contextPath}/genres"
				class="btn-custom">Back to Genres</a>
		</div>
	</div>
	<div class="count">
		<p>Number of movies in ${genre.name}:${movieCount}</p>
		<p>Total number of movies:${totalMovieCount} </p>
	</div>
</body>
</html>
