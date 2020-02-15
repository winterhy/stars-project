package edu.brown.cs.cshi18.stars;

import edu.brown.cs.cshi18.trees.HasCoordinates;

import java.util.List;

/**
 * This is a star object. Includes ID, Name, and Coordinates.
 */
public class Star implements HasCoordinates {
  private Integer id;
  private String name;
  private List<Number> coordinates;

  /**
   * Access method for id.
   * @return id as integer.
   */
  public int getId() {
    return id;
  }

  /**
   * Access method for name.
   * @return name as string.
   */
  public String getName() {
    return name;
  }

  /**
   * This is an access method in accordance from implementing HasCoordinates.
   * @return coordinates in a list of numbers.
   */
  @Override
  public List<Number> getCoordinates() {
    return coordinates;
  }

  /**
   * Takes a Number, a String, and a List of Numbers
   * and constructs a Star object.
   *
   * @param id the non-negative integer id of a star
   * @param name the name of a star in strings
   * @param coordinates a list of numbers as the coordinates
   */
  public Star(Integer id, String name, List<Number> coordinates) {
    this.id = id;
    this.name = name;
    this.coordinates = coordinates;
  }

  /**
   * Supporting method to get the euclidean distance between two lists
   * of numbers. Takes two equal sized lists.
   *
   * @param targetPoint list of numbers that represent a coordinate
   * @return the euclidean distance
   */
  @Override
  public double euclideanDistance(List<Number> targetPoint) {
    double squareSum = 0.00;
    for (int i = 0; i < targetPoint.size(); i++) {
      double difference = coordinates.get(i).doubleValue() - targetPoint.get(i).doubleValue();
      squareSum = squareSum + Math.pow(difference, 2.00);
    }
    // Square roots the sum of squares
    return Math.sqrt(squareSum);
  }
}
