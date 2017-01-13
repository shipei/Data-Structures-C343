package lab1;
import java.util.*;

public class lab1 {
	
	public static void main(String[] args)
	{
		ArrayList<Double> numbers = new ArrayList<Double>();
		Scanner scan = new Scanner(System.in);
		System.out.println("please enter the daily rainfall amounts:");
		String[] str = scan.nextLine().split(" ");
		
		for(int i = 0; i < str.length; i++)
		{
			if(Double.parseDouble(str[i]) >= 0 && Double.parseDouble(str[i]) > -999)
			{
				numbers.add(Double.parseDouble(str[i]));
			}
		}
		if(numbers.size() == 0)
		{
			System.out.println("There's no average");
		}
		else
		{
			double total = 0;
			for(double ele: numbers)
			{
				total = total + ele;
			}
			double ave = total/numbers.size();
			System.out.println("The average of rainfall amounts is: " + ave);
		}
		
	}
}
