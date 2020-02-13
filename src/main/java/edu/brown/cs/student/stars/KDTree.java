package edu.brown.cs.student.stars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ListIterator;

/**
 * K-D Tree data structure for organizing coordinates.
 *
 * @param <T> An object that has coordinates
 */
public class KDTree<T extends HasCoordinates> {
  public T root;
  public KDTree<T> left;
  public KDTree<T> right;
  public int depth;

  /**
   * Constructor class. Takes in a list of objects that
   * implements HasCoordinates. Also takes
   * in a depth parameter that determine's the tree's
   * initial depth.
   *
   * @param l list of objects that has coordinates
   * @param depth keep track of the depth of the tree, init: 0
   */
  public KDTree(List<T> l, int depth) {
    int size = l.size();
    if (size == 0) {
      // This error will not be revealed to user
      // Merely for internal testing
      System.out.println("ERROR: Empty List");
    } else if (size == 1) {
      root = l.get(0);
      this.depth = depth;
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
        medianIndex = size / 2;
      } else {
        // This would get the median in an odd elements list
        medianIndex = (size - 1) / 2;
      }
      root = l.get(medianIndex);
      this.depth = depth;
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
   * Wrapper neighbors.
   */
  public List<T> neighbors(int k, List<Number> targetPoint, List<T> lon) {
    if (k == 0) {
      return null;
    } else {
      PriorityQueue<T> queue = new PriorityQueue<T>(
          k, new FurthestFirstDistanceComparator<>(targetPoint));
      //queue.addAll(lon);
      // outputs queue from furthest to nearest
      neighborsQueue(k, targetPoint, queue);
      return new ArrayList<>(queue);
    }
  }

  /**
   * Main part of neighbors method, accepts queue.
   *
   * @param k number of neighbors to find
   * @param targetPoint target point for closest neighbors
   * @param queue list of neighbors
   * @return the list of neighbors
  */
  public void neighborsQueue(int k, List<Number> targetPoint, PriorityQueue<T> queue) {
    if (queue.size() < k) {
      queue.add(root);
      System.out.println("Queue: " + queue);
    } else {
      queue.add(root);
      System.out.println("Queue before remove: " + queue);
      // removes the furthest neighbor at the head of the queue
      queue.remove();
      System.out.println("Queue after remove: " + queue);
    }
    //List<T> updated = new ArrayList<>(queue);
    //System.out.println("Updated List first: " + updated);
    List<Number> nodeCoordinates = root.getCoordinates();
    int dimension = nodeCoordinates.size();
    int axis = depth % dimension;
    double axisDistance = Math.abs(
        nodeCoordinates.get(axis).doubleValue()
            - targetPoint.get(axis).doubleValue());
    // Euclidean distance between
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


  public List<T> radius(double r, List<Number> targetPoint, List<T> listOfStars) {
    PriorityQueue<T> queue = new PriorityQueue<T>(
        new ClosestFirstDistanceComparator<>(targetPoint));
    // outputs queue from nearest to furthest
    radiusQueue(r, targetPoint, queue);
    return new ArrayList<>(queue);
  }

  public void radiusQueue(double r, List<Number> targetPoint, PriorityQueue<T> queue) {
    if (root.euclideanDistance(targetPoint) < r) {
      queue.add(root);
      if (left != null && right != null) {
        left.radiusQueue(r, targetPoint, queue);
        right.radiusQueue(r, targetPoint, queue);
      } else if (left == null && right != null) {
        right.radiusQueue(r, targetPoint, queue);
      } else if (left != null) {
        left.radiusQueue(r, targetPoint, queue);
      }
    } else {
      List<Number> nodeCoordinates = root.getCoordinates();
      int dimension = nodeCoordinates.size();
      int axis = depth % dimension;
      // My trees are left-leaning, so check left when equals to
      if (nodeCoordinates.get(axis).doubleValue()
          >= targetPoint.get(axis).doubleValue() && left != null) {
        left.radiusQueue(r, targetPoint, queue);
      } else if (nodeCoordinates.get(axis).doubleValue()
          < targetPoint.get(axis).doubleValue() && right != null) {
        right.radiusQueue(r, targetPoint, queue);
      }
    }
  }
}
