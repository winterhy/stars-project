package edu.brown.cs.student.stars;

import java.util.List;

/**
 * This is a star object. Includes ID, Name, and Coordinates
 */
public class Star {

  private Integer id;
  private String name;
  private List<Number> coordinates;

  /**
   * Takes a Number, a String, and a List of Numbers
   * and constructs a Star object.
   *
   * @param id
   * @param name
   * @param coordinates
   */
  public Star(Integer id, String name, List<Number> coordinates) {
    this.id = id;
    this.name = name;
    this.coordinates = coordinates;
  }
}
