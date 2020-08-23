<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="attribute" required="true" %>
<%@ attribute name="order" required="true" type="com.es.phoneshop.model.order.Order" %>
<%@ attribute name="errors" required="true" type="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
    <c:set var="error" value="${errors[attribute]}"/>
    <td>${name}<span style="color: red">*</span></td>
    <td>
        <input name="${attribute}"
               value="${not empty error ? param[attribute] : order[attribute]}" required>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
    </td>
</tr>