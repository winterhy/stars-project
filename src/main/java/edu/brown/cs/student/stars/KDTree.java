package edu.brown.cs.student.stars;

import java.util.Collections;
import java.util.List;

/**
 * K-D Tree data structure for organizing coordinates.
 *
 * @param <T> An object that has coordinates
 */
public class KDTree<T extends HasCoordinates> {
  public T root;
  public KDTree<T> left;
  public KDTree<T> right;

  /**
   * Constructor class. Takes in a list of objects that
   * implements HasCoordinates. Also takes
   * in a depth parameter that determine's the tree's
   * initial depth.
   *
   * @param l list of objects that has coordinates
   * @param depth keep track of the depth of the tree
   */
  public KDTree(List<T> l, int depth) {
    // depth 0 is the first coordinate

    // TODO: Is there a way to fix? About empty lists?
    //if (l.isEmpty()) {
//      root = Integer.parseInt(null);
//      left = null;
//      right = null;
    int size = l.size();
    if (size == 1) {
      root = l.get(0);
      left = null;
      right = null;
    } else {
      // depth starts from 0
      // dimension is 3 for the stars project
      int dimension = l.get(0).getCoordinates().size();
      int coordinate = depth % dimension;
      Collections.sort(l, new CoordinateComparator<T>(coordinate));
      int medianIndex;
      if (size % 2 == 0) {
        // This would get the latter of the two medians in an even elt list
        // TODO: Test??
        medianIndex = size / 2;
      } else {
        // This would get the median in an odd elements list
        medianIndex = (size - 1) / 2;
      }
      root = l.get(medianIndex);
      List<T> leftList = l.subList(0, medianIndex);
      List<T> rightList = l.subList(medianIndex + 1, size);
      if (leftList.isEmpty()) {
        left = null;
        right = new KDTree<>(rightList, depth + 1);
      } else if (rightList.isEmpty()) {
        left = new KDTree<>(leftList, depth + 1);
        right = null;
      } else {
        left = new KDTree<>(leftList, depth + 1);
        right = new KDTree<>(rightList, depth + 1);
      }
    }

  }

  /**
   * Main neighbors method
   */
  public List<T> neighbors() {
    return null;
  }

}
