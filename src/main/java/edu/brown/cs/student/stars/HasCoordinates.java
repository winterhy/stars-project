package edu.brown.cs.student.stars;

import java.util.List;

/**
 * Interface that insures a data structure
 * to have a field of a coordinates.
 */
public interface HasCoordinates {

  /**
   * Method to get coordinates from a class
   * that implements this interface.
   * @return a list of numbers
   */
  List<Number> getCoordinates();
}

