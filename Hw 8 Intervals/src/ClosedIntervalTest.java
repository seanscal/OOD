import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class ClosedIntervalTest<T> {

  Integer ten = new Integer(10);


  OpenClosedInterval OC= new OpenClosedInterval<Integer>(1,20);
  ClosedOpenInterval CO= new ClosedOpenInterval<Integer>(7,18);
  ClosedOpenInterval COupperEqualToClosed= new ClosedOpenInterval<Integer>(7,15);
  ClosedInterval Closed= new ClosedInterval<Integer>(7,17);

  OpenClosedInterval empty = new OpenClosedInterval<Integer>(1,1);
  ClosedInterval backwards= new ClosedInterval<Integer>(14,7, Comparator.reverseOrder());

  OpenClosedInterval combined = new OpenClosedInterval<Integer>(1,20);

  ClosedInterval intersectionClosed = new ClosedInterval<Integer>(7,17);
  ClosedOpenInterval intersectionClosedOpen = new ClosedOpenInterval<Integer>(7,15);

  @Test
  public void testContains() {
    assertTrue(Closed.contains(ten));
  }

  @Test
  public void testIsEmpty() throws Exception {
    assertTrue(empty.isEmpty());
    assertFalse(Closed.isEmpty());
  }

  @Test
  public void testBackwardsLBUB() throws Exception {
    assertTrue(backwards.contains(ten));
    assertEquals(backwards.upperBound(),7);
    assertEquals(backwards.lowerBound(),14);
    assertTrue(backwards.contains(ten));
    assertEquals(Closed.upperBound(),17);
    assertEquals(Closed.lowerBound(),7);
  }

  @Test
  public void testIncludes() throws Exception {
    assertTrue(Closed.includes(COupperEqualToClosed));
  }

  @Test
  public void testSpanClosed() throws Exception {
    assertEquals(combined.lowerBound(), Closed.span(OC).lowerBound());
    assertEquals(combined.upperBound(), Closed.span(OC).upperBound());
    assertEquals(combined.lowerBoundType(), Closed.span(OC).lowerBoundType());
    assertEquals(combined.upperBoundType(), Closed.span(OC).upperBoundType());
    assertTrue(combined.equals(Closed.span(OC)));

    assertTrue(Closed.equals(Closed.span(empty)));
  }

  @Test
  public void testClosedIntersection() throws Exception {
    assertEquals(intersectionClosed.lowerBound(), Closed.intersection(OC).lowerBound());
    assertEquals(intersectionClosed.upperBound(), Closed.intersection(OC).upperBound());
    assertEquals(intersectionClosed.lowerBoundType(), Closed.intersection(OC).lowerBoundType());
    assertEquals(intersectionClosed.upperBoundType(), Closed.intersection(OC).upperBoundType());
    assertTrue(intersectionClosed.equals(Closed.intersection(OC)));

    assertEquals(intersectionClosed.lowerBound(), Closed.intersection(CO).lowerBound());
    assertEquals(intersectionClosed.upperBound(), Closed.intersection(CO).upperBound());
    assertEquals(intersectionClosed.lowerBoundType(), Closed.intersection(CO).lowerBoundType());
    assertEquals(intersectionClosed.upperBoundType(), Closed.intersection(CO).upperBoundType());
    assertTrue(intersectionClosed.equals(Closed.intersection(CO)));

    assertEquals(intersectionClosedOpen.lowerBound(),
                 Closed.intersection(COupperEqualToClosed).lowerBound());
    assertEquals(intersectionClosedOpen.upperBound(),
                 Closed.intersection(COupperEqualToClosed).upperBound());
    assertEquals(intersectionClosedOpen.lowerBoundType(),
                 Closed.intersection(COupperEqualToClosed).lowerBoundType());
    assertEquals(intersectionClosedOpen.upperBoundType(),BoundType.Open);
    assertEquals(intersectionClosedOpen.upperBoundType(),
                 Closed.intersection(COupperEqualToClosed).upperBoundType());

    assertTrue(intersectionClosedOpen.equals(Closed.intersection(COupperEqualToClosed)));

    assertEquals("Interval is [7,15)",intersectionClosedOpen.toString());
  }


  @Test(expected = java.lang.IndexOutOfBoundsException.class)
  public void testLowerGreater() throws Exception {
    OpenClosedInterval X = new OpenClosedInterval<Integer>(21,20);
  }

  @Test(expected = java.lang.IndexOutOfBoundsException.class)
  public void testLowerGreaterString() throws Exception {
    OpenClosedInterval X = new OpenClosedInterval<String>("zevra","aminal");
  }

//
//  @Test
//  public void testIntersection() throws Exception {
//
//  }
//
//  @Test
//  public void testSpan() throws Exception {
//
//  }
//
//  @Test
//  public void testGetComparator() throws Exception {
//
//  }
}