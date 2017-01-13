import java.util.Arrays; 
public class Solution { 
    public static int[] twoSum(int[] nums, int target) { 
        int[] result = new int[2]; 
        for(int i = 0; i < nums.length; i++) { 
            for(int j = 0; j < nums.length; j++) { 
                if(nums[i] + nums[j] == target && i != j) { 
                  System.out.println(i+" " +j);
                    nums[0] = i; 
                    nums[1] = j; 
                    break; 
                } 
            } 
        } 
        return result; 
    } 
    public static void main(String[] args) {
      int[] test = {3,2,4};
      System.out.println(Arrays.toString(twoSum(test, 6)));
    }
}