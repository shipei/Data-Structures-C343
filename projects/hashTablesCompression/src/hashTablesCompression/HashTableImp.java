package hashTablesCompression;

import java.util.function.BiFunction;
import java.util.*;
import java.util.LinkedList;

class Entry<K,V> {
    Entry(K k, V v) { key = k; value = v; }
    final K key;
    V value;
    int hash;
}

@SuppressWarnings("unchecked")
public class HashTableImp<K,V> implements HashTable<K,V> {
    // an array of linked lists to handle chaining
    private LinkedList<Entry<K,V>>[] array;
    private int itemCount;
    private BiFunction<K,Integer,Integer> h;

    private int hash(K k){
 return this.h.apply(k,this.itemCount);
    }
    
    public HashTableImp(int initialCapacity, HashMethod hm){
 // This cast violates type safety of the program but we wanted
 // you to implement hash table on top of a data structure that
 // does not do dynamic resizing (arrays instead of ArrayList)
 // so you can do the hash table growing yourself.
 // Do NOT do it in the future.
    
   this.array = (LinkedList<Entry<K,V>>[]) new LinkedList<?>[initialCapacity];
   for(int i = 0; i < initialCapacity; i++) {
	   this.array[i] = new LinkedList<Entry<K,V>>();
   }
   this.itemCount = 0;
   switch (hm) {
   	case Div:
     // division method
     h = (k,m) -> {return k.hashCode() % m;};
     break;
 case MAD:
     h = (k,m) -> {return mad(k,m);};
     break;
 }
    }

    // Implement all the following stubs

    // Multiply-Add-and-Divide (MAD) hashing method
    private int mad(K k, int m) {
    	int hashIndex;
    	int p = primeNumberRange(m); //p: any prime number > m
    	int a = (int) Math.random() * (p - 1) + 1; //integer from range [1..(p-1)]
    	int b = (int) Math.random() * (p - 1); //integer from range [0..(p-1)]
    	hashIndex = (Math.abs(a * k.hashCode() + b) % p) % m;
    	return hashIndex;
    }
    private int primeNumberRange(int m) {
    	int res = 0;
    	for(int current = m+1; current < 100; current++) {
    		boolean is_prime = true;
    		int sqr_root = (int) Math.sqrt(current);
    		for(int i = 2; i < sqr_root; i++) {
    			if(current % i == 0)
    				is_prime = false;
    		}
    		if(is_prime)
    			res = current;
    	}
    	return res;
    }
    // this method needs to be called appropriately in the put method
    private void growTable() {
    	 LinkedList<Entry<K,V>>[] new_array = (LinkedList<Entry<K,V>>[]) new LinkedList<?>[this.array.length*2];
    	 for(int i = 0; i < new_array.length; i++) 
    		   new_array[i] = new LinkedList<Entry<K,V>>(); 
    	//rehash:
    	 for(LinkedList<Entry<K, V>> ll: this.array) {
    		 for(Entry<K, V> entry: ll) 
    			 new_array[hash(entry.key)].add(new Entry<K, V>(entry.key, entry.value));
    	 }
    	 this.array = new_array;
    }

    @Override
    //returns true if the hash table contains a mapping for the specified key.
    public boolean containsKey(K key) {
    	boolean res = false;
    	for(Entry<K, V> entry: this.array[hash(key)]) {
    		if(entry.key.equals(key)) {
    			res = true;
    			break;
    		}
    	}
    	return res;
    }

    @Override  
    //return the value associated with the key.
    public V get(K key) {
    	V val = null;
    	for(Entry<K, V> entry: this.array[hash(key)]) {
    		if(entry.key.equals(key))
    			val = entry.value;
    	}
    	return val;
    }

    @Override   
    //associates the value with the key within the hash table.
    public V put(K key, V value) {
    		this.itemCount++;
    		this.array[hash(key)].add(new Entry<K, V>(key ,value));
    		int count = 0;
    		for(LinkedList<Entry<K, V>> ll: this.array) {
    			if(ll.size() != 0)
    				count++;
    		}
    		if(count > this.array.length*0.75) 
    			growTable(); 
    	return value;
    }

    // not used in this client code so make sure it works correctly by
    // implementing unit tests.
    
    //returns true if the hash table contains no key-value mappings.
    @Override    
    public boolean isEmpty() {
    	boolean res = true;
    	for(LinkedList<Entry<K, V>> ll: this.array) {
    		if(ll.size() != 0) 
    			res = false;
    	}
    	return res;
    }

    @Override  
    //return a set containing all the keys in the hash table.
    public Set<K> keySet() {
    	Set<K> set = new HashSet<K>();
    	for(LinkedList<Entry<K, V>> ll: this.array) {
    		for(Entry<K ,V> entry: ll)
    			set.add(entry.key);
    	}
    	return set;
    }

    // not used in this client code so make sure it works correctly by
    // implementing unit tests.
    @Override 
    //remove the key-value item as specified by key.
    public V remove(K key) {
    	V val = null;
    	for(Entry<K, V> entry: this.array[hash(key)]) {
    		if(entry.key.equals(key)) {
    			val = entry.value;
    			this.array[hash(key)].remove(entry);
    		}
    	}
    	return val;
    }
}