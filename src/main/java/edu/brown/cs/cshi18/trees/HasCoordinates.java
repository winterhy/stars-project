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

  /**
   * Method to find a coordinate's euclidean distance from
   * a target point.
   * @param targetPoint a list of numbers that is the coordinate
   *                    of the target point
   * @return a double that represents the euclidean distance.
   */
  double euclideanDistance(List<Number> targetPoint);
}

