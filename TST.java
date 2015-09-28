import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.LinkedList;

/**
 * Ternary Search Tree.
 * @author Dhruv Relwani
 * Citations - 
 * TST.java (http://algs4.cs.princeton.edu/52trie/TST.java.html)
 * Arshi Aggarwal (help).
 */
public class TST {
    private int size;  // size
    private Node root = new Node(); // root of TST

    private PriorityQueue<Node> maxPQ;
    private PriorityQueue<Node> minPQ;

    /**
     * The Node Class
    */
    public static class Node {
        private char c;  // character
        private Node left, mid, right;  // left, middle, and right subtries
        private Double val, max; // weights
        private String finalWord;

        /**
        * @return the final word
        */ 
        public String returnString() {
            return finalWord;
        }

        /**
        * @return the value of the node 
        */ 
        public Double returnVal() {
            return val;
        }

        /**
        * Consrtrucor for the Node.
        */ 
        private Node() {
            max = 0.0;
        }
    }

    /**
     * Initializes an empty string symbol table.
    */   
    public PriorityQueue<Node> returnMinPQ() {
        return this.minPQ;
    }

    /**
     * Initializes an empty string symbol table.
     */
    public TST() {
    }

    /**
     * @param  prefix  the input to be searched for
     * @param  k  the number of terms    
     */
    public LinkedList<String> sort(String prefix, int k) {
        //TreeSet<Node> words = new TreeSet<Node>(new CompareOne());
        LinkedList<String> words = new LinkedList<String>();
        minPQ = new PriorityQueue<Node>(k, new CompareOne());
        Node pqNode = new Node();
        pqNode.val = -1.0;
        minPQ.add(pqNode);
        maxPQ = new PriorityQueue<Node>(2, new CompareTwo());
        Node start = null;
        if (prefix.equals("")) {
            start = root;
            maxPQ.add(start);
        } else {
            start = get(root, prefix, 0);
        } if (start == null) {
            return new LinkedList<String>();
        } else {
            if (start.val != null) {
                if (minPQ.peek().val < start.val) {
                    if (minPQ.size() == k) {
                        minPQ.poll();
                        //words.remove(node);
                    } if (!minPQ.contains(start)) {
                        minPQ.add(start);
                        // Node node = minPQ.peek();
                        // words.addFirst(node.returnString());
                    }
                }
            } if (start.mid != null && start.mid.returnVal() != null) {
                if (minPQ.peek().val < start.mid.val) {
                    if (minPQ.size() == k) {
                        minPQ.poll();
                        //words.remove(node);
                    } if (!minPQ.contains(start.mid)) {
                        minPQ.add(start.mid);
                        // Node node = minPQ.peek();
                        // words.addFirst(node.returnString());
                    }
                }
            }
            if (start.mid != null && start.mid.left != null) {
                maxPQ.add(start.mid.left);
            } if (start.mid != null && start.mid.mid != null) {
                maxPQ.add(start.mid.mid);
            } if (start.mid != null && start.mid.right != null) {
                maxPQ.add(start.mid.right);
            }
            while((!maxPQ.isEmpty()) && maxPQ.peek().max > minPQ.peek().val) {
                Node ptr = maxPQ.poll();
                while (ptr != null) {
                    if (ptr.val != null) {
                        if (minPQ.peek().val < ptr.val) {
                            if (minPQ.size() == k) {
                                minPQ.poll();
                                //words.remove(node);
                            } if (!minPQ.contains(ptr)) {
                                minPQ.add(ptr);
                                // Node node = minPQ.peek();
                                // words.addFirst(node.returnString());
                            }
                        }
                    }
                    Node maxNode = null;
                    if (ptr.left != null){
                        if (ptr.max == ptr.left.max) {
                            maxNode = ptr.left;
                        } else {
                            maxPQ.add(ptr.left);
                        }
                    }
                    if (ptr.mid != null){
                        if (ptr.max == ptr.mid.max) {
                            maxNode = ptr.mid;
                        } else {
                            maxPQ.add(ptr.mid);
                        }
                    }
                    if (ptr.right != null){
                        if (ptr.max == ptr.right.max) {
                            maxNode = ptr.right;
                        } else {
                            maxPQ.add(ptr.right);
                        }
                    }
                    ptr = maxNode;
                }
            }
        }
        while(minPQ.size() > 0) {
            Node node = minPQ.poll();
            if (node.finalWord != null) {
                words.addFirst(node.returnString());
            }
        }
        return words;
    }

    // /**
    //  * Returns the final iterable required.
    //  * @return words LinkedList.
    //  */
    // public LinkedList<String> returnIterable() {
        

    //     for (Node node : minPQ) {

    //     }
    //     NavigableSet<Node> toIterate = words.descendingSet();
    //     for (Node node : toIterate) {
    //         result.add(node.returnString());
    //     }
    //     return result;
    // }

    // public void //addTree(TreeSet<Node> words, Node node) {
    //     if (node.finalWord != null && (!words.contains(node))) {
    //         words.add(node);
    //     }
    // }

    /**
     * Returns the number of key-Double pairs in this symbol table.
     * @return the number of key-Double pairs in this symbol table
     */
    public int size() {
        return this.size;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return <tt>true</tt> if this symbol table contains <tt>key</tt> and
     *     <tt>false</tt> otherwise
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Returns the Double associated with the given key.
     * @param key the key
     * @return the Double associated with the given key
     */
    public Double get(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return x.val;
    }

    /**
     * Returns the Node associated with the given key.
     * @param x the node being checked
     * @param key the key
     * @param d the string index
     * @return the Node associated with the given key
    */
    private Node get(Node x, String key, int d) {
        if (key == null) {
            throw new NullPointerException();
        } if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        } if (x == null) {
            return null;
        }
        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d+1);
        } else {
            return x;
        }
    }

    /**
     * Inserts the key-Double pair into the symbol table, overwriting the old Double
     * with the new Double if the key is already in the symbol table.
     * @param key the key
     * @param val the Double
     */
    public void put(String key, Double val) {
        if (!contains(key)) {
            this.size++;
        }
        root = put(root, key, val, 0);
    }

    /**
     * Inserts the key-Double pair into the symbol table, overwriting the old Double
     * with the new Double if the key is already in the symbol table.
     * @param x the node being checked
     * @param key the key
     * @param val the weight of the node
     * @param d the string index
     * @return the Node associated with the given key
    */
    private Node put(Node x, String key, Double val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        } if (x.max < val) {
            x.max = val;
        }
        if (c < x.c) {
            x.left  = put(x.left, key, val, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, val, d+1);
        } else {
            x.val = val;
            x.finalWord = key;
        } 
        return x;
    }

    /**
    * Comparator class for minPQ
    */
    private class CompareOne implements Comparator<Node> {
        public int compare(Node x, Node y) {
            if ((x.val - y.val) > 0) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
    * Comparator class for maxPQ
    */
    private class CompareTwo implements Comparator<Node> {
        public int compare(Node x, Node y) {
            if ((y.max - x.max) > 0) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
    * Comparator class for strings
    */
    private class CompareThree implements Comparator<Node> {
        public int compare(Node x, Node y) {
            if ((x.c == y.c)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
