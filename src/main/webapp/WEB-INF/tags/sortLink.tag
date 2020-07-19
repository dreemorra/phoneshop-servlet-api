<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>
<%@ attribute name="query" required="true" %>

<a href="?sort=${sort}&order=${order}&query=${query}" >${order}</a>