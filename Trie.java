import java.util.HashMap;

/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Dhruv Relwani
 */
public class Trie {
    private Node root = new Node();

    /** 
     * Node Class for each node in our trie
    */  
    public class Node {
        private boolean exists;
        private HashMap<Character, Node> links;
        
        /** 
        * Node Class Constructor
        */  
        public Node() {
            links = new HashMap<Character, Node>(2);
            exists = false;
        }

        /** 
         * To access the boolean exists
         * @return exists Node
        */  
        public boolean returnExists() {
            return exists;
        }

        /** 
         * To access the map links
         * @return links 
        */  
        public HashMap<Character, Node> returnLinks() {
            return links;
        }
    } 

    /** 
     * To access the root of the Trie
     * @return root trie
    */  
    public Node returnRoot() {
        return root;
    }

    /** 
     * Put Method for array implementation of Trie
     * @param  x  node in our trie
     * @param  key  the string being inserted
     * @param  d  index
     * @return root trie
    */  
    private Node put(Node x, String key, int d) {
        if (x == null) { 
            x = new Node();
        }

        if (d == key.length()) {
            x.exists = true;
            return x;
        }

        char c = key.charAt(d);
        x.links.put(c, put(x.links.get(c), key, d + 1));
        return x;
    }

    /** 
     * Insert Method for array implementation of Trie
     * @param  s  the string being inserted
    */  
    public void insert(String s) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException();
        }
        put(root, s, 0);
    }

    /** 
     * Find method to find prefixes and full words.
     * @param  s  the string being searched
     * @param  isFullWord  specifies whether fullword or prefix is searched
     * @return Whether prefix or word in our trie
    */  
    public boolean find(String s, boolean isFullWord) {
        if (isFullWord) {
            return findFullWord(root, s, 0);
        } else {
            return findPrefix(root, s, 0);
        }
    }

    /** 
     * @param  x  node in our trie
     * @param  key  the string being searched
     * @param  d  index
     * @return whether prefix in our trie
    */  
    private boolean findPrefix(Node x, String key, int d) {
        if (x == null) {
            return false;
        } else if (d == key.length()) {
            return true;
        }
        char c = key.charAt(d);
        return findPrefix(x.links.get(c), key, d + 1);  
    }

    /** 
     * @param  x  node in our trie
     * @param  key  the string being searched
     * @param  d  index
     * @return whether word in our trie
    */  
    private boolean findFullWord(Node x, String key, int d) {
        if (x == null) {
            return false;
        } else if (d == key.length() && x.exists) {
            return true;
        } else if (d == key.length()) {
            return false;
        }       
        char c = key.charAt(d);
        return findFullWord(x.links.get(c), key, d + 1);
    }

    /** 
    * Main Method
    * @param  args  command line arguments
    */  
    public static void main(String[] args) {  
    }
}
