<jsp:useBean id='basket' scope='session' class='shop.Basket'/>
<jsp:useBean id='db' class='shop.ShopDB'/>
<jsp:include page="header.jsp"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/order.css">
</head>
<body>
<%
    String custName = request.getParameter("name");
    if (custName != null) {
        db.order(basket, custName);
        basket.clearBasket();
%>
<div id="inName">
    <h2>Thank you for your order, <%= custName %>!</h2>
    <h4><a href="index.jsp"> <b>&#8592;</b> Return to products </a></h4>
</div>
<%
} else {
%>
<div id="noName">
    <h2>A name is required for your order!</h2>
    <h4><a href="basket.jsp"> <b>&#8592;</b> Return to basket </a></h4>
</div>
<%
    }
%>
</body>
</html>