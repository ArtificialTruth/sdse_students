package edu.sdse.csvprocessor;


/**
 * DataClass to store city info
 */
public class CityRecord {
  private int id;
  private int year;
  private String city;
  private int population; 

  // Static makes this variable shared across all instances of CityRecord to avoid
  // duplicate mem usage
  private static String[] header; // https://www.tutorialspoint.com/class-and-static-variables-in-java

  // This will init the header value for all instances of CityRecord
  // Overloading constructor
  CityRecord(String[] header){
    CityRecord.header = header; // Access header variable in a "static way"
  }

  CityRecord(int id, int year, String city, int population){
    this.id=id;
    this.year=year;
    this.city=city;
    this.population=population;
  }

  public int getId() {
    return id;
  }

  public int getYear() {
    return year;
  }

  public String getCity() {
    return city;
  }

  public int getPopulation() {
    return population;
  }

  public String[] getHeader() {
    return header;
  }

  @Override
  public String toString(){
    return header[0] + ": " + id + ", " + header[1] + ": " + year + ", " + header[2] + ": " + city + ", " + header[3] + ": " + population;
  }
}