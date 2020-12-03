//*
// Implementation was taken from book
// «Robert Sedgewick, Kevin Wayne Algorithms, part 2, 4th Edition Addison»
// *//


package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;

import java.util.ArrayList;
import java.util.List;

public class RWayTrie implements Trie {
    private static final int R = 26;
    // This is used to make int representation of char to be in range (0, 26)
    private static final int DIFF = 97;
    private Node root = new Node();

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    @Override
    public void add(Tuple t) {
        root = put(root, t.term, t.weight, 0);
    }

    private Node put(Node x, String key, int val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        x.next[c - DIFF] = put(x.next[c - DIFF], key, val, d + 1);
        return x;
    }

    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            root = delete(root, word, 0);
            return true;
        }
        return false;
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length())
            x.val = null;
        else {
            char c = key.charAt(d);
            x.next[c - DIFF] = delete(x.next[c - DIFF], key, d + 1);
        }
        if (x.val != null) return x;
        for (char c = 0; c < R; c++)
            if (x.next[c] != null) return x;
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    private Node get(Node x, String key, int d) {
        // Return node associated with key in the subtrie rooted at x.
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        return get(x.next[c - DIFF], key, d + 1);
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);
        List<String> result = new ArrayList<>();
        while (!q.isEmpty()) {
            result.add((String) q.dequeue());
        }
        return result;
    }

    public Node get(String key) {
        return get(root, key, 0);
    }

    private void collect(Node x, String pre, Queue q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        for (char c = DIFF; c < DIFF + R; c++) {
            collect(x.next[c - DIFF], pre + c, q);
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        int res = 0;
        if (x.val != null) res++;
        for (char c = 0; c < R; c++)
            res += size(x.next[c]);
        return res;
    }

}
