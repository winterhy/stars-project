package edu.brown.cs.cshi18.stars;

import edu.brown.cs.cshi18.parser.CSVParser;
import edu.brown.cs.cshi18.repl.CommandManager;
import edu.brown.cs.cshi18.trees.HasCoordinates;
import edu.brown.cs.cshi18.trees.KDTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

public class Universe implements CommandManager.Install {
  private KDTree<Star> tree;
  private HashMap<String, Star> nameHashMap = new HashMap<>();

  /**
   * Method from the install interface.
   * Installs commands specifically for stars using .register.
   *
   * @param cp the CommandManager
   */
  public void installCommands(CommandManager cp) {
    cp.register("stars\\s.*", new StarsCommand());
    // TODO: Need to change to accept decimals and no negatives for first
    // Non-negative integer first and rest are real numbers
    cp.register("neighbors\\s(\\d+)(?:\\s(-?\\d+(\\.\\d+)?)){3}",
        new NeighborsCommand());
    cp.register("neighbors\\s(\\d+)\\s\".*\"",
        new NeighborsCommand());
    // Non-negative real number first and rest are real numbers
    cp.register("radius\\s(\\d+(\\.\\d+)?)(?:\\s(-?\\d+(\\.\\d+)?)){3}",
        new RadiusCommand());
    cp.register("radius\\s(\\d+(\\.\\d+)?)\\s\".*\"",
        new RadiusCommand());
  }

  class StarsCommand implements CommandManager.Command {
    @Override
    public void execute(List<String> tokens) {
      String fileName = tokens.get(0);
      String pattern = ".*\\.csv";
      if (Pattern.matches(pattern, fileName)) {
        CSVParser parser = new CSVParser(fileName);
        List<List<String>> content = parser.getParsed();
        if (content != null) {
          List<String> headerPattern = new ArrayList<>(List.of("StarID",
              "ProperName", "X", "Y", "Z"));
          if (!content.get(0).equals(headerPattern)) {
            System.err.println("ERROR: Malformed header in csv.");
          } else if (content.size() < 2) {
            System.err.println("ERROR: 0 stars in csv file.");
          } else {
            int numberOfStars = content.size() - 1;
            System.out.println("Read " + numberOfStars + " stars from " + fileName);
            List<Star> listOfStars = new ArrayList<>();
            for (int i = 1; i < content.size(); i++) {
              List<String> row = content.get(i);
              String joinedRow = String.join(",", row);
              String rowPattern = "(\\d+),\"(.*)\"(?:,(-?\\d+(\\.\\d+)?)){3}";
              if (!Pattern.matches(rowPattern, joinedRow)) {
                System.err.println("ERROR: Malformed data in csv");
              }
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
            tree = new KDTree<>(listOfStars, 0);
          }
        }
      } else {
        System.out.println("ERROR: Filename must end in \".csv\"");
      }
    }
  }

  class NeighborsCommand implements CommandManager.Command {

    @Override
    public void execute(List<String> tokens) {
      // call neighbors on KDTree
      // Will be an integer because of regexp checking
      int k = Integer.parseInt(tokens.get(0));
      // If it is an integer and a string
      if (tokens.size() == 2) {
        Star centerStar = nameHashMap.get(tokens.get(1));
        List<Number> starCoordinates = centerStar.getCoordinates();
        Queue<Star> nameNeighbors = tree.neighbors(k + 1, starCoordinates);
        nameNeighbors.remove();
        if (nameNeighbors.size() != 0) {
          for (Star element : nameNeighbors) {
            System.out.println(element.getId());
          }
        }
      // If it is an integer followed by three doubles
      } else if (tokens.size() == 4) {
        List<Number> targetPoint = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
          targetPoint.add(i, Double.parseDouble(tokens.get(i + 1)));
        }
        Queue<Star> coordinateNeighbors = tree.neighbors(k, targetPoint);
        for (Star element : coordinateNeighbors) {
          System.out.println(element.getId());
        }
      }
    }
  }

  class RadiusCommand implements CommandManager.Command {

    @Override
    public void execute(List<String> tokens) {
      int k = Integer.parseInt(tokens.get(0));
      // If it is an integer and a string
      if (tokens.size() == 2) {
        Star centerStar = nameHashMap.get(tokens.get(1));
        List<Number> starCoordinates = centerStar.getCoordinates();
        Queue<Star> nameNeighbors = tree.radius(k + 1, starCoordinates);
        nameNeighbors.remove();
        if (nameNeighbors.size() != 0) {
          for (Star element : nameNeighbors) {
            System.out.println(element.getId());
          }
        }
        // If it is an integer followed by three doubles
      } else if (tokens.size() == 4) {
        List<Number> targetPoint = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
          targetPoint.add(i, Double.parseDouble(tokens.get(i + 1)));
        }
        Queue<Star> coordinateNeighbors = tree.radius(k, targetPoint);
        for (Star element : coordinateNeighbors) {
          System.out.println(element.getId());
        }
      }
    }
  }


}
