package business;

import static org.junit.Assert.*;
import org.junit.Test;
import dal.TFIDFCalculator;

public class TFIDFTest {

    @Test
    public void testTFIDFPositivePath() {
        TFIDFCalculator calculator = new TFIDFCalculator();
        
        // Phase B Requirement: Initialize corpus so IDF > 0
        // Adding specific Arabic documents to the corpus
        calculator.addDocumentToCorpus("قلم رصاص"); 
        calculator.addDocumentToCorpus("كتاب الطالب");
        calculator.addDocumentToCorpus("مدرسة جميلة");
        
        // Test Document containing terms from the corpus
        String document = "قلم كتاب مدرسة"; 
        
        double actualScore = calculator.calculateDocumentTfIdf(document);
        
        // Requirement: Assert the score is a positive weight for valid matches
        assertTrue("TF-IDF score should be positive for document matching corpus terms", actualScore > 0);
    }

    @Test
    public void testTFIDFNegativePath() {
        TFIDFCalculator calculator = new TFIDFCalculator();
        
        // Phase B Requirement: Feed empty or special character inputs
        String emptyDoc = "";
        String specialChars = "!!! $$$ @@@";
        
        try {
            double scoreEmpty = calculator.calculateDocumentTfIdf(emptyDoc);
            double scoreSpecial = calculator.calculateDocumentTfIdf(specialChars);
            
            // Requirement: Assert graceful exit with 0.0 score
            assertEquals("Empty document must return 0.0", 0.0, scoreEmpty, 0.01);
            assertEquals("Special characters must return 0.0", 0.0, scoreSpecial, 0.01);
        } catch (Exception e) {
            // Fails if the calculator crashes (e.g., NaN or DivideByZero)
            fail("Calculator crashed on invalid input: " + e.getMessage());
        }
    }
}