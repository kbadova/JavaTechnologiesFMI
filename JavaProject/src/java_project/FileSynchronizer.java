package java_project;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileSynchronizer extends SimpleFileVisitor<Path> {

	private Map<String, Boolean> visitedFiles;
	private QueueStore queue;

	public FileSynchronizer(QueueStore queue) {
		visitedFiles = new ConcurrentHashMap<>();
		this.queue = queue;
	}

	public boolean isVisited(String name) {
		if (null == visitedFiles.get(name)) {
			return false;
		}
		return true;
	}

	public synchronized boolean tryVisitFile(String name) {
		if (isVisited(name)) {
			return false;
		}

		visitedFiles.put(name, true);
		return true;
	}

	public void readLines(File file) throws FileNotFoundException, IOException {
		String line;
		long row = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((line = reader.readLine()) != null) {
				row++;
				queue.add(new Line(file.getName(), row, line));
			}
		}
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
			throws FileNotFoundException, IOException {
		if (tryVisitFile(file.getFileName().toString())) {

			readLines(file.toFile());
		}
		return CONTINUE;
	}
}
