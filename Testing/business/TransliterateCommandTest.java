package business;

import static org.junit.Assert.*;
import org.junit.Test;
import dal.Transliteration;

public class TransliterateCommandTest {

    @Test
    public void testTransliterateExecution() {
        // Positive Path: Standard Arabic word
        String inputArabic = "بسم";
        
        // Based on your Transliteration.java logic:
        // b + s + m = bsm -> Formatted to "Bsm"
        String result = Transliteration.transliterate(inputArabic);
        
        assertNotNull("Result should not be null", result);
        assertEquals("Phonetic mapping should match Bsm", "Bsm", result);
    }

    @Test
    public void testTransliterateSentence() {
        // Testing multi-word capitalization logic
        String input = "ب س"; // b and s
        String result = Transliteration.transliterate(input);
        
        // Your code capitalizes each word: "B S"
        assertEquals("Each word should be capitalized", "B S", result);
    }

    @Test
    public void testTransliterateNegativePath() {
        // Negative Path: Empty input
        String result = Transliteration.transliterate("");
        assertEquals("Empty string should return empty", "", result);
        
        // Negative Path: Special characters (which are ignored by your map)
        String special = "!!!";
        String resultSpecial = Transliteration.transliterate(special);
        assertEquals("Characters not in map should be ignored", "", resultSpecial);
    }
}