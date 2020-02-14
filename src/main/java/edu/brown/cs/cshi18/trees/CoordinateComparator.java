package edu.brown.cs.cshi18.trees;

import java.util.Comparator;

public class CoordinateComparator<T extends HasCoordinates> implements Comparator<T> {
  private int coordinate;

  public CoordinateComparator(int coordinate) {
    this.coordinate = coordinate;
  }

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
