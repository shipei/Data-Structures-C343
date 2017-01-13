class DNode {
  DNode(int d, DNode n, DNode p)
  {
	  data = d;
	  next = n;
	  prev = p;
  }
  int data;
  DNode next;
  DNode prev;
}