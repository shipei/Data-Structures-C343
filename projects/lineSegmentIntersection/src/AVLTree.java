import java.util.function.BiPredicate;
import lineSegmentIntersection.Node;

class AVLNode<Key> implements Node<Key>{
	 public Key key;
	 public AVLNode<Key> parent;
	 public AVLNode<Key> left;
	 public AVLNode<Key> right;
	 AVLNode(Key k, AVLNode<Key> l, AVLNode<Key> r, AVLNode<Key> p) {
	    this.key = k;
	    this.left = l;
	    this.right = r;
	    this.parent = p;
	  }
	 
	@Override
	public AVLNode<Key> after() {
		// TODO Auto-generated method stub
		AVLNode<Key> curr = this;
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
	
	/*public int height(AVLNode<Key> n) {
		if(n == null) {
			return 0;
		}
		return 1 + Math.max(height(n.left), height(n.right));
	}*/
	
	public void print_inorder(){
		if(this.left != null){
			this.left.print_inorder();
		}
		System.out.println(key);
		if(this.right != null){
			this.right.print_inorder();
		}	
	}
}
	 
public class AVLTree<Key> implements SearchTree<Key>{
	AVLNode<Key> root;
	BiPredicate<Key, Key> bi;
	public AVLTree(BiPredicate<Key, Key> bi) {
		this.root = null;
		this.bi = bi;
	}
	public int height(AVLNode<Key> n) {
		if(n == null) {
			return 0;
		}
		return 1 + Math.max(height(n.left), height(n.right));
	}
	//LL:
	public AVLNode<Key> singleRight(AVLNode<Key> n) {
		AVLNode<Key> newRoot = n.left;
		n.left = newRoot.right;
		newRoot.right = n;
		return newRoot;
	}
	//RR:
	public AVLNode<Key> singleLeft(AVLNode<Key> n) {
		AVLNode<Key> newRoot = n.right;
		n.right = newRoot.left;
		newRoot.left = n;
		return newRoot;
	}
	//LR:
	public AVLNode<Key> doubleRight(AVLNode<Key> k1) {
		//left rotation first and then right rotation
		AVLNode<Key> k2 = k1.left; 
		singleLeft(k2);
		return singleRight(k1);
	}
	//RL:
	public AVLNode<Key> doubleLeft(AVLNode<Key> k1) {
		//right rotation first and then left rotation
		AVLNode<Key> k2 =k1.right;
		singleRight(k2);
		return singleLeft(k1);
	}
	
	public AVLNode<Key> rotate_helper(AVLNode<Key> n) {
		//check if the bottom left node is balanced or not, if yes, go check its parent, if not, check which case it is and then rotate
		n = this.root;
		System.out.println(n.key+"!!!!!!!");
		System.out.println(height(n));
		//go to bottom left:
		while(n.left != null) {
			n = n.left;
		}

		int balance = height(n.left) - height(n.right);
		if(balance <= 1 && balance >= 0) {
			//balanced, nothing here
			return n;
		}
		else if(balance > 1) {
			if(height(n.left.left) >= height(n.left.right)) {
				//LL:
				n = singleRight(n);
			}
			else {
				//LR:
				n = doubleRight(n);
			}
		}
		//balance < -1:
		else {
			if(height(n.right.right) >= height(n.right.left)) {
				//RR:
				n = singleLeft(n);
			}
			else {
				//RL:
				n = doubleLeft(n);
			}
		}
		if(n.parent != null) {
			n = n.parent;
			rotate_helper(n);
		}
		return n;
	}
	 @Override
	
	public AVLNode<Key> insert(Key key) {
		// TODO Auto-generated method stub
		BSTinsert(key);
		AVLNode<Key> curr = this.root;
		rotate_helper(curr);
		return curr;
	}
	
	
	@Override 
	public AVLNode<Key> search(Key key) {
		// TODO Auto-generated method stub
		AVLNode<Key> curr = this.root;
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
		BSTdelete(key);
		AVLNode<Key> curr = this.root;
		rotate_helper(curr);
	}
	
	public AVLNode<Key> BSTinsert(Key key) {
		AVLNode<Key> curr = this.root;
		if(curr == null) {
			this.root =  new AVLNode<Key>(key, null, null, null);
			return this.root;
		}
		while(true) {
			if(this.bi.test(key, curr.key)) {
				if(curr.left != null) {
					curr = curr.left;
				}
				else {
					curr.left = new AVLNode<Key>(key, null, null, null);
					curr.left.parent = curr;
					return curr.left;
				}
			}
			else{
				if(curr.right != null) {
					curr = curr.right;
				}
				else {
				curr.right = new AVLNode<Key>(key, null, null, null);
				curr.right.parent = curr;
				return curr.right;
				}
			}	
		}
	}
	
	
	public void BSTdelete(Key key) {
		AVLNode<Key> curr = search(key);
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
		else if(curr.after() != curr.right){
			curr.key = curr.after().key;
			if(curr.after().right != null) {
				curr.after().parent.left = curr.after().right;
			}
		}
		else {
			AVLNode<Key> T = curr.after();
			curr.key = T.key;
			curr.right = T.right;
			T.right = null;
		}
	}
		
	public static void main(String[] args){
		BiPredicate<Integer, Integer> bi = (a,b) -> a < b;
		AVLTree<Integer> t1 = new AVLTree<Integer>(bi);
		int a[] = {10,4,2,16,33,1,7,40};
//		for(int num : a){
			t1.insert(10);
			t1.insert(9);
			t1.insert(5);
			
//			t1.insert(12);
//			t1.insert(13);
//			t1.insert(14);
//			System.out.println("-------------");
//			System.out.println(t1.root.right.key);
//			System.out.println(t1.root.);
//			System.out.println(t1.root.right.key);
//			System.out.println(t1.root.right.key);
//			System.out.println(t1.root.left.key);
//			System.out.println(t1.root.left.left.key);
//			}
		
//		System.out.println("testcase for print the tree");
//		//t1.root.print_inorder();
//		t1.BSTdelete(16);
//		t1.root.print_inorder();
	}
}
