package edu.brown.cs.cshi18.parser;

import edu.brown.cs.cshi18.repl.REPL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

/**
 * Public class for parsing csv files.
 * Each row of the csv is parsed into a list of strings.
 * Each list of strings is then added to a list field.
 */
public class CSVParser {
  private List<List<String>> parsed;

  /**
   * Access method to get the parsed list.
   * @return the parsed list.
   */
  public List<List<String>> getParsed() {
    return parsed;
  }

  /**
   * Takes a string of csv file and parse each row  into a list of strings.
   * Then adds the list of strings into a list field.
   *
   * @param fileName a string that represents the path to file and file
  */
  public CSVParser(String fileName) {
    try {
      File csvFile = new File(fileName);
      BufferedReader reader =
          new BufferedReader(new FileReader(csvFile));
      String firstLine = reader.readLine();
      // the first line || header cannot be empty
      if (firstLine == null) {
        REPL.errorPrint("ERROR: Empty header.");
      } else {
        // parse the lines in the file by comma
        String[] arrayOfFirstLine = firstLine.split(",");
        List<String> firstRow = Arrays.asList(arrayOfFirstLine);
        parsed = new ArrayList<>();
        parsed.add(firstRow);
        String line;
        // loop through each line
        while ((line = reader.readLine()) != null) {
          String[] arrayOfStrings = line.split(",");
          List<String> row = Arrays.asList(arrayOfStrings);
          parsed.add(row);
        }
      }
    } catch (IOException e) {
      // handles read files and read line
      REPL.errorPrint("ERROR: File does not exist.");
    }
  }
}
