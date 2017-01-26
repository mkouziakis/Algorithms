package gr.headstart.algorithms;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

import gr.headstart.algorithms.ComplementaryPairs;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @Author KouziaMi
 * @Date 5/1/2017.
 */
public class ComplementaryPairsTest {
    private final static Logger logger = Logger.getLogger(ComplementaryPairsTest.class.getName());

    ComplementaryPairs complementaryPairs;

    @Before
    public void setUp() throws Exception {
        complementaryPairs = new ComplementaryPairs();

    }

    @Test
    public void testGetKComplementaryPairs_happy() throws Exception {
        logger.info("Input: 10, {3,1,9,5,7,4}");
        List results = complementaryPairs.calculate(10, new Integer[]{3,1,9,5,7,4});
        logger.info("Results: " + String.valueOf(results));
        assertTrue(results.size() == 2);
    }

    @Test
    public void testGetKComplementaryPairs_null_input() throws Exception {
        logger.info("Input: 10, null");
        List results = complementaryPairs.calculate(10, null);
        logger.info("Results: " + String.valueOf(results));
        assertNull(results);
    }

    @Test
    public void testGetKComplementaryPairs_empty_input() throws Exception {
        logger.info("Input: 10, {}");
        List results = complementaryPairs.calculate(10, new Integer[0]);
        logger.info("Results: " + String.valueOf(results));
        assertNull(results);
    }

    @Test
    public void testGetKComplementaryPairs_duplicates() throws Exception {
        logger.info("Input: 10, {7,9,5,4,3,1,3}");
        List results = complementaryPairs.calculate(10, new Integer[]{7,9,5,4,3,1,3});
        logger.info("Results: " + String.valueOf(results));
        assertTrue(results.size() == 3);
    }


    @Test
    public void testGetKComplementaryPairs_duplicates_2() throws Exception {
        logger.info("Input: 10, {3,1,3,9,5,7,5,7,5,4}");
        List results = complementaryPairs.calculate(10, new Integer[]{3,1,3,9,5,7,5,7,5,4});
        logger.info("Results: " + String.valueOf(results));
        assertTrue(results.size() == 8);
    }

    @Test
    public void testGetKComplementaryPairs_all_elements_equal() throws Exception {
        logger.info("Input: 10, {3,3,3,3,3}");
        List results = complementaryPairs.calculate(10, new Integer[]{3, 3, 3, 3, 3});
        logger.info("Results: " + String.valueOf(results));
        assertTrue(results.size() == 0);
    }

    @Test
    public void testGetKComplementaryPairs_all_elements_equal_and_matching() throws Exception {
        logger.info("Input: 10, {5,5,5,5,5}");
        List results = complementaryPairs.calculate(10, new Integer[]{5, 5, 5, 5, 5});
        logger.info("Results: " + String.valueOf(results));
        assertTrue(results.size() == 10);
    }
}