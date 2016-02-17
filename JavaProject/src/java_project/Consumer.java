package java_project;

public class Consumer extends Thread {
	private static String keyword;
	private QueueStore queue;

	public Consumer(QueueStore queue) {
		this.queue = queue;
	}

	public static void setKeyword(String key) {
		keyword = key;
	}

	public static String getKeyword() {
		return keyword;
	}

	@Override
	public void run() {
		synchronized (queue) {

			while (!queue.isProducingFinished() || !queue.isEmpty()) {
				Line data = queue.get();
				if (data.getLine().contains(keyword)) {
					System.out.println(data.toString());
				}
			}

		}
	}
}
