package edu.brown.cs.cshi18.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

/**
 * Public class for parsing csv files.
 * Each row of the csv is parsed into a list of strings.
 * Each list of strings is then added to a list field.
 */
public class CSVParser {
  public List<List<String>> parsed;


  /**
   * Takes a string of csv file and parse each row  into a list of strings.
   * Then adds the list of strings into a list field.
   *
   * @param file a string that represents the path to file and file
  */
  public CSVParser(String file) {
    try {
      File f = new File(file);
      BufferedReader reader =
          new BufferedReader(new FileReader(file));
      boolean sameNumberOfColumns = false;
      String firstLine = reader.readLine();
      if (firstLine == null) {
        System.err.println("ERROR: Empty file.");
      } else {
        String[] arrayOfFirstLine = firstLine.split(",");
        List<String> firstRow = Arrays.asList(arrayOfFirstLine);
        parsed.add(firstRow);
        String line;
        while ((line = reader.readLine()) != null) {
          String[] arrayOfStrings = line.split(",");
          List<String> row = Arrays.asList(arrayOfStrings);
          parsed.add(row);
        }
      }
    } catch (IOException e) {
      // handles read files and read line
      System.err.println("ERROR: File does not exist.");
    }
  }
}
