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
	void test3_getShortestPath_multiple_vertices() {
		String[] path = new {
		List<String> path = graph.getShortestPath("cat", "wheat");
		for(int i = 0; i < path.size();i++)
		{
			assertEquals(, path.get(i));
		}
		
	}

}
