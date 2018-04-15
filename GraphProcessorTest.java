import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

	@Before
	public void setUp() throws Exception {
		List<String> dict = new ArrayList<String>();
		dict.add("cat");
		dict.add("rat");
		dict.add("hat");
		dict.add("heat");
		dict.add("wheat");
		dict.add("kit");
		this.graph = new GraphProcessor();
	} 
	@After
	public void tearDown() throws Exception {
		this.graph = null;
	}
	@Test
	public void test00_populate_with_invalid_filepath() {
		assertEquals("-1",graph.populateGraph("A") + "");
	}
	@Test
	public void test01_populate_valid_filepath() {
		assertEquals("6", graph.populateGraph("src/testWordList.txt")+"");
	}
	@Test
	public void test02_getShortestPath_multiple_vertices() {
		graph.populateGraph("src/testWordList.txt");
		List<String> path = graph.getShortestPath("cat", "wheat");
		List<String> expectedPath = new ArrayList<String>();
		expectedPath.add("cat");
		expectedPath.add("hat");
		expectedPath.add("heat");
		expectedPath.add("wheat");
		for(int i = 0; i < path.size(); i++)
		{
			assertEquals(expectedPath.get(i), path.get(i));
		}
	}
	@Test 
	public void test03_getShortestPath_invalid_path() {
		graph.populateGraph("src/testWordList.txt");
		List<String> path = graph.getShortestPath("cat", "kit");
		for(String s: path)
			assertEquals(null, s);
	}
	@Test
	public void test04_getShortestPath_no_vertices() {
		GraphProcessor graph1 = new GraphProcessor();
		List<String> path = graph1.getShortestPath("cat", "wheat");
		assertEquals(null, path);
	}
	@Test
	public void test05_getShortestPath_same_vertices() {
		graph.populateGraph("src/testWordList.txt");
		List<String> path = graph.getShortestPath("cat", "cat");
		for(String s: path)
			assertEquals(null, s);
	}
	@Test
	public void test06_getShortestDistance_multiple_vertices() {
		graph.populateGraph("src/testWordList.txt");
		assertEquals("3", graph.getShortestDistance("cat", "wheat") + "");
	} 
	@Test
	public void test07_getShortestDistance_same_vertices() {
		graph.populateGraph("src/testWordList.txt");
		assertEquals("-1", graph.getShortestDistance("cat", "cat") + "");
	}
	@Test
	public void test08_getShortestDistance_no_path() {
		graph.populateGraph("src/testWordList.txt");
		assertEquals("-1", graph.getShortestDistance("cat", "bat") + "");
	}
	@Test
	public void test09_getWordStream_invalid_filepath() {
		try {
			Stream<String> words = WordProcessor.getWordStream("src/testWordList.txt");
	}catch (IOException e) {
		fail("Could not get file at specified path" + e.getMessage());
	}	
	}
	@Test
	public void test10_getwordStream_valid_filepath() {
		try {
			List<String> words = WordProcessor.getWordStream("src/testWordList.txt").collect(Collectors.toList());
			for(int i = 0; i < words.size(); i++) {
				if(!words.get(i).equals(words.get(i).toUpperCase())) {
					fail("Stream did not return all uppercase words");
				}
				if(words.get(i) == "") {
					fail("Stream returned an empty line");
				}
				for(int j = 0; j < words.get(i).length(); j++) {
					if(words.get(i).charAt(j) == ' ') {
						fail("Stream did not trim all lines");
					}
				}
			}
		} catch (IOException e) {
			fail("Could not get file at specified path");
		}	
	}

}
