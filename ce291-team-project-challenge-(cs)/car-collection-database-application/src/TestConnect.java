import java.sql.*;

public class TestConnect {
    public static void main(String args[]) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ce29x_fk18726", "[USER]", "[PASSWORD]");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        viewTable(connection);
    }

    public static void viewTable(Connection con) {
        String query = "SELECT * FROM users";
        try(Statement stat = con.createStatement()) {
            ResultSet rs = stat.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String postcode = rs.getString("postcode");
                System.out.println(id + " : " + name + " : " + postcode);
            }
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
}
