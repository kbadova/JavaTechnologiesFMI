package java_project;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueStore {
	private BlockingQueue<Line> queue;
	private boolean areProducersDone;

	public QueueStore() {
		queue = new LinkedBlockingQueue<>();
		areProducersDone = false;
	}

	public void add(Line data) {
		synchronized (queue) {
			queue.add(data);
			queue.notify();
		}
	}

	public Line get() {
		synchronized (queue) {
			while (queue.isEmpty()) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return queue.poll();
		}
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public boolean isProducingFinished() {
		return areProducersDone;
	}

	public void setFinished() {
		this.areProducersDone = true;
	}
}
