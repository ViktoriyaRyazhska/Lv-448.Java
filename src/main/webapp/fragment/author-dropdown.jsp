<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<c:forEach var="author" items="${authors}">
    <option value="${author.id}">${author.firstName} ${author.lastName}</option>
</c:forEach>

