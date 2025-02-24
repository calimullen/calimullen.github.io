package clockapp;

import java.util.Scanner;

public class ClockProject {

	static int Check_12_Hour(int hour)		//Setting parameters for 12 hour clock (12 + 1 = 1) 
	{
		if (hour == 12)
		{
			hour = 1;
		}
		else
		{
			hour++;
		}
		return hour;
	}
	static int Check_24_Hour(int hour) // Setting parameters for 24 hour clock (12 + 1 = 13) (23 + 1 = 00)
	{
		if (hour == 23)
		{
			hour = 0;
		}
		else
		{
			hour++;
		}
		return hour;
	}
	
	static int AddNumber(int num) // Setting parameters for minutes and seconds (:59 + :01 = :00)
	{
		if (num == 59)
		{
			num = 0;
		}
		else
		{
			num++;
		}
		return num;
	}

	static void DisplayTime(int hour12,int hour24,int minute, int second)  // defining function displaytime that outputs current time
	{ 
		System.out.print("*******************	");
		System.out.println("*******************");
		System.out.print("* 12 - hour clock *	");
		System.out.println("* 24 - hour clock *");
		System.out.print("*  " + String.format("%02d", hour12) + ":" +
				String.format("%02d", minute) + ":" + String.format("%02d", second) + "	  *	"); // Adding leading 0 to single digits		
		System.out.println("*	" + String.format("%02d",hour24) + ":" +
				String.format("%02d", minute) + ":" + String.format("%02d", second) + "  *"); // Adding leading 0 to single digits
		System.out.print("*******************	");
		System.out.println("*******************" );;
	}

	static void DisplayInstrucitons() // defining function displayinstructions that outputs systems instructions to set clocks
	{
		System.out.println("	********************");
		System.out.println("	*     Add Input    *" );
		System.out.println("	* 1 - Add hour     *");
		System.out.println("	* 2 - Add minute   *");
		System.out.println("	* 3 - Add second   *");
		System.out.println("	* 4 - Exit program *");
		System.out.println("	********************");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int userInput = 0;				//Defining variables 
		int hour12 = 05;
		int hour24 = 17;
		int minute = 03;
		int second = 59;
		
		Scanner scanner = new Scanner(System.in);
		while (userInput != 4) {          // while userInput does not equal 4, continue while loop
		
			DisplayTime(hour12, hour24, minute, second);
			
			DisplayInstrucitons(); // calling displayInstructions function
			
			try {
				userInput = scanner.nextInt();
				if (userInput == 1) {    // userinput = 1; add an hour to both clocks 				
					hour12 = Check_12_Hour(hour12);
					hour24 = Check_24_Hour(hour24);
				}
				
				else if (userInput == 2) {  // userinput = 2; add a minute to both clocks				
					if (minute == 59) {		// if minute is 59; add hour and set minutes to :00					
						hour12 = Check_12_Hour(hour12);
						hour24 = Check_24_Hour(hour24);
						minute = AddNumber(minute);
					}
					else {					
						minute = AddNumber(minute);
					}
				}
				
				else if (userInput == 3) { // userinput = 3; add second to both clocks				
					if (second == 59) {  // if second is :59, add one minute and seconds to :00					
						if (minute == 59) {   //if second and minute are both :59, add one hour and minute and second = :00						
							hour12 = Check_12_Hour(hour12);
							hour24 = Check_24_Hour(hour24);
						}
						minute = AddNumber(minute);
						second = AddNumber(second);
					}
					else{					
						second = AddNumber(second);
					}
				} 
				else if (userInput == 4) { // userinput = 4;
					System.out.println("Good Bye...");
				} 
			}
			catch(Exception e) {
				userInput = 0;
				System.out.println("Error");
				scanner.nextLine();
			}
		}
	}
}
