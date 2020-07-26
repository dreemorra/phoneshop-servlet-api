<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<c:if test="${not empty recentlyViewedProducts}">
    <div style="margin-top: 5%">
        <h2>Recently viewed</h2>
        <div class="viewed-items">
            <c:forEach var="product" items="${recentlyViewedProducts}">
            <div class="viewed-item">
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                <p><a href="${pageContext.request.contextPath}/products/${product.id}">${product.description}</a></p>
                <p><fmt:formatNumber value="${product.price}" type="currency"
                                                   currencySymbol="${product.currency.symbol}"/></p>
            </div>
            </c:forEach>
        </div>
    <div>
</c:if>