<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Details">
  <p style="margin: 20px 0px 10px 20px;">Cart: ${cart}</p>
  <div class="product">
      <div style="display: flex;">
          <div>
              <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
          </div>
          <div class="product-description">
            <h1>${product.description}</h1>
            <form method="post" action="${pageContext.request.contextPath}/products/${product.id}">
                <table>
                    <tr>
                        <td>Code</td>
                        <td>${product.code}</td>
                    </tr>
                    <tr>
                        <td>Stock</td>
                        <td>${product.stock}</td>
                    </tr>
                    <tr>
                        <td>Price</td>
                        <td><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></td>
                    </tr>
                    <tr>
                        <td>Quantity</td>
                        <td><input name="quantity" type="number" value="${not empty param.quantity ? param.quantity : 1}" min="1" max="${product.stock}" style="width: 20%;"></td>
                    </tr>
                </table>
                <div style="display: flex;">
                    <button class="default-button">Add to Cart</button>
                    <c:choose>
                        <c:when test="${not empty error}">
                            <p class="error-message">${error}</p>
                        </c:when>
                        <c:when test="${not empty param.message}">
                            <p class="success-message">${param.message}</p>
                        </c:when>
                    </c:choose>
                </div>
            <form>
          </div>
       </div>
       <jsp:include page="recentlyViewed.jsp" />
  </div>
</tags:master>