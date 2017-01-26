package gr.headstart.algorithms;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author KouziaMi
 * @Date 5/1/2017.
 */
public class PalindromeTest {
    Palindrome palindrome;
    @Before
    public void setUp() throws Exception {
        palindrome = new Palindrome();
    }

    @Test
    public void testCheck_happy() throws Exception {
        String s = "1221";
        assertTrue(palindrome.check(s));
    }

    @Test
    public void testCheck_not_palindrome() throws Exception {
        String s = "1222";
        assertFalse(palindrome.check(s));
    }

    @Test
    public void testCheck_null_input() throws Exception {
        String s = null;
        assertFalse(palindrome.check(s));
    }

    @Test
    public void testCheck_empty_string_input() throws Exception {
        String s = "";
        assertTrue(palindrome.check(s));
    }

    @Test
    public void testCheck_odd_length_input() throws Exception {
        String s = "123321";
        assertTrue(palindrome.check(s));
    }

    @Test
    public void testCheck_even_length_input() throws Exception {
        String s = "1234321";
        assertTrue(palindrome.check(s));
    }
}