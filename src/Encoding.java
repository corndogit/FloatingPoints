import java.util.HashMap;
import java.util.Map;

public class Encoding {
    public static String encodeRunLength(String phrase) {
        final int minLength = 3;  // minimum fragment length to encode
        StringBuilder encodedPhrase = new StringBuilder();
        StringBuilder fragment = new StringBuilder();

        for (int i=0; i<phrase.length(); i++) {
            // Make sure we have a character to start with
            if (fragment.length() == 0) {
                fragment.append(phrase.charAt(i));
            }

            // Check if next char in phrase is the same as the one in the fragment (and i+1 is not out of bounds)
            if (!(i == phrase.length() - 1)) {
                if (phrase.charAt(i + 1) == fragment.charAt(0)) {
                    fragment.append(phrase.charAt(i+1));
                } else {

                    // a new character has been reached, so append the fragment to encodedPhrase
                    if (fragment.length() < minLength) {
                        encodedPhrase.append(fragment);
                        fragment.setLength(0);  // reset the fragment
                    } else {
                        encodedPhrase.append(String.format("*%s%d", fragment.charAt(0), fragment.length()));
                        fragment.setLength(0);
                    }
                }

            // append the last fragment to the encoded phrase
            } else if (fragment.length() < minLength) {
                encodedPhrase.append(fragment);
            } else {
                encodedPhrase.append(String.format("*%s%d", fragment.charAt(0), fragment.length()));
            }
        }
        return encodedPhrase.toString();
    }

    public static Map<Character, Integer> charOccurrences(String phrase) {

        Map<Character, Integer> occurTable = new HashMap<>();
        for (int i=0; i<phrase.length()-1; i++) {
            char currentChar = phrase.charAt(i);
            if (occurTable.containsKey(currentChar)) {
                occurTable.put(currentChar, occurTable.get(currentChar) + 1);
            } else {
                occurTable.put(currentChar, 1);
            }
        }
        return occurTable;
    }

    public static Map<Character, Double> charOccurProbability(String phrase) {
        Map<Character, Integer> charCount = charOccurrences(phrase);
        Map<Character, Double> charProbability = new HashMap<>();

        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            char key = entry.getKey();
            double value = (double) entry.getValue() / phrase.length();
            charProbability.put(key, value);
        }
        return charProbability;
    }
}
