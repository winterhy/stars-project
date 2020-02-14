package edu.brown.cs.cshi18.stars;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StarTest {
  private Star origin;
  private Star one;
  private Star negative;

  /**
   * Sets up the KDTrees and Stars.
   */
  @Before
  public void setUp() {
    origin = new Star(0, "Sol",
        new ArrayList<>(List.of(0, 0, 0)));
    one = new Star(1, "Star One",
        new ArrayList<>(List.of(1, 0, 0)));
    negative = new Star(1, "",
        new ArrayList<>(List.of(-1,-1,-1)));
  }

  /**
   * Resets the stars.
   */
  @After
  public void tearDown() {
    origin = null;
    one = null;
  }

  /**
   * Tests the getCoordinates method.
   */
  @Test
  public void testGetCoordinates() {
    setUp();
    assertEquals(origin.getCoordinates(),
        new ArrayList<>(List.of(0, 0, 0)));
    assertEquals(one.getCoordinates(),
        new ArrayList<>(List.of(1, 0, 0)));
    tearDown();
  }

  /**
   * Tests the euclideanDistance method.
   */
  @Test
  public void testEuclideanDistance() {
    setUp();
    assertEquals(origin.euclideanDistance(one.getCoordinates()),
        1, 0);
    assertEquals(origin.euclideanDistance(
        new ArrayList<>(List.of(3,0,4))), 25, 0);
    assertEquals(negative.euclideanDistance(
        new ArrayList<>(List.of(2,-1,3))), 25, 0);
    tearDown();
  }


}
