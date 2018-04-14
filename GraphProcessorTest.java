import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class GraphProcessorTest {
	GraphProcessor graph;
	String expected;
	String actual;

	@Before
	public void setUp() throws Exception {
		List<String> dict = new ArrayList<String>();
		dict.add("cat");
		dict.add("rat");
		dict.add("hat");
		dict.add("neat");
		dict.add("wheat");
		dict.add("kit");
		this.graph = new GraphProcessor();
	}
	@After
	public void tearDown() throws Exception {
		this.graph = null;
	}
	@Test
	void test0_populate_with_invalid_filepath() {
		assertEquals("-1",graph.populate("A") + "");
	}
	@Test
	void test1_populate_valid_filepath() {
		assertEquals("6", graph.populate("testWordTest.txt"));
	}
	@Test
	void test2_invalid_filepath_log() {
		try {
			graph.populate("A");
			fail("Graph.populate() Did not throw IOException when invalid filepath passed");
		} catch(Exception e) {}
		
	}
	@Test
	void test_getShortestDistance_multiple_vertices()
	{
		assertEquals("3",graph.getShortestDistance("cat", "wheat") + "");
	}
	@Test
	void test_getShortestDistance_same_vertices()
	{
		assertEquals("-1", graph.getShortestDistance("cat", "cat") + "");
	}
	@Test
	void test_getShortestDistance_no_path()
	{
		assertEquals("-1", graph.getShortestDistance("cat", "bat"));
	}
	@Test
	void test_isAdjacent_non_adj_words()
	{
		assertFalse(WordProcessor.isAdjacent("int, "wiki"));
	}
	@Test 
	void test_isAdjacent_adj_words_add()
	{
		assertTrue(WordProcessor.isAdjacent("bat","bath"));
	}
	@Test
	void test_isAdjacent_adj_words_replace()
	{
		assertTrue(WordProcessor.isAdjacent("bat", "cat"));
	}
	@Test
	void test_isAdjacent_adj_words_delete()
	{
		assertTrue(WordProcessor.isAdjacent("bat", "ba"));
	}

}
