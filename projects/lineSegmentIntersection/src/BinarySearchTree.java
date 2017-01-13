import java.util.function.BiPredicate;

import lineSegmentIntersection.Node;

class BSTNode<Key> implements Node<Key> {
	 public Key key;
	 public BSTNode<Key> parent;
	 public BSTNode<Key> left;
	 public BSTNode<Key> right;
	  
	 BSTNode(Key k, BSTNode<Key> l, BSTNode<Key> r, BSTNode<Key> p) {
	    this.key = k;
	    this.left = l;
	    this.right = r;
	    this.parent = p;
	  }
	 
	@Override
	public BSTNode<Key> after() {
		// TODO Auto-generated method stub
		BSTNode<Key> curr = this;
		if(curr.right == null) {
			while(curr == curr.parent.left){
				curr = curr.parent;
			}
			return curr.parent;
		}
		else {
			curr = curr.right;
			while(curr.left != null) { 
				curr = curr.left;
			}
			return curr;
		}
	}
	
	public void print_inorder() {
		if(this.left != null) {
			this.left.print_inorder();
		}
		System.out.println(key);
		if(this.right != null) {
			this.right.print_inorder();
		}
		
	}
	
	@Override
	public Node<Key> before() {
		// TODO Auto-generated method stub
		BSTNode<Key> curr = this;
		//if curr has left, return curr.left, if not, go all the way to the right then find the left one of curr
		if(curr.left == null) {
			while(curr == curr.parent.right) {
				curr = curr.parent;
			}
			return curr.parent;
		}
		else {
			curr = curr.left;
			while(curr.right != null) {
				curr = curr.right;
			}
		}
		return curr;
	}
	
	@Override
	public Key getKey() {
		// TODO Auto-generated method stub
		return this.key;
	}
	}

class BinarySearchTree<Key> implements SearchTree<Key> {
	 BSTNode<Key> root;
	 BiPredicate<Key, Key> bi;
	 
	 BinarySearchTree(BiPredicate<Key, Key> bi) {
		 this.root = null;
		 this.bi = bi;
	  }

	@Override
	public Node<Key> insert(Key key) {
		// TODO Auto-generated method stub
		BSTNode<Key> curr = this.root;
		if(curr == null) {
			this.root =  new BSTNode<Key>(key, null, null, null);
			return this.root;
		}
		while(true) {
			if(this.bi.test(key, curr.key)) {
				if(curr.left != null) {
					curr = curr.left;
				}
				else {
					curr.left = new BSTNode<Key>(key, null, null, null);
					curr.left.parent = curr;
					return curr.left;
				}
			}
			else{
				if(curr.right != null) {
					curr = curr.right;
				}
				else {
				curr.right = new BSTNode<Key>(key, null, null, null);
				curr.right.parent = curr;
				return curr.right;
				}
			}	
		}
	}
	
	@Override
	public BSTNode<Key> search(Key key) {
		// TODO Auto-generated method stub
		BSTNode<Key> curr = this.root;
		while(curr != null) {
			if(curr.key == key) {
				return curr;
			}
			else if(this.bi.test(key, curr.key)) {
				curr = curr.left;
			}
			else {
				curr = curr.right;
			}
		}
		return curr;
	}
	
	@Override
	public void delete(Key key) {
		// TODO Auto-generated method stub
		// curr = this.root;
	//curr.left.parent = root;
		BSTNode<Key> curr = search(key);
		//case 1: if node to be deleted has no children
		if(curr.left == null && curr.right == null) {
			if(this.bi.test(key, curr.parent.key)) {
				curr.parent.left = null;
			}
			else {
				curr.parent.right = null;
			}
		}
		//case 2: if node to be deleted has only one child
		else if(curr.left == null && curr.right != null) {
			curr = curr.right;
			curr.right = null;
		}
		else if(curr.left != null && curr.right == null) {
			curr = curr.left;
			curr.left = null;
		}
		//case 3: if node to be deleted has 2 or more children
		else if(curr.after() != curr.right) {
			curr.key = curr.after().key;
			if(curr.after().right != null) {
				curr.after().parent.left = curr.after().right;
			}
		}
		else {
			BSTNode<Key> T = curr.after();
			curr.key = T.key;
			curr.right = T.right;
			T.right = null;
		}
	}
	
	public static void main(String[] args){
		BiPredicate<Integer, Integer> bi = (a,b) -> a < b;
		BinarySearchTree<Integer> t1 = new BinarySearchTree<Integer>(bi);
		int a[] = {10,4,2,16,33,1,7,40};
		for(int num : a){
			t1.insert(num);
			}
		/*System.out.println("testcase for print the tree");
		t1.root.print_inorder();
		System.out.println("testcase for after :");
		System.out.println(t1.root.after().getKey());
		System.out.println("testcase for before :");
		System.out.println(t1.root.before().getKey());*/
		t1.delete(10);
		System.out.println("testcase for delete");
		t1.root.print_inorder();
		
	}
}



