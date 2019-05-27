<%--
  Created by IntelliJ IDEA.
  User: Saturn
  Date: 27.05.2019
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!-- Navbar -->
        <div class="w3-top">
            <div class="w3-bar w3-theme-d2 w3-left-align">
                <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-hover-white w3-theme-d2"
                   href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
                <a href="${pageContext.request.contextPath}/" class="w3-bar-item w3-button w3-teal"><i class="fa fa-home w3-margin-right"></i>Home</a>
                <a href="${pageContext.request.contextPath}/books" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Books</a>
                <a href="${pageContext.request.contextPath}/users" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Users</a>

                <a href="searchPage" class="w3-bar-item w3-button w3-right w3-hover-white" title="Search">
                    <img src="${pageContext.request.contextPath}/resources/images/icons8-google-web-search-30.jpg"></a>

                <c:if test="${empty sessionScope.user}">
                    <button type="button" class="w3-bar-item w3-button w3-right w3-hide-small w3-hover-white" data-toggle="modal" data-target="#loginModal">
                        Login
                    </button>
                </c:if>
                <c:if test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/logout" class="w3-bar-item w3-button w3-right w3-hide-small w3-hover-white">Logout</a>
                    <a class="w3-bar-item w3-right w3-hide-small">
                        <c:out value="${sessionScope.user.userName}"/> - <c:out value="${sessionScope.user.contact_type_id.name}"/>
                    </a>
                </c:if>

            </div>

        </div>
<jsp:include page="loginModal.jsp"></jsp:include>
