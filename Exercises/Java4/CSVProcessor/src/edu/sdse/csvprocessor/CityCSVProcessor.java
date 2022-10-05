package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


/* Java HashMap sneak peak:
 * {Copenhagen=[Id: 1, Year: 2003, City: Copenhagen, Population: 501285, Id: 4, Year: 2004, City: Copenhagen, Population: 501664, ...],
 * Oslo=[Id: 3, Year: 2012, City: Oslo, Population: 618683, Id: 7, Year: 2008, City: Oslo, Population: 567980, ....
 */

public class CityCSVProcessor {

	public void readAndProcess(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			//Extract header row
			String[] header = br.readLine().trim().split(",");
			new CityRecord(header); // Make header shared for all instances of CityRecord

			ArrayList<CityRecord> allRecords = new ArrayList<CityRecord>();

			String line;

			HashMap< String, ArrayList<CityRecord> > cityDict = new HashMap<>();
			
			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");
				
				int id = convertToint(rawValues[0]);
				int year = convertToint(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToint(rawValues[3]);

				CityRecord cr = new CityRecord(id, year, city, population);
				
				// System.out.println(cr);
				allRecords.add(cr);
				
				// use map to colllect records by their city
				// HashMap is similiar to Python Dictionary
				/* { 'copenhagen': [<CityRecord2001>, <CityRecord2002>] } */
				
				ArrayList<CityRecord> currentList = cityDict.getOrDefault(city, new ArrayList<CityRecord>());
				currentList.add(cr);
				cityDict.put(city, currentList);

			}

			printStatistics(cityDict);

			//For debug: System.out.println(cityDict);
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}

	private void printStatistics(HashMap< String, ArrayList<CityRecord> > cityDict){
		// proccess data of each city, a) b) c):

		for (Entry< String, ArrayList<CityRecord> > entry : cityDict.entrySet()) {
			String city = entry.getKey();
			ArrayList<CityRecord> cityHistory = entry.getValue();

			int cityEntries = 0;
			int cityMinYear = 999999999; // Init to high val so first compare will always be smaller
			int cityMaxYear = -999999999; // Init to low val so first compare will always be larger
			int cityPopCumSum = 0;
			int cityAvgPop = 0;

			for (CityRecord record : cityHistory){
				cityEntries++; // a) the total number of entries for that city
				int cityYear = record.getYear();
				
				if (cityYear < cityMinYear){ // b) the minimum year
					cityMinYear = cityYear;
				}

				if (cityYear > cityMaxYear){ // c) the maximum year
					cityMaxYear = cityYear;
				}

				// Cummulated sum
				cityPopCumSum += record.getPopulation();
			}

			// d) the average population over that period (i.e., the sum of all 
			// population measurements divided by the total number of entries).
			// Cast to Int to avoid decimal ppl
			cityAvgPop = (int) (cityPopCumSum / cityEntries);

			System.out.println("Average population of " + city + "(" + cityMinYear + "-"+ cityMaxYear + "; " + cityEntries + " entries): " + cityAvgPop);
		}

	}
	
	private String cleanRawValue(String rawValue) {
		return rawValue.trim();
	}
	
	private int convertToint(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		return Integer.parseInt(rawValue);
	}
	
	private String convertToString(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		
		return rawValue;
	}
	
	public static final void main(String[] args) {
		CityCSVProcessor reader = new CityCSVProcessor();
		
		File dataDirectory = new File("Exercises/Java4/CSVProcessor/data/");
		File csvFile = new File(dataDirectory, "Cities.csv");
		
		reader.readAndProcess(csvFile);
	}
}
