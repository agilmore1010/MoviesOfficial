<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Movie Court</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="d-flex justify-content-between align-items-center">
            <h1>Edit Comment</h1>
            <p>
                <a href="/dashboard" class="btn btn-link" aria-label="Home">Home</a> |
                <a href="/logout" class="btn btn-link" aria-label="Logout">Logout</a>
            </p>
        </div>
        <!-- Edit Comment Form -->
        <div class="mt-3">
            <form:form action="/movies/${commentForm.movie.id}/comments/update/${commentForm.id}" method="post" modelAttribute="commentForm">
                <div class="form-group">
                    <form:label class="form-label" path="text">Edit your comment:</form:label>
                    <form:errors class="text-danger" path="text" />
                    <form:textarea class="form-control" path="text" rows="3" />
                </div>
                <input type="hidden" name="movieId" value="<c:out value='${commentForm.movie.id}'/>" />
                <button class="btn btn-primary" type="submit" aria-label="Update Comment">Update</button>
            </form:form>
        </div>
    </div>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
