import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 * @author Dhruv Relwani
 */
public class AlphabetSort {
    private static List<Character> alphabet;
    private static Trie words;

    /** 
    * Sort Method
    * @param  node  The Trie Node on which the sort method is applied.
    * @param  word  The word to sort
    */
    public static void sort(String word, Trie.Node node) {
        if (node.returnExists()) {
            System.out.println(word);
        }

        for (Character charac : alphabet) {
            if (node.returnLinks().containsKey(charac)) {
                sort(word + charac, node.returnLinks().get(charac));
            }
        }
    }

    /** 
    * Main Method
    * @param  args  command line arguments
    */  
    public static void main(String[] args) {
        alphabet = new ArrayList<Character>();
        words = new Trie();

        Scanner in = new Scanner(System.in); 
        if (in.hasNextLine()) {
            String input = in.nextLine();
            for (int ctr = 0; ctr < input.length(); ctr++) {
                char ch = input.charAt(ctr);
                if (!alphabet.contains(ch)) {
                    alphabet.add(ch);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        if (in.hasNextLine()) {
            while (in.hasNextLine()) {
                words.insert(in.nextLine());
            }
        } else {
            throw new IllegalArgumentException();
        }
        sort("", words.returnRoot());
    }
}
