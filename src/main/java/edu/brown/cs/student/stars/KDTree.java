package edu.brown.cs.student.stars;

import java.util.Collections;
import java.util.List;

/**
 * K-D Tree data structure for organizing coordinates.
 */
public class KDTree {
  private KDTree left;
  private KDTree right;
  private int root;

  /**
   * Constructor class. Takes in a list of integers which
   * represents the ID's of generic objects. Also takes
   * in a depth parameter that determine's the tree's
   * depth.
   *
   * @param l
   * @param depth
   */
  public KDTree(List<Integer> l, int depth) {
    // Maybe invoke a method to a comparator that outputs a comparator?
    // to fix the compare coordinate probelm
    // depth 0 is the first coordinate
    List<Integer> sorted = Collections.sort(l, CoordinateComparator(depth));
    int medianIndex;
    if (sorted.size() % 2 == 0) {
      // this would get the latter of the two medians in an even list
      // TODO: Test!!!!!!!!
      medianIndex = sorted.size() / 2;
    } else {
      medianIndex = (sorted.size() - 1) / 2;
    }
    // No empty list accepted, will catch in parser / stars command
    // Could change
    if (l.size() == 1) {
      root = l.get(0);
      left = null;
      right = null;
    } else {
      left = KDTree(sorted.subList(0, medianIndex - 1))
      right = KDTree(sorted.subList(medianIndex + 1, 0));
    }

  }
}
