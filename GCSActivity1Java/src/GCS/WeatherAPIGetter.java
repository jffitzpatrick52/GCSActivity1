package GCS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//Description: This class handles calls to the Open Weather API

public class WeatherAPIGetter {
	static String API_KEY = "YOUR_API_KEY"; //Paste a valid API Key here
	public String apiKey;
	public WeatherAPIGetter() {
		this.apiKey = API_KEY;
	}
	
	public void getForecast(String location) { //This function gets the forecast data from the OpenWeatherAPI and prints it in a human-readable format
		String formattedLocation = location.replaceAll(" ", "%20");
		String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + formattedLocation + "&appid=" + apiKey;
		
		try {
			//Handling connection with java.net
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) { 
			    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			    String inputLine;
			    StringBuilder content = new StringBuilder();

			    while ((inputLine = in.readLine()) != null) {
			        content.append(inputLine);
			    }
			    in.close();
			    
			    //Parsing json response
			    
			    Object obj = new JSONParser().parse(content.toString()); 
			    
			    JSONObject forecast = (JSONObject) obj;
			    
			    Map forecastMain = (Map) forecast.get("main");
			    
			    JSONArray forecastArray = (JSONArray) forecast.get("weather");
			    
			    JSONObject forecastWeather = (JSONObject) forecastArray.get(0);
			    
			    //Print visibility, temperature in Celsius, and description
			    
			    long visib = (long) forecast.get("visibility"); 
			    double tempKelvin = (double) forecastMain.get("temp");
			    double tempCelsius = tempKelvin - (double) 270.15;
			    String formattedTemp = String.format("%1$,.2f", tempCelsius);
			    String description = (String) forecastWeather.get("description");
			    
			    System.out.println("Temperature: " + formattedTemp + " degrees Celsius");
			    System.out.println("Visibility: " + visib);
			    System.out.println("Description: " + description);			    
		    
			} else { //Error handling, getting a response code other than 200 indicates failure
			    System.out.println("GET request not worked, Response Code: " + responseCode);
			    System.out.println(connection.getResponseMessage());
			}
		} catch (MalformedURLException ex) { 
			System.out.println("Error: Bad URL");
		} catch (ProtocolException ex) {
			System.out.println("Error: Protocol Exception");
		} catch (IOException ex) {
			System.out.println("Error: Bad input");
		} catch (ParseException ex) {
			System.out.println("Error: Failed to parse JSON object");
		}
	}

}
