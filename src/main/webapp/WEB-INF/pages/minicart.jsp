<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<a href="${pageContext.request.contextPath}/cart" class="minicart">
    <p>Cart: ${totalQuantity} items </p>
    <p>Cost: ${totalCost}$ </p>
</a>