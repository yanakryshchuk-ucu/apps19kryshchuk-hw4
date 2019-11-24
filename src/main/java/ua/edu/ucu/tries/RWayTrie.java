package ua.edu.ucu.tries;


import ua.edu.ucu.utils.Queue;
import ua.edu.ucu.utils.QueueIterable;

import java.util.*;

public class RWayTrie implements Trie {

    private TrieNode root = new TrieNode();

    @Override
    public void add(Tuple t) {
        root.add(t.term, t.weight);
    }


    @Override
    public boolean contains(String word) {
        return root.contains(word);
    }

    @Override
    public boolean delete(String word) {
        return root.delete(word);
    }

    @Override
    public Iterable<String> words() {
        final Queue<String> queue = new Queue<>();
        root.collectTerms("", queue);
        return new QueueIterable(queue);
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        if (s == null || s.length() < 2) {
            throw new IllegalArgumentException("Too short prefix");
        }
        Optional<TrieNode> node = root.findNode(s);
        if (node.isPresent()) {
            final Queue<String> queue = new Queue<>();
            node.get().collectTerms(s, queue);
            return new QueueIterable(queue);
        } else {
            return Collections.emptyList();
        }
    }

//    private Queue<TrieNode> traverseQueue() {
//        int head = 0;
//        Queue<TrieNode> queue = new Queue<>();
//        queue.enqueue(root);
//        boolean hasMoreChildren = true;
//        while (hasMoreChildren) {
//            final Queue<TrieNode> result = new Queue<>();
//            for (int i = 0; i < head; i++) {
//                result.enqueue(queue.dequeue());
//            }
//            final Queue<TrieNode> children = new Queue<>();
//            while (!queue.empty()) {
//                final TrieNode node = queue.dequeue();
//                result.enqueue(node);
//                final Collection<TrieNode> nodeChildren = node.getChildren();
//                nodeChildren.forEach(children::enqueue);
//                head++;
//            }
//            hasMoreChildren = !children.empty();
//            while (!children.empty()) {
//                result.enqueue(children.dequeue());
//            }
//            queue = result;
//        }
//        return queue;
//    }

    @Override
    public int size() {
        return root.size();
    }
}
