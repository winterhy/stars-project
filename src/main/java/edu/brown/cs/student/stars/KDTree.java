package edu.brown.cs.student.stars;

import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import edu.brown.cs.student.stars.Parser;
/**
 * K-D Tree data structure for organizing coordinates.
 */
public class KDTree {
  private int root;
  private KDTree left;
  private KDTree right;

  /**
   * Constructor class. Takes in a list of integers which
   * represents the ID's of generic objects. Also takes
   * in a depth parameter that determine's the tree's
   * depth.
   *
   * @param l
   * @param dimension
   */
  public KDTree(List<Integer> l, int dimension, int depth) {
    // depth 0 is the first coordinate

    // TODO: Is there a way to fix?
    //if (l.isEmpty()) {
//      root = Integer.parseInt(null);
//      left = null;
//      right = null;
    if (l.size() == 1) {
      root = l.get(0);
      left = null;
      right = null;
    } else {
    // TODO: Change coordinate comparator and its tests to acommodate hashmaps
      // depth is not what it seems
      int coordinate =  dimension;
      List<Integer> sorted = Collections.sort(l, CoordinateComparator(coordinate));
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
      root = sorted.get(medianIndex);
      List<Integer> leftList = sorted.subList(0, medianIndex - 1);
      List<Integer> rightList = sorted.subList(medianIndex + 1, 0);
      if (leftList.isEmpty()) {
        left = null;
        right = new KDTree(rightList, dimension, depth+1);
      } else if (rightList.isEmpty()) {
        left = new KDTree(leftList, dimension, depth+1);
        right = null;
      } else {
        left = new KDTree(leftList, dimension, depth+1);
        right = new KDTree(rightList, dimension, depth+1;
      }
    }

  }

}
