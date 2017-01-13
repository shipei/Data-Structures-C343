import java.util.function.BiPredicate;

interface SearchTree<Key> {
	Node<Key> insert(Key key);
	Node<Key> search(Key key);
}
interface Node<Key> {
	Node<Key> after();
	Node<Key> before();
	Key getKey();
}
class BSTNode<Key> implements Node<Key>{
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
	public Node<Key> after() {
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
	
	public void print_inorder(){
		if(this.left != null){
			this.left.print_inorder();
		}
		System.out.println(key);
		if(this.right != null){
			this.right.print_inorder();
		}
		
	}
	
	@Override
	public Node<Key> before() {
		// TODO Auto-generated method stub
		return null;
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
	private Object b1;
	 
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
					return curr.left;
				}
			}
			else{
				if(curr.right != null) {
					curr = curr.right;
				}
				else {
				curr.right = new BSTNode<Key>(key, null, null, null);
				return curr.right;
				}
			}	
		}
	}
	
	@Override
	public Node<Key> search(Key key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args){
		BiPredicate<Integer, Integer> bi = (a,b) -> a < b;
		BinarySearchTree<Integer> t1 = new BinarySearchTree<Integer>(bi);
		int a[] = {10,4,2,16,33,1,7,40};
		for(int num : a){
			t1.insert(num);
			}
		System.out.println("testcase for print the tree");
		t1.root.print_inorder();
		System.out.println("testcase for asfter :");
		System.out.println(t1.root.after().getKey());

	}
}



