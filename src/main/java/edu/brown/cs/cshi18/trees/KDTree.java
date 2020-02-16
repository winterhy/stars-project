package edu.brown.cs.cshi18.trees;

import edu.brown.cs.cshi18.repl.REPL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * K-D Tree data structure for organizing objects that implments
 * HasCoordinates.
 *
 * @param <T> An object that has coordinates
 */
public class KDTree<T extends HasCoordinates> {
  private T root;
  private KDTree<T> left;
  private KDTree<T> right;
  private int depth;

  /**
   * Access method to get the root of the tree.
   * @return the root of the tree.
   */
  public T getRoot() {
    return root;
  }

  /**
   * Access method to get the left children of the tree.
   * @return the left children of the tree.
   */
  public KDTree<T> getLeft() {
    return left;
  }

  /**
   * Access method to get the right children of the tree.
   * @return the right children of the tree.
   */
  public KDTree<T> getRight() {
    return right;
  }

  /**
   * Access method to get the depth of the tree.
   * @return the depth of the tree.
   */
  public int getDepth() {
    return depth;
  }

  /**
   * Constructor class. Takes in a list of objects that
   * implements HasCoordinates. Also takes
   * in a depth parameter that determine's the tree's
   * initial depth.
   *
   * @param l NON-EMPTY list of objects that has coordinates
   * @param depth keep track of the depth of the tree, init: 0
   */
  public KDTree(List<T> l, int depth) {
    this.setUp(l, depth);
  }

  /**
   * Helps set up in the constructor class.
   *
   * @param l NON-EMPTY list of objects that has coordinates
   * @param depthTracking keep track of the depth of the tree, init: 0
   */
  public void setUp(List<T> l, int depthTracking) {
    int size = l.size();
    if (size == 0) {
      // This error will not be revealed to user
      // Merely for internal testing
      REPL.errorPrint("ERROR: Empty List");
    } else if (size == 1) {
      // End of recursion if size is 1.
      root = l.get(0);
      depth = depthTracking;
      left = null;
      right = null;
    } else {
      int dimension = l.get(0).getCoordinates().size();
      // The coordinate is the axis.
      int coordinate = depthTracking % dimension;
      Collections.sort(l, new CoordinateComparator<T>(coordinate));
      int medianIndex;
      if (size % 2 == 0) {
        // This would get the latter of the two medians in an even elt list
        medianIndex = size / 2;
      } else {
        // This would get the median in an odd elements list
        medianIndex = (size - 1) / 2;
      }
      root = l.get(medianIndex);
      depth = depthTracking;
      List<T> leftList = l.subList(0, medianIndex);
      List<T> rightList = l.subList(medianIndex + 1, size);
      // Recur down the children.
      if (leftList.isEmpty()) {
        left = null;
        right = new KDTree<>(rightList, depthTracking + 1);
      } else if (rightList.isEmpty()) {
        left = new KDTree<>(leftList, depthTracking + 1);
        right = null;
      } else {
        left = new KDTree<>(leftList, depthTracking + 1);
        right = new KDTree<>(rightList, depthTracking + 1);
      }
    }
  }

  /**
   * Wrapper for main part of neighbors method. Takes in the number of
   * neighbors to search and target point and returns a list of k
   * neighbors
   *
   * @param k number of neighbors to search
   * @param targetPoint coordinates of the target point
   * @return a list of k nearest neighbors including the T on the target point
   */
  public List<T> neighbors(int k, List<Number> targetPoint) {
    if (k == 0) {
      return new ArrayList<>();
    } else {
      PriorityQueue<T> queue = new PriorityQueue<>(k,
          new FurthestFirstDistanceComparator<>(targetPoint));
      // outputs queue from furthest to nearest
      neighborsQueue(k, targetPoint, queue);
      List<T> listOfNeighbors = new ArrayList<>(queue);
      Collections.sort(listOfNeighbors, new ClosestFirstDistanceComparator<>(targetPoint));
      return listOfNeighbors;
    }
  }

  /**
   * Main part of neighbors method, accepts queue to recur on.
   *
   * @param k number of neighbors to find
   * @param targetPoint target point for closest neighbors
   * @param queue list of neighbors
  */
  public void neighborsQueue(int k, List<Number> targetPoint, PriorityQueue<T> queue) {
    if (queue.size() < k) {
      queue.add(root);
    } else {
      queue.add(root);
      // Removes the furthest neighbor at the head of the queue
      // Could be what was just added
      queue.remove();
    }
    List<Number> nodeCoordinates = root.getCoordinates();
    int dimension = nodeCoordinates.size();
    int axis = depth % dimension;
    double axisDistance = Math.abs(
        nodeCoordinates.get(axis).doubleValue()
            - targetPoint.get(axis).doubleValue());
    // if less than k neighbors, check both sides
    if (queue.element().euclideanDistance(targetPoint) > axisDistance
        || queue.size() < k) {
      if (left != null && right != null) {
        left.neighborsQueue(k, targetPoint, queue);
        right.neighborsQueue(k, targetPoint, queue);
      } else if (left == null && right != null) {
        right.neighborsQueue(k, targetPoint, queue);
      } else if (left != null) {
        left.neighborsQueue(k, targetPoint, queue);
      }
    } else if (nodeCoordinates.get(axis).doubleValue()
        < targetPoint.get(axis).doubleValue() && right != null) {
      right.neighborsQueue(k, targetPoint, queue);
    } else if (nodeCoordinates.get(axis).doubleValue()
        >= targetPoint.get(axis).doubleValue() && left != null) {
      left.neighborsQueue(k, targetPoint, queue);
    }
  }

  /**
   * Wrapper for main part of radius method. Takes in a non-negative
   * radius and target point and returns the list of T's that are
   * within the radius inclusive
   *
   * @param r radius
   * @param targetPoint coordinates of the target point
   * @return a list of T's that are in the radius including
   * the T on the target point
   */
  public List<T> radius(double r, List<Number> targetPoint) {
    PriorityQueue<T> queue = new PriorityQueue<T>(
        new ClosestFirstDistanceComparator<>(targetPoint));
    // outputs queue from nearest to furthest
    radiusQueue(r, targetPoint, queue);
    List<T> listOfNeighbors = new ArrayList<>(queue);
    Collections.sort(listOfNeighbors, new ClosestFirstDistanceComparator<>(targetPoint));
    return listOfNeighbors;
  }

  /**
   * Main part of neighbors method, accepts queue to recur on.
   *
   * @param r radius
   * @param targetPoint coordinates of the target point
   * @param queue list of T within the radius
   */
  public void radiusQueue(double r, List<Number> targetPoint, PriorityQueue<T> queue) {
    if (root.euclideanDistance(targetPoint) <= r) {
      // Adds to queue if it is within radius
      queue.add(root);
    }
    List<Number> nodeCoordinates = root.getCoordinates();
    int dimension = nodeCoordinates.size();
    int axis = depth % dimension;
    double axisDistance = Math.abs(
        nodeCoordinates.get(axis).doubleValue()
            - targetPoint.get(axis).doubleValue());
    // Recur on both if axis distance is within radius
    if (axisDistance <= r) {
      if (left != null && right != null) {
        left.radiusQueue(r, targetPoint, queue);
        right.radiusQueue(r, targetPoint, queue);
      } else if (left == null && right != null) {
        right.radiusQueue(r, targetPoint, queue);
      } else if (left != null) {
        left.radiusQueue(r, targetPoint, queue);
      }
    } else {
      // My trees are left-leaning, so check left when equals to
      if (nodeCoordinates.get(axis).doubleValue()
          < targetPoint.get(axis).doubleValue() && right != null) {
        right.radiusQueue(r, targetPoint, queue);
      } else if (nodeCoordinates.get(axis).doubleValue()
          >= targetPoint.get(axis).doubleValue() && left != null) {
        left.radiusQueue(r, targetPoint, queue);
      }
    }
  }
}
