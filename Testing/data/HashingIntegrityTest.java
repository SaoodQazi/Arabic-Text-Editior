package data;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull; // JUnit 4 Assumption
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;
import dal.DatabaseConnection;
import dal.EditorDBDAO;
import dal.HashCalculator;

public class HashingIntegrityTest {

    @Test
    public void editingFile_changesSessionHash_butKeepsOriginalHashInDB() throws Exception {
        // 1) Get DB connection from the singleton
        Connection conn = DatabaseConnection.getInstance().getConnection();

        // Skip test if DB is down to avoid false failures
        assumeNotNull(conn);

        conn.setAutoCommit(true);

        String fileName        = "hash_test_" + System.currentTimeMillis();
        String originalContent = "Original content for hashing integrity test.";
        String updatedContent  = "Updated content for hashing integrity test (changed).";

        EditorDBDAO dao = new EditorDBDAO();

        // 2) Create file through DAO
        boolean created = dao.createFileInDB(fileName, originalContent);
        assertTrue("createFileInDB should return true for valid input", created);

        conn.setAutoCommit(true);

        // 3) Read original hash from DB
        int fileId;
        String dbOriginalHash;

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT fileId, fileHash FROM files WHERE fileName = ? ORDER BY dateCreated DESC LIMIT 1")) {
            stmt.setString(1, fileName);
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue("Inserted file must be present in DB", rs.next());
                fileId = rs.getInt("fileId");
                dbOriginalHash = rs.getString("fileHash");
            }
        }

        // 4) Verification: DB hash must match calculation
        String expectedOriginalHash = HashCalculator.calculateHash(originalContent);
        assertEquals("DB fileHash must equal MD5 of original content", expectedOriginalHash, dbOriginalHash);

        // 5) Simulate an edit - session hash must differ
        String sessionHashAfterEdit = HashCalculator.calculateHash(updatedContent);
        assertNotEquals("Session hash should differ from original DB hash", dbOriginalHash, sessionHashAfterEdit);

        // 6) Update via DAO
        boolean updated = dao.updateFileInDB(fileId, fileName, 1, updatedContent);
        assertTrue("updateFileInDB should return true", updated);

        conn.setAutoCommit(true);

        // 7) Verify original hash in DB remained unchanged
        String dbHashAfterEdit;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT fileHash FROM files WHERE fileId = ?")) {
            stmt.setInt(1, fileId);
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next());
                dbHashAfterEdit = rs.getString("fileHash");
            }
        }

        assertEquals("Original import hash in DB must NOT change after editing", dbOriginalHash, dbHashAfterEdit);

        // 8) Cleanup
        try (PreparedStatement del = conn.prepareStatement("DELETE FROM files WHERE fileId = ?")) {
            del.setInt(1, fileId);
            del.executeUpdate();
        }
    }
}