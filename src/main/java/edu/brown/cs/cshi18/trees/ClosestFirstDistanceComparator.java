package edu.brown.cs.cshi18.trees;

import java.util.Comparator;
import java.util.List;

/**
 * Public class for comparing the distance to the target point from
 * a set of coordinates.
 *
 * @param <T> an object that has coordinates
 */
public class ClosestFirstDistanceComparator<T extends HasCoordinates>
    implements Comparator<T> {
  private List<Number> targetPoint;

  /**
   * Constructor method. Takes in a list of numbers representing the
   * coordinate of a target point to compare to.
   * @param targetPoint
   */
  public ClosestFirstDistanceComparator(List<Number> targetPoint) {
    this.targetPoint = targetPoint;
  }

  /**
   * Takes in two objects that implements the HasCoordinate interface.
   * Returns a positive value if object a is further away from the targetPoint
   * than object b. Negative other way around. Zero if equal.
   * @param a
   * @param b
   * @return
   */
  public int compare(T a, T b) {
    return Double.compare(a.euclideanDistance(targetPoint),
        b.euclideanDistance(targetPoint));
  }
}
