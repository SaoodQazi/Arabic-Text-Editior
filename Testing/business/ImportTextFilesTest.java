package business;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bll.EditorBO;
import dal.IFacadeDAO;
import dto.Documents;

class ImportTextFilesTest {

    private static final String SAMPLE_CONTENT = "Hello\nWorld";
    private File txtFile;
    private File pdfFile;

    private FakeFacadeDAO fakeDao;
    private EditorBO editorBO;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary .txt file
        txtFile = File.createTempFile("import-test-", ".txt");
        try (FileWriter writer = new FileWriter(txtFile)) {
            writer.write(SAMPLE_CONTENT);
        }

        // Create a temporary .pdf file (unsupported extension)
        pdfFile = File.createTempFile("import-test-", ".pdf");
        try (FileWriter writer = new FileWriter(pdfFile)) {
            writer.write(SAMPLE_CONTENT);
        }

        fakeDao = new FakeFacadeDAO();
        editorBO = new EditorBO(fakeDao);
    }

    @AfterEach
    void tearDown() {
        if (txtFile != null && txtFile.exists()) {
            txtFile.delete();
        }
        if (pdfFile != null && pdfFile.exists()) {
            pdfFile.delete();
        }
    }

    @Test
    void importTxtFile_callsCreateFileInDB_andReturnsTrue() {
        fakeDao.createFileResult = true;

        boolean result = editorBO.importTextFiles(txtFile, txtFile.getName());

        assertTrue(result, "importTextFiles should return true when DAO returns true");
        assertTrue(fakeDao.createFileCalled, "createFileInDB should be called for .txt files");
        assertEquals(txtFile.getName(), fakeDao.lastName, "File name passed to DAO should match");
        assertTrue(fakeDao.lastContent.contains("Hello"),
                "DAO should receive file content that contains the original text");
    }

    @Test
    void importTxtFile_returnsFalseWhenDaoReturnsFalse() {
        fakeDao.createFileResult = false;

        boolean result = editorBO.importTextFiles(txtFile, txtFile.getName());

        assertFalse(result, "importTextFiles should return false when DAO returns false");
        assertTrue(fakeDao.createFileCalled, "createFileInDB should still be called");
    }

    @Test
    void importUnsupportedExtension_doesNotCallDao_andReturnsFalse() {
        boolean result = editorBO.importTextFiles(pdfFile, pdfFile.getName());

        assertFalse(result, "importTextFiles should return false for unsupported extensions");
        assertFalse(fakeDao.createFileCalled, "createFileInDB should NOT be called for unsupported extensions");
    }

    @Test
    void importNonExistentFile_returnsFalse_andDoesNotCallDao() {
        File nonExistent = new File("this_file_should_not_exist_12345.txt");
        if (nonExistent.exists()) {
            nonExistent.delete();
        }

        boolean result = editorBO.importTextFiles(nonExistent, nonExistent.getName());

        assertFalse(result, "importTextFiles should return false when the file cannot be read");
        assertFalse(fakeDao.createFileCalled, "createFileInDB should NOT be called when reading fails");
    }

    private static class FakeFacadeDAO implements IFacadeDAO {

        boolean createFileCalled = false;
        String lastName;
        String lastContent;
        boolean createFileResult = true;

        @Override
        public boolean createFileInDB(String nameOfFile, String content) {
            this.createFileCalled = true;
            this.lastName = nameOfFile;
            this.lastContent = content;
            return createFileResult;
        }


        @Override
        public boolean updateFileInDB(int id, String fileName, int pageNumber, String content) {
            return false;
        }

        @Override
        public boolean deleteFileInDB(int id) {
            return false;
        }

        @Override
        public List<Documents> getFilesFromDB() {
            return null;
        }

        @Override
        public String transliterateInDB(int pageId, String arabicText) {
            return null;
        }

        @Override
        public Map<String, String> lemmatizeWords(String text) {
            return null;
        }

        @Override
        public Map<String, List<String>> extractPOS(String text) {
            return null;
        }

        @Override
        public Map<String, String> extractRoots(String text) {
            return null;
        }

        @Override
        public double performTFIDF(List<String> unSelectedDocsContent, String selectedDocContent) {
            return 0;
        }

        @Override
        public Map<String, Double> performPMI(String content) {
            return null;
        }

        @Override
        public Map<String, Double> performPKL(String content) {
            return null;
        }

        @Override
        public Map<String, String> stemWords(String text) {
            return null;
        }

        @Override
        public Map<String, String> segmentWords(String text) {
            return null;
        }
    }
}