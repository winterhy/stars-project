package edu.brown.cs.student.stars;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class KDTreeTest {
  private KDTree<Star> oneNodeTree;
  private KDTree<Star> twoNodeLeftTree;
  private KDTree<Star> twoNodeRightTree;
  private KDTree<Star> threeNodeEvenTree;
  private KDTree<Star> twoNodeSameCoordinates;
  // Should be arbitrary in parent and child

  /**
   * Sets up the KDTrees and Stars.
   */
  @Before
  public void setUp() {

  }


  /**
   * Test oneNodeTree.
   */
  @Test
  public void testOneNodeTree() {
    setUp();
    Star origin = new Star(0, "Sol",
        new ArrayList<>(List.of(0,0,0)));
    oneNodeTree = new KDTree<>(new ArrayList<>(List.of(origin)),0);
    assertEquals(oneNodeTree.root, origin);
    assertNull(oneNodeTree.left);
    assertNull(oneNodeTree.right);
  }

  /**
   * Test twoNodeLeftTree.
   */
  @Test
  public void testTwoNodeLeftTree() {
    Star origin = new Star(0, "Sol",
        new ArrayList<>(List.of(0,0,0)));
    Star one = new Star(1, "Star One",
        new ArrayList<>(List.of(1,0,0)));
    twoNodeLeftTree = new KDTree<>(
        new ArrayList<>(List.of(one, origin)), 0);
    assertEquals(twoNodeLeftTree.root, one);
    assertEquals(twoNodeLeftTree.left.root, origin);
    assertNull(twoNodeLeftTree.right);
  }

  /**
   * Test twoNodeRightTree.
   */
  @Test
  public void testTwoNodeRightTree() {
  // can't exist
  }

  /**
  * Test threeNodeEvenTree.
  */
  @Test
  public void testThreeNodeEvenTree() {
    Star origin = new Star(0, "Sol",
        new ArrayList<>(List.of(0,0,0)));
    Star one = new Star(1, "Star One",
        new ArrayList<>(List.of(1,0,0)));
    Star two = new Star(2, "Star Two",
        new ArrayList<>(List.of(2,0,0)));
    threeNodeEvenTree = new KDTree<>(
        new ArrayList<>(List.of(origin, one, two)), 0);
    assertEquals(threeNodeEvenTree.root, one);
    assertEquals(threeNodeEvenTree.left.root, origin);
    assertEquals(threeNodeEvenTree.right.root, two);
  }


}
