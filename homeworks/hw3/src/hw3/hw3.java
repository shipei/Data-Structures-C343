package hw3;

public class hw3 {
	//Exercise 7.11
	//modify quicksort to find the smallest k values in an array of records
	static int partition(int[] A, int start, int end) {
		int pivot = A[end];
		int pIndex = start;
		int temp;
		int temp2;
		
		for(int i = start; i < end; i++) {
			if(A[i] <= pivot) {
				temp = A[i];
				A[i] = A[pIndex];
				A[pIndex] = temp;
				pIndex = pIndex + 1;
			}
		}
		temp2 = A[pIndex];
		A[pIndex] = A[end];
		A[end] = temp2;
		return pIndex;
	}
//	static void quickSort(int[] A, int start, int end, int k) {
//		if(start < end) {
//			int pIndex = partition(A, start, end); 
//			quickSort(A, start, pIndex-1, k);
//			quickSort(A, pIndex+1, end, k);
//			}
//		}
	static void quickSort(int[] A, int start, int end, int k) {
		if(start < end) {
			int pIndex = partition(A, start, end); 
			//quickSort(A, start, pIndex-1, k);
			//quickSort(A, pIndex+1, end, k);
			System.out.println("pivot:" + pIndex);
			//System.out.println(k);
			System.out.println("k is: " + k);
			if(k-1 < pIndex) {
				quickSort(A, start, pIndex-1, k);
			}
			else if(k-1== pIndex) {
				return;
			}
			else {
				//quickSort(A, start, pIndex-1, k);
				quickSort(A, pIndex+1, end, k);
			}
			}
		return;
		}
	/*static void quickSort_helper(int[] A, int start, int end, int k) {
			int x = partition(A, start, end);
			quickSort(A, start, x-1, k);
			quickSort(A, x+1, k-2, k);	
		}*/
		
	public static void main(String[] args) {
		int[] test = {2, 8, 3, 1, 4, 7, 5};
		quickSort(test, 0, 6, 4);
		System.out.println(java.util.Arrays.toString(test));
	}
 }
