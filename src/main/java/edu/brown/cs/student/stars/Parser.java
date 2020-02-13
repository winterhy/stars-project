package edu.brown.cs.student.stars;

// do a public map of the stars so every class can use them

// the MAPS are so that when a name / id is inputted, a star will be found
// there are no duplicate star names

// do a list too maybe (or can be converted from maps)

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {
  public HashMap<Integer, Star> starHashMap = new HashMap<>();
  public HashMap<String, Star> nameHashMap = new HashMap<>();

  public Parser(String file) {
    try {
      File f = new File(file);
      BufferedReader reader =
          new BufferedReader(new FileReader(file));
      String line;
      String header = reader.readLine();
      if (header == null) {
        System.out.println("ERROR: Empty file");
      } else {
        String headerPattern = "StarID,ProperName,X,Y,Z";
        if (Pattern.matches(headerPattern, header)) {
          while ((line = reader.readLine()) != null) {
            String[] los = line.split(",");
            List<String> list = Arrays.asList(los);
            Star newStar = new Star(Integer.parseInt(list.get(0)), list.get(1),
                list.subList(2, list.size() - 1));
          }
        } else {
          System.out.println("ERROR: Malformed header.");
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: File does not exist.");
    }
  }

}
