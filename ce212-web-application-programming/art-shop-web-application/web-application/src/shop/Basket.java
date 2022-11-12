package shop;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    //variables for the database connection, and to create the map
    ShopDB db;
    Map<Product, Integer> basket;

    //constructor for the basket connection and map
    public Basket() {
        //gets database connection
        db = ShopDB.getSingleton();
        //creates new map
        basket = new HashMap<Product, Integer>();
    }

    //returns the map "basket"
    public Map<Product, Integer> getBasket() {
        return basket;
    }

    //clears all products within the map "basket"
    public void clearBasket() {
        basket.clear();
    }

    //calls method "addItem(Product)" to check and add the provided product
    public void addItem(String pid) {
        //sets "p" to product with the matching PID from the database
        Product p = db.getProduct(pid);
        //parses "p" through "addItem(Product)"
        addItem(p);
    }

    //validates that product is found, and if it is already in the basket
    public void addItem(Product p) {
        //boolean for determining if product is present
        Boolean found = false;
        //int for storing the current products quantity in the basket
        int quant = 0;
        //placeholder product for incrementing the product quantity in the basket
        Product increaseQuant = null;
        //if product is in the database
        if (p != null) {
            //for each product in the basket
            for (Product product : basket.keySet()) {
                //"thisPID" is set to the current products PID
                String thisPID = product.PID;
                //if "thisPID" is equal to the PID of the provided product
                if (thisPID.equals(p.PID)) {
                    //the product is within the basket
                    found = true;
                    //for each product "i" in basket
                    for (Product i : basket.keySet()) {
                        //"iPID" is set to the current products PID
                        String iPID = i.PID;
                        //temporary product set to the current product
                        increaseQuant = i;
                        //if "iPID" is equal to the PID of the provided product
                        if (iPID.equals(p.PID)) {
                            //"quant" is set to the current quantity of the product in the basket
                            quant = basket.get(i);
                        }
                    }
                    //increment the quantity of the product in the basket
                    basket.put(increaseQuant, quant + 1);
                }
            }
            //if product is not currently in the basket
            if (!found) {
                //add the product to the basket with a quantity of 1
                basket.put(p, 1);
            }
        }
    }

    //returns the total price of the products in the basket
    public int getTotal() {
        //int for storing the total price
        int total = 0;
        //for each product in the basket
        for (Product product : basket.keySet()) {
            //total is increased by the current products quantity, multiplied by its price
            total += (getQuant(product) * product.price);
        }
        //returns the total
        return total;
    }

    //returns the total price of the products in the basket in correct decimal format
    public String getTotalString() {
        //temp double variable to store the price from the "getTotal()" method
        double temp = getTotal();
        //returns conversion
        return convertToDF(temp);
    }

    //returns the quantity of the provided product in the basket
    public Integer getQuant(Product p) {
        return basket.get(p);
    }

    //returns the total price of provided product and its quantity in the basket
    public String getProductTotal(Product p) {
        //temp double variable for storing the provided products price, multiplied by its quantity in the basket
        double temp = (double) p.price * getQuant(p);
        //returns conversion
        return convertToDF(temp);
    }

    //converts provided price to a price in correct format
    public String convertToDF(double temp) {
        //dividing price by 100 as current price is in pence
        double total = temp / 100;
        //creates a new format to use for the string
        DecimalFormat df = new DecimalFormat("#0.00");
        //returns a string in the format "0.00"
        return String.valueOf(df.format(total));
    }

    //method for removing product from the basket
    public void removeProduct(String thisPID) {
        //for each product within the basket
        for (Product i : basket.keySet()) {
            //if "thisPID" is equal to the PID of the provided product
            if (thisPID.equals(i.PID)) {
                //remove product from the basket
                basket.remove(i);
            }
        }
    }

    //method for increasing the quantity of a product in the basket
    public void increaseQuant(String thisPID) {
        //int for storing the current products quantity in the basket
        int quant;
        //for each product inside the basket
        for (Product product : basket.keySet()) {
            //"quant" is set to the current products quantity
            quant = basket.get(product);
            //if "thisPID" is equal to the PID of the provided product
            if (thisPID.equals(product.PID)) {
                //increment the quantity of the product in the basket
                basket.put(product, quant + 1);
            }
        }
    }

    //method for decreasing the quantity of a product in the basket
    public void decreaseQuant(String thisPID) {
        //int for storing the current products quantity in the basket
        int quant;
        //for each product inside the basket
        for (Product product : basket.keySet()) {
            //"quant" is set to the current products quantity
            quant = basket.get(product);
            //if "thisPID" is equal to the PID of the provided product
            if (thisPID.equals(product.PID)) {
                //if the quantity of the product is more than 1
                if (quant > 1) {
                    //decrement the quantity of the product in the basket
                    basket.put(product, quant - 1);
                } else {
                    //remove product from the basket
                    removeProduct(product.PID);
                }
            }
        }
    }
}