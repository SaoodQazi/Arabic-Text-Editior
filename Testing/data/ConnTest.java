package data;

import static org.junit.Assert.*;
import org.junit.Test;
import dal.DatabaseConnection;
import java.sql.Connection;

public class ConnTest {
    @Test
    public void testDatabaseConnectivity() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        // Verifies the connection is established and the JAR is linked
        assertNotNull("Database connection should not be null", conn);
    }

    @Test
    public void testSingletonProperty() {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        // Verifies that only one connection instance exists in memory
        assertSame("Both instances must be the same", instance1, instance2);
    }
}