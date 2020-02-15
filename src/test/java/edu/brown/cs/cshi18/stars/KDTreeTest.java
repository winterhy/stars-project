package edu.brown.cs.cshi18.stars;
import edu.brown.cs.cshi18.trees.KDTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import static org.junit.Assert.*;

public class KDTreeTest {
  private KDTree<Star> oneNodeTree;
  private KDTree<Star> twoNodeLeftTree;
  private KDTree<Star> threeNodeEvenTree;
  private KDTree<Star> depthThreeA;
  private Star origin;
  private Star one;
  private Star oneTwo;
  private Star twoOne;
  private Star firstLeft;
  private Star firstRight;
  private Star secondLeftLeft;
  private Star secondLeftRight;
  private Star secondRightLeft;
  private Star secondRightRight;
  private Star thirdLeftLeftLeft;
  private Star thirdRightLeftLeft;
  // Should be arbitrary in parent and child

  /**
   * Sets up the KDTrees and Stars.
   */
  @Before
  public void setUp() {
    origin = new Star(0, "Sol",
        new ArrayList<>(List.of(0,0,0)));
    one = new Star(10, "Star One",
        new ArrayList<>(List.of(1,0,0)));
    oneTwo = new Star(12, "Star One Two",
        new ArrayList<>(List.of(1,2,0)));
    twoOne = new Star(21, "Star Two One",
        new ArrayList<>(List.of(2,1,0)));
    firstLeft =  new Star(1, "",
        new ArrayList<>(List.of(-1,3,0)));
    firstRight =  new Star(2, "",
        new ArrayList<>(List.of(1,2,0)));
    secondLeftLeft =  new Star(3, "",
        new ArrayList<>(List.of(-1,0,5)));
    secondLeftRight =  new Star(4, "",
        new ArrayList<>(List.of(-2,4,0)));
    secondRightLeft =  new Star(5, "",
        new ArrayList<>(List.of(2,1,4)));
    secondRightRight =  new Star(6, "",
        new ArrayList<>(List.of(2,3,0)));
    thirdLeftLeftLeft =  new Star(7, "",
        new ArrayList<>(List.of(-2,1,4)));
    thirdRightLeftLeft =  new Star(8, "",
        new ArrayList<>(List.of(1,1.2,0)));
    oneNodeTree = new KDTree<>(new ArrayList<>(List.of(origin)),0);
    twoNodeLeftTree = new KDTree<>(
        new ArrayList<>(List.of(one, origin)), 0);
    threeNodeEvenTree = new KDTree<>(
        new ArrayList<>(List.of(origin, oneTwo, twoOne)), 0);
    depthThreeA = new KDTree<>(
        new ArrayList<>(List.of(
            origin, firstLeft, firstRight, secondLeftLeft,
            secondLeftRight, secondRightLeft, secondRightRight,
            thirdLeftLeftLeft, thirdRightLeftLeft)), 0);
  }

  /**
   * Resets every KDtree.
   */
  @After
  public void tearDown() {
    oneNodeTree = null;
    twoNodeLeftTree = null;
    threeNodeEvenTree = null;
    depthThreeA = null;
  }


  /**
   * Test oneNodeTree.
   */
  @Test
  public void testOneNodeTree() {
    setUp();
    assertEquals(oneNodeTree.getRoot(), origin);
    assertNull(oneNodeTree.getLeft());
    assertNull(oneNodeTree.getRight());
    tearDown();
  }

  /**
   * Test twoNodeLeftTree.
   */
  @Test
  public void testTwoNodeLeftTree() {
    setUp();
    assertEquals(twoNodeLeftTree.getRoot(), one);
    assertEquals(twoNodeLeftTree.getLeft().getRoot(), origin);
    assertNull(twoNodeLeftTree.getRight());
    tearDown();
  }

  /**
  * Test threeNodeEvenTree.
  */
  @Test
  public void testThreeNodeEvenTree() {
    setUp();
    assertEquals(threeNodeEvenTree.getRoot(), oneTwo);
    assertEquals(threeNodeEvenTree.getLeft().getRoot(), origin);
    assertEquals(threeNodeEvenTree.getRight().getRoot(), twoOne);
    tearDown();
  }

  /**
   * Test depthThreeA.
   */
  @Test
  public void testDepthThreeA() {
    setUp();
    assertEquals(depthThreeA.getRoot(), origin);
    assertEquals(depthThreeA.getLeft().getRoot(), firstLeft);
    assertEquals(depthThreeA.getRight().getRoot(), firstRight);
    assertEquals(depthThreeA.getLeft().getLeft().getRoot(), secondLeftLeft);
    assertEquals(depthThreeA.getLeft().getRight().getRoot(), secondLeftRight);
    assertEquals(depthThreeA.getRight().getLeft().getRoot(), secondRightLeft);
    assertEquals(depthThreeA.getRight().getRight().getRoot(), secondRightRight);
    assertEquals(depthThreeA.getLeft().getLeft().getLeft().getRoot(), thirdLeftLeftLeft);
    assertEquals(depthThreeA.getRight().getLeft().getLeft().getRoot(), thirdRightLeftLeft);
    tearDown();
  }

  /**
   * Test neighbors method. Mainly for base and edge cases. Comprehensive testing is
   * done in System Tests.
   */
  @Test
  public void testNeighbors() {
    setUp();

    // One node tree case:
    assertEquals(oneNodeTree.neighbors(0,
        new ArrayList<>(List.of(0,0,0))), new ArrayList<>());
    assertEquals(oneNodeTree.neighbors(1,
        new ArrayList<>(List.of(0,0,0))), new ArrayList<>(List.of(origin)));
    assertEquals(oneNodeTree.neighbors(1,
        new ArrayList<>(List.of(99,99,-99))), new ArrayList<>(List.of(origin)));

    // Two node tree case:
    // Target point equals first root
    assertEquals(twoNodeLeftTree.neighbors(1,
        new ArrayList<>(List.of(1,0,0))), new ArrayList<>(List.of(one)));

    // Target point equals left child
    assertEquals(twoNodeLeftTree.neighbors(1,
       new ArrayList<>(List.of(0,0,0))), new ArrayList<>(List.of(origin)));
    assertEquals(twoNodeLeftTree.neighbors(2,
        new ArrayList<>(List.of(1,0,0))), new ArrayList<>(List.of(one, origin)));
    assertEquals(twoNodeLeftTree.neighbors(2,
        new ArrayList<>(List.of(0,0,0))), new ArrayList<>(List.of(origin, one)));

    tearDown();
  }

  /**
   * Test radius method. Mainly for base and edge cases. Comprehensive testing in
   * system tests.
   */
  @Test
  public void testRadius() {
    setUp();

    // One node tree case:
    assertEquals(oneNodeTree.radius(0,
        new ArrayList<>(List.of(0,0,0))), new ArrayList<>(List.of(origin)));
    assertEquals(oneNodeTree.radius(1,
        new ArrayList<>(List.of(0,0,0))), new ArrayList<>(List.of(origin)));
    assertEquals(oneNodeTree.radius(5,
        new ArrayList<>(List.of(99,99,-99))), new ArrayList<>());

    // Two node tree case:
    // Target point equals first root
    assertEquals(twoNodeLeftTree.radius(0,
        new ArrayList<>(List.of(1,0,0))), new ArrayList<>(List.of(one)));

    // Target point equals left child
    assertEquals(twoNodeLeftTree.radius(1,
        new ArrayList<>(List.of(0,0,0))), new ArrayList<>(List.of(origin, one)));
    assertEquals(twoNodeLeftTree.radius(2,
        new ArrayList<>(List.of(1,0,0))), new ArrayList<>(List.of(one, origin)));
    assertEquals(twoNodeLeftTree.radius(0.99,
        new ArrayList<>(List.of(0,0,0))), new ArrayList<>(List.of(origin)));

    tearDown();
  }
}
