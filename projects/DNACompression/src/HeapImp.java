import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;
    
public class HeapImp<E> implements Heap<E> {
    private ArrayList<E> data;
    private Comparator<E> compare;
    private int heapSize;

    HeapImp(ArrayList<E> data, Comparator<E> compare) {
	this.data = data;
	this.compare = compare;
	buildMinHeap();
    }

    @Override
    public E minimum() {
    	return data.get(0);
    }

    @Override
    public void insert(E e) {
    	data.add(data.size(), e);
    	int num_elts = data.size();
    	decreaseKey(num_elts-1);
    }

    @Override
    public E extractMin() {
    	int num_elts = data.size();
    	E min = data.get(0);
    	data.set(0, data.get(num_elts-1));
    	data.remove(num_elts-1);
    	minHeapify(0);
    	return min;
    }

    private void decreaseKey(int i) {
    	int parent = (int) Math.floor((i-1)/2);
    	if(compare.compare(data.get(i), data.get(parent)) == -1) {
    		E temp = data.get(i);
    		data.set(i, data.get(parent));
    		data.set(parent, temp);
    		decreaseKey(parent);
    	}
    }
    
    
    @Override
    public void minHeapify(int i) {
        int smallest;
        int num_elts = data.size(); 
        //find the smallest element at position i, left and right:
        if(num_elts > 1 && compare.compare(data.get(2*i+1), data.get(i)) == -1) {
        	smallest = 2*i+1;  	
        }        
        else {
        	smallest = i;
        }
        if(num_elts > 2*i+2 && compare.compare(data.get(2*i+2), data.get(smallest)) == -1) {
        	smallest = 2*i+2;
        }
        if(smallest != i && smallest < num_elts/2) {
        	//swap values in position i with the one in smallest:
        	E temp = data.get(i);
        	data.set(i, data.get(smallest));
        	data.set(smallest, temp);
        	minHeapify(smallest);
        }
        //if is leaf, swap one more time:
        else if(smallest != i && smallest >= num_elts/2 && smallest <= num_elts) {
           	E temp2 = data.get(i);
        	data.set(i, data.get(smallest));
        	data.set(smallest, temp2);
        }
        else
        	return;
    }
    

    @Override
    public void buildMinHeap(){
        int last_parent = data.size()/2-1;
        for(int i = last_parent; i >= 0; --i) {
        	minHeapify(i);
        }
    }

    private void sortHeap() {
    	
    }
    
    public String toString() {
    	return data.toString();
    }
    
 
    public static void main(String[] args) {    	
    	ArrayList<Integer> test = new ArrayList<Integer>();
    	  Comparator<Integer> comparator = new Comparator<Integer>() {
    	        @Override
    	        public int compare(Integer o1, Integer o2) {
    	            int res = o1.compareTo(o2);
    	            if (res == 0)
    	                return o1 < o2 ? -1 : 1;
    	            else
    	                return res;
    	        }
    	    };

    	HeapImp<Integer> heap = new HeapImp<Integer>(test, comparator);
    	test.add(1);
    	test.add(2);
    	test.add(3);
    	test.add(17);
    	test.add(19);
    	test.add(36);
    	test.add(7);
    	test.add(0);
    	test.add(100);
//    	test.add(10);
//    	test.add(44);
//    	test.add(19);
//    	test.add(14);
//    	test.add(31);
//    	test.add(42);
//    	test.add(27);
//    	test.add(26);
//    	test.add(35);
//    	test.add(33);
//    	heap.minHeapify(1);
//    	heap.buildMinHeap();
//    	System.out.println(heap.toString());
//    	heap.extractMin();
    	heap.decreaseKey(7);
    	System.out.println(heap.toString());
    	//System.out.println(heap.minimum());
    }
}
