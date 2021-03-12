// Kasper Rosenberg karo5568

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MySkipList<T extends Comparable<T>> {

	private int maxLevels;
	private Node first;
	private static final Random RANDOM = new Random();

	public MySkipList(int maxLevels) {
		this.maxLevels = maxLevels;
		first = new Node(null, maxLevels);
	}

	public void insert(T data) {
		Node<T> newNode = new Node(data);
		Node<T> previous = first;
		int levels = previous.levels.length - 1;

		for (int i = levels; i >= 0; i--) {
			while (previous.next != null && previous.levels[i].data.compareTo(data) < 0) {
				previous = previous.levels[i];
				levels = previous.levels.length - 1;
			}
		}
		Node<T> next = previous.levels[0];

		for (int i = 0; i < newNode.levels.length; i++) {
			if (next != null && i < next.levels.length) {
				newNode.levels[i] = next;
			}
			if (i < previous.levels.length) {
				previous.levels[i] = newNode;
			}
		}

		for (int i = first.levels.length - 1; i >= 0; i--) {
			if (first.levels[i] == null && newNode.levels.length >= i) {
				first.levels[i] = newNode;
			}
		}
	}

	public void remove(T data) {
		Node previous = first;
		int levels = previous.levels.length - 1;

		for (int i = levels; i >= 0; i--) {
			while (previous.levels[i] != null && previous.levels[i].data.compareTo(data) < 0) {
				previous = previous.levels[i];
				levels = previous.levels.length - 1;
			}
		}
		Node<T> next = previous.levels[0].levels[0];
		for (int i = 0; i < previous.levels.length; i++) {
			if (next != null && i < next.levels.length) {
				previous.levels[i] = next;
			} else {
				previous.levels[i] = null;
			}
		}
	}

	public boolean contains(T data) {
		Node<T> previous = first;
		int levels = previous.levels.length - 1;
		for (int i = levels; i >= 0; i--) {
			while (previous.levels[i] != null && previous.levels[i].data.compareTo(data) < 0) {
				previous = previous.levels[i];
				levels = previous.levels.length - 1;
			}
		}
		if (previous.levels[0] != null) {
			System.out.println("true");
			return previous.levels[0].data == data || previous.levels[0].data.equals(data);
		} else {
			System.out.println("false");
			return false;
		}
	}

	@Override
	public String toString() {
		String s = "[";
		Node<T> previus = first;
		while (previus.levels[0] != null) {
			if (previus.levels[0].levels[0] == null) {
				s += previus.levels[0].data;
			} else {
				s += previus.levels[0].data + ", ";
			}
			previus = previus.levels[0];
		}
		return s + "]";
	}

	int setHeight() {
		int n = 0;
		int level = 0;
		while (n != 1 && level < maxLevels) {
			n = RANDOM.nextInt(2);
			level++;
		}
		return level;
	}

	private class Node<T extends Comparable<T>> {
		T data;
		Node next;
		Node<T>[] levels;
		

		Node(T data) {
			this(data, setHeight());
		}

		Node(T data, int levels) {
			this.data = data;
			this.levels = new Node[levels];
		}
		@Override
		public String toString() {
			if(data == null) {
				return "";
			}
			return data.toString();
		}
		
	}

	public static void main(String[] args) {

		MySkipList s = new MySkipList(4);

		s.insert(9);
		s.insert(4);
		s.insert(7);
		s.insert(2);
		s.insert(15);
		System.out.println("insert är klar, och nu ser det ut så här:");
		System.out.println(s);
		System.out.println();

		System.out.println("Nu börjar remove");
		s.remove(15);
		System.out.println("remove är klar, och nu ser det ut så här:");
		System.out.println(s);

		System.out.println("Nu börjar contains");
		s.contains(15);

	}
}
