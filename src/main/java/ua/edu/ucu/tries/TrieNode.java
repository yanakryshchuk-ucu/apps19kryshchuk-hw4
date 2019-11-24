package ua.edu.ucu.tries;

import ua.edu.ucu.utils.Queue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

class TrieNode {
    private static final int R = 26;

    private Integer weight;
    private TrieNode[] children = new TrieNode[R]; //array of pointers

    int size() {
        int size = weight == null ? 0 : 1;
        for (TrieNode n : children) {
            if (n != null) {
                size += n.size();
            }
        }
        return size;
    }

    Collection<TrieNode> getChildren() {
        return Arrays.asList(children).stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    boolean hasWeight() {
        return weight != null;
    }

    private int getIndex(String str) {
        return str.charAt(0) - 'a';
    }

    void add(final String term, final int termWeight) {
        final int index = getIndex(term);
        final TrieNode node = getChild(index);
        if (term.length() > 1) {
            node.add(term.substring(1), termWeight);
        } else {
            node.weight = termWeight;
        }
    }

    boolean contains(final String term) {
        Optional<TrieNode> node = findNode(term);
        // call map if not empty, if empty - return false always
        return node.map(n -> n.weight != null).orElse(false);
    }

    private TrieNode getChild(final int index) {
        if (children[index] == null) {
            children[index] = new TrieNode();
        }
        return children[index];
    }

    boolean delete(final String term) {
        final int index = getIndex(term);
        final TrieNode node = children[index];
        if (node == null) {
            return false;
        } else {
            if (term.length() == 1) {
                if (node.weight != null) {
                    if (node.size() == 1) {
                        //delete completely
                        children[index] = null;
                    } else {
                        node.weight = null;
                    }
                    return true;
                }
                return false;
            }
            return node.delete(term.substring(1));
        }
    }

    /**
     * Return all terms including this ones.
     *
     * @return
     */
    void collectTerms(String pref, Queue<String> terms) {
        if (this.weight != null) {
            terms.enqueue(pref);
        }
        for (int i = 0; i < children.length; i++) {
            TrieNode child = children[i];
            if (child != null) {
                char letter = (char) (i + 'a');
                child.collectTerms(pref + letter, terms);
            }
        }
    }

    Optional<TrieNode> findNode(String pref) {
        final int index = getIndex(pref);
        final TrieNode node = children[index];
        if (node == null) {
            return Optional.empty();
        } else {
            if (pref.length() == 1) {
                return Optional.of(node);
            }
            return node.findNode(pref.substring(1));
        }
    }
}
