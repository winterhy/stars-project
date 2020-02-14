package edu.brown.cs.cshi18.trees;

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
  double euclideanDistance(List<Number> targetPoint);
}

