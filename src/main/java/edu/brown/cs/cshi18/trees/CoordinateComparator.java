package edu.brown.cs.cshi18.trees;

import java.util.Comparator;

/**
 * Class to compare one specific coordinate, or axis, of a list of numbers.
 * @param <T> an object that has coordinates
 */
public class CoordinateComparator<T extends HasCoordinates> implements Comparator<T> {
  private int coordinate;

  /**
   * Constructor method. Takes in a parameter of coordinate.
   * @param coordinate number representing the coordinate / index
   */
  public CoordinateComparator(int coordinate) {
    this.coordinate = coordinate;
  }

  /**
   * Takes in two objects that implements the HasCoordinate interface.
   * Returns a positive value if the specified coordinate is greater.
   * Negative is smaller. Zero if equal.
   *
   * @param a object T
   * @param b object T
   * @return value in accordance with compare method
   */
  public int compare(T a, T b) {
    // depths will NOT be greater or equal to list length
    if (a.getCoordinates().get(coordinate).doubleValue()
        > b.getCoordinates().get(coordinate).doubleValue()) {
      return 1;
    } else if (a.getCoordinates().get(coordinate).doubleValue()
        < b.getCoordinates().get(coordinate).doubleValue()) {
      return -1;
    } else {
      return 0;
    }
  }
}
