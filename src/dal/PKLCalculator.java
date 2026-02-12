package dal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PKLCalculator {
    private String document;
    private Map<String, Integer> wordFreq;
    private int totalWords;

    public PKLCalculator(String document) {
        this.document = PreProcessText.preprocessText(document);
        this.wordFreq = new HashMap<>();
        this.totalWords = 0;
        computeWordFrequencies();
    }

    private void computeWordFrequencies() {
        String[] words = document.split("\\s+");
        totalWords = words.length;

        for (String word : words) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }
    }

    private double calculateWordProbability(String word) {
        return (double) wordFreq.getOrDefault(word, 0) / totalWords;
    }

    public double calculatePKL(String v, String ul, String ur) {
        double pV = calculateWordProbability(v);
        double pUl = calculateWordProbability(ul);
        double pUr = calculateWordProbability(ur);

        if (pV == 0 || pUl == 0 || pUr == 0) {
            return 0.0;
        }

        return pV * Math.log(pV / (pUl * pUr));
    }

    public Map<String, Double> calculatePKLForAllWords() {
        Map<String, Double> pklScores = new LinkedHashMap<>();
        String[] words = document.split("\\s+");

        for (int i = 1; i < words.length - 1; i++) {
            String ul = words[i - 1];
            String v = words[i];
            String ur = words[i + 1];

            double pkl = calculatePKL(v, ul, ur);
            pklScores.put(v + " (" + ul + ", " + ur + ")", pkl);
        }

        return pklScores;
    }

//    public static void main(String[] args) {
//        String document = "إِنَّا أَعْطَيْنَاكَ ٱلْكَوْثَرَ فَصَلِّ لِرَبِّكَ وَٱنْحَرْ إِنَّ شَانِئَكَ هُوَ ٱلْأَبْتَرُ";
//
//        PKLCalculator pklCalculator = new PKLCalculator(document);
//
//        Map<String, Double> pklScores = pklCalculator.calculatePKLForAllWords();
//
//        System.out.println("PKL Divergence Scores: ");
//        for (Map.Entry<String, Double> entry : pklScores.entrySet()) {
//            System.out.printf("%s: %.4f\n", entry.getKey(), entry.getValue());
//        }
//    }
}