package edu.brown.cs.student.stars;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateComparatorTest {
  private CoordinateComparator _firstCoordinateComparator;
  private CoordinateComparator _secondCoordinateComparator;
  private CoordinateComparator _thirdCoordinateComparator;
  private List<Number> zeroes;
  private List<Number> genericListOne;
  private List<Number> genericListTwo

  /**
   * Sets up the CoordinateComparator.
   */
  @Before
  public void setUp() {
    _firstCoordinateComparator = new CoordinateComparator(0);
    _secondCoordinateComparator = new CoordinateComparator(1);
    _thirdCoordinateComparator = new CoordinateComparator(2);
    zeroes = new ArrayList<Number>();
    zeroes.add(0);
    zeroes.add(0);
    zeroes.add(0);
    genericListOne = new ArrayList<Number>();
    genericListOne.add(1);
    genericListOne.add(-1);
    genericListOne.add(-2);
    genericListTwo = new ArrayList<Number>();
    genericListTwo.add(1);
    genericListTwo.add(2);
    genericListTwo.add(3);
  }

  /**
   * Resets the CommandManager.
   */
  @After
  public void tearDown() {
    _firstCoordinateComparator = null;
    _secondCoordinateComparator = null;
    _thirdCoordinateComparator = null;
  }

  /**
   * Test greater than for various depths
   */
  @Test
  public void testGreaterThan() {
    setUp();
    assertEquals(_firstCoordinateComparator.compare(genericListOne, zeroes), 1);
    tearDown();
  }

}
