package hw4;
import java.util.*;
import javafx.util.*;

public class hashTable {
	public int linear_probing(int k, int i) {
		return 0;
	}
	public int hash(int k) {
		return k % 10;
	}
	public void hashInsert(int k, int r) {
		int tombstone = -99999;
		int home = hash(k);
		int pos = hash(k);
		ArrayList<Pair<Integer, Integer>> HT = new ArrayList<>();
		for(int i = 0; (HT.get(pos) != null && HT.get(pos).getValue() == tombstone); i++) {
			int M = HT.size();                 
			pos = (home + linear_probing(k, i)) % M;
			assert HT.get(pos).getKey().compareTo(k) != 0:
				"Duplicates not allowed";
		}
		HT.add(pos, new Pair(k ,r));
	}
	
	public int search(int k) {
		int home = hash(k);
		int pos = hash(k);
		int tombstone = -99999;
		ArrayList<Pair<Integer, Integer>> HT = new ArrayList<>();
		int M = HT.size();
		for(int i = 1; (HT.get(pos) != null) && (HT.get(pos).getKey().compareTo(k) != 0) 
				&& (HT.get(pos).getValue() != tombstone); i++) {
			pos = (home + linear_probing(k, i)) % M;
		}
		if(HT.get(pos) == null) return -9999;  //key not in hash table
		else return HT.get(pos).getValue();
	}
	
	public void delete(int k) {
		int tombstone = -99999;
		ArrayList<Pair<Integer, Integer>> HT = new ArrayList<>();
		//find where the key k is by search method, 
		int v = search(k);
		// get the index of the pair that we want to remove: 
		int index = 0;
		for(int i = 0; i < HT.size(); ++i){
			if(HT.get(i).getKey() == k){
				index  = i;
				break;
			}
		}
		//and place a special mark in place of the deleted record(tomb stone):
		HT.set(index, new Pair(k, tombstone));
	}
	
	public static void main(String[] args) {
	}
}
