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
    <script src="${pageContext.request.contextPath}/js/jQueryValidator.js"></script>
</head>
<body style="background-image: url('${pageContext.request.contextPath}/images/wallpaper.jpg');">
<c:if test="${empty error}">

    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1><c:out value="${book.name}"/></h1>
            <p>
                <c:forEach items="${book.authors}" var="author">
                    <c:out value="${author.firstName} ${author.lastName}"/>
                </c:forEach>
            </p>
        </div>
    </div>


    <div class="container-fluid">
        <div class="row">
            <div class = "col-md-4" >
                <img class="img-fluid rounded" src="${pageContext.request.contextPath}/images/${book.imageUrl}.jpg" alt="${book.name} image">
            </div>
            <div class = "col-md-8" >
                <h2 class="text-center">Description</h2>
                <p style="font-size: 26px;">
                    <c:out value="${book.description}"/>
                </p>
                <p class="text-left" style="font-size: 26px;">
                    <strong>Page quantity:</strong> <c:out value="${book.pageQuantity}"/>
                </p>
                <p class="text-left" style="font-size: 26px;">
                    <strong>Rating:</strong> <c:out value="${book.rating}"/> / 100
                </p>
                <p class="text-left" style="font-size: 26px;">
                    <strong>Was ordered: </strong><c:out value="${book.ordersQuantity}"/> times
                </p>
            </div>
        </div>
    </div>



    <div class="container-fluid">
        <div class="row">
            <div class = "col-md-9" >
                <h2 class="text-center">All copies by book</h2>
            </div>
        </div>

        <div class="row">
            <div class = "col-md-9 d-flex" >

                <table class="table table-striped table-bordered table-hover">
                    <thead class="thead-dark">
                    <tr>
                        <th>Publication year</th>
                        <th>Publishing house</th>
                        <th>Available</th>

                        <c:if test="${user == 'librarian'}">
                            <th>Order</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${copies}" var="copy">
                        <tr>
                            <td><c:out value="${copy.publicationYear}"/></td>
                            <td><c:out value="${copy.publishingHouse}"/></td>
                            <td><c:out value="${copy.available ? 'Yes': 'No'}"/></td>

                            <c:if test="${user == 'librarian'}">
                                <td>
                                    <c:if test="${copy.available}">
                                        <form action="" class="needs-validation" novalidate>
                                            <input type="hidden" name="copy_id" value="${copy.id}">
                                            <div class="form-check-inline">
                                                <select class="form-control form-check-input" name="user_select" required>
                                                    <option hidden disabled selected value></option>
                                                    <c:forEach items="${users}" var="user">
                                                        <option value="${user.id}"><c:out value="${user.id} ${user.firstname} ${user.lastName}"/></option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <button type="submit" class="btn btn-outline-success">Order</button>
                                        </form>
                                    </c:if>
                                </td>
                            </c:if>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</c:if>
<c:if test="${not empty error}">
    <h2>error</h2>
</c:if>

</body>
</html>
