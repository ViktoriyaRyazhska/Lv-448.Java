<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="author-select-wrapper">
    <select class="browser-default custom-select author-select mt-3" id="exhibit-author-${counter}" name="author-${counter}">
        <c:forEach var="author" items="${authors}">
            <option value="${author.id}">${author.firstName} ${author.lastName}</option>
        </c:forEach>
    </select>
    <div class="btn btn-dark ml-1 mt-3 delete-more-authors"><i class="fas fa-minus"></i></div>
</div>

