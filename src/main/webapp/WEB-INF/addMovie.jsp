<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add a Movie</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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

        .image-container {
            display: flex;
            flex-wrap: wrap;
            gap: 10px; /* Space between images */
        }

        .image-container img {
            width: 100px; /* Fixed width for images */
            cursor: pointer; /* Change cursor to pointer on hover */
            border-radius: 5px; /* Rounded corners for images */
            transition: transform 0.2s; /* Smooth zoom effect */
        }

        .image-container img:hover {
            transform: scale(1.1); /* Zoom effect on hover */
        }
    </style>
</head>

<body>
    <a href="/logout" class="btn btn-danger">Logout</a>
    <div class="container">
        <div class="card">
            <div class="card-header text-center">
                <h3>Add a New Movie</h3>
            </div>
            <div class="card-body">
                <form:form action="/new/movies" method="POST" modelAttribute="movie">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <div class="form-group">
                        <form:label path="title">Title:</form:label>
                        <form:input class="form-control" path="title" placeholder="Enter movie title" required="required" />
                        <form:errors class="text-danger" path="title" />
                    </div>
                    <div class="form-group">
                        <form:label path="description">Description:</form:label>
                        <form:textarea class="form-control" path="description" placeholder="Enter movie description" required="required" />
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
                        <form:input class="form-control" path="timeLength" placeholder="Enter time length" required="required" />
                        <form:errors class="text-danger" path="timeLength" />
                    </div>
                    <div class="form-group">
                        <form:label path="genre">Genre:</form:label>
                        <form:select path="genre.id" class="form-control">
                            <c:forEach var="genre" items="${genres}">
                                <option value="${genre.id}">${genre.name}</option>
                            </c:forEach>
                        </form:select>
                        <form:errors class="text-danger" path="genre" />
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
                    <div class="form-group">
                        <form:label path="posterUrl">Movie Poster:</form:label>
                        <input type="text" id="posterUrl" name="posterUrl" class="form-control"
                            placeholder="Add a movie poster..." />
                        <div class="image-container" id="imageResults"></div>
                    </div>
                    
                    <button type="submit" class="btn btn-custom btn-block">Add Movie</button>
                </form:form>

                <div class="container mt-5">
                    <h2>Create a New Genre</h2>
                    <form action="/genres" method="post" class="form-inline mb-3">
                        <div class="form-group mr-2">
                            <label for="genreName" class="sr-only">Genre Name</label>
                            <input type="text" class="form-control" id="genreName" name="genreName" placeholder="Genre Name" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Add Genre</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="text-center mt-3">
            <a href="/genres" class="btn btn-secondary">Back to the Movie Hub</a>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#posterUrl').on('input', function () {
                const query = $(this).val();
                if (query.length > 2) {
                    $.ajax({
                        url: `https://api.imgur.com/3/gallery/search/?q=${query}`,
                        method: 'GET',
                        headers: {
                            'Authorization': '80d1de7e568270c' // Replace with your Imgur Client ID
                        },
                        success: function (data) {
                            $('#imageResults').empty();
                            data.data.forEach(image => {
                                if (image.images && image.images.length > 0) {
                                    $('#imageResults').append(`<img src="${image.images[0].link}" alt="${image.title}" data-url="${image.images[0].link}" />`);
                                } else if (image.link) {
                                    $('#imageResults').append(`<img src="${image.link}" alt="${image.title}" data-url="${image.link}" />`);
                                }
                            });
                        }
                    });
                } else {
                    $('#imageResults').empty(); // Clear results if input is less than 3 characters
                }
            });

            $(document).on('click', '.image-container img', function () {
                const selectedImage = $(this).data('url');
                $('#posterUrl').val(selectedImage); // Set the selected image URL in the input field
            });
        });
    </script>
</body>

</html>
