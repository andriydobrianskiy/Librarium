
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <title>Users</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jQueryValidator.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/w3.css">

    <style>
        #searchLine {
            padding: 20px;
        }
        #configurator {
            margin: 20px;
            padding: 20px;
            border: 1px solid rgba(111, 111, 111, 0.76);
            border-radius: 5px;
        }
    </style>
</head>
<body style="background-image: url('${pageContext.request.contextPath}/resources/images/wallpaper.jpg');">

<div id="header">
    <%-- include header and navigation bar --%>
    <jsp:include page="/WEB-INF/header.jsp"></jsp:include>
</div>
<div class="container-fluid">

    <div class="row" style="margin-top: 40px;" id="configurator">

        <div class="col-md-8 offset-2">
            <form method="post" class="form-inline needs-validation" action="${pageContext.request.contextPath}/users" novalidate>

                <button type="submit" class="btn btn-outline-success mb-2">Search people overdue</button>
                <a href="${pageContext.request.contextPath}/users" class="btn btn-outline-danger mb-2" style="margin-left: 20px;">Reset</a>
            </form>
        </div>
    </div>

    <div class="row">


        <div class = "col-md-12 d-flex" >

            <table class="table table-striped table-bordered table-hover">
                <thead class="thead-dark">
                <tr> <th>First name</th>
                    <th>Last Name</th>
                    <th>created_at</th>
                    <th>phone</th>
                    <th>address</th>
                    <th>birthday_date</th>
                    <th>Actions</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.firstname}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.createdAt}"/></td>
                        <td><c:out value="${user.phone}"/></td>
                        <td><c:out value="${user.address}"/></td>
                        <td><c:out value="${user.birthday_date}"/></td>
                        <td>
                            <div class = "col-md-3 d-flex" id="usersCards">

                                    <a href="${pageContext.request.contextPath}/user/${user.id}" style="float:right" class="btn btn-primary stretched-link">Details</a>
                            </div>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>


    </div>
</div>
<script>
    $(document).ready(function(){
        $("#searchLine").on("keyup", function() {
            var value = $(this).val().toLowerCase();
            $("#usersCards div:first-child").filter(function() {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
            });
        });
        <c:if test="${not empty unpopularFirst}">
        $("#unpopularFirst").prop('checked', true);
        </c:if>
    });
</script>

<c:if test="${empty users}">
        <div class="container-fluid">
            <div class="row">
                <div class = "col-md-12" >
                    <h2 class="text-center">There are no users</h2>
                </div>
            </div>
        </div>
    </c:if>
</div>


</head>
</div>


<%-- include footer --%>
<jsp:include page="/WEB-INF/footer.jsp"></jsp:include>
</body>
</html>
