package dal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PMICalculator {
    private String document;
    private Map<String, Integer> wordFreq;
    private Map<String, Integer> bigramFreq;
    private int totalWords;

    public PMICalculator(String document) {
        this.document = PreProcessText.preprocessText(document);
        this.wordFreq = new HashMap<>();
        this.bigramFreq = new HashMap<>();
        this.totalWords = 0;
        computeWordAndBigramFrequencies();
    }

    private void computeWordAndBigramFrequencies() {
        String[] words = document.split("\\s+");
        totalWords = words.length;

        for (String word : words) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }

        for (int i = 0; i < words.length - 1; i++) {
            String bigram = words[i] + " " + words[i + 1];
            bigramFreq.put(bigram, bigramFreq.getOrDefault(bigram, 0) + 1);
        }
    }

    private double calculateWordProbability(String word) {
        return (double) wordFreq.getOrDefault(word, 0) / totalWords;
    }

    private double calculateBigramProbability(String word1, String word2) {
        String bigram = word1 + " " + word2;
        return (double) bigramFreq.getOrDefault(bigram, 0) / totalWords;
    }

    public double calculatePMI(String word1, String word2) {
        double probWord1 = calculateWordProbability(word1);
        double probWord2 = calculateWordProbability(word2);
        double probBigram = calculateBigramProbability(word1, word2);

        if (probWord1 == 0 || probWord2 == 0 || probBigram == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        return Math.log(probBigram / (probWord1 * probWord2)) / Math.log(2);
    }

    public Map<String, Double> calculatePMIForAllBigrams() {
        Map<String, Double> pmiScores = new LinkedHashMap<>();
        String[] words = document.split("\\s+");

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            String bigram = word1 + " " + word2;

            double pmiScore = calculatePMI(word1, word2);
            pmiScores.put(bigram, pmiScore);
        }

        return pmiScores;
    }

//    public static void main(String[] args) {
//        String document = "ٱللَّهُ لَآ إِلَـٰهَ إِلَّا هُوَ ٱلْحَىُّ ٱلْقَيُّومُ ۚ لَا تَأْخُذُهُۥ سِنَةٌۭ وَلَا نَوْمٌۭ ۚ لَّهُۥ مَا فِى ٱلسَّمَـٰوَٰتِ وَمَا فِى ٱلْأَرْضِ ۗ مَن ذَا ٱلَّذِى يَشْفَعُ عِندَهُۥٓ إِلَّا بِإِذْنِهِۦ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَىْءٍۢ مِّنْ عِلْمِهِۦٓ إِلَّا بِمَا شَآءَ ۚ وَسِعَ كُرْسِيُّهُ ٱلسَّمَـٰوَٰتِ وَٱلْأَرْضَ ۖ وَلَا يَـُٔودُهُۥ حِفْظُهُمَا ۚ وَهُوَ ٱلْعَلِىُّ ٱلْعَظِيمُ";
//        PMI pmiCalculator = new PMI(document);
//
//        Map<String, Double> pmiForBigrams = pmiCalculator.calculatePMIForAllBigrams();
//
//        System.out.println("Output: ");
//        for (Map.Entry<String, Double> entry : pmiForBigrams.entrySet()) {
//            System.out.printf("%s: %.4f\n", entry.getKey(), entry.getValue());
//        }
//    }
}