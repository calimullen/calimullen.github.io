// Cali Mullen
#include<iostream>
#include<string>
#include<iomanip>
using namespace std;
int Check_12_Hour(int hour)		//Setting perameters for 12 hour clock (12 + 1 = 1) 
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
int Check_24_Hour(int hour) // Setting perameters for 24 hour clock (12 + 1 = 13) (23 + 1 = 00)
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
int AddNumber(int num) // Setting perameters for minutes and seconds (:59 + :01 = :00)
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
void DisplayTime(int hour12,int hour24,int minute, int second)  // defining function displaytime that outputs current time
{
	cout << setw(2) << setfill('0');
	cout << "*******************	";
	cout << "*******************" << endl;
	cout << "* 12 - hour clock *	";
	cout << "* 24 - hour clock *" << endl;
	cout << "*  " << setfill('0') << setw(2) << hour12 << ":" << setw(2) << minute << ":" << setw(2) << second << "	  *	"; // Adding leading 0 to single digits 
	cout << "*	" << setfill('0') << setw(2) << hour24 << ":" << setw(2) << minute << ":" << setw(2) << second << "  *" << endl; // Adding leading 0 to single digits
	cout << "*******************	";
	cout << "*******************" << endl;
}
void DisplayInstrucitons()					// defining function displayinstructions that outputs systems instructions to set clocks
{
	cout << "	********************" << endl;
	cout << "	*     Add Input    *" << endl;
	cout << "	* 1 - Add hour     *" << endl;
	cout << "	* 2 - Add minute   *" << endl;
	cout << "	* 3 - Add second   *" << endl;
	cout << "	* 4 - Exit program *" << endl;
	cout << "	********************" << endl;
}
int main()
{
	int userInput = 0;				//Defining variables 
	int hour12 = 05;
	int hour24 = 17;
	int minute = 03;
	int second = 59;

	while (userInput != 4)           // while userInput does not equal 4, continue while loop
	{
		DisplayTime(hour12, hour24, minute, second);    //calling displayTime function passing peramteters hour12, hour24, minute, and second
		cout << "\n";

		DisplayInstrucitons(); // calling displayInstructions function 

		cin >> userInput;			//Enter userInput

		if (userInput == 1)    // userinput = 1; add an hour to both clocks 
		{
			hour12 = Check_12_Hour(hour12);
			hour24 = Check_24_Hour(hour24);
		}
		else if (userInput == 2)  // userinput = 2; add a minute to both clocks
		{
			if (minute == 59)		// if minute is 59; add hour and set minutes to :00
			{
				hour12 = Check_12_Hour(hour12);
				hour24 = Check_24_Hour(hour24);
				minute = AddNumber(minute);
			}
			else
			{
				minute = AddNumber(minute);
			}
		}
		else if (userInput == 3) // userinput = 3; add second to both clocks
		{
			if (second == 59)  // if second is :59, add one minute and seconds to :00
			{
				if (minute == 59)   //if second and minute are both :59, add one hour and minute and second = :00
				{
					hour12 = Check_12_Hour(hour12);
					hour24 = Check_24_Hour(hour24);
				}
				minute = AddNumber(minute);
				second = AddNumber(second);

			}
			else
			{
				second = AddNumber(second);
			}
		} 
		else if (userInput != 4)
		{
			cout << "Error" << endl;
		}
	}

}
