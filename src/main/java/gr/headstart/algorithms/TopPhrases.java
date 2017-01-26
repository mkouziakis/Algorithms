package gr.headstart.algorithms;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * I think the most important challenge of this exercise is to find a way to use the minimum amount
 * of memory possible and of course less than the actual size of the input. You must save at least
 * each unique phrase once so you have to think of a way to use less than the total space needed.
 * I initially thought of creating some kind of catalog for all the words that exist in the input file
 * and use some kind of indexes instead of the actual words to keep phrases. I abandoned this solution
 * because words are random, so there may be too many of them and in order to preserve a unique index
 * for each of them you may need more space than the actual input.
 *
 * I came up with a solution that uses a data structure called "Trie". It is a tree structure in which
 * each node do not hold the key for finding it but the key is created by combining element's parent
 * nodes values(https://en.wikipedia.org/wiki/Trie). It is usually used for keeping strings and you
 * actually save the space used for strings with equal prefixes. The phrases are inserted into the
 * tree using recursion so as every phrase is a branch consisting of nodes and each subsequent node
 * represents the next character of the phrase. The leaf node of the branch holds a counter that is
 * incrementing every time a phrase is fully inserted.
 *
 * After inserting all the phrases I recursively traverse the tree nodes and every time I found a counter > 0,
 * I insert the phrase-counter pair in a priority queue where the maximum length is the number of
 * requested top phrases. I used the priority queue because it gives you direct access to the minimum
 * element(in our case the pair with the minimum counter) in only O(log(n)) and you can instantly
 * remove it. So I end up having a priority queue with the top n elements :-)
 *
 * The insertion of an element is a series of gets from Hashmaps and since the keys are chars each get
 * has O(1) complexity so the total complexity for each phrase is O(k) where k is the number of
 * characters. If we consider that the mean all lengths of the phrases is a constant factor then the
 * complexity for all the insertions is O(n). Traversing the tree is also O(n) for the same reasons.
 * Putting an element in the priority queue, as stated in the documentation, costs O(log(n)). So,
 * considering all the above, I would say that the time complexity of the algorith is O(n).
 *
 * @Author KouziaMi
 * @Date 6/1/2017.
 */
public class TopPhrases {
    private final static Logger logger = Logger.getLogger(TopPhrases.class.getName());

    Trie trie = new Trie();
    Queue<Phrase> priorityQueue = new PriorityQueue<>();

    public static void main(String[] args) {
        // read the input as "filename topPhrasesCount"
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split("\\s");

        String filename = input[0];
        int numOfTopElements = Integer.valueOf(input[1]);
        Queue<Phrase> results = new TopPhrases().find(filename, numOfTopElements);

        logger.info("Results: " + results);
    }

    public Queue<Phrase> find(String filePath, int numOfTopElements) {
        Path file = Paths.get(filePath);

        //handle invalid inputs
        if (file == null || !file.toFile().exists()){
            logger.warning("File " + filePath + " is not valid.");
            return null;
        }

        if (numOfTopElements <= 0){
            logger.warning("Number of top elements must be a positive integer");
            return null;
        }

        try {
            // read the file using Streams api, more efficient than the "Scanner" approach
            // but requires Java 8
            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);
            for (String line : (Iterable<String>) lines::iterator) {
                String[] phrases = line.split("\\|");
                for (String phrase : phrases) {
                    // Insert each parsed phrase in the tree
                    trie.insertToTrie(phrase);
                }
            }

            // traverse all the trie nodes and reconstruct the phrases using a StringBuilder
            // add the phrases to the priority queue, removing the smallest phrase count element
            // if the one added has a higher phrase count. Do not allow more than 10000 elements
            // to be inserted
            trie.findTopElements(numOfTopElements);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return priorityQueue;
    }

    class Trie {
        Map<Character, TrieElement> children = new HashMap<>();
        StringBuilder wordBuilder = new StringBuilder();

        public void insertToTrie(String phrase) {
            if (phrase != null && phrase.length() > 0) {
                // find or create the appropriate element for the first char
                char first = phrase.charAt(0);
                if (!trie.children.containsKey(first)) {
                    trie.children.put(first, new TrieElement(first));
                }

                TrieElement child = trie.children.get(first);
                // invoke the actual recursive function
                child.insert(phrase, 1);
            }
        }

        public void findTopElements(int numOfTopElements) {
            for (TrieElement element : children.values()) {
                // invoke the "traverse and add" recursive function
                element.addToQueue(wordBuilder, numOfTopElements);
            }
        }
    }

    class TrieElement {
        char character;
        long phraseCount = 0;
        Map<Character, TrieElement> children = new HashMap<>();

        public TrieElement(char character) {
            this.character = character;
        }

        public void insert(String phrase, int i) {
            if (phrase.length() >= i + 1) {
                // if there are more chars in the phrase invoke the recursive
                // function of the correct child
                char c = phrase.charAt(i);
                if (!children.containsKey(c)) {
                    children.put(c, new TrieElement(c));
                }

                TrieElement child = children.get(c);
                child.insert(phrase, i + 1);
            } else {
                //increment the counter and return
                phraseCount++;
            }
        }

        public void addToQueue(StringBuilder wordBuilder, int topElements) {
            wordBuilder.append(character);
            if (phraseCount > 0) {
                if (priorityQueue.size() >= topElements) {
                    Phrase minOccurrencesPhrase = priorityQueue.peek();
                    if (minOccurrencesPhrase.phraseCount < phraseCount) {
                        priorityQueue.add(new Phrase(phraseCount, wordBuilder.toString()));
                        priorityQueue.poll();
                    }
                } else {
                    priorityQueue.add(new Phrase(phraseCount, wordBuilder.toString()));
                }
            }
            for (TrieElement element : children.values()) {
                element.addToQueue(wordBuilder, topElements);

            }
            // we don't need these anymore...
            children.clear();

            wordBuilder.deleteCharAt(wordBuilder.length() - 1);
        }
    }

    class Phrase implements Comparable {
        long phraseCount;
        String phrase;

        public Phrase(long phraseCount, String phrase) {
            this.phraseCount = phraseCount;
            this.phrase = phrase;
        }

        @Override
        public int compareTo(Object o) {
            Phrase phrase = (Phrase) o;
            return this.phraseCount > phrase.phraseCount ? 1 : (this.phraseCount < phrase.phraseCount ? -1 : 0);
        }

        @Override
        public String toString() {
            return "{" + phrase +" : " + phraseCount + '}';
        }
    }
}
