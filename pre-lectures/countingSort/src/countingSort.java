class countingSort {
	public int[] sort(int[] arr, int k) {
		int[] count = new int[k+1];
		int[] sorted = new int[arr.length];
		//initialize count array
		for(int i = 0; i < k; i++) {
			count[i] = 0;
		}
		//store data in count
		for(int j = 0; j < arr.length; j++) {
			count[arr[j]]++; 
		}
		//iterate through new array
		for(int m = 0; m < k; m++) {
			count[m+1] += count[m];
		}
		//build data to new sorted array
		for(int n = 0; n < arr.length; n++) {
			sorted[count[arr[n]]-1] = arr[n];
			count[arr[n]]--;
		}
		return sorted;
	}
	public static void main(String[] args) {
		countingSort cs = new countingSort();
		int[] test = {3,5,2,2,8,3};
		int[] result = cs.sort(test, 8);
		for(int i = 0; i < result.length; i++) {	
			System.out.print(result[i]);
		}
	}
}
