<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
    <title>Book</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<c:if test="${empty error}">
    <c:out value="${book.name}\n${book.description}\n${book.pageQuantity}"/>
    <img src="${pageContext.request.contextPath}/images/${book.imageUrl}.jpg" alt="${book.name} image">
    <c:forEach items="${book.authors}" var="author">
        <c:out value="${author.firstName} ${author.lastName}"/>
    </c:forEach>

    <div class="container">
        <h2>All copies by book</h2>

        <table class="table table-striped table-bordered table-hover">
            <thead class="thead-dark">
            <tr>
                <th>Publication year</th>
                <th>Publishing house</th>
                <th>Available</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${copies}" var="copy">
                    <tr>
                        <td><c:out value="${copy.publicationYear}"/></td>
                        <td><c:out value="${copy.publishingHouse}"/></td>
                        <td><c:out value="${copy.available ? 'Yes': 'No'}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
<c:if test="${not empty error}">
    <h2>error</h2>
</c:if>
</body>
</html>
