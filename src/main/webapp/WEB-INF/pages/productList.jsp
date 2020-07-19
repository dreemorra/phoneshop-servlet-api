<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
    <input name="query" type="text" value="${param.query}">
    <input type="submit" value="Search">
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
            Description
            <tags:sortLink sort="description" order="asc" query="${param.query}"/>
            <tags:sortLink sort="description" order="desc" query="${param.query}"/>
        </td>
        <td class="price">
            Price
            <tags:sortLink sort="price" order="asc" query="${param.query}"/>
            <tags:sortLink sort="price" order="desc" query="${param.query}"/>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                ${product.description}
            </a>
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
      </tr>
    </c:forEach>
  </table>
  <script src="${pageContext.servletContext.contextPath}/scripts/popupScript.js"></script>
</tags:master>