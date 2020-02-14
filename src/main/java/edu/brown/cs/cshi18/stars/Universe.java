package edu.brown.cs.cshi18.stars;

import edu.brown.cs.cshi18.repl.CommandManager;
import edu.brown.cs.cshi18.trees.HasCoordinates;
import edu.brown.cs.cshi18.trees.KDTree;
import edu.brown.cs.cshi18.stars.Star;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Universe<Star> implements CommandManager.Install {
  public KDTree<HasCoordinates> tree;
  public HashMap<Integer, Star> starHashMap = new HashMap<>();
  public HashMap<String, edu.brown.cs.cshi18.stars.Star> nameHashMap = new HashMap<>();

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
      // calls parser here
      String line = tokens.get(0);
      String pattern = ".*\\.csv";
      if (Pattern.matches(line, pattern)) {
        System.out.println("Read n stars from <filename>");
        //Parser parser = new Parser(line);





        String header = reader.readLine();
        if (header == null) {
          System.err.println("ERROR: Empty file");
        } else {
          String headerPattern = "StarID,ProperName,X,Y,Z";
          if (Pattern.matches(headerPattern, header)) {
            while ((line = reader.readLine()) != null) {
              String[] los = line.split(",");
              List<String> list = Arrays.asList(los);
              List<Number> coordinates = new ArrayList<>();
              for (int i = 0; i < 3; i++) {
                coordinates.add(i, Double.parseDouble(list.get(i + 2)));
              }
              edu.brown.cs.cshi18.stars.Star newStar = new edu.brown.cs.cshi18.stars.Star(Integer.parseInt(list.get(0)), list.get(1),
                  coordinates);
              // This puts stars with names into a HashMap with a key of names
              // and a value of stars. Those without names will not be put in.
              if (list.get(1) != "") {
                nameHashMap.put(list.get(1), newStar);
              }
              starHashMap.put(list.get(0), newStar);
            }
          } else {
            System.err.println("ERROR: Malformed header.");
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

      // Remember if it's name, no itself
      // If it is coordinates, can include itself
      // to solve this, create two KDTrees?? One with name, one without
      // actually nah, just pass in k+1 for those with names, and delete it after
      System.out.println("Neighbors");
    }

  }

  class RadiusCommand implements CommandManager.Command {

    @Override
    public void execute(List<String> tokens) {

      System.out.println("Radius");
    }
  }


}
