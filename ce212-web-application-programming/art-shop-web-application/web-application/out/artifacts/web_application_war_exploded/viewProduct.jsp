<%@ page import="shop.Product" %>
<jsp:useBean id='db' scope='session' class='shop.ShopDB'/>
<jsp:include page="header.jsp"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/viewProduct.css">
</head>
<body>
<%
    String pid = request.getParameter("pid");
    Product product = db.getProduct(pid);
    if (product == null) {
%>
<div id="noPID">
    <h2>Product not found!</h2>
    <h4><a href="index.jsp"><b>&#8592;</b> Return to products </a></h4>
</div>
<%
} else {
%>
<div class="productInfo">
    <h2><%= product.title %> by <%= product.artist %>
        <span style="font-size: small"> Price &pound;<%= product.getPrice() %></span></h2>
    <p><%= product.description %>
    </p><br>
    <h4><a href='<%="basket.jsp?addItem="+product.PID %>'><b>&#8594;</b> Add to basket </a></h4>
    <h4><a href="index.jsp"><b>&#8592;</b> Return to products </a></h4>
</div>/
<div class="product">
    <img src="<%= product.fullimage %>" alt="product"/>
</div>
<%
    }
%>
</body>
</html>