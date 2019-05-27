
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>User</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jQueryValidator.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/w3.css">
    <script type="text/javascript">
        function showAlert(){
            alert("Success");
        }
    </script>
    <style>
        .bookStatistics {
            font-size: 21px;
        }
    </style>
</head>
<body style="background-image: url('${pageContext.request.contextPath}/resources/images/wallpaper.jpg');">

<div id="header">
    <%-- include header and navigation bar --%>
    <jsp:include page="/WEB-INF/header.jsp"></jsp:include>
</div>


    <div class="jumbotron jumbotron-fluid" style="margin-top: 20px;">
    <div class="container">
    <h1><c:out value="${user.firstname}"/></h1>
        <h1><c:out value="${user.lastName}"/></h1>
    </div>
    </div>


    <div class="container-fluid">
    <div class="row">
    <div class = "col-md-8" >


    <p class="text-left bookStatistics">
    <strong>Create date:</strong> <c:out value="${user.createdAt}"/>
    </p>
    <p class="text-left bookStatistics">
    <strong>Phone:</strong> <c:out value="${user.phone}"/>
    </p>
    <p class="text-left bookStatistics">
    <strong>address: </strong><c:out value="${user.address}"/> times
    </p>
        <p class="text-left bookStatistics">
            <strong>Uses library services: </strong><c:out value="${daysOfUsingLibraryByUser}"/> times
        </p>



    </div>
    </div>
    </div>
<div class="container-fluid">
    <div class="row">
        <div class = "col-md-12" >
            <h2 class="text-center">All copies by user</h2>
        </div>
    </div>

    <div class="row">
        <div class = "col-md-12 d-flex" >

            <table class="table table-striped table-bordered table-hover">
                <thead class="thead-dark">
                <tr> <th>Name book</th>
                    <th>Publication year</th>
                    <th>Publishing house</th>
                    <th>Orders quantity</th>
                    <th>Available</th>
                    <c:if test="${user1 == 'librarian'}">
                        <th>Order</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${copies}" var="copy">
                    <tr>
                        <td><c:out value="${copy.bookId.name}"/></td>
                        <td><c:out value="${copy.publicationYear}"/></td>
                        <td><c:out value="${copy.publishingHouse}"/></td>
                        <td><c:out value="${copy.ordersQuantity}"/></td>
                        <td><c:out value="${copy.available ? 'Returned': 'Not returned'}"/></td>

                        <c:if test="${user1 == 'librarian'}">
                            <td>
                                <c:if test="${copy.available != true}">
                                    <form method="post" action="${pageContext.request.contextPath}/user/${user.id}" class="needs-validation" novalidate>
                                        <input type="hidden" name="user_id" value="${user.id}">
                                        <input type="hidden" name="copy_id" value="${copy.id}">
                                        <button type="submit" class="btn btn-outline-success">Return</button>
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



<%-- include footer --%>
<jsp:include page="/WEB-INF/footer.jsp"></jsp:include>
</body>
</html>
