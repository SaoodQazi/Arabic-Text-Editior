package dal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PreProcessText {
	private static final Set<Character> DIACRITICS = new HashSet<>(
			Arrays.asList('َ', 'ً', 'ُ', 'ٌ', 'ِ', 'ٍ', 'ْ', 'ّ'));

	public static String removeHarakat(String text) {
		StringBuilder result = new StringBuilder();
		for (char ch : text.toCharArray()) {
			if (!DIACRITICS.contains(ch)) {
				result.append(ch);
			}
		}
		return result.toString();
	}

	public static String removeNonArabicCharacters(String text) {
		return text.replaceAll("[^\\p{IsArabic}\\s]", "");
	}

	public static String preprocessText(String text) {
		text = removeHarakat(text);
		text = removeNonArabicCharacters(text);
		return text.toLowerCase();
	}
}
