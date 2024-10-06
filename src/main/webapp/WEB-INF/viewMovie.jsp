<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- For JSTL Forms -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Movie Details</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<style>
.button {
	background-color: #007bff; /* Primary color */
	color: #fff; /* Text color */
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	transition: background-color 0.3s;
}

.button:hover {
	background-color: #0056b3; /* Darker blue on hover */
}

.button-container {
	margin-top: 20px;
}

.header-link {
	color: #007bff; /* Primary color */
	font-size: 18px;
	text-decoration: none;
	margin-right: 15px;
}

.header-link:hover {
	text-decoration: underline;
	color: #0056b3; /* Darker blue on hover */
}
</style>
</head>
<body>
	<div class="container mt-5">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<a href="/genres/${movie.genre.id}" class="btn btn-primary">Back
				to ${movie.genre.name} Movies</a> <a href="/logout"
				class="btn btn-danger">Logout</a> <a href="/genres"
				class="btn btn-primary">Back to Movie Hub</a>
		</div>
		<h1 class="mb-4">
			<c:out value="${movie.title}" />
		</h1>
		<table class="table table-bordered table-striped">
			<tbody>
				<tr>
					<th scope="row">Added By:</th>
					<td><c:out
							value="${movie.user.firstName} ${movie.user.lastName}" /></td>
				</tr>
				<tr>
					<th scope="row">Movie Title Name:</th>
					<td><c:out value="${movie.title}" /></td>
				</tr>
				<tr>
					<th scope="row">User Description:</th>
					<td><c:out value="${movie.description}" /></td>
				</tr>
				<tr>
					<th scope="row">Release Year:</th>
					<td><c:out value="${movie.releaseYear}" /></td>
				</tr>
				<tr>
					<th scope="row">User Rating:</th>
					<td><c:out value="${movie.rating}" /></td>
				</tr>
				<tr>
					<th scope="row">Movie Rating:</th>
					<td><c:out value="${movie.movieRating}" /></td>
				</tr>
				<tr>
					<th scope="row">Time Length:</th>
					<td><c:out value="${movie.timeLength}"/></td>
				</tr>
				<tr>
					<th scope="row">Genre:</th>
					<td><c:out value="${movie.genre.name}" /></td>
				</tr>
				<tr>
					<th scope="row">Secondary Genre:</th>
					<td><c:out value="${movie.SGenre != null ? movie.SGenre : 'N/A'}" /></td>
				</tr>
				<tr>
					<th scope="row">Lead Actor/Actress:</th>
					<td><c:out value="${movie.leadActor}" /></td>
				</tr>
				<tr>
					<th scope="row">Director:</th>
					<td><c:out value="${movie.director}" /></td>
				</tr>
			</tbody>
		</table>
		<c:if test="${isOwner}">
			<div class="button-container">
				<a href="/edit/movies/${movie.id}" class="button">Edit Movie</a>
				<form action="/delete/movies/${movie.id}" method="post"
					style="display: inline;">
					<input type="hidden" name="_method" value="delete"> <input
						type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					<button type="submit" class="btn btn-danger"
						onclick="return confirm('Are you sure you want to delete this movie?')">Delete
						Movie</button>
				</form>
			</div>
		</c:if>

		<h3>Users who have seen this movie:</h3>
		<c:if test="${not empty user}">
			<form action="/movies/${movie.id}/seen" method="post">
				<button type="submit" class="btn btn-primary">I have seen
					this movie</button>
			</form>
		</c:if>
		<ul>
			<c:forEach var="seenUser" items="${usersWhoHaveSeen}">
				<li>${seenUser.firstName} ${seenUser.lastName} <!-- Show the "They have not seen the movie" button only if the logged-in user is the owner of the seen user -->
					<c:if test="${user.id == seenUser.id}">
						<form action="/movies/${movie.id}/notSeen/${seenUser.id}"
							method="post" style="display: inline;">
							<button type="submit" class="btn btn-danger btn-sm">Nevermind</button>
						</form>
					</c:if>
				</li>
			</c:forEach>
		</ul>




		<!-- Existing comment display form -->
		<div class="card border-danger mb-3">
			<div class="card-header">Comments</div>
			<div class="card-body">
				<c:forEach var="comment" items="${movie.comments}">
					<p>
						<strong><c:out value="${comment.user.firstName}" />:</strong>
						<c:out value="${comment.text}" />
						<c:if test="${user.id == comment.user.id}">
							<a href="/editComment/${comment.id}"
								class="btn btn-warning btn-sm ml-2">Edit</a>
							<form action="/deleteComment/${comment.id}" method="post"
								style="display: inline;">
								<input type="hidden" name="_method" value="delete"> <input
									type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}">
								<button type="submit" class="btn btn-danger btn-sm ml-2">Delete</button>
							</form>
						</c:if>
					</p>
				</c:forEach>
			</div>
		</div>

		<!-- Single form for adding comments -->
		<div class="card border-danger mb-3">
			<div class="card-header">Add a Comment</div>
			<div class="card-body">
				<form:form action="/addComment" method="post"
					modelAttribute="commentForm">
					<div class="form-group">
						<form:label class="form-label" path="text">Add a comment:</form:label>
						<p class="text-danger">
							<c:out value="${error}"></c:out>
						</p>
						<form:errors class="text-danger" path="text" />
						<form:textarea class="form-control" path="text" rows="3" />
					</div>
					<input type="hidden" name="movieId"
						value="<c:out value='${movie.id}'/>" />
					<button class="btn btn-danger" type="submit"
						aria-label="Submit Comment">Submit</button>
				</form:form>
			</div>
		</div>

	</div>
</body>
</html>
