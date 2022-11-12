package shop;

import java.text.DecimalFormat;

public class Product {
    //variables from each product from the database
    public String PID;
    public String artist;
    public String title;
    public String description;
    public int price;
    public String thumbnail;
    public String fullimage;

    //constructor for creating an object from the database
    public Product(String PID, String artist, String title, String description, int price, String thumbnail, String fullimage) {
        this.PID = PID;
        this.artist = artist;
        this.title = title;
        this.description = description;
        this.price = price;
        this.thumbnail = thumbnail;
        this.fullimage = fullimage;
    }

    //overwrites toString() method to return the product title and price
    public String toString() {
        return title + "\t " + price;
    }

    //returns product price in decimal format
    public String getPrice() {
        //temp variable storing price
        double temp = price;
        //dividing price by 100 as current price is in pence
        double total = temp / 100;
        //creates a new format to use for the string
        DecimalFormat df = new DecimalFormat("#0.00");
        //returns a string in the format "0.00"
        return String.valueOf(df.format(total));
    }

}