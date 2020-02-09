package edu.brown.cs.student.stars;

import java.util.Comparator;
import java.util.List;

public class CoordinateComparator implements Comparator<List<Number>> {
  private int coordinate;

  CoordinateComparator(int coordinate) {
    this.coordinate = coordinate;
  }

  public int compare(List<Number> a, List<Number> b) {
    // depths will not be greater or equal to list length
    if (a.get(coordinate).doubleValue() > b.get(coordinate).doubleValue()) {
      return 1;
    } else if (a.get(coordinate).doubleValue() < b.get(coordinate).doubleValue()) {
      return -1;
    } else {
      return 0;
    }

  }

}
