package edu.sdse.csvprocessor;

/**
 * DataClass to store city info
 */
public class CityRecord{
  int id;
  int year;
  String city;
  int population; 

  CityRecord(int id, int year, String city, int population){
    this.id=id;
    this.year=year;
    this.city=city;
    this.population=population;
  }
}