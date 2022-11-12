package shop;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class ShopDB {

    //variable for database connection
    Connection con;
    //int for storing the number of orders, initially set to 0
    static int nOrders = 0;
    //defines singleton instance of "ShopDB"
    static ShopDB singleton;

    //constructor for shop database
    public ShopDB() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("loaded class");
            con = DriverManager.getConnection("jdbc:hsqldb:C:\\Users\\fredd\\OneDrive\\Documents\\GitHub\\university-projects\\ce212-web-application-programming\\art-shop-web-application\\web-application\\shopdb", "sa", "");
            System.out.println("created con");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public static ShopDB getSingleton() {
        if (singleton == null) {
            singleton = new ShopDB();
        }
        return singleton;
    }

    //returns all products within the database as a set to the console
    public ResultSet getProducts() {
        try {
            //creates sql statement connection
            Statement s = con.createStatement();
            System.out.println("Created statement");
            //result set equal to the supplied query executed from "s"
            ResultSet rs = s.executeQuery("Select * from Product");
            System.out.println("Returning result set...");
            //returns the result set
            return rs;
        } catch (Exception e) {
            System.out.println("Exception in getProducts(): " + e);
            //returns null
            return null;
        }
    }

    //supplies the necessary query to retrieve all products from the database
    public Collection<Product> getAllProducts() {
        return getProductCollection("Select * from Product");
    }

    //returns the information from a product with a provided PID
    public Product getProduct(String pid) {
        try {
            //returns all products matching the provided PID (should only be 1)
            String query = "Select * from Product where PID = '" + pid + "'";
            //collection of products is created using the results from the query
            Collection<Product> c = getProductCollection(query);
            Iterator<Product> i = c.iterator();
            //returns the information on the product
            return i.next();
        } catch (Exception e) {
            // unable to find the product matching that pid
            return null;
        }
    }

    //returns a list of products found using a supplied query
    public Collection<Product> getProductCollection(String query) {
        //list of products created
        LinkedList<Product> list = new LinkedList<Product>();
        try {
            //creates sql statement connection
            Statement s = con.createStatement();
            //result set equal to the supplied query executed from "s"
            ResultSet rs = s.executeQuery(query);
            //whilst an item is within "rs"
            while (rs.next()) {
                //new product created from the columns of the data from the database
                Product product = new Product(
                        rs.getString("PID"),
                        rs.getString("artist"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("thumbnail"),
                        rs.getString("fullimage"));
                //adds the product to the list
                list.add(product);
            }
            //returns product list
            return list;
        } catch (Exception e) {
            System.out.println("Exception in getProducts(): " + e);
            //returns null
            return null;
        }
    }

    //method for providing data to be added to the "orders" table
    public void order(Basket basket, String customer) {
        try {
            //creates a unique orderID
            String orderId = System.currentTimeMillis() + ":" + nOrders++;
            //for each product entry (product and quantity) within the basket
            for (Map.Entry<Product, Integer> product : basket.getBasket().entrySet()) {
                //parses data to the "order(Connection, Product, Integer, String, String)" method
                order(con, product.getKey(), product.getValue(), orderId, customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //method for adding the data to the "orders" table using a prepared statement
    private void order(Connection con, Product p, Integer quant, String orderId, String customer) throws Exception {
        //creates a prepared statement
        PreparedStatement s = con.prepareStatement("INSERT INTO orders VALUES (?, ?, ?, ? ,?)");
        //sets each "?" to the correct data (first "?" is set to PID...)
        s.setString(1, p.PID);
        s.setString(2, orderId);
        s.setString(3, customer);
        s.setDouble(4, quant);
        s.setDouble(5, p.price);
        //executes the prepared statement
        s.executeUpdate();
    }

    //method for filtering the database by artist name
    public Collection<Product> searchArtist(String name) {
        //creates a string version of a statement for filtering by artist name
        String s = "Select * from Product where " + "LOWER( ARTIST ) like LOWER ( '%" + name + "%' )";
        //parses the statement through "getProductCollection"
        return getProductCollection(s);
    }

    //method for filtering the database by price
    public Collection<Product> searchPrice(int lowerBound, int upperBound) {
        //creates a string version of a statement for filtering by price range
        String s = "Select * from Product where PRICE" + " >= " + lowerBound + " and " + "PRICE <= " + upperBound;
        //parses the statement through "getProductCollection"
        return getProductCollection(s);
    }
}