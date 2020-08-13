<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
  <link href='${pageContext.servletContext.contextPath}/styles/popup.css' rel='stylesheet' type='text/css'>
</head>
<body>
  <header>
    <a href="${pageContext.servletContext.contextPath}" style="display:flex;">
      <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop
    </a>
    <jsp:include page="/minicart"/>
  </header>
  <main>
      <div class="main-container">
        <jsp:doBody/>
      </div>
  </main>
  <footer class="footer">
      <nav> </nav>
      <div id="footer-div">
          <a href="mailto:s.satturn@gmail.com">Bug report</a>
          <p>(c) Expert Soft, 2020</p>
      </div>
  </footer>
</body>
</html>