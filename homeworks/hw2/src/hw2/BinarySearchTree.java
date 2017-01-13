package hw2;
import java.util.HashSet;
//Sep 16, 2016
//hw2
//Exercise 5.19


class BSTNode{
  int key;
  BSTNode left;
  BSTNode right;
  
  BSTNode(int k, BSTNode l, BSTNode r) {
    key = k;
    left = l;
    right = r;
  }
}

class BinarySearchTree {
	  BSTNode root;
	  BinarySearchTree() {
	    root = null;
	  }
	  
	  void insert(int key) {
		    root = insert_helper(root, key);
		  }
	  
	  BSTNode getRoot() {
		  return this.root;
	  }
	  
	  
private BSTNode insert_helper(BSTNode n, int key) {
	if (n == null) {
		      return new BSTNode(key, null, null);
		    } else if (key < n.key) {
		      n.left = insert_helper(n.left, key);
		      return n;
		    } else if (key > n.key) {
		      n.right = insert_helper(n.right, key);
		      return n;
		    } else { // no need to insert, already there
		      return n;
		    }
		  }	

int smallcount(BSTNode root, int k)
{
	BSTNode curr = root;
	int count = 0;
	if(curr == null)
		return 0;
	else if(curr.key <= k) {
		count = count + getSize(curr.left) + 1;
		curr = curr.right;
		count += smallcount(curr, k);
	}
	else {
		curr = curr.left;
		count += smallcount(curr, k);
	}
	return count;
}
	 
int getSize(BSTNode x) 
{
	if(x == null)
		  return 0;
	  return 1 + getSize(x.left) + getSize(x.right);
}

public static void main(String[] args) {
    BinarySearchTree T = new BinarySearchTree();
    HashSet<Integer> H = new HashSet<Integer>();
    int[] A = { 5, 2, 4, 1, 5, 9, 8 };
    
    // Test insert and find
    for (int i = 0; i != A.length; ++i) {
      T.insert(A[i]);
      H.add(A[i]);
    }
    //Test smallcount
    for(int i = 0; i != 10; i++) {
    int r = T.smallcount(T.getRoot(), i);
    System.out.println("k = " + i + "\t result: " + r);
    }
}	  
	  	  
}