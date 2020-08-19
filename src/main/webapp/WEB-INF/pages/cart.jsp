<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">

  <form class="search-form">
    <input id="search-input" name="query" type="text" value="${param.query}" placeholder="Search">
    <input id="search-button" class="default-button" type="submit" value="Search">
  </form>
  <div class="product-list">
      <c:choose>
          <c:when test="${not empty error}">
              <p class="error-message">${error}</p>
          </c:when>
          <c:when test="${not empty param.message}">
              <p class="success-message">${param.message}</p>
          </c:when>
      </c:choose>
      <form method="post" action="${pageContext.request.contextPath}/cart">
          <table>
            <thead>
              <tr>
                <td>Image</td>
                <td class="description">Description</td>
                <td>Quantity</td>
                <td class="price">Price</td>
                <td></td>
              </tr>
            </thead>
            <c:forEach var="item" items="${cart.items}">
              <tr>
                <td>
                  <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                </td>
                <td class="description">
                    <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                        ${item.product.description}
                    </a>
                </td>
                <td>
                    <input name="quantity" type="number" value="${not empty paramValues["quantity"][item.product.id]
                                                                                        ? paramValues["quantity"][item.product.id]
                                                                                        : item.quantity}" min="1" max="${item.product.stock + item.quantity}" style="width: 20%;">
                    <input type="hidden" value="${item.product.id}" name="productId" >
                    <c:if test="${not empty errors[item.product.id.longValue()]}">
                        <p class="error">${errors[item.product.id.longValue()]}</p>
                    </c:if>
                </td>
                <td class="price">
                  <div class="popup">
                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                    <span class="popuptext">
                      <h3>Price history</h3>
                      <p>${product.description}<p>
                        <table>
                            <thead>
                                <tr>
                                    <td>Start date</td>
                                    <td>Price</td>
                                </tr>
                            </thead>
                            <c:forEach var="price" items="${item.product.priceList}">
                                <tr>
                                    <td><fmt:formatDate value="${price.date}" pattern="dd MMM yyyy"/></td>
                                    <td><fmt:formatNumber value="${price.price}" type="currency"
                                        currencySymbol="${price.currency.symbol}"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </span>
                  </div>
                </td>
                <td>
                    <button form="deleteCartItem" formaction="${pageContext.request.contextPath}/cart/deleteCartItem/${item.product.id}">Delete</button>
                </td>
              </tr>
            </c:forEach>
          </table>
          <button class="default-button">Update</button>
      </form>
      <form id="deleteCartItem" method="post"></form>
  </div>
  <script src="${pageContext.servletContext.contextPath}/scripts/popupScript.js"></script>
</tags:master>