import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        String dbName = "testdatabase";

        String host = "localhost";

        int port = 3306;

        String jdbcURL = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);

        String username = "root";
        String password = "";


        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            System.out.println("successfully connected to database");

            System.out.println("querying for employees...");

            String qString = "SELECT id, first_name, last_name, age FROM employees";

            PreparedStatement ps = connection.prepareStatement(qString);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");

                System.out.println("Employee ID: " + id);
                System.out.println("Name: " + firstName + " " + lastName);
                System.out.println("Age: " + age);
                System.out.println("---------------------");
            }

            connection.close();
        } catch(SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }
}
