import java.util.Arrays;
import java.util.ArrayList;

class SearchResult {
    BTreeNode node;
    int pos;
    SearchResult(BTreeNode n, int p) { node = n ; pos = p; }
}

class BTreeNode {
    boolean is_leaf;
    ArrayList<Integer> keys;
    ArrayList<BTreeNode> children;
    
    BTreeNode(ArrayList<Integer> k, ArrayList<BTreeNode> c, boolean l) {
        keys = k; children = c; is_leaf = l;
    }

    // precondition: keys is a sorted array
    // returns: an integer i such that
    //   * if key is greater than all the keys, then i == keys.size()
    //   * otherwise, i is the lowest index where key <= keys.get(i)
    //   (So keys.add(i, key) produces a sorted array.)
    int key_search(int key) {
        for (int i = 0; i != keys.size(); ++i) {
            if (key <= keys.get(i)) {
                return i;
            }
        }
        return keys.size();
    }

    public SearchResult search(int key) {
        int i = key_search(key);
        if (i != keys.size() && key == keys.get(i)) {
            return new SearchResult(this, i);
        } else if (is_leaf) {
            return null;
        } else {
            return children.get(i).search(key);
        }
    }

    void split_child(int i, int t) {
        // students: put your code here
    	int median_key = children.get(i).keys.get(t-1);
    	//shift the median key up and delete it from child i:
		keys.add(i, median_key);
		children.get(i).keys.remove(t);
		//add all the elements from child i from index 0 to t-1(before median) to the first node:
		ArrayList<Integer> first_key = new ArrayList<Integer>();
		for(int m = 0; m < t; m++) {
			first_key.add(children.get(i).keys.get(m));
		}
		//add all the elements right after median to the end of the second node:
		ArrayList<Integer> second_key = new ArrayList<Integer>();
		for(int n = t+1; n < children.get(i).keys.size(); n++) {
			second_key.add(children.get(i).keys.get(n));
		}
		BTreeNode first = null;
		BTreeNode second = null;
		ArrayList<BTreeNode> first_children = new ArrayList<BTreeNode>();
		ArrayList<BTreeNode> second_children = new ArrayList<BTreeNode>();
		//if child i is leaf, then set the new two nodes's children to null:
    	if(children.get(i).is_leaf) {
    		first = new BTreeNode(first_key, null, true);
    		second = new BTreeNode(second_key, null, true);
    	}    	
    	//if not, add child i's children from index [0, t) to the first node and [t, size) to the second node:
    	else {
    		int children_size = children.get(i).children.size();
    		for(int p = 0; p < t; p++)
    			first_children.add(children.get(i).children.get(p));
    		for(int q = t; q < children_size; q++)
    			second_children.add(children.get(i).children.get(q));
    		first = new BTreeNode(first_key, first_children, false);
    		second = new BTreeNode(second_key, second_children, false);
    	}
    	//remove child i and add the new two in:
    	children.remove(i);
		children.add(i, first);
		children.add(i+1, second);
    }

    public void insert(int key, int t) {
        int i = key_search(key);
        if (is_leaf) {
            keys.add(i, key);
        } else {
            // if child is full, split into two children
            if (children.get(i).keys.size() == 2 * t - 1) {
                split_child(i, t);
            }
            // determine which of the two new children to insert into
            if (i != keys.size() && key > keys.get(i)) {
                ++i;
            }
            children.get(i).insert(key, t);
        }
    }

    public String toString() {
        return String.format("(%1$s, %2$s)", keys.toString(),
                             children.toString());
    }
    
}


class BTree {
    BTreeNode root;
    int t;
    
    BTree(int t_init) {
        ArrayList<Integer> keys = new ArrayList<Integer>(0);
        ArrayList<BTreeNode> children = new ArrayList<BTreeNode>(0);
        root = new BTreeNode(keys, children, true);
        t = t_init;
    }

    SearchResult search(int key) { return root.search(key); }

    void insert(int key) {
        if (root.keys.size() == 2*t - 1) { // root is full
            // create a new root with old root as its child
            ArrayList<Integer> keys = new ArrayList<Integer>(0);
            ArrayList<BTreeNode> children = new ArrayList<BTreeNode>();
            children.add(root);
            root = new BTreeNode(keys, children, false);
            // split the old root then insert key
            root.split_child(0, t);
            root.insert(key, t);
        } else {
            root.insert(key, t);
        }
    }

    public String toString() { return root.toString(); }

    public static void main(String[] args) {
        BTree T = new BTree(3);
        int A[] = {1,2,3,5,6,7,9,12,16,18,21};
        for (int i = 0; i != A.length; ++i) {
            T.insert(A[i]);
        }
        
        for (int i = 0; i != A.length; ++i) {
            SearchResult r = T.search(A[i]);
            assert r != null;
            assert r.node.keys.get(r.pos) == A[i];
        }
        assert T.toString().equals("([5, 7], [([1, 2], []), ([5, 6], []), ([9, 12, 16, 18, 21], [])])");
        
        System.out.println("passed BTree tests");
    }
    
}
