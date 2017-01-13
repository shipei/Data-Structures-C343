
public class BinarySearch {
	
	static int binary_search(int x, int[] A, int begin, int end)
	{
		if(begin <= end)
		{
			for(int i = begin; i <= end; i++)
			{
				if(A[i] == x)
				{
					return i;
				}
				else
				{
				}		
			}
		}
		else
		{
			return -1;
		}
			return end;
		}
	
	public static void main(String[] args)
	{
		int[] A = {1,3,4,7,9,14,21,22,26};
		int pos = binary_search(9, A, 0, A.length);
		System.out.println(pos);
	}
}
