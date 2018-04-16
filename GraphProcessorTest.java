import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphProcessorTest {
    GraphProcessor graph;
    String expected;
    String actual;

    /**
     * Instantiates a List of type String to contain the words to be inserted into the dictionary
     * and assigns the GraphProcessor reference to a new GraphProcessor object.
     */
    @Before
    public void setUp() {
        List<String> dict = new ArrayList<String>();
        dict.add("cat");
        dict.add("rat");
        dict.add("hat");
        dict.add("heat");
        dict.add("wheat");
        dict.add("kit");
        this.graph = new GraphProcessor();
    }

    /**
     * Clears the contents of the GraphProcessor instance.
     */
    @After
    public void tearDown() {
        this.graph = null;
    }

    /**
     * Verifies that the populateGraph method correctly returns -1 when an invalid filepath is
     * passed to it.
     */
    @Test
    public void test00_populate_with_invalid_filepath() {
        assertEquals("-1", graph.populateGraph("A") + "");
    }

    /**
     * Verifies that the populateGraph method correctly populates the graph with the data from a
     * valid filepath and subsequently returns the correct number of entries added.
     */
    @Test
    public void test01_populate_valid_filepath() {
        assertEquals("6", graph.populateGraph("src/testWordList.txt") + "");
    }

    /**
     * Verifies that the getShortestPath method correctly returns the shortest path between two
     * given vertices, by comparing the contents of an expected path List to the List returned by
     * passing in the "cat" and "wheat" vertices.
     */
    @Test
    public void test02_getShortestPath_multiple_vertices() {
        graph.populateGraph("src/testWordList.txt");
        List<String> path = graph.getShortestPath("cat", "wheat");
        List<String> expectedPath = new ArrayList<String>();
        expectedPath.add("cat");
        expectedPath.add("hat");
        for (int i = 0; i < path.size(); i++) {
            assertEquals(expectedPath.get(i), path.get(i));
        }
    }

    /**
     * Verifies that the getShortestPath returns null when two vertices are passed to it which do
     * not share a valid edge path.
     */
    @Test
    public void test03_getShortestPath_invalid_path() {
        graph.populateGraph("src/testWordList.txt");
        List<String> path = graph.getShortestPath("cat", "kit");
        for (String s : path)
            assertEquals(null, s);
    }

    /**
     * Verifies that the getShortestPath method returns null when two vertices are passed to the
     * method on an instance without any vertices contained within the graph.
     */
    @Test
    public void test04_getShortestPath_no_vertices() {
        GraphProcessor graph1 = new GraphProcessor();
        List<String> path = graph1.getShortestPath("cat", "wheat");
        assertEquals(null, path);
    }

    /**
     * Verifies that the getShortestPath method returns null when the same vertex is passed as both
     * parameters.
     */
    @Test
    public void test05_getShortestPath_same_vertices() {
        graph.populateGraph("src/testWordList.txt");
        List<String> path = graph.getShortestPath("cat", "cat");
        for (String s : path)
            assertEquals(null, s);
    }

    /**
     * Verifies that the getShortestDistance method returns the correct number of edges from the
     * source to the destination vertices along the shortest path.
     */
    @Test
    public void test06_getShortestDistance_multiple_vertices() {
        graph.populateGraph("src/testWordList.txt");
        assertEquals("3", graph.getShortestDistance("cat", "wheat") + "");
    }

    /**
     * Verifies that the getShortestDistance method returns -1 upon being passed the same vertex as
     * both parameters.
     */
    @Test
    public void test07_getShortestDistance_same_vertices() {
        graph.populateGraph("src/testWordList.txt");
        assertEquals("-1", graph.getShortestDistance("cat", "cat") + "");
    }

    /**
     * Verifies that the getShortestDistance method returns -1 upon being passed two vertices that
     * do not share a valid edge path.
     */
    @Test
    public void test08_getShortestDistance_no_path() {
        graph.populateGraph("src/testWordList.txt");
        assertEquals("-1", graph.getShortestDistance("cat", "bat") + "");
    }

    /**
     * Verifies that the getWordStream method in WordProcessor throws an IOException when an invalid
     * filepath is passed.
     */
    @Test
    public void test09_getWordStream_invalid_filepath() {
        boolean thrown = false;
        try {
            @SuppressWarnings("unused")
            Stream<String> words = WordProcessor.getWordStream("src/invalidFilepath.txt");
        } catch (IOException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Verifies that the getWordStream method in WordProcessor takes the data from a valid filepath
     * and that the method formats the data entries correctly.
     */
    @Test
    public void test10_getwordStream_valid_filepath() {
        try {
            List<String> words = WordProcessor.getWordStream("src/testWordList.txt")
                            .collect(Collectors.toList());
            for (int i = 0; i < words.size(); i++) {
                if (!words.get(i).equals(words.get(i).toUpperCase())) {
                    fail("Stream did not return all uppercase words");
                }
                if (words.get(i) == "") {
                    fail("Stream returned an empty line");
                }
                for (int j = 0; j < words.get(i).length(); j++) {
                    if (words.get(i).charAt(j) == ' ') {
                        fail("Stream did not trim all lines");
                    }
                }
            }
        } catch (IOException e) {
            fail();
        }
    }

}
