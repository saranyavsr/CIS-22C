import java.util.NoSuchElementException;

public class Queue {
	private int n;     
	private Node first;    
	private Node last; 
	public int item1;

	private class Node {
		private int item;
		private Node next;
	}

	public Queue() {
		first = null;
		last  = null;
		n = 0;
	}

	public int last() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		return last.item;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return n;     
	}

	public int length() {
		return n;     
	}

	public int peek() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		return first.item;
	}

	public void enqueue(int item) {
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty()) first = last;
		else           
			oldlast.next = last;
		n++;
	}

	public int dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		int item = first.item;
		first = first.next;
		n--;
		if (isEmpty()) last = null;   
		return item;
	}
}
