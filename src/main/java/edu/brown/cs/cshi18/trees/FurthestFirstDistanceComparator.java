package edu.brown.cs.cshi18.trees;

import java.util.Comparator;
import java.util.List;

/**
 * Public class for comparing the distance to the target point from
 * a set of coordinates. Used to sort queue from furthest neighbor to closest
 * neighbor.
 *
 * @param <T> an object that has coordinates
 */
public class FurthestFirstDistanceComparator<T extends HasCoordinates> implements Comparator<T> {
  private List<Number> targetPoint;

  /**
   * Constructor method. Takes in a list of numbers representing the
   * coordinate of a target point to compare to.
   * @param targetPoint
   */
  public FurthestFirstDistanceComparator(List<Number> targetPoint) {
    this.targetPoint = targetPoint;
  }

  /**
   * Takes in two objects that implements the HasCoordinate interface.
   * Returns the opposite of closest distance comparator.
   * Returns a positive value if object a is closer to the targetPoint.
   * Negative other way around. Zero if equal.
   * @param a
   * @param b
   * @return
   */
  public int compare(T a, T b) {
    return -Double.compare(a.euclideanDistance(targetPoint),
        b.euclideanDistance(targetPoint));
  }

}
