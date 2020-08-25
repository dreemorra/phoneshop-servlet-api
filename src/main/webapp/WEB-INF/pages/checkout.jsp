<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">

  <div class="product-list">
      <c:choose>
          <c:when test="${not empty error}">
              <p class="error-message">${error}</p>
          </c:when>
          <c:when test="${not empty param.message}">
              <p class="success-message">${param.message}</p>
          </c:when>
      </c:choose>
      <form method="post" action="${pageContext.request.contextPath}/checkout">
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
          <tags:orderFormField name="First name" attribute="firstName" order="${order}" errors="${errors}"></tags:orderFormField>
          <tags:orderFormField name="Last name" attribute="lastName" order="${order}" errors="${errors}"></tags:orderFormField>
          <tags:orderFormField name="Phone" attribute="phone" order="${order}" errors="${errors}"></tags:orderFormField>
          <tags:orderFormField name="Delivery address" attribute="deliveryAddress" order="${order}" errors="${errors}"></tags:orderFormField>
          <tr>
                <td>Delivery date
                    <span style="color: red;">*</span>
                </td>
                <td><input type="date" name="deliveryDate" required style="width: 25%; height: 10px"></td>
          </tr>
          <tr>
                <td>Payment method
                    <span style="color: red">*</span>
                </td>
                <td>
                    <select name="paymentMethod" style="width: 25%">
                        <option></option>
                        <c:forEach var="paymentMethod" items="${paymentMethods}">
                            <option>${paymentMethod}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty errors['paymentMethod']}">
                        <p class="error">${errors['paymentMethod']}</p>
                    </c:if>
                </td>
          </tr>
          </table>
          <button class="default-button">Place order</button>
      </form>
      <form id="deleteCartItem" method="post"></form>
  </div>
  <script src="${pageContext.servletContext.contextPath}/scripts/popupScript.js"></script>
</tags:master>