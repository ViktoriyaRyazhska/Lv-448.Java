<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand text-primary" href="<c:url value="/"/>">MUSEUM</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link text-white" href="<c:url value="/schedule"/>">Schedule<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="<c:url value="/excursions"/>">Excursions</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="<c:url value="/exhibits"/>">Exhibits</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="<c:url value="/employees"/>">Employees</a>
                </li>
            </ul>
            <ul class="nav ml-auto">
                <%--            <li class="nav-item ml-auto">--%>
                <%--                <a class="nav-link text-white" href="#">Logout</a>--%>
                <%--            </li>--%>
            </ul>
        </div>
    </nav>
</header>
