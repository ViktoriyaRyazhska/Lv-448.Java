<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/registration.css">
    <title>Login Page</title>
</head>
<body>
<div class="width30">
    <form action="/login" method="POST">
            <div class="form-line">
                <div>
                    <c:forEach var="configParams" items="${messages}">
                        <c:if test="${configParams.key=='email'}">
                            <c:out value="${configParams.value}" />
                        </c:if>
                    </c:forEach>
                </div>
                <label>
                    <span class="required-field-class" title="Required field">*</span>
                </label>
                <label>
                    <b>Email: </b><input type="text" placeholder="Enter Email" name="email" required>
                </label>
            </div>
            <div class="form-line">
                <label>
                    <span class="required-field-class" title="Required field">*</span>
                </label>
                <label>
                    <b>Password: </b><input type="password" placeholder="Enter Password" name="password" required>
                </label>
            </div>
        <button type="submit" class="registerbtn">Sign in</button>
        <div class="container signin">
            <p>Haven't created an account yet? <a href="registration.jsp">Sign up</a>.</p>
        </div>
    </form>
</div>
</body>
</html>