import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
/*
 * Description: This class contains a number of useful static methods.
 * Author: Wanda Song
 * Date: Oct 26, 2017
 */

public class Useful {

	//scanner that can be used in all methods within this class
	private static Scanner input = new Scanner(System.in);
	
	/**
	 * @param n1, n2	The range of numbers
	 * @return  A random int between n1 and n2, inclusive
	 */
	public static int getRandomInt(int n1, int n2)
	{
		//whether n1 or n2 is bigger does not matter, as long as the range is consistent, 
		//the addition of n1 at the end will ensure that the number is between n1 and n2
		//if: n1=9, n2=0
		//n2-n1=-9
		//then when math.round gives a very high number like 0.999999, the number will be -9*0.999999+9=0
		//while when math.round gives a very low number like 0.000001, the number will be -9*0.000001+9=9
		//so we will get the same number range, just reversed.
		//ensure that the subtracter is added at the end, not the number being subtracted
		int randomNum=(int)Math.round((Math.random()*(n2-n1)+n1));
		return randomNum;
	}//end RandomInt
	
	/**
	 * @param n1, n2	The range of numbers
	 * @return  A random double between n1 and n2
	 */
	public static double getRandomDouble(int n1, int n2)
	{
		//it is not possible to be inclusive of both n1 and n2, this method is inclusive of the lower number
		double num1=n1;
		double num2=n2;
		double randomNum=(Math.random()*(num2-num1)+num1);//the same as getRandomInt except without the rounding
		return randomNum;
	}//end RandomDouble
	
	//rounds double to integer
	public static int getRounded(double n)
	{
		int num=(int)Math.round(n);
		return num;
	}
	
	/**
	 * @param n, d	n is the double that needs to be rounded, d is the number of decimal places
	 * @return  n rounded to dth decimal places
	 */
	public static double getRounded(double n, int d)
	{
		double num = Double.parseDouble(String.format("%."+d+"f",n));
		return num;
	}
	
	
	//takes in a double, rounds it to two decimal places, adds commas every three digits, 
	//and adds a money sign in front of it
	public static String getMoney(double m)
	{
		double num = Useful.getRounded(m,2);
		String money = "$"+NumberFormat.getNumberInstance(Locale.US).format(num);
		return money;
	}
	
	//failsafe for user inputed double
	public static double askDouble()
	{	
		//failsafe loop
		while(!input.hasNextDouble())
		{
			System.out.print("Invalid Input. Please try again: ");
			input.nextLine(); 
		}
		double num= input.nextDouble();
		input.nextLine(); //flush buffer
		return num;
	}
	
	//failsafe for user inputed int
	public static int askInt()
	{		
		//failsafe loop
		while(!input.hasNextInt())
		{
			System.out.print("Invalid Input. Please try again: ");
			input.nextLine(); 
		}
		int num= input.nextInt();
		input.nextLine(); //flush buffer
		return num;
	}
	
	//asks for user inputed string
	public static String askString()
	{		
		String string= input.nextLine();
		return string;
	}
	
	/**
	 * @param hour, minute, second is the amount of time to display
	 * @return  A string formatted with the parameters to look like a digital clock
	 */
	public static String clock(double hour, double minute, double second)
	{
		if(minute>60||minute<0||second>60||second<0)
		{
			System.out.println("Error, minute or second not between 0-60.");
			System.exit(0);
		}
		String time = String.format("%02.0f:%02.0f:%02.0f",hour, minute, second);
		return time;
	}
	
	/**
	 * @param minute, second is the amount of time to display
	 * @return  A string formatted with the parameters to look like a digital clock
	 */
	public static String clock(double minute, double second)
	{
		if(second>60||second<0)
		{
			System.out.println("Error, second not between 0-60.");
			System.exit(0);
		}
		String time = String.format("%02.0f:%02.0f", minute, second);
		return time;
	}
	
	/**
	 * @param password is the correct password
	 * @return  boolean value that indicates whether user inputted correct password
	 */
	public static Boolean password(String password)
	{
		Boolean check = false;
		System.out.println("What is the password: ");
		String answer = Useful.askString();
		if(answer.compareTo(password)==0)
		{
			check =true;
		}
		return check;
	}
}//end class