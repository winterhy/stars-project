package edu.brown.cs.cshi18.stars;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cshi18.trees.FurthestFirstDistanceComparator;
import edu.brown.cs.cshi18.trees.ClosestFirstDistanceComparator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceComparatorTest {
  private FurthestFirstDistanceComparator<Star> furthestFirstDistanceComparator;
  private ClosestFirstDistanceComparator<Star> closestFirstDistanceComparator;
  private Star zeroes;
  private Star genericListOne;
  private Star genericListTwo;

  /**
   * Sets up the Distance Comparators.
   */
  @Before
  public void setUp() {
    List<Number> zeroList = new ArrayList<>(List.of(0,0,0));
    List<Number> oneList = new ArrayList<>(List.of(1,-1,-2.999));
    List<Number> twoList = new ArrayList<>(List.of(1,2,-3));
    furthestFirstDistanceComparator = new FurthestFirstDistanceComparator<>(zeroList);
    closestFirstDistanceComparator = new ClosestFirstDistanceComparator<>(zeroList);
    zeroes = new Star(0,"", zeroList);
    genericListOne = new Star(1, "", oneList);
    genericListTwo = new Star(2, "", twoList);
  }

  /**
   * Resets the CommandManager.
   */
  @After
  public void tearDown() {
    furthestFirstDistanceComparator = null;
    closestFirstDistanceComparator = null;
    zeroes = null;
    genericListOne = null;
    genericListTwo = null;
  }

  /**
   * Test furthest.
   */
  @Test
  public void testFurthest() {
    setUp();
    assertEquals(furthestFirstDistanceComparator.compare(zeroes, genericListOne),
        1);
    assertEquals(furthestFirstDistanceComparator.compare(genericListTwo, zeroes),
        -1);
    assertEquals(furthestFirstDistanceComparator.compare(zeroes, zeroes),
        0);
    tearDown();
  }

  /**
   * Test closest.
   */
  @Test
  public void testClosest() {
    setUp();
    assertEquals(closestFirstDistanceComparator.compare(zeroes, genericListOne),
        -furthestFirstDistanceComparator.compare(zeroes, genericListOne));
    assertEquals(closestFirstDistanceComparator.compare(genericListTwo, zeroes),
        -furthestFirstDistanceComparator.compare(genericListTwo, zeroes));
    assertEquals(closestFirstDistanceComparator.compare(zeroes, zeroes),
        0);
    tearDown();
  }

}
