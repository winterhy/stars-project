package edu.brown.cs.cshi18.stars;
import edu.brown.cs.cshi18.trees.KDTree;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class KDTreeTest {
  private KDTree<Star> oneNodeTree;
  private KDTree<Star> twoNodeLeftTree;
  private KDTree<Star> threeNodeEvenTree;
  private KDTree<Star> twoNodeSameCoordinates;
  private KDTree<Star> depthThreeA;
  private Star origin;
  private Star one;
  // Should be arbitrary in parent and child

  /**
   * Sets up the KDTrees and Stars.
   */
  @Before
  public void setUp() {
    origin = new Star(0, "Sol",
        new ArrayList<>(List.of(0,0,0)));
    one = new Star(1, "Star One",
        new ArrayList<>(List.of(1,0,0)));
    oneNodeTree = new KDTree<>(new ArrayList<>(List.of(origin)),0);
    twoNodeLeftTree = new KDTree<>(
        new ArrayList<>(List.of(one, origin)), 0);
  }

  /**
   * Resets every KDtree.
   */
  @After
  public void tearDown() {
    oneNodeTree = null;
    twoNodeLeftTree = null;
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
    Star origin = new Star(0, "Sol",
        new ArrayList<>(List.of(0,0,0)));
    Star one = new Star(1, "Star One",
        new ArrayList<>(List.of(1,2,0)));
    Star two = new Star(2, "Star Two",
        new ArrayList<>(List.of(2,1,0)));
    Star oneShift = new Star(3, "",
        new ArrayList<>(List.of(1,0,0)));
    threeNodeEvenTree = new KDTree<>(
        new ArrayList<>(List.of(origin, one, two)), 0);
    assertEquals(threeNodeEvenTree.getRoot(), one);
    assertEquals(threeNodeEvenTree.getLeft().getRoot(), origin);
    assertEquals(threeNodeEvenTree.getRight().getRoot(), two);
  }

  /**
   * Test depthFourA.
   * //TODO: ASK IF THIS TREE IS CORRECT!!!
   */
  @Test
  public void testDepthFourA() {
    Star origin = new Star(0, "Sol",
        new ArrayList<>(List.of(0,0,0)));
    Star firstLeft =  new Star(1, "",
        new ArrayList<>(List.of(-1,3,0)));
    Star firstRight =  new Star(2, "",
        new ArrayList<>(List.of(1,2,0)));
    Star secondLeftLeft =  new Star(3, "",
        new ArrayList<>(List.of(-1,0,5)));
    Star secondLeftRight =  new Star(4, "",
        new ArrayList<>(List.of(-2,4,0)));
    Star secondRightLeft =  new Star(5, "",
        new ArrayList<>(List.of(2,1,4)));
    Star secondRightRight =  new Star(6, "",
        new ArrayList<>(List.of(2,3,0)));
    Star thirdLeftLeftLeft =  new Star(7, "",
        new ArrayList<>(List.of(-2,1,4)));
    Star thirdRightLeftLeft =  new Star(8, "",
        new ArrayList<>(List.of(1,1.2,0)));
    depthThreeA = new KDTree<>(
        new ArrayList<>(List.of(
            origin, firstLeft, firstRight, secondLeftLeft,
            secondLeftRight, secondRightLeft, secondRightRight,
            thirdLeftLeftLeft, thirdRightLeftLeft)), 0);
    assertEquals(depthThreeA.getRoot(), origin);
    assertEquals(depthThreeA.getLeft().getRoot(), firstLeft);
    assertEquals(depthThreeA.getRight().getRoot(), firstRight);
    assertEquals(depthThreeA.getLeft().getLeft().getRoot(), secondLeftLeft);
    assertEquals(depthThreeA.getLeft().getRight().getRoot(), secondLeftRight);
    assertEquals(depthThreeA.getRight().getLeft().getRoot(), secondRightLeft);
    assertEquals(depthThreeA.getRight().getRight().getRoot(), secondRightRight);
    assertEquals(depthThreeA.getLeft().getLeft().getLeft().getRoot(), thirdLeftLeftLeft);
    assertEquals(depthThreeA.getRight().getLeft().getLeft().getRoot(), thirdRightLeftLeft);
  }


  /**
   * Test neighbors method.
   */
  @Test
  public void testNeighbors() {
    setUp();

    // One node tree case:
    assertNull(oneNodeTree.neighbors(0,
        new ArrayList<>(List.of(0,0,0))));
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

    assertEquals(new HashSet<>(twoNodeLeftTree.neighbors(2,
        new ArrayList<>(List.of(1,0,0)))),
        new HashSet<>(new ArrayList<>(List.of(one, origin))));
    assertEquals(new HashSet<>(twoNodeLeftTree.neighbors(2,
        new ArrayList<>(List.of(0,0,0)))),
        new HashSet<>(new ArrayList<>(List.of(one, origin))));
    tearDown();
  }
}
