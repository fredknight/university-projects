package car_collection;

import java.util.*;
import java.sql.*;

public class DataConnect {

    private Connection connection;

    public DataConnect() {
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ce29x_fk18726", "[USER]", "[PASSWORD]");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public List<Users> getAllUsers() throws Exception {
        List<Users> list = new ArrayList<>();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.createStatement();
            rs = stat.executeQuery("select * from users order by users.name");
            while (rs.next()) {
                Users tempUser = convertRowToUser(rs);
                list.add(tempUser);
            }
            return list;
        } finally {
            close(stat, rs);
        }
    }

    public List<Users> searchUsers(String name) throws Exception {
        List<Users> list = new ArrayList<>();
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            name += "%";
            stat = connection.prepareStatement("select * from users where users.name like ? order by users.name");
            stat.setString(1, name);
            rs = stat.executeQuery();
            while (rs.next()) {
                Users tempUser = convertRowToUser(rs);
                list.add(tempUser);
            }
            return list;
        } finally {
            close(stat, rs);
        }
    }

    private Users convertRowToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String postcode = rs.getString("postcode");
        Users tempUser = new Users(id, name, postcode);
        return tempUser;
    }

    public List<Cars> getAllCars() throws Exception {
        List<Cars> list = new ArrayList<>();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.createStatement();
            rs = stat.executeQuery("select * from car order by car.manufacturerID");
            while (rs.next()) {
                Cars tempCar = convertRowToCar(rs);
                list.add(tempCar);
            }
            return list;
        } finally {
            close(stat, rs);
        }
    }

    public List<Cars> searchCars(String id) throws Exception {
        List<Cars> list = new ArrayList<>();
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            id += "%";
            stat = connection.prepareStatement("select * from car where car.id = ? order by car.manufacturerID");
            stat.setString(1, id);
            rs = stat.executeQuery();
            while (rs.next()) {
                Cars tempCar = convertRowToCar(rs);
                list.add(tempCar);
            }
            return list;
        } finally {
            close(stat, rs);
        }
    }

    public void createCar(String model, String bodyType, int buildYear, int retailPrice, int manufacturerID) {
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement("insert into car values(default, ?, ?, ?, ?, ?)");
            stat.setString(1, model);
            stat.setString(2, bodyType);
            stat.setInt(3, buildYear);
            stat.setInt(4, retailPrice);
            stat.setInt(5, manufacturerID);
            stat.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Cars convertRowToCar(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String model = rs.getString("model");
        String bodyType = rs.getString("bodyType");
        int buildYear = rs.getInt("buildYear");
        int retailPrice = rs.getInt("retailPrice");
        int manufacturerID = rs.getInt("manufacturerID");
        Cars tempCar = new Cars(id, model, bodyType, buildYear, retailPrice, manufacturerID);
        return tempCar;
    }

    public List<Manufacturers> getAllManufacturers() throws Exception {
        List<Manufacturers> list = new ArrayList<>();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.createStatement();
            rs = stat.executeQuery("select * from manufacturer order by manufacturer.name");
            while (rs.next()) {
                Manufacturers tempManufacturer = convertRowToManufacturer(rs);
                list.add(tempManufacturer);
            }
            return list;
        } finally {
            close(stat, rs);
        }
    }

    public List<Manufacturers> searchManufacturers(String name) throws Exception {
        List<Manufacturers> list = new ArrayList<>();
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            name += "%";
            stat = connection.prepareStatement("select * from manufacturer where manufacturer.name like ? order by manufacturer.name");
            stat.setString(1, name);
            rs = stat.executeQuery();
            while (rs.next()) {
                Manufacturers tempManufacturer = convertRowToManufacturer(rs);
                list.add(tempManufacturer);
            }
            return list;
        } finally {
            close(stat, rs);
        }
    }

    private Manufacturers convertRowToManufacturer(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String country = rs.getString("country");
        Manufacturers tempManufacturer = new Manufacturers(id, name, country);
        return tempManufacturer;
    }

    public List<Ownerships> getAllOwnerships() throws Exception {
        List<Ownerships> list = new ArrayList<>();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.createStatement();
            rs = stat.executeQuery("select * from carOwnership order by carOwnership.userID");
            while (rs.next()) {
                Ownerships tempOwnership = convertRowToOwnership(rs);
                list.add(tempOwnership);
            }
            return list;
        } finally {
            close(stat, rs);
        }
    }

    public List<Ownerships> searchOwnerships(String id) throws Exception {
        List<Ownerships> list = new ArrayList<>();
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            id += "%";
            stat = connection.prepareStatement("select * from carOwnership where carOwnership.userID = ? order by carOwnership.userID");
            stat.setString(1, id);
            rs = stat.executeQuery();
            while (rs.next()) {
                Ownerships tempOwnership = convertRowToOwnership(rs);
                list.add(tempOwnership);
            }
            return list;
        } finally {
            close(stat, rs);
        }
    }

    private Ownerships convertRowToOwnership(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String numberPlate = rs.getString("numberPlate");
        int userID = rs.getInt("userID");
        int carID = rs.getInt("carID");
        String colour = rs.getString("colour");
        int purchasePrice = rs.getInt("purchasePrice");
        Ownerships tempOwnership = new Ownerships(id, numberPlate, userID, carID, colour, purchasePrice);
        return tempOwnership;
    }

    private static void close(Connection connection, Statement stat, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    private void close(Statement stat, ResultSet rs) throws SQLException {
        close(null, stat, rs);
    }
}
