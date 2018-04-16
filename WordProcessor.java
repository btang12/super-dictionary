import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.lang.StringBuilder;

/**
 * This class contains some utility helper methods for the GraphProcessor class to process words.
 * 
 * @author Chris Sullivan (
 */
public class WordProcessor {

	/**
	 * Gets a Stream of words from the filepath.
	 * 
	 * The Stream contains trimmed, non-empty, and UPPERCASE words.
	 * 
	 * @param filepath
	 *            file path to the dictionary file
	 * @return Stream<String> stream of words read from the filepath
	 * @throws IOException exception resulting from accessing the filepath
	 * 
	 */
	public static Stream<String> getWordStream(String filepath) throws IOException {
		// All lines from file returned as stream. Trim and upper-case functions are
		// applied to stream.
		// Empty lines are filtered.
		return Files.lines(Paths.get(filepath)).map(w -> w.trim().toUpperCase()).filter(w -> w != "");
	}

	/**
	 * Determines the adjacency of two words
	 * 
	 * Adjacency between word1 and word2 is defined by: if the difference between
	 * word1 and word2 is of 1 char replacement 1 char addition 1 char deletion then
	 * word1 and word2 are adjacent else word1 and word2 are not adjacent
	 * 
	 * Note: if word1 is equal to word2, they are not adjacent
	 * 
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return true if word1 and word2 are adjacent else false
	 */
	public static boolean isAdjacent(String word1, String word2) {
		if (word1.equals(word2))
			return false;
		StringBuilder temp;
		char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		// replacement
		for (int i = 0; i < word1.length(); i++) {
			temp = new StringBuilder(word1);
			for (int j = 0; j < alphabet.length; j++) {
				temp.setCharAt(i, alphabet[j]);
				if (temp.toString().equalsIgnoreCase(word2))
					return true;
			}
		}

		// addition
		for (int i = 0; i <= word1.length(); i++) {
			for (int j = 0; j < alphabet.length; j++) {
				temp = new StringBuilder(word1);
				temp.insert(i, alphabet[j]);
				if (temp.toString().equalsIgnoreCase(word2))
					return true;
			}
		}

		// deletion
		for (int i = 0; i < word1.length(); i++) {
			temp = new StringBuilder(word1);
			temp.deleteCharAt(i);
			if (temp.toString().equalsIgnoreCase(word2))
				return true;
		}
		return false;
	}
}
