<%@ page import="shop.Product" %>
<%@ page import="java.util.Collection" %>
<jsp:useBean id='db' scope='session' class='shop.ShopDB'/>
<jsp:include page="header.jsp"/>
<%
    Collection<Product> prods = null;
    String artistName = request.getParameter("artist");
    String lowerBound = request.getParameter("min");
    String upperBound = request.getParameter("max");

    int max = Integer.MAX_VALUE;
    int min = Integer.MIN_VALUE;
    String name = "";
    if (artistName != null) {
        name = artistName;
        try {
            prods = db.searchArtist(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    if (lowerBound != null && lowerBound.length() > 0) {
        if (upperBound != null && upperBound.length() > 0) {
            min = (int) (Double.parseDouble(lowerBound) * 100);
            max = (int) (Double.parseDouble(upperBound) * 100);
        } else {
            min = (int) (Double.parseDouble(lowerBound) * 100);
        }
        try {
            prods = db.searchPrice(min, max);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (upperBound != null && upperBound.length() > 0) {
        max = (int) (Double.parseDouble(upperBound) * 100);
        try {
            prods = db.searchPrice(min, max);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<html>
<head>
    <link href="css/index.css" rel="stylesheet"/>
    <script src="js/index.js"></script>
    <style>
        #in {
            background-color: #333333;
        }
    </style>
</head>
<body>
<div id="filter">
    <h2>Filter Search</h2>
    <form action="index.jsp" method="get" name="searchArtist" onsubmit="return checkInp()">
        <h3>Filter by Artist Name</h3>
        <%
            if (artistName != null) {
        %>
        <input type="text" value="<%= artistName %>" placeholder="enter artist" name="artist" id="artist"
               size="20">
        <%
        } else {
        %>
        <input type="text" placeholder="enter artist" name="artist" id="artist"
               size="20">
        <%
            }
        %>
        <input type="submit" value="Filter by Artist"/>
        <button value="Clear" onclick="document.getElementById('artist').value = ''">Clear</button>
    </form>
    <form action="index.jsp" method="get" name="searchPrice" onsubmit="return checkInp()">
        <h3>Filter by Price Range</h3>
        <%
            if (lowerBound != null) {
        %>
        <input type="text" value="<%= lowerBound %>" placeholder="enter min" name="min" id="min"
               size="20">
        <%
        } else {
        %>
        <input type="text" placeholder="enter min" name="min" id="min"
               size="20">
        <%
            }
        %>
        <div id="arrow"><b>&#8594;</b></div>
        <%
            if (upperBound != null) {
        %>
        <input type="text" value="<%= upperBound %>" placeholder="enter max" name="max" id="max"
               size="20">
        <%
        } else {
        %>
        <input type="text" placeholder="enter max" name="max" id="max"
               size="20">
        <%
            }
        %>
        <input type="submit" value="Filter by Price"/>
        <button value="Clear" onclick="document.getElementById('min').value = '';
                                       document.getElementById('max').value = ''">Clear</button>
    </form>
</div>
<div id="products">
    <div class="grid">
        <%
            Collection<Product> p;
            if (prods != null) {
                p = prods;
            } else {
                p = db.getAllProducts();
            }
            for (Product product : p) {
        %>
        <a id="gridItem" class="transition" href='<%="viewProduct.jsp?pid="+product.PID
         %>'>
            <img src="<%= product.thumbnail %>" alt="preview"/>
            <h6><span style="text-decoration: underline"><%= product.title %></span><br>
                <span style="font-size: smaller">by <%= product.artist %></span><br>
                <span style="font-size: x-small">&pound;<%= product.getPrice() %></span></h6>
        </a>
        <%
            }
        %>
    </div>
    <br><br><br>
</div>
</body>
</html>