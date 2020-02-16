package edu.brown.cs.cshi18.stars;

import edu.brown.cs.cshi18.parser.CSVParser;
import edu.brown.cs.cshi18.repl.CommandManager;
import edu.brown.cs.cshi18.repl.REPL;
import edu.brown.cs.cshi18.trees.HasCoordinates;
import edu.brown.cs.cshi18.trees.KDTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Main class for stars program. Stores information for stars in trees.
 * Install commands for program. Stores output.
 */
public class Universe implements CommandManager.Install {
  private KDTree<Star> tree;
  private HashMap<String, Star> nameHashMap = new HashMap<>();
  private List<String> neighborsOutput;
  private List<String> radiusOutput;

  public KDTree<Star> getTree() {
    return tree;
  }

  public HashMap<String, Star> getNameHashMap() {
    return nameHashMap;
  }

  public List<String> getNeighborsOutput() {
    return neighborsOutput;
  }

  public List<String> getRadiusOutput() {
    return radiusOutput;
  }

  /**
   * Method from the install interface.
   * Installs commands specifically for stars using .register.
   *
   * @param cp the CommandManager
   */
  public void installCommands(CommandManager cp) {
    cp.register("stars\\s.+", new StarsCommand());
    // Non-negative integer first and rest are real numbers
    cp.register("neighbors\\s(\\d+)(?:\\s(-?\\d+(\\.\\d+)?)){3}",
        new NeighborsCommand());
    cp.register("neighbors\\s(\\d+)\\s\".+\"",
        new NeighborsCommand());
    // Non-negative real number first and rest are real numbers
    cp.register("radius\\s(\\d+(\\.\\d+)?)(?:\\s(-?\\d+(\\.\\d+)?)){3}",
        new RadiusCommand());
    cp.register("radius\\s(\\d+(\\.\\d+)?)\\s\".+\"",
        new RadiusCommand());
  }

  /**
   * Class for the star command.
   */
  class StarsCommand implements CommandManager.Command {
    /**
     * Executes the star command. Takes in a csv files as a string.
     * Uses the populate universe to populate the HashMap and KDTree.
     *
     * @param tokens list of arguments needed for the method
     */
    @Override
    public void execute(List<String> tokens) {
      String fileName = tokens.get(0);
      if (!Pattern.matches(".*\\.csv", fileName)) {
        REPL.errorPrint("ERROR: Filename must end in \".csv\"");
      } else {
        CSVParser parser = new CSVParser(fileName);
        List<List<String>> content = parser.getParsed();
        List<String> headerPattern = new ArrayList<>(List.of("StarID",
            "ProperName", "X", "Y", "Z"));
        if (content != null) {
          if (!content.get(0).equals(headerPattern)) {
            REPL.errorPrint("ERROR: Malformed header in csv.");
          } else if (content.size() < 2) {
            // can pass the output to a method that returns it to gui method
            REPL.print("Read 0 stars from " + fileName);
            REPL.errorPrint("ERROR: 0 stars in csv file.");
          } else {
            populateUniverse(content, fileName);
          }
        }
      }
    }

    /**
     * Method that takes a list of list of strings that represent the contents
     * in a star and returns a HashMap of names and their respective stars
     * and populated a KDTree.
     *
     * @param content list of list of strings from the parser
     * @param fileName file name that the stars are read from
     */
    public void populateUniverse(List<List<String>> content, String fileName) {
      List<Star> listOfStars = new ArrayList<>();
      for (int i = 1; i < content.size(); i++) {
        List<String> row = content.get(i);
        String joinedRow = String.join(",", row);
        if (!Pattern.matches("(\\d+),(.*)(?:,(-?\\d+(\\.\\d+)?)){3}", joinedRow)) {
          REPL.errorPrint("ERROR: Malformed data in csv");
        } else {
          int id = Integer.parseInt(row.get(0));
          String name = row.get(1);
          List<Number> coordinates = new ArrayList<>();
          for (int j = 0; j < 3; j++) {
            coordinates.add(j, Double.parseDouble(row.get(j + 2)));
          }
          Star newStar = new Star(id, name, coordinates);
          listOfStars.add(newStar);
          // This puts stars with names into a HashMap with a key of names
          // and a value of stars. Those without names will not be put in.
          if (!name.equals("")) {
            nameHashMap.put(name, newStar);
          }
        }
      }
      if (!listOfStars.isEmpty()) {
        int numberOfStars = content.size() - 1;
        REPL.print("Read " + numberOfStars + " stars from " + fileName);
        tree = new KDTree<>(listOfStars, 0);
      }
    }
  }

  /**
   * Class for the neighbors command.
   */
  class NeighborsCommand implements CommandManager.Command {
    /**
     * Executes the neighbors command. It can
     * detect whether a neighbors search with name is needed
     * or with coordinate is needed.
     *
     * @param tokens list of arguments needed for the method
     */
    @Override
    public void execute(List<String> tokens) {
      // call neighbors on KDTree
      // Will be an integer because of regexp checking
      int k = Integer.parseInt(tokens.get(0));
      // If it is an integer and a string
      if (tokens.size() == 2) {
        // This strips away the input name's quote
        String name = tokens.get(1).replace("\"", "");
        // This gets the star from the HashMap with names
        Star centerStar = nameHashMap.get(name);
        try {
          List<Number> starCoordinates = centerStar.getCoordinates();
          try {
            List<Star> nameNeighbors = tree.neighbors(k + 1, starCoordinates);
            nameNeighbors.remove(0);
            neighborsOutput = new ArrayList<>();
            if (nameNeighbors.size() != 0) {
              for (Star element : nameNeighbors) {
                REPL.print(element.getId());
                neighborsOutput.add(
                    "ID: " + element.getId() + " Name: " + element.getName()
                    + " Coordinates: " + element.getCoordinates());
              }
            }
          } catch (NullPointerException e) {
            neighborsOutput = new ArrayList<>();
            neighborsOutput.add("ERROR: Please load a valid file first.");
            REPL.errorPrint("ERROR: Please load a valid file first.");
          }
        } catch (NullPointerException e) {
          neighborsOutput = new ArrayList<>();
          neighborsOutput.add(
              "ERROR: Cannot find stars with this name. Change the name or load a new file.");
          REPL.errorPrint(
              "ERROR: Cannot find stars with this name. Change the name or load a new file.");
        }
      // If it is an integer followed by three doubles
      } else if (tokens.size() == 4) {
        List<Number> targetPoint = createPoint(tokens.subList(1, 4));
        try {
          List<Star> coordinateNeighbors = tree.neighbors(k, targetPoint);
          neighborsOutput = new ArrayList<>();
          for (Star element : coordinateNeighbors) {
            REPL.print(element.getId());
            neighborsOutput.add(
                "ID: " + element.getId() + " Name: " + element.getName()
                + " Coordinates: " + element.getCoordinates());
          }
        } catch (NullPointerException e) {
          neighborsOutput = new ArrayList<>();
          neighborsOutput.add("ERROR: Please load a valid file first.");
          REPL.errorPrint("ERROR: Please load a valid file first.");
        }
      }
    }

//    @Override
//    public List<String> guiExecute(List<String> tokens) {
//
//    }
  }

  class RadiusCommand implements CommandManager.Command {
    /**
     * Executes the neighbors command. It can
     * detect whether a neighbors search with name is needed
     * or with coordinate is needed.
     *
     * @param tokens list of arguments needed for the method
     */
    @Override
    public void execute(List<String> tokens) {
      double r = Double.parseDouble(tokens.get(0));
      // If it is an integer and a string
      if (tokens.size() == 2) {
        // This strips away the input name's quote
        String name = tokens.get(1).replace("\"", "");
        // This gets the star from the HashMap with names
        try {
          Star centerStar = nameHashMap.get(name);
          List<Number> starCoordinates = centerStar.getCoordinates();
          try {
            List<Star> nameRadius = tree.radius(r, starCoordinates);
            nameRadius.remove(0);
            radiusOutput = new ArrayList<>();
            if (nameRadius.size() != 0) {
              for (Star element : nameRadius) {
                REPL.print(element.getId());
                radiusOutput.add(
                    "ID: " + element.getId() + " Name: " + element.getName()
                    + " Coordinates: " + element.getCoordinates());
              }
            }
          } catch (NullPointerException e) {
            radiusOutput = new ArrayList<>();
            radiusOutput.add("ERROR: Please load a valid file first.");
            REPL.errorPrint("ERROR: Please load a valid file first.");
          }
        } catch (NullPointerException e) {
          radiusOutput = new ArrayList<>();
          radiusOutput.add(
              "ERROR: Cannot find stars with this name. Change the name or load a new file.");
          REPL.errorPrint(
              "ERROR: Cannot find stars with this name. Change the name or load a new file.");
        }
      // If it is an integer followed by three doubles
      } else if (tokens.size() == 4) {
        List<Number> targetPoint = createPoint(tokens.subList(1, 4));
        try {
          List<Star> coordinateNeighbors = tree.radius(r, targetPoint);
          radiusOutput = new ArrayList<>();
          for (Star element : coordinateNeighbors) {
            REPL.print(element.getId());
            radiusOutput.add(
                "ID: " + element.getId() + " Name: " + element.getName()
                + " Coordinates: " + element.getCoordinates());
          }
        } catch (NullPointerException e) {
          radiusOutput = new ArrayList<>();
          radiusOutput.add("ERROR: Please load a valid file first.");
          REPL.errorPrint("ERROR: Please load a valid file first.");
        }
      }
    }
  }

  /**
   * Helper method for the NeighborsCommand execute
   * and the RadiusCommand execute. This creates a
   * coordinate made of a list of numbers from a
   * list of strings representing numbers.
   *
   * @param tokens a list of strings representing numbers
   * @return a list of numbers representing a coordinate
   */
  public List<Number> createPoint(List<String> tokens) {
    List<Number> targetPoint = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      targetPoint.add(i, Double.parseDouble(tokens.get(i)));
    }
    return targetPoint;
  }
}
