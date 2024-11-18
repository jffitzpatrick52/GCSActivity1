package GCS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.ArrayList;

//This is the main driver for the command line program. It handles both user input and storage of the favorite cities

public class Main {
	public static ArrayList<String> favorites; //list of all favorited cities
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		
		favorites = new ArrayList<String>();
		
		int currentOption = 0;
		WeatherAPIGetter weather = new WeatherAPIGetter();
		
		System.out.println("Welcome to the weather application.");	
		
		while (currentOption != 5) {
			printMenu();
			try {	
				currentOption = Integer.parseInt(reader.readLine());
				if (currentOption < 1 || currentOption > 5) {
					System.out.println("Error: The number you entered was not one of the valid options");
					currentOption = 0;
					
				} else if (currentOption == 1) { //Display weather for an input city
					System.out.println("Enter the name of the city you'd like information on: ");
					String city = reader.readLine();
					weather.getForecast(city);
					
				} else if (currentOption == 2) { //Add a city to favorites
					if (favorites.size() >= 3) {
						System.out.println("Error: All favorite slots filled, try option 4.");
					} else {
						System.out.println("Enter the name of the city you'd like to favorite: ");
						String city = reader.readLine();
						favorites.add(city);
					}
					
				} else if (currentOption == 3) { //Display forecasts for all the favorite cities
					if (favorites.size() == 0) {
						System.out.println("Error: No cities favorited");
					} else {
						for (int i = 0; i < favorites.size(); i++) {
							String city = favorites.get(i);
							System.out.println("Forecast for " + city + ":");
							weather.getForecast(city);
							System.out.println(" "); //whitespace to make listed info more readable
						}
					}
					
				} else if (currentOption == 4) { //Replace city in list with another
					if (favorites.size() == 0) {
						System.out.println("Error: No cities favorited");
					} else {
						System.out.println("Here are your current favorite cities: ");
						for (int i = 0; i < favorites.size(); i++) {
							int j = i + 1;
							String city = favorites.get(i);
							System.out.println(j + ") " + city); //For the user, the list starts at 1
						}
						System.out.println("Enter the number of the city you'd like to replace: ");
						int favToReplace = Integer.parseInt(reader.readLine());
						int favToReplaceIndex = favToReplace - 1; //converting between human-readable option and list index
						if (favToReplace < 1 || favToReplace > favorites.size()) {
							System.out.println("Error: The option you entered was not listed");
						} else {
							System.out.println("Enter your new favorite city: ");
							String replacementCity = reader.readLine();
							favorites.remove(favToReplaceIndex);
							favorites.add(replacementCity);
						}
					}
				}
				else if (currentOption == 5) {
					System.out.println("Quitting program");
					return;
				}
			} catch (IOException ex) {
				System.out.println("Error: failure to read input");
			} catch (NumberFormatException ex) {
				System.out.println("Error: The option you entered is not an integer.");
				currentOption = 0;
			}
		}
	}
	
	public static void printMenu() { //simple function for printing command line options
		System.out.println("Please enter the number for one of the following options:");
		System.out.println("1) Display weather for a city.");
		System.out.println("2) Add a favorite city, to a maximum of three favorites.");
		System.out.println("3) Display weather for all your favorite cities.");
		System.out.println("4) Replace one of your favorite cities with another.");
		System.out.println("5) Quit the program.");
	}
}
		

