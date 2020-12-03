package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;


public class PrefixMatches {
    private final int NORM_SIZE = 3;
    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String element : strings) {
            String[] words = element.split(" ");
            for (String word : words) {
                int len = word.length();
                if (len > 2) {
                    trie.add(new Tuple(word, len));
                }
            }
        }
        return size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() >= 2) {
            return trie.wordsWithPrefix(pref);
        }
        throw new IllegalArgumentException("pref.len < 2");
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        int lenWord = NORM_SIZE;
        if (pref.length() < 2) {
            throw new IllegalArgumentException("pref.len < 2");
        } else if (pref.length() > 2) {
            lenWord = pref.length();
        }

        ArrayList<String> result = new ArrayList<>();
        Iterable<String> wordsWithPrefix = wordsWithPrefix(pref);
        for (String word : wordsWithPrefix) {
            if (word.length() < lenWord + k) {
                result.add(word);
            }
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
