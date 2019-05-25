<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="resources/css/w3.css">
    <title>Librarium</title>
</head>

<body id="homePage">
<div id="header">

    <!-- Navbar -->
    <div class="w3-top">
        <div class="w3-bar w3-theme-d2 w3-left-align">
            <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-hover-white w3-theme-d2"
               href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
            <a href="#" class="w3-bar-item w3-button w3-teal"><i class="fa fa-home w3-margin-right"></i>Home</a>
            <a href="books" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Books</a>
            <a href="#users" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Users</a>

            <a href="#" class="w3-bar-item w3-button w3-hide-small w3-right w3-hover-teal" title="Search"><i
                    class="fa fa-search"></i></a>
        </div>

        <!-- Navbar on small screens -->
        <div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium">
            <a href="#books" class="w3-bar-item w3-button">Books</a>
            <a href="#users" class="w3-bar-item w3-button">Users</a>
            <a href="#" class="w3-bar-item w3-button">Search</a>
        </div>
    </div>

    <!-- Image Header -->
    <div class="w3-display-container w3-animate-opacity">
        <img src="../../resources/images/librarium-home-page-demo.jpg" alt="boat" style="width:100%;min-height:350px;max-height:600px;">
    </div>


</div>

<!-- Team Container -->
<div class="w3-container w3-padding-64 w3-center" id="team">
    <h2>OUR TEAM</h2>
    <p>Meet the team - our office rats:</p>

    <div class="w3-row"><br>

        <div class="w3-quarter">
            <img src="/w3images/avatar.jpg" alt="Boss" style="width:45%" class="w3-circle w3-hover-opacity">
            <h3>Johnny Walker</h3>
            <p>Web Designer</p>
        </div>

        <div class="w3-quarter">
            <img src="/w3images/avatar.jpg" alt="Boss" style="width:45%" class="w3-circle w3-hover-opacity">
            <h3>Rebecca Flex</h3>
            <p>Support</p>
        </div>

        <div class="w3-quarter">
            <img src="/w3images/avatar.jpg" alt="Boss" style="width:45%" class="w3-circle w3-hover-opacity">
            <h3>Jan Ringo</h3>
            <p>Boss man</p>
        </div>

        <div class="w3-quarter">
            <img src="/w3images/avatar.jpg" alt="Boss" style="width:45%" class="w3-circle w3-hover-opacity">
            <h3>Kai Ringo</h3>
            <p>Fixer</p>
        </div>

    </div>
</div>

<div id="main">
    Books in period of independence = ${BooksQuantityInIndependencePeriod}
    Average reader age = ${AverageReaderAge}
    Average Time Of Using Library = ${AverageTimeOfUsingLibrary}
</div>

<div id="footer">
    <%--<jsp:include page="common/footer.jsp"/>--%>
    <hr/>
    <div style="text-align: center;">
        <p>SoftServe 2019</p>
    </div>
</div>
</body>
</html>