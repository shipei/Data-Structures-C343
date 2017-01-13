package hw1;
import java.util.*;

public class hw1 {
 
 //exercise 4.7
 public Node mergeTwoLists(Node list1, Node list2)
 {
  if(list1 == null) { return list2;}
  if(list2 == null) { return list1;}
  if(list1.d < list2.d)
  {
   list1.n = mergeTwoLists(list1.n, list2);
   return list1;
  }
  else
  {
   list2.n = mergeTwoLists(list2.n, list1);
   return list2;
  }
 }
 
 //exercise 2.15
 public static ArrayList<ArrayList<Integer>> permutations(int[] array)
 {
  //create an empty arraylist consists of arraylists
  ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
  result.add(new ArrayList<Integer>());
  
  for(int i = 0; i < array.length; i++)
  {
   ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
   
   for(ArrayList<Integer> a: result)
   {
    for(int j = 0; j <= a.size(); j++)
    {
     a.add(j, array[i]);
     ArrayList<Integer> temp = new ArrayList<Integer>(a);
     current.add(temp);
     System.out.println(temp);
     a.remove(j);
    }
   }
   result = new ArrayList<ArrayList<Integer>>(current);
  }
  return result;
 }
 //exercise 4.15
 public static boolean isPalindrome(String s)
 {
  Stack<String> stack = new Stack<String>();
  Queue<String> queue = new LinkedList<String>();
  String str = new String();
  for(int i = 0; i < str.length(); i++)
  {
   str =str.charAt(i) + "";
   queue.add(str);
  }
  while(!queue.isEmpty())
  {
   if(!queue.remove().equals(stack.pop()))
   {
    return false;
   }
   //return true;
  }
  return true;
 }
 

 public static void main(String[] args)
 {
  int[] arr = {1, 2, 3};
  ArrayList<ArrayList<Integer>> res = permutations(arr);
  
  String str = "abcba";
  System.out.println(isPalindrome(str));
  
 }
}