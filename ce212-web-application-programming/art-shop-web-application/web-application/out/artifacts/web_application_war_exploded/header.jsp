<%@ page import="shop.Product" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id='basket' scope='session' class='shop.Basket'/>
<html>
<head>
    <!-- fonts used in this project -->
    <link href="https://fonts.googleapis.com/css?family=Lora:400,700|Montserrat:300" rel="stylesheet">
    <link href="css/header.css" rel="stylesheet">
    <title>CE291 Art</title>
</head>
<body onload="active()">
<nav>
    <ul>
        <%
            int count = 0;
            for (Product product : basket.getBasket().keySet()) {
                count += basket.getQuant(product);
            }
        %>
        <li id="ba"><a href="basket.jsp">Basket (<%= count %>)</a></li>
        <li id="in"><a href="index.jsp">Products</a></li>
        <li id="logo" onclick="shopInfo()"><b>CE212 Art</b></li>
        <script>
            function shopInfo() {
                alert("'Art Shop' Assignment for Module CE212.\n\n" +
                    "Author: Fred Knight\n" +
                    "RegNo: 1804162");
            }
        </script>
    </ul>
</nav>
</body>
</html>