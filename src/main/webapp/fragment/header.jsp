<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<header>
    <nav class="navbar navbar-dark bg-dark">
        <ul class="nav mr-auto">
            <li class="nav-item">
                <a class="nav-link text-white" href="<c:url value="/schedule"/>">Schedule</a>
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
            <li class="nav-item ml-auto">
                <a class="nav-link text-white" href="#">Logout</a>
            </li>
        </ul>
    </nav>
</header>
</html>
