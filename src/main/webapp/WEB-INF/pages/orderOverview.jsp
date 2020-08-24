<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Order overview">

  <h1 style="margin: 5% 5% 0 5%; text-align: center">Order overview</h1>
  <div class="product-list">
      <table>
        <thead>
          <tr>
            <td>Image</td>
            <td class="description">Description</td>
            <td>Quantity</td>
            <td class="price">Price</td>
          </tr>
        </thead>
        <c:forEach var="item" items="${order.items}">
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
                ${item.quantity}
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
          </tr>
        </c:forEach>
        <tr class="price">
            <td></td>
            <td></td>
            <td>Subtotal</td>
            <td>
                <fmt:formatNumber value="${order.subtotal}" type="currency"
                                  currencySymbol="${order.currency.symbol}"/>
            </td>
        </tr>
        <tr class="price">
            <td></td>
            <td></td>
            <td>Delivery cost</td>
            <td>
                <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                  currencySymbol="${order.currency.symbol}"/>
            </td>
        </tr>
        <tr class="price">
            <td></td>
            <td></td>
            <td>Total cost</td>
            <td>
                <fmt:formatNumber value="${order.totalCost}" type="currency"
                                  currencySymbol="${order.currency.symbol}"/>
            </td>
        </tr>
      </table>
      <h2 style="padding: 20px;">Your details</h2>
      <table>
          <tags:orderOverviewField name="First name" attribute="firstName" order="${order}"></tags:orderOverviewField>
          <tags:orderOverviewField name="Last name" attribute="lastName" order="${order}" ></tags:orderOverviewField>
          <tags:orderOverviewField name="Phone" attribute="phone" order="${order}" ></tags:orderOverviewField>
          <tags:orderOverviewField name="Delivery address" attribute="deliveryAddress" order="${order}" ></tags:orderOverviewField>
          <tags:orderOverviewField name="Delivery date" attribute="deliveryDate" order="${order}" ></tags:orderOverviewField>
          <tags:orderOverviewField name="Payment method" attribute="paymentMethod" order="${order}" ></tags:orderOverviewField>
      </table>
  </div>
  <script src="${pageContext.servletContext.contextPath}/scripts/popupScript.js"></script>
</tags:master>