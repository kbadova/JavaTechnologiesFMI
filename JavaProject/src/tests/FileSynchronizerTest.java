package tests;

import static org.junit.Assert.*;

import org.junit.*;

import java_project.FileSynchronizer;
import java_project.QueueStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileSynchronizerTest {

	private File filename;
	private QueueStore queue;
	private FileSynchronizer fileSynch;

	@Before
	public void initalize() {
		filename = new File(
				"/home/krasi_b2/java/directory_tree/first/first.1/hello.txt");
		queue = new QueueStore();
		fileSynch = new FileSynchronizer(queue);
	}

	@Test
	public void testReadLines() throws FileNotFoundException, IOException {
		fileSynch.readLines(filename);
		assertFalse(queue.isEmpty());
	}

	@Test
	public void testIsVisited() {
		fileSynch.tryVisitFile(filename.toString());
		assertEquals(true, fileSynch.isVisited(filename.toString()));
	}

	@Test
	public void testIsNotVisited() {
		assertEquals(false, fileSynch.isVisited(filename.toString()));
	}
}
