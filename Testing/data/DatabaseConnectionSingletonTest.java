package data;

import static org.junit.Assert.*; // JUnit 4 Assertion
import java.sql.Connection;
import org.junit.Test;           // JUnit 4 Annotation
import dal.DatabaseConnection;

public class DatabaseConnectionSingletonTest {

    @Test
    public void getInstance_shouldReturnSameObjectEveryTime() {
        DatabaseConnection first = DatabaseConnection.getInstance();
        DatabaseConnection second = DatabaseConnection.getInstance();

        // Requirement: Prove the class is a Singleton
        assertSame("getInstance() should always return the same DatabaseConnection object (Singleton)", first, second);
    }

    @Test
    public void getConnection_shouldReturnSameInstanceEachTime() {
        DatabaseConnection instance = DatabaseConnection.getInstance();

        Connection c1 = instance.getConnection();
        Connection c2 = instance.getConnection();

        // Requirement: Ensure the connection reference remains consistent
        assertSame("getConnection() should return the same Connection instance each time", c1, c2);
    }
}