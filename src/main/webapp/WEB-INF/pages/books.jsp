<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
    <title>Books</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-3">
            <input class="form-control" id="searchLine" type="text" placeholder="Search...">
        </div>
    </div>
    <div class="row">
        <c:forEach items="${books}" var="book">
            <div class = "col-md-3 d-flex" id="booksCards">
                <div class="card flex-fill">
                    <img class="card-img-top img-fluid" src="${pageContext.request.contextPath}/images/${book.imageUrl}.jpg" alt="${book.name} image">
                    <div class="card-body">
                        <h4 class="card-title"><c:out value="${book.name}"/></h4>
                        <p class="card-text">by
                            <c:forEach items="${book.authors}" var="author">
                                <c:out value="${author.firstName} ${author.lastName}"/>
                            </c:forEach>
                        </p>
                    </div>
                    <div class="card-footer">
                        <a href="${pageContext.request.contextPath}/book/${book.id}" class="btn btn-primary stretched-link">See detailed info</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
  $(document).ready(function(){
    $("#searchLine").on("keyup", function() {
      var value = $(this).val().toLowerCase();
      $("#booksCards div:first-child").filter(function() {
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
    });
  });
</script>
</body>
</html>
