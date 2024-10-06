<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Genre List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
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
        .genre-list {
            list-style-type: none;
            padding: 0;
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
        }
        .genre-list li {
            margin: 10px 0;
        }
        .genre-list li a {
            display: inline-block;
            padding: 10px 20px;
            color: #fff;
            background-color: #007bff;
            text-decoration: none;
            border-radius: 4px;
            font-size: 1.2em;
            transition: background-color 0.3s ease;
        }
        .genre-list li a.horror:hover {
            background-color: orangered;
        }
        .genre-list li a.romance:hover {
            background-color: darkred;
        }
        .genre-list li a.action:hover {
            background-color: darkblue;
        }
        .genre-list a.comedy:hover{
            background-color: yellow;
        }
        .genre-list a.family:hover{
            background-color: pink;
        }
        .genre-list a.holiday:hover{
            background-color: rgb(137, 240, 93);
        }
        .genre-list a.drama:hover{
            background-color: red;
        }
        .genre-list a.musical:hover{
            background-color: purple;
        }
        .genre-list a.children:hover{
            background-color: lightsalmon;
        }
        .genre-list a.scifi:hover{
            background-color:rgb(10, 70, 10);
        }
        .genre-list a.thriller:hover{
            background-color: rgb(47, 29, 33);
        }
        .btn-custom {
            background-color: #007bff;
            color: #fff;
            border-radius: 4px;
            padding: 10px 20px;
            text-decoration: none;
            font-size: 1.2em;
            transition: background-color 0.3s ease;
            text-align: center;
        }
        .btn-custom:hover {
            background-color: #0056b3;
        }
        .text-center {
            text-align: center;
        }
        .mt-3 {
            margin-top: 1rem;
        }
        .count {
            color:grey;
        }
    </style>
</head>
<body>
    <div>
        <a href="${pageContext.request.contextPath}/new/movies" class="btn btn-primary">Add a New Movie</a>
    </div>
    <div class="container">
        <div class="text-center mb-4">
            <h1>Hi ${user.lastName} Family! Welcome to Your Movie Database!</h1>
        </div>

        <ul class="genre-list">
            <c:forEach var="genre" items="${genres}">
                <li>
                    <a href="${pageContext.request.contextPath}/genres/${genre.id}" 
                        class="${genre.name eq 'Action' ? 'action' :''} ${genre.name eq 'Horror' ? 'horror' : ''}${genre.name eq 'Romance' ? 'romance' : ''}${genre.name eq 'Comedy' ? 'comedy' : ''}${genre.name eq 'Family' ? 'family' : ''}
                        ${genre.name eq 'Holiday' ? 'holiday' : ''} ${genre.name eq 'Drama' ? 'drama' :''}${genre.name eq 'Musical' ? 'musical' :''} ${genre.name eq 'Children' ? 'children' : ''} ${genre.name eq 'Scifi' ? 'scifi' :''}
                        ${genre.name eq 'Thriller'?'thriller' : ''}" >
                        ${genre.name}
                    </a>
                </li>
            </c:forEach>
        </ul>

        <a href="${pageContext.request.contextPath}/genres/random" class="btn btn-warning">Pick a Random Movie</a>
        <p>Or get more specific!</p>

        <form action="${pageContext.request.contextPath}/searchRandomMovie" method="get">
            <div class="form-group">
                <label for="genre">Genre:</label>
                <select name="genre" class="form-control">
                    <option value="">Any</option>
                    <c:forEach var="genre" items="${genres}">
                        <option value="${genre.name}">${genre.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="releaseYear">Release Year:</label>
                <input type="number" name="releaseYear" class="form-control" placeholder="Enter release year">
            </div>
            <div class="form-group">
                <label for="rating">User Rating:</label>
                <input type="number" name="rating" class="form-control" placeholder="Enter the user rating">
            </div>
            <div class="form-group">
                <label for="rating">Movie Rating:</label>
                <input type="text" name="movieRating" class="form-control" placeholder="Enter the movie rating">
            </div>
            <div class="form-group">
                <label for="timeLength">Time Length:</label>
                <input type="text" name="timeLength" class="form-control" placeholder="Enter the time length">
            </div>
            <div class="form-group">
                <label for="director">Secondary Genre:</label>
                <input type="text" name="sGenre" class="form-control" placeholder="Enter secondary genre">
            </div>
            <div class="form-group">
                <label for="leadActor">Lead Actor/Actress:</label>
                <input type="text" name="leadActor" class="form-control" placeholder="Enter the lead actor/actress">
            </div>
            <div class="form-group">
                <label for="director">Director:</label>
                <input type="text" name="director" class="form-control" placeholder="Enter director">
            </div>
            <div class="form-group">
                <label for="user">User:</label>
                <input type="text" name="user" class="form-control" placeholder="Enter user who added the movie">
            </div>
            <button type="submit" class="btn btn-primary">Find Random Movie</button>
        </form>
        
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/new/movies" class="btn btn-primary">Add a New Movie</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger mt-3">Logout</a>
        </div>
    </div>
    <div class="count">
        <p>Total movie count: ${totalMovieCount}</p>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
