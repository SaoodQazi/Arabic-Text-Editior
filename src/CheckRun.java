import dal.DatabaseConnection;
import java.sql.Connection;

public class CheckRun {
    public static void main(String[] args) {
        System.out.println("Starting Connection Test...");
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("SUCCESS: Your code is successfully talking to MariaDB!");
            } else {
                System.out.println("FAILURE: Connection is null or closed. Check your config.properties.");
            }
        } catch (Exception e) {
            System.err.println("ERROR: A crash occurred during testing: " + e.getMessage());
        }
    }
}