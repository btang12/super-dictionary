import static org.junit.jupiter.api.Assertions.fail;

import java.awt.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class GraphProcessorTest {
	GraphProcessor graph;
	String expected;
	String actual;

	@Before
	public void setUp() throws Exception {
		this.graph = new GraphProcessor();
	}
	@After
	public void tearDown() throws Exception {
		this.graph = null;
	}
	@Test
	//
	void test0_getShortestPath_no_vertices() {
		expected = null;
		List vertices = graph.getShortestPath("A","B");
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test1_getShortestDistance_no_vertices() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test2_populate_with_invalid_filepath() {
		expected = -1;
		actual = graph.populate("A");
		fail("Not yet implemented");
	}
	@Test
	void test3_shortestPathPrecomputation_no_vertices() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test4_populate_one_verticy() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test5_getShortestPath_one_verticy() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test6_getShortestDistance_one_verticy() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test7_shortestPathPrecomputation_one_verticy() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test8_populate_multiple_vertices() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test9_getShortestPath_short() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test10_getShortestDistance_short() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test11_shortestPathPrecomputation_short() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test10_getShortestDistance_long() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	@Test
	void test11_shortestPathPrecomputation_long() {
		expected =
		actual =
		fail("Not yet implemented");
	}
	

}
