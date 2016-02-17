package java_project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Path path = Paths.get("/home/krasi_b2/java/directory_tree");

		Scanner reader = new Scanner(System.in); // Reading from System.in
		System.out.println("Enter a word you want to search for : ");
		String word = reader.next();

		QueueStore queue = new QueueStore();

		Consumer.setKeyword(word);

		int numberProducers = 3;
		int numberConsumers = 3;

		Thread[] producers = new Thread[numberProducers];
		Thread[] consumers = new Thread[numberConsumers];

		for (int i = 0; i < numberConsumers; i++) {
			producers[i] = new Producer(queue, path);
		}

		for (int i = 0; i < numberConsumers; i++) {
			consumers[i] = new Consumer(queue);
		}

		for (Thread producer : producers) {
			producer.start();
		}

		for (Thread consumer : consumers) {
			consumer.start();
		}

		for (Thread producer : producers) {
			producer.join();
		}

		for (Thread consumer : consumers) {
			consumer.join();
		}
	}

}
