public class ArrayStack implements Stack{
 int[] data;     //empty stack
 private int N;
 private int size;
 
 ArrayStack()
 { 
  size = 0;  
  N = 0;     //the top one in the stack
  data = new int[10];
 }

 @Override
 public void push(int d) {
  // TODO Auto-generated method stub
  if(size == data.length)
  {
   data[N++] = d;
   size++;
  }
 }

 @Override
 public int pop() {
  // TODO Auto-generated method stub
  int d;
  if(size == 0)
  {
   return -1;
  }
  else
  {
   d = data[--N];
   Integer integer = (Integer)null;
   data[N] = integer;
   size = size - 1;
  }  
  return data[data.length];
 }

 @Override
 public int peek() {
  // TODO Auto-generated method stub
  if(size == 0)
  {
   return -1;
  }
  else
  {
   return data[size-1];
  }
 }

 @Override
 public boolean empty() {
  // TODO Auto-generated method stub
  if(size == 0)
  {
   return true;
  }
  else 
  {
   return false;
  }
 }
 public static void main(String[] args)
 {
 }
}