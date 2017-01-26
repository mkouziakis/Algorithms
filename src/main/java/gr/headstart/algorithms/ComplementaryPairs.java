package gr.headstart.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Well, the most important thing in this exercise is to understand what exactly is the
 * desired result. I understand that we have to find the pairs of indices, not the pairs of values
 * neither the count of pairs. I assume that these pairs are not directional ie (4,5) is the same as
 * (5,4)
 * The first solution I thought was a double loop where you add every element with every
 * other and keep the combinations that their values are complementary. This solution has constant
 * space complexity but a very bad O(n(n-1)/2) = O(n^2) time complexity which is the same for best, average
 * and worst scenario.
 * The solution I came up with is a O(n^2) solution with O(n) space complexity for the worst scenario
 * where all the elements are equal and complementary. For the best scenario the time complexity is O(n) and for the
 * average scenarios is related to the number of discovered pairs. There are 2 loops, the 1st
 * stores the indices of the array as a list in a map, grouped by the values of the array. The 2nd loop finds
 * for each array value the indices of the complementary value  and creates the pairs. Each index that has
 * been used is removed from the the map in order to avoid creating duplicate
 * pairs(ie [4,5], [5,4]).
 * I assume that all java Map operations used is performed in constant time. That is not far
 * from true for the given scenario since elements are Integers and their hashcode is the integer itself
 * so we do not have keys with same hashcodes, which lead to non constant time for Map operations.
 *
 * @Author KouziaMi
 * @Date 5/1/2017.
 */
public class ComplementaryPairs {

    private final static Logger logger = Logger.getLogger(ComplementaryPairs.class.getName());

    public static void main(String[] args) {
        // read the input from the console
        // the first number is k
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] svalues = input.split("\\s");
        int k = Integer.valueOf(svalues[0]);
        List<Integer> values = new ArrayList<Integer>();
        for (int i = 1; i < svalues.length; i++) {
            values.add(Integer.valueOf(svalues[i]));
        }

        List results = new ComplementaryPairs().calculate(k, values.toArray(new Integer[0]));
        logger.info("results:" + String.valueOf(results));
    }

    public List calculate(int k, Integer[] integers) {

        // check for null or empty input
        if (integers == null || integers.length == 0) {
            return null;
        }

        List<Pair> results = new ArrayList<>();

        // we will use this map to store arrays of the indices of every unique value
        Map<Integer, List<Integer>> valuesMap = new HashMap<>();

        // iterate through the values and store indices in the corresponding lists
        for (int i = 0; i < integers.length; i++){ // O(n)
            if (valuesMap.containsKey(integers[i])){
                List<Integer> indices = valuesMap.get(integers[i]); // O(1)
                indices.add(i); // O(1)
            } else {
                List<Integer> indices = new ArrayList<>();
                indices.add(i); // O(1)
                valuesMap.put(integers[i], indices); // O(1)
            }
        }


        for (int i = 0; i < integers.length; i++){ // O(n)
            // find the complementary value
            int complValue = k - integers[i];
            // check if this value is stored in the Map
            if (valuesMap.containsKey(complValue)){ // O(1)
                // if it is, take the corresponding indeces and create the pairs
                List<Integer> indices = valuesMap.get(complValue);
                for (int j = 0; j < indices.size(); j++){ // O(n)
                    // if value == complValue you will find the same index in the list. don't use it
                    if (i != indices.get(j)){
                        results.add(new Pair(i, indices.get(j))); // O(1)
                    }
                }

                // find the index of the current element in the map and remove it, you don't need it
                List valueIndices = valuesMap.get(integers[i]); // O(1)
                valueIndices.remove(Integer.valueOf(i)); // O(1)
            }
        }

        return results;
    }

    class Pair {
        public Pair(int index1, int index2) {
            this.firstIndex = index1;
            this.lastIndex = index2;
        }

        int firstIndex;
        int lastIndex;

        @Override
        public String toString() {
            return "[" + firstIndex + "," + lastIndex + "]";
        }
    }
}
