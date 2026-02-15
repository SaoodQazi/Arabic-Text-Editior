package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.EditorPO;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseConnection {
    private static DatabaseConnection INSTANCE;
    private Connection connection;
    private String url;
    private String username;
    private String password;
    final Logger LOGGER = LogManager.getLogger(DatabaseConnection.class); // Corrected logger class

    private DatabaseConnection() {
        try (FileInputStream propertiesInput = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(propertiesInput);
            
            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");
            
            Class.forName("org.mariadb.jdbc.Driver"); 
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            // FIXED: Added missing closing parenthesis and brace below
            LOGGER.error("Database connection failed: " + e.getMessage()); 
        } 
    }

    public static synchronized DatabaseConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseConnection();
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}