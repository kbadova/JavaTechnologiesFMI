package java_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Producer extends Thread {
	private FileSynchronizer visitor;
	private Path start;
	private QueueStore queue;

	public Producer(QueueStore queue, Path path) {
		this.queue = queue;
		this.start = path;
		visitor = new FileSynchronizer(queue);
	}

	@Override
	public void run() {
		synchronized (queue) {
			try {
				Files.walkFileTree(start, visitor);
				queue.setFinished();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
