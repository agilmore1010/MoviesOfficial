<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Movie</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
body {
	background-color: #f8f9fa;
}

.container {
	margin-top: 50px;
}

.card {
	border-radius: 15px;
}

.btn-custom {
	background-color: #007bff;
	color: #fff;
}
</style>
</head>
<body>
	<a href="/logout" class="btn btn-danger">Logout</a>
	<div class="container">
		<div class="card">
			<div class="card-header text-center">
				<h3>Edit Movie</h3>
			</div>
			<div class="card-body">
				<form:form action="/edit/movies/${movie.id}" method="POST" modelAttribute="movie">
					<input type="hidden" name="_method" value="PUT" />
					<c:if test="${not empty error}">
						<div class="alert alert-danger">${error}</div>
					</c:if>
					<c:if test="${not empty success}">
						<div class="alert alert-success">${success}</div>
					</c:if>
					<div class="form-group">
						<form:label path="title">Title:</form:label>
						<form:input class="form-control" path="title" placeholder="Enter movie title" required="required" />
						<form:errors class="text-danger" path="title" />
					</div>
					<div class="form-group">
						<form:label path="description">User Description:</form:label>
						<form:input class="form-control" path="description" placeholder="Enter movie description" required="required" />
						<form:errors class="text-danger" path="description" />
					</div>
					<div class="form-group">
						<form:label path="releaseYear">Release Year:</form:label>
						<form:input class="form-control" path="releaseYear" placeholder="Enter release year" required="required" />
						<form:errors class="text-danger" path="releaseYear" />
					</div>
					<div class="form-group">
						<form:label path="rating">User Rating:</form:label>
						<form:input class="form-control" path="rating" placeholder="Enter user rating" required="required" />
						<form:errors class="text-danger" path="rating" />
					</div>
					<div class="form-group">
						<form:label path="movieRating">Movie Rating:</form:label>
						<form:input class="form-control" path="movieRating" placeholder="Enter movie rating" required="required" />
						<form:errors class="text-danger" path="movieRating" />
					</div>
					<div class="form-group">
                        <form:label path="timeLength">Time Length:</form:label>
                        <form:input class="form-control" path="timeLength" placeholder="Enter time length" required="required"/>
                        <form:errors class="text-danger" path="timeLength"/>
                    </div>
					<div class="form-group">
						<form:label path="genre">Genre:</form:label>
						<form:select path="genre.id" class="form-control">
							<c:forEach var="genre" items="${genres}">
								<option value="${genre.id}" <c:if test="${genre.id == movie.genre.id}">selected</c:if>>${genre.name}</option>
							</c:forEach>
						</form:select>
						<form:errors class="text-danger" path="genre.id" />
					</div>
					<div class="form-group">
						<form:label path="sGenre">Secondary Genre:</form:label>
						<form:input class="form-control" path="sGenre" placeholder="Enter secondary genre" />
						<form:errors class="text-danger" path="sGenre" />
					</div>
					<div class="form-group">
						<form:label path="leadActor">Lead Actor/Actress:</form:label>
						<form:input class="form-control" path="leadActor" placeholder="Enter lead actor/actress" required="required" />
						<form:errors class="text-danger" path="leadActor" />
					</div>
					<div class="form-group">
						<form:label path="director">Director:</form:label>
						<form:input class="form-control" path="director" placeholder="Enter director name" required="required" />
						<form:errors class="text-danger" path="director" />
					</div>
					<button type="submit" class="btn btn-custom btn-block">Update Movie</button>
				</form:form>
			</div>
		</div>
		
		<div class="text-center mt-3">
			<a href="/genres" class="btn btn-secondary">Back to the Movie Hub</a>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
