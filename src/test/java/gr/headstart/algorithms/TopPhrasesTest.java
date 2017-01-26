package gr.headstart.algorithms;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

/**
 * @Author KouziaMi
 * @Date 6/1/2017.
 */
public class TopPhrasesTest {
    private final static Logger logger = Logger.getLogger(TopPhrasesTest.class.getName());
    TopPhrases topPhrases;

    @Before
    public void setUp() throws Exception {
        String file = "test.txt";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file))) {
            writer.write("Foobar Candy | Olympics 2012 | PGAr | CNETx | Microsoft Bingw \n");
            writer.write("Foobar Candy | Olympicsb 2012 | PGA | CNET3 | Microsoft Bingx \n");
            writer.write("Foobara Candy | Olympics 2012 | PGAd | CNETt | Microsoft Bingd \n");
            writer.write("Foobar Candy | Olympicsb 2012 | PGA | CNETx | Microsoft Bingx \n");
            writer.write("Foobar Candy | Olympics 2012 | PGAt | CNET | Microsoft Bingg \n");
        }

        topPhrases = new TopPhrases();
    }

    @Test
    public void testInsertToTrie_happy_path() throws Exception {
        Queue<TopPhrases.Phrase> results = topPhrases.find("test.txt", 3);
        logger.info("Results: " + results);
        assertTrue(results.size() == 3);
        assertTrue(results.poll().phraseCount == 2); // result { CNETx  : 2}
        assertTrue(results.poll().phraseCount == 3); // result { Olympics 2012  : 3}
        assertTrue(results.poll().phraseCount == 4); // result {Foobar Candy  : 4}
    }

    @Test
    public void testInsertToTrie_invalid_file() throws Exception {
        Queue<TopPhrases.Phrase> results = topPhrases.find("blahblah.txt", 3);
        logger.info("Results: " + results);
        assertTrue(results == null);
    }

    @Test
    public void testInsertToTrie_invalid_top_elements() throws Exception {
        Queue<TopPhrases.Phrase> results = topPhrases.find("test.txt", 0);
        logger.info("Results: " + results);
        assertTrue(results == null);
    }
}