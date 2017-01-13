import java.util.*;
class Coord {
	int x; int y;		
	Coord(int init_x, int init_y) {
		this.x = init_x;
		this.y = init_y; 
	}
}

class Node {
	int val;
	Coord pos = null;
	Node parent = null;
	Node(int v, Coord p1, Node p2) {
		this.val = v;
		this.pos = p1;
		this.parent = p2;
	}
}

public class SeamCarving {
	static int carve_seam(int[][] D, ArrayList<Coord> seam) {
		//initialize:
		Node[][] matrix = new Node[D.length][D[0].length];
		for(int m = 0; m < D.length; m++) {
			for(int n = 0; n < D[0].length; n++) {
				Coord pos = new Coord(m, n);	
				matrix[m][n] = new Node(D[m][n], pos, null);
			}
		}	
		// for every element in the matrix: 
		for(int i = 0; i < matrix.length - 1; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				//current node in the first column:
				if(j == 0) {
					//diagright:
					matrix[i+1][j+1].parent = matrix[i][j];}
				//current node in the last column:
				else if(j == matrix[0].length-1) {
					//diagleft:
					if(matrix[i+1][j-1].parent.val + matrix[i+1][j-1].val > matrix[i][j].val + matrix[i+1][j-1].val) {
						matrix[i+1][j-1].parent = matrix[i][j];
					}
				}
				//current node in the middle:
				else {
					//diagright:
						matrix[i+1][j+1].parent = matrix[i][j];
					//diagleft:
					if(matrix[i+1][j-1].parent.val + matrix[i+1][j-1].val > matrix[i][j].val + matrix[i+1][j-1].val) {
						matrix[i+1][j-1].parent = matrix[i][j];
					}
				}
				//bottom:
				//if bottom does not have a parent:
				if(matrix[i+1][j].parent == null)
					matrix[i+1][j].parent = matrix[i][j];
				//if yes, compare and choose the parent with the smallest value:
				else {
					if(matrix[i+1][j].parent.val + matrix[i+1][j].val > matrix[i][j].val + matrix[i+1][j].val)
						matrix[i+1][j].parent = matrix[i][j];
					}
				}
			//update values for each row:
			for(int r = 0; r < matrix[0].length; r++) {
				matrix[i+1][r].val += matrix[i+1][r].parent.val;
				}	
			}	
		//find the min value in the last row:
		Node min = matrix[matrix.length-1][0];
		for(int b = 0; b < matrix[0].length-1; b++) {	
			if(min.val > matrix[matrix.length-1][b+1].val) {
				min = matrix[matrix.length-1][b+1];
			}
		}
		//System.out.println("min is: " + min.val);
		//trace back all the nodes and add them to the seam
		Node temp = min;
		while(temp != null) {
			seam.add(temp.pos);
			temp = temp.parent;
		}	
		return min.val;
	}
	
	public static void main(String[] args) {
		int[][] test = new int[][]{
			  { 1, 2, 3, 4},
			  { 4, 3, 2, 1},
			  { 4, 3, 2, 1},
			  { 3, 1, 6, 9},
			};
		ArrayList<Coord> arr = new ArrayList<Coord>();
		int ans = carve_seam(test,arr);
		System.out.println("The disruption value is:");
		System.out.println(ans);
		System.out.println("The path of seam is:");
		for(Coord c: arr) {
			System.out.println("(" + c.x + "," + c.y + ")");
		}
		int[][] D = new int[3][3];
	    D[0][0] = 1; D[0][1] = 0; D[0][2] = 1;
	    D[1][0] = 1; D[1][1] = 0; D[1][2] = 1;
	    D[2][0] = 1; D[2][1] = 0; D[2][2] = 1;

	    ArrayList<Coord> seam = new ArrayList<Coord>();
	    int disruption = carve_seam(D, seam);
	    assert disruption == 0;
	    for (int i = 0; i != seam.size(); ++i) {
	    Coord c = seam.get(i);
	    assert c.x == i;
	    assert c.y == 1;
	    }
	}	
	}
