package data;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

import dal.DatabaseConnection;
import dal.EditorDBDAO;
import dal.HashCalculator;

public class HashingIntegrityTest {

    @Test
    void editingFile_changesSessionHash_butKeepsOriginalHashInDB() throws Exception {
        // 1) Get DB connection from the singleton
        Connection conn = DatabaseConnection.getInstance().getConnection();

        // If DB is not available, skip the test instead of failing the whole suite
        assumeTrue(conn != null,
                "Database connection is not available; skipping hashing integrity test");

        // Make sure our direct queries auto-commit
        conn.setAutoCommit(true);

        String fileName        = "hash_test_" + System.currentTimeMillis();
        String originalContent = "Original content for hashing integrity test.";
        String updatedContent  = "Updated content for hashing integrity test (changed).";

        EditorDBDAO dao = new EditorDBDAO();

        // 2) Create file through DAO (stores original hash in files.fileHash)
        boolean created = dao.createFileInDB(fileName, originalContent);
        assertTrue(created, "createFileInDB should return true for valid input");

        // DAO methods may change auto-commit; reset it for our own SQL
        conn.setAutoCommit(true);

        // 3) Read fileId and original hash from DB
        int fileId;
        String dbOriginalHash;

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT fileId, fileHash FROM files WHERE fileName = ? ORDER BY dateCreated DESC LIMIT 1")) {
            stmt.setString(1, fileName);
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "Inserted file must be present in DB");
                fileId = rs.getInt("fileId");
                dbOriginalHash = rs.getString("fileHash");
            }
        }

        // 4) Sanity check: DB hash must equal MD5 of original content
        String expectedOriginalHash = HashCalculator.calculateHash(originalContent);
        assertEquals(expectedOriginalHash, dbOriginalHash,
                "DB fileHash must equal MD5 of original content");

        // 5) Compute session hash for UPDATED content -> must differ from original hash
        String sessionHashAfterEdit = HashCalculator.calculateHash(updatedContent);
        assertNotEquals(dbOriginalHash, sessionHashAfterEdit,
                "Session hash (MD5 of updated content) should differ from original DB hash");

        // 6) Update file content via DAO (simulates editing in the app)
        boolean updated = dao.updateFileInDB(fileId, fileName, 1, updatedContent);
        assertTrue(updated, "updateFileInDB should return true for valid update");

        // Reset auto-commit again for our direct queries
        conn.setAutoCommit(true);

        // 7) Re-read fileHash from DB -> should STILL be the original hash
        String dbHashAfterEdit;
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT fileHash FROM files WHERE fileId = ?")) {
            stmt.setInt(1, fileId);
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "File row must still exist after update");
                dbHashAfterEdit = rs.getString("fileHash");
            }
        }

        assertEquals(dbOriginalHash, dbHashAfterEdit,
                "Original import hash in DB must NOT change after editing content");

        // 8) Clean up: delete the test file row (cascades to pages & analytics)
        try (PreparedStatement del = conn.prepareStatement(
                "DELETE FROM files WHERE fileId = ?")) {
            del.setInt(1, fileId);
            del.executeUpdate();
        }
    }
}