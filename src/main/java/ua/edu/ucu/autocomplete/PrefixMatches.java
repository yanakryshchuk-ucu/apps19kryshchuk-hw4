package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        trie = new RWayTrie();
        for (int j = 0; j < strings.length; j++) {
            if (strings[j] != null) {
                String[] words = strings[j].split(" ");
                for (String word : words) {
                    if (word.length() > 2) {
                        trie.add(new Tuple(word, word.length()));
                    }
                }
            }
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    private static int compareByLength(String s1, String s2) {
        return s1.length() - s2.length();
    }

    public Iterable<String> wordsWithPrefix(final String pref, final int k) {
        final List<String> sorted = new ArrayList<>();
        trie.wordsWithPrefix(pref).forEach(sorted::add);
        Collections.sort(sorted, PrefixMatches::compareByLength);
        final Set<String> result = new HashSet<>();
        // add to array matching words
        int lenGroup = 0;
        final Iterator<String> sortedTerms = sorted.iterator();
        // iterate sortedTerms
        int prevLen = 0;
        while (lenGroup <= k && sortedTerms.hasNext()) {
            final String next = sortedTerms.next();
            // if length next word is bigger than of previous - change group
            if (next.length() > prevLen) {
                lenGroup++;
                prevLen = next.length();
            }
            if (lenGroup <= k) {
                result.add(next);
            }
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
