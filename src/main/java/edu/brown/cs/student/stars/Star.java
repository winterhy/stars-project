package edu.brown.cs.student.stars;

import java.util.List;

/**
 * This is a star object. Includes ID, Name, and Coordinates
 */
public class Star implements HasCoordinates {

  private Integer id;
  private String name;
  private List<Number> coordinates;

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

  @Override
  public List<Number> getCoordinates() {
    return coordinates;
  }
}
