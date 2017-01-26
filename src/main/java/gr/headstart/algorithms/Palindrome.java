package gr.headstart.algorithms;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * A really easy way to solve this problem is to copy the input and reverse it.
 * Then you can compare the 2 strings and if they are equal you have a palindrome!
 * The problem with this approach is the space complexity which, since you create
 * a 2nd string of the same size, is O(n).
 * I used a different approach where I compare each char in the i position to the
 * corresponding char in the n-i position. In this case the time complexity is O(n/2)
 * and the space complexity is constant.
 *
 * @Author KouziaMi
 * @Date 5/1/2017.
 */

public class Palindrome {
    private final static Logger logger = Logger.getLogger(Palindrome.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final String palindromeCandidate = scanner.nextLine();
        boolean result = new Palindrome().check(palindromeCandidate);
        System.out.println("String " + palindromeCandidate + " is " + (result?"":"not ") + "palindrome");
    }

    public boolean check(String palindromeCandidate) {
        if (palindromeCandidate == null){
            logger.warning("Input string is null");
            return false;
        }

        final int n = palindromeCandidate.length();
        for (int i = 0; i < n /2; i++){ // O(n/2) = O(n)
            //Comparing each char starting from the beginning of the input to the
            //corresponding char by counting the same positions from the end of the input
            if (!(palindromeCandidate.charAt(i) == palindromeCandidate.charAt(n-i-1))){
                return false;
            }
        }
        return true;
    }
}
