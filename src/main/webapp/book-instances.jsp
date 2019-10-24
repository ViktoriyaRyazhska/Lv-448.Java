<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<div class="book-instance-wrapper">
    <label>Choose book instance</label>
    <select class="browser-default custom-select mt-3" id="select-book-instance" name="bookInstance">
        <c:forEach var="bookInstance" items="${instances}">
            <option value="${bookInstance.id}">Instance SN: ${bookInstance.id}</option>
        </c:forEach>
    </select>
</div>
</html>
