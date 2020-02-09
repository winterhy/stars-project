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
  private List<Number> genericListTwo;

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
    genericListOne.add(-2.999);
    genericListTwo = new ArrayList<Number>();
    genericListTwo.add(1);
    genericListTwo.add(2);
    genericListTwo.add(-3);
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
    assertEquals(_firstCoordinateComparator.compare(genericListTwo, zeroes), 1);
    assertEquals(_secondCoordinateComparator.compare(zeroes, genericListOne), 1);
    assertEquals(_secondCoordinateComparator.compare(genericListTwo, genericListOne), 1);
    assertEquals(_thirdCoordinateComparator.compare(genericListOne, genericListTwo), 1);
    assertEquals(_thirdCoordinateComparator.compare(zeroes, genericListTwo), 1);
    tearDown();
  }

  /**
   * Test less than for various depths
   */
  @Test
  public void testLessThan() {
    setUp();
    assertEquals(_firstCoordinateComparator.compare(zeroes, genericListOne), -1);
    assertEquals(_firstCoordinateComparator.compare(zeroes, genericListTwo), -1);
    assertEquals(_secondCoordinateComparator.compare(genericListOne, zeroes), -1);
    assertEquals(_secondCoordinateComparator.compare(genericListOne, genericListTwo), -1);
    assertEquals(_thirdCoordinateComparator.compare(genericListTwo, genericListOne), -1);
    assertEquals(_thirdCoordinateComparator.compare(genericListTwo, zeroes), -1);
    tearDown();
  }

  /**
   * Test equal for various depths
   */
  @Test
  public void testEqual() {
    setUp();
    assertEquals(_firstCoordinateComparator.compare(zeroes, zeroes), 0);
    assertEquals(_firstCoordinateComparator.compare(genericListOne, genericListTwo), 0);
    assertEquals(_firstCoordinateComparator.compare(genericListTwo, genericListOne), 0);
    assertEquals(_secondCoordinateComparator.compare(zeroes, zeroes), 0);
    assertEquals(_thirdCoordinateComparator.compare(genericListOne, genericListOne), 0);
    tearDown();
  }
}
