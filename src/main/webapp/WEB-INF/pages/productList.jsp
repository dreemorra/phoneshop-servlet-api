<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <form class="search-form">
    <input id="search-input" name="query" type="text" value="${param.query}" placeholder="Search">
    <input id="search-button" class="default-button" type="submit" value="Search">
  </form>
  <div class="product-list">
      <table>
        <thead>
          <tr>
            <td>Image</td>
            <td class="description">
                Description
                <tags:sortLink sort="DESCRIPTION" order="ASC" query="${param.query}"/>
                <tags:sortLink sort="DESCRIPTION" order="DESC" query="${param.query}"/>
            </td>
            <td>Quantity</td>
            <td class="price">
                Price
                <tags:sortLink sort="PRICE" order="ASC" query="${param.query}"/>
                <tags:sortLink sort="PRICE" order="DESC" query="${param.query}"/>
            </td>
            <td></td>
          </tr>
        </thead>
        <c:forEach var="product" items="${products}">
          <tr>
            <td>
              <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td class="description">
                <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                    ${product.description}
                </a>
            </td>
            <td>
                <input name="quantity" type="number" value="${not empty param.quantity ? param.quantity : 1}" min="1" max="${product.stock}" style="width: 20%;" id="input-${product.id}">
            </td>
            <td class="price">
              <div class="popup">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
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
                        <c:forEach var="price" items="${product.priceList}">
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
                <button id="button-${product.id}"
                            formaction="${pageContext.request.contextPath}/products/${product.id}?returnMainPage=true&quantity=1"
                            form="addToCartForm">Add to cart
                </button>
                <c:if test="${param.id == product.id}">
                    <c:choose>
                        <c:when test="${not empty param.error}">
                            <p class="error">${param.error}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="success">${param.message}</p>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </td>
          </tr>
        </c:forEach>
      </table>
      <jsp:include page="recentlyViewed.jsp" />
  </div>
  <form id="addToCartForm" method="post"></form>
  <script src="${pageContext.servletContext.contextPath}/scripts/popupScript.js"></script>
  <script src="${pageContext.servletContext.contextPath}/scripts/postQuantity.js"></script>
</tags:master>