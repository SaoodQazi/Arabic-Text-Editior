package data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import dal.DatabaseConnection;

public class DatabaseConnectionSingletonTest {

    @Test
    void getInstance_shouldReturnSameObjectEveryTime() {
        DatabaseConnection first = DatabaseConnection.getInstance();
        DatabaseConnection second = DatabaseConnection.getInstance();

        assertSame(first, second,
                "getInstance() should always return the same DatabaseConnection object (Singleton)");
    }

    @Test
    void getConnection_shouldReturnSameInstanceEachTime() {
        DatabaseConnection instance = DatabaseConnection.getInstance();

        Connection c1 = instance.getConnection();
        Connection c2 = instance.getConnection();

        // Even if the connection is null or closed, the singleton should
        // always return the SAME reference from getConnection().
        assertSame(c1, c2, "getConnection() should return the same Connection instance each time");
    }
}