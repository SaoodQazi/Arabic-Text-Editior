package dal;

import java.util.List;
import java.util.Map;

import dto.Documents;

public interface IEditorDBDAO {
	boolean createFileInDB(String nameOfFile, String content);

	boolean updateFileInDB(int id, String fileName, int pageNumber, String content);

	boolean deleteFileInDB(int id);

	List<Documents> getFilesFromDB();

	String transliterateInDB(int pageId, String arabicText);

	Map<String, String> lemmatizeWords(String text);

	Map<String, List<String>> extractPOS(String text);

	Map<String, String> extractRoots(String text);

	double performTFIDF(List<String> unSelectedDocsContent, String selectedDocContent);

	Map<String, Double> performPMI(String content);

	Map<String, Double> performPKL(String content);

	Map<String, String> stemWords(String text);

	Map<String, String> segmentWords(String text);

}
