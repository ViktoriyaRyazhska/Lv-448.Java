<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Travel agency "Valhalla"</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
    <h2>We welcome you in Valhalla</h2>
    <a href="login.jsp">Sign in</a>
    <a href="registration.jsp">Sign up</a>

    <div>
        <h3>Countries we work in</h3>
        <c:forEach var="country" items="${countries}">
            <h4>${country.country}</h4>
            <ul>
                <c:forEach var="city" items="${country.cities}">
                    <li><a href="hotels/${city.city}?cityId=${city.id}">${city.city}</a></li>
                </c:forEach>
            </ul>
        </c:forEach>
    </div>

    <form id="formId" action="/hotels/search" method="POST">
        <fieldset>
            <legend>Search for a hotel</legend>
            <label>
                <span class="requiredFieldClass" title="Required field">*</span>
            </label>
            <label>
                County: <input type="text" name="countryName" placeholder="For instance, USA" required>
            </label>
            <br>
            <label>
                <span class="requiredFieldClass" title="Required field">*</span>
            </label>
            <label>
                City: <input type="text" name="cityName" placeholder="For instance, Las Vegas" required>
            </label>
            <button type="submit">Search</button>
        </fieldset>
    </form>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</body>
</html>
