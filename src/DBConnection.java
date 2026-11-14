import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    // Database Configuration
    // If your MySQL has a password, change "root" below to your password (e.g., "1234" or "")
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "electricity_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // <-- EDIT YOUR MYSQL PASSWORD HERE

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
            + "?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static boolean initialized = false;

    /**
     * Establishes and returns a Connection object to MySQL database.
     */
    public static Connection getConnection() throws Exception {
        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

        // Auto-initialize tables if not yet done in this session
        if (!initialized) {
            autoInitializeTables(con);
            initialized = true;
        }

        return con;
    }

    /**
     * Helper to auto-create 'users' and 'complaints' tables if they don't exist.
     */
    private static void autoInitializeTables(Connection con) {
        try (Statement st = con.createStatement()) {
            // Create 'users' table
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "  username VARCHAR(100) NOT NULL PRIMARY KEY" +
                ")"
            );

            // Create 'complaints' table
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS complaints (" +
                "  complaint_id VARCHAR(50) NOT NULL PRIMARY KEY," +
                "  consumer_number VARCHAR(50) NOT NULL," +
                "  category VARCHAR(100) NOT NULL," +
                "  status VARCHAR(50) NOT NULL DEFAULT 'Pending'," +
                "  complaint_date VARCHAR(50) NOT NULL," +
                "  address VARCHAR(255)," +
                "  description TEXT" +
                ")"
            );
        } catch (Exception e) {
            // Silently continue if tables already exist or init fails
            System.err.println("Database auto-init note: " + e.getMessage());
        }
    }

    /**
     * Tests the database connection from command line or IntelliJ.
     */
    public static void main(String[] args) {
        System.out.println("Testing connection to: " + URL);
        try {
            Connection con = getConnection();
            if (con != null && !con.isClosed()) {
                System.out.println("Connection successful! Database is ready.");
                con.close();
            }
        } catch (Exception e) {
            System.err.println("Connection failed! Reason: " + e.getMessage());
            System.err.println("Please check that MySQL service is running and PASSWORD in DBConnection.java is correct.");
        }
    }
}
