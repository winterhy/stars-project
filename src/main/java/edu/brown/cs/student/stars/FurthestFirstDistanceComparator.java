package edu.brown.cs.student.stars;

import java.util.Comparator;
import java.util.List;

/**
 * Public class for comparing the distance to the target point from
 * a set of coordinates.
 *
 * @param <T> an object that has coordinates
 */
public class FurthestFirstDistanceComparator<T extends HasCoordinates> implements Comparator<T> {
  private List<Number> targetPoint;

  FurthestFirstDistanceComparator(List<Number> targetPoint) {
    this.targetPoint = targetPoint;
  }

  // think this sort furthest to closest
  public int compare(T a, T b) {
    return -Double.compare(a.euclideanDistance(targetPoint),
        b.euclideanDistance(targetPoint));
  }

}
