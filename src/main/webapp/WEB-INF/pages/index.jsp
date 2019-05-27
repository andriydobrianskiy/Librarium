<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/w3.css">
    <script src="${pageContext.request.contextPath}/resources/js/jQueryValidator.js"></script>
    <title>Librarium</title>
</head>

<body id="homePage">
<div id="header">
<%-- include header and navigation bar --%>
<jsp:include page="/WEB-INF/header.jsp"></jsp:include>
    <!-- Image Header -->
    <div class="w3-display-container w3-animate-opacity">
        <img src="${pageContext.request.contextPath}/resources/images/librarium-home-page-demo.jpg" alt="boat"
             style="width:100%;min-height:350px;max-height:600px;">
    </div>
</div>

<!-- Work Row -->
<div class="w3-row-padding w3-padding-large w3-theme-l5" id="work">
    <h1 class="w3-wide w3-padding-large w3-center">LIBRARIUM</h1>
    <div class="w3-twothird w3-padding-large" style="font-style: italic;">
        <h3>The library of your dream developed by Andrii Dobrianskyi, Olha Lozinska,
            Volodymyr Oseredchuk. Here you can find the book for every taste by picking
            it up by name, author and rating. On the LIBRARIUM website, you can see if
            the book you have selected is available for order, then order this book and
            enjoy it for a month.</h3>
    </div>

    <div>
        <div class="w3-third  w3-center w3-white">
            <div class="w3-container w3-padding-large w3-theme-l5" style="font-style: italic;">
                <h2>Statistical info</h2>
                <p>${BooksQuantityInIndependencePeriod} books publicated in Independence period.</p>
                <p>Average age of ours readers is ${AverageReaderAge} years.</p>
                <p>Average time of using Librarium is ${AverageTimeOfUsingLibrary} days.</p>
                <p>Total quantity of orders is ${QuantityOfOrdersInAllPeriod}.</p>
            </div>
        </div>
    </div>
</div>

<!-- The Contacts Section -->
<div class="w3-container w3-theme-l3 w3-padding-64"
     style="width: 100%" id="contacts">
    <h2 class="w3-wide w3-left-align">CONTACTS</h2>
    <div class="w3-row w3-padding-32">
        <div class="w3-col m6 w3-large">

            <i class="fa fa-map-marker" style="width: 30px"></i> Lviv, UA<br>
            <i class="fa fa-phone" style="width: 30px"></i> Phone: +30 867 67 3409<br>
            <i class="fa fa-envelope" style="width: 30px"> </i> Email:
            info@softserveinc.com<br>

        </div>
        <div class="w3-col m6 w3-large">

            <i class="fa fa-working-hours" style="width: 30px"></i> Working hours: 10:00-20:00<br>
            <p></p>
            <i class="fa fa-welcome" style="width: 30px"></i> Welcome!<br>
        </div>
    </div>

</div>

<%-- include footer --%>
<jsp:include page="/WEB-INF/footer.jsp"></jsp:include>

</body>
</html>
