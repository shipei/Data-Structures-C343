interface Iterator<E>{
	E get();
    void set(E e);
    Iterator<E> advance();
    boolean equals(Iterator<E> other);
    Iterator<E> clone();
}
	
class Node<E> {
    Node(E d, Node<E> n) { data = d; next = n; }
    E data;
    Node<E> next;
}
	
class ListIter<E> implements Iterator<E> {
    Node<E> curr;
    ListIter(Node<E> n) { curr = n; }
    public E get() { return curr.data; }
    public void set(E e) { curr.data = e; }
    public Iterator<E> advance() { curr = curr.next; return this; }
    public boolean equals(Iterator<E> other) { 
        return curr == ((ListIter<E>)other).curr; 
    }
    public Iterator<E> clone() {
        return new ListIter<E>(curr);
    }
}

class List<E> {
    Node<E> head;
    List() { head = null; }
    public void addFirst(E d) {
        head = new Node<E>(d, head);
    }
    public Iterator<E> begin() { return new ListIter<E>(head); }
    public Iterator<E> end() { return new ListIter<E>(null); }
} // List

class ArrayIter<E> implements Iterator<E> {   
	Object[] data;
	int pos;
    ArrayIter(Object[] d, int p) { data = d; pos = p; }
    public E get() { return (E)data[pos]; }
    public void set(E e) { data[pos] = e; }
    public Iterator<E> advance() {
        ++pos;
        return this;
    }
    public boolean equals(Iterator<E> other) { 
        return pos == ((ArrayIter<E>)other).pos; 
    }
    public Iterator<E> clone() {
        return new ArrayIter<E>(data, pos);
    }
}

class Array<E> {
    Object[] data;
    int num_elements;
    
    
    Array() {
        data = new Object[10];
        num_elements = 0;
    }
    public void add(E d) {
        if (num_elements >= data.length) {
            resize(data.length * 2);
        }
        data[num_elements] = d;
        ++num_elements;
    }

    void resize(int s) {
        Object[] new_data = new Object[s];
        for (int i = 0; i != num_elements; ++i) {
            new_data[i] = data[i];
        }
        data = new_data;
    }
    
    public ArrayIter<E> begin() { return new ArrayIter<E>(data, 0); }
    public ArrayIter<E> end() { return new ArrayIter<E>(data, num_elements); }
} // Array

public class Main <E extends Comparable<? super E>>{
    
	
    public Iterator<E> partition(Iterator<E> begin, Iterator<E> end) {
    	// TODO Auto-generated method stub
		Iterator<E> pre = begin.clone();
		Iterator<E> cur = begin.clone();
		cur.advance();
		while (!cur.equals(end)){
			pre.advance();
			cur.advance();
		}
		Iterator<E> last = pre;
		E pivot = last.get();
    	Iterator<E> pIter = begin.clone();
    	Iterator<E> i = begin.clone();
    	Iterator<E> prev = null; 
    	for(i = pIter.clone(); ! i.equals(last); i.advance()) {
    		if(i.get().compareTo(pivot) < 1) {  
    			//swap i with the element in pIndex position
    			E temp = i.get();
    			i.set(pIter.get());
    			pIter.set(temp);
    			pIter.advance();
    		}
    	}	
    	//swap pIter with pivot
    	last.set(pIter.get());
    	pIter.set(pivot);
    	//return Iterator
    	return pIter;
    }
    
//    static <E extends Comparable<? super E>>
    void quicksort(Iterator<E> begin, Iterator<E> end) {
        // put your solution here
    	if(!begin.equals(end)) {
    	Iterator<E> prev = partition(begin, end);
    	quicksort(begin, prev);
    	quicksort(prev.advance(), end);
    	}
    }

	public static void main(String[] args) {
		Main a = new Main();
		Array<Integer> arr = new Array();
    	arr.add(17);
    	arr.add(2);
    	arr.add(22);
    	arr.add(5);
    	arr.add(18);
    	arr.add(6);
    	arr.add(3);
    	arr.add(9);
    	arr.add(8);
    	arr.add(7);
		ArrayIter<Integer> begin= arr.begin();
		ArrayIter<Integer> end  = arr.end();
		System.out.println("array before quick sort: \n");
	   	for(int i = 0; i != arr.num_elements; i++) {
    		System.out.print(arr.data[i] + " ");
    	}
	   	System.out.print("\n");
    	a.quicksort(begin, end);
    	System.out.println("array after quick sort: \n");
    	for(int i = 0; i != arr.num_elements; i++) {
    		System.out.print(arr.data[i] + " ");
    	}
    	
    }

}