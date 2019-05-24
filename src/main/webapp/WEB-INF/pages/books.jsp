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
        <c:forEach items="${books}" var="book">
            <div class = "col-md-3 d-flex" >
                <div class="card flex-fill">
                    <img class="card-img-top" src="${pageContext.request.contextPath}/images/${book.value}.jpg" alt="Card image">
                    <div class="card-body">
                        <h4 class="card-title"><c:out value="${book.key.name}"/></h4>
                        <p class="card-text">Author</p>
                    </div>
                    <div class="card-footer">
                        <a href="#" class="btn btn-primary stretched-link">See detailed info</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
