import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalTests<T> {

  //testing contains
  Integer ten = new Integer(10);
  Integer one = new Integer(1);
  Integer two = new Integer(2);
  Integer fourteen = new Integer(14);
  Integer fifteen = new Integer(15);

  //testing contains
  String aardvark = new String("aardvark");
  String animal = new String("animal");
  String cat = new String("cat");
  String bird = new String("bird");


  //for testing contains and includes
  ClosedInterval stringCsame= new ClosedInterval<String>("aardvark","cat");
  OpenClosedInterval stringOCsame= new OpenClosedInterval<String>("aardvark","cat");
  ClosedOpenInterval stringCOsame= new ClosedOpenInterval<String>("aardvark","cat");
  OpenInterval stringOsame= new OpenInterval<String>("aardvark","cat");
  ClosedInterval stringCnotsame= new ClosedInterval<String>("aardvark","dog");

  //make sure doubles dont return small decimals when compared.
  ClosedInterval doubleCnotsame= new ClosedInterval<Double>(.1,1.1);


  //for testing contains and includes
  OpenClosedInterval OCsame= new OpenClosedInterval<Integer>(1,15);
  ClosedOpenInterval COsame= new ClosedOpenInterval<Integer>(1,15);
  ClosedInterval Csame = new ClosedInterval<Integer>(1,15);
  OpenInterval Osame = new OpenInterval<Integer>(1,15);


  //testing span and intersection
  OpenClosedInterval OC= new OpenClosedInterval<Integer>(1,20);
  ClosedOpenInterval CO= new ClosedOpenInterval<Integer>(7,18);
  ClosedOpenInterval COupperEqualToClosed= new ClosedOpenInterval<Integer>(7,15);
  ClosedInterval Closed= new ClosedInterval<Integer>(7,17);

  //more span
  ClosedInterval Cspan = new ClosedInterval<Integer>(2,24);
  OpenClosedInterval OCspan= new OpenClosedInterval<Integer>(23,40);
  ClosedOpenInterval COspan= new ClosedOpenInterval<Integer>(24,41);
  OpenInterval Ospan = new OpenInterval<Integer>(2,35);
  OpenInterval OupperForC = new OpenInterval<Integer>(1,24);
  ClosedInterval ClowerForOC = new ClosedInterval<Integer>(23,38);

  //span results Closed
  ClosedInterval CspanOC = new ClosedInterval<Integer>(2,40);
  ClosedOpenInterval CspanCO = new ClosedOpenInterval<Integer>(2,41);
  ClosedOpenInterval CspanO = new ClosedOpenInterval<Integer>(2,35);
  OpenClosedInterval CspanOup = new OpenClosedInterval<Integer>(1,24);

  //span results openclosed
  ClosedInterval OCspanClow = new ClosedInterval<Integer>(23,40);
  OpenInterval OCspanCO = new OpenInterval<Integer>(23,41);
  OpenClosedInterval OCspanO = new OpenClosedInterval<Integer>(2,40);

  //span results closedopen
  OpenInterval COspanO = new OpenInterval<Integer>(2,41);

  //wacky cases
  EmptyInterval empty = new EmptyInterval<>();
  OpenClosedInterval alsoEmpty = new OpenClosedInterval<Integer>(1,1);
  ClosedInterval backwards= new ClosedInterval<Integer>(14,7, Comparator.reverseOrder());

  //span
  OpenClosedInterval combined = new OpenClosedInterval<Integer>(1,20);

  //intersection
  ClosedInterval intersectionClosed = new ClosedInterval<Integer>(7,17);
  ClosedOpenInterval intersectionClosedOpen = new ClosedOpenInterval<Integer>(7,15);


  //SPECIFIC FOR INTERSECTION, try all same vals
  ClosedInterval Cint = new ClosedInterval<Integer>(1,10);
  OpenClosedInterval OCint= new OpenClosedInterval<Integer>(1,10);
  ClosedOpenInterval COint= new ClosedOpenInterval<Integer>(1,10);
  OpenInterval Oint = new OpenInterval<Integer>(1,10);
  ClosedInterval part = new ClosedInterval(5,13);
  OpenInterval OintOut = new OpenInterval<Integer>(40,50);

  //Results Closed
  OpenClosedInterval CintOC = new OpenClosedInterval<Integer>(1,10);
  ClosedOpenInterval CintCO = new ClosedOpenInterval<Integer>(1,10);
  OpenInterval CintO = new OpenInterval<Integer>(1,10);
  ClosedInterval CintPart = new ClosedInterval<Integer>(5,10);

  //Results ClosedOpen
  OpenInterval COintOC = new OpenInterval<Integer>(1,10);
  OpenInterval COintO = new OpenInterval<Integer>(1,10);
  ClosedOpenInterval COintpart = new ClosedOpenInterval<Integer>(5,10);

  //Results OpenClosed
  OpenInterval OCintO = new OpenInterval<Integer>(1,10);
  ClosedInterval OCintpart = new ClosedInterval<Integer>(5,10);

  //Results Open
  ClosedOpenInterval OintPart = new ClosedOpenInterval<Integer>(5,10);

  @Test
  public void testContains() {
    assertTrue(Closed.contains(ten));
  }

  @Test
  public void testIsEmpty() throws Exception {
    assertTrue(empty.isEmpty());
    assertTrue(alsoEmpty.isEmpty());
    assertFalse(Closed.isEmpty());
    assertEquals(empty.upperBoundType(), BoundType.Open);
    assertEquals(empty.lowerBoundType(),BoundType.Closed);
    assertEquals(alsoEmpty.upperBoundType(), BoundType.Closed);
    assertEquals(alsoEmpty.lowerBoundType(),BoundType.Open);
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
  public void testSpan() throws Exception {

    assertTrue(stringCnotsame.equals(stringCsame.span(stringCnotsame)));
    assertTrue(combined.equals(Closed.span(OC)));


    assertTrue(CspanOC.equals(Cspan.span(OCspan)));
    assertTrue(Cspan.equals(Cspan.span(Cspan)));
    assertTrue(CspanCO.equals(Cspan.span(COspan)));
    assertTrue(CspanO.equals(Cspan.span(Ospan)));
    assertTrue(CspanOup.equals(Cspan.span(OupperForC)));

    assertTrue(OCspan.equals(OCspan.span(OCspan)));
    assertTrue(CspanOC.equals(OCspan.span(Cspan)));
    assertTrue(OCspanCO.equals(OCspan.span(COspan)));
    assertTrue(OCspanO.equals(OCspan.span(Ospan)));
    assertTrue(OCspanClow.equals(OCspan.span(ClowerForOC)));

    assertTrue(OCspanCO.equals(COspan.span(OCspan)));
    assertTrue(CspanCO.equals(COspan.span(Cspan)));
    assertTrue(COspan.equals(COspan.span(COspan)));
    assertTrue(COspanO.equals(COspan.span(Ospan)));

    assertTrue(OCspanO.equals(Ospan.span(OCspan)));
    assertTrue(CspanO.equals(Ospan.span(Cspan)));
    assertTrue(COspanO.equals(Ospan.span(COspan)));
    assertTrue(Ospan.equals(Ospan.span(Ospan)));

    assertTrue(Closed.equals(Closed.span(empty)));
    assertTrue(Closed.equals(Closed.span(alsoEmpty)));
  }

  @Test
  public void testIntersection() throws Exception {

    assertTrue(intersectionClosed.equals(Closed.intersection(OC)));
    assertTrue(intersectionClosed.equals(Closed.intersection(CO)));

    assertEquals(7, Closed.intersection(COupperEqualToClosed).lowerBound());
    assertEquals(15, Closed.intersection(COupperEqualToClosed).upperBound());
    assertEquals(intersectionClosedOpen.lowerBoundType(), BoundType.Closed);
    assertEquals(intersectionClosedOpen.upperBoundType(),BoundType.Open);

    assertTrue(intersectionClosedOpen.equals(Closed.intersection(COupperEqualToClosed)));

    assertTrue(CintOC.equals(Cint.intersection(OCint)));
    assertTrue(CintCO.equals(Cint.intersection(COint)));
    assertTrue(CintO.equals(Cint.intersection(Oint)));
    assertTrue(Cint.equals(Cint.intersection(Cint)));
    assertTrue(CintPart.equals(Cint.intersection(part)));
    assertTrue(Cint.intersection(OintOut).isEmpty());
    assertTrue(Cint.intersection(empty).isEmpty());

    assertTrue(OCint.equals(OCint.intersection(OCint)));
    assertTrue(COintOC.equals(OCint.intersection(COint)));
    assertTrue(OCintO.equals(OCint.intersection(Oint)));
    assertTrue(CintOC.equals(OCint.intersection(Cint)));
    assertTrue(OCintpart.equals(OCint.intersection(part)));
    assertTrue(OCint.intersection(OintOut).isEmpty());
    assertTrue(OCint.intersection(empty).isEmpty());

    assertTrue(COintOC.equals(COint.intersection(OCint)));
    assertTrue(COint.equals(COint.intersection(COint)));
    assertTrue(COintO.equals(COint.intersection(Oint)));
    assertTrue(CintCO.equals(COint.intersection(Cint)));
    assertTrue(COintpart.equals(COint.intersection(part)));
    assertTrue(COint.intersection(OintOut).isEmpty());
    assertTrue(COint.intersection(empty).isEmpty());

    assertTrue(OCintO.equals(Oint.intersection(OCint)));
    assertTrue(COintO.equals(Oint.intersection(COint)));
    assertTrue(Oint.equals(Oint.intersection(Oint)));
    assertTrue(CintO.equals(Oint.intersection(Cint)));
    assertTrue(OintPart.equals(Oint.intersection(part)));
    assertTrue(Oint.intersection(OintOut).isEmpty());
    assertTrue(Oint.intersection(empty).isEmpty());

  }


  @Test(expected = java.lang.IndexOutOfBoundsException.class)
  public void testLowerGreater() throws Exception {
    OpenClosedInterval X = new OpenClosedInterval<Integer>(21,20);
  }

  @Test(expected = java.lang.IndexOutOfBoundsException.class)
  public void testLowerGreaterString() throws Exception {
    OpenClosedInterval X = new OpenClosedInterval<String>("zebra","animal");
  }

  @Test(expected = java.lang.IllegalStateException.class)
  public void testEmptyLower() throws Exception {
    assertEquals(alsoEmpty.lowerBound(),'s');
  }

  @Test(expected = java.lang.IllegalStateException.class)
  public void testEmptyUpper() throws Exception {
    assertEquals(empty.upperBound(),'s');
  }


  @Test
  public void testGetComparator(){
    assertEquals(backwards.getComparator(),Comparator.reverseOrder());
    assertEquals(OC.getComparator(),Comparator.naturalOrder());
  }

  @Test
  public void testEdges() throws Exception {
    assertFalse(OCsame.contains(one));
    assertTrue(OCsame.contains(two));
    assertTrue(COsame.contains(one));
    assertTrue(Csame.contains(one));
    assertFalse(Osame.contains(one));
    assertTrue(Osame.contains(two));

    assertTrue(OCsame.contains(fifteen));
    assertFalse(COsame.contains(fifteen));
    assertTrue(COsame.contains(fourteen));
    assertTrue(Csame.contains(fifteen));
    assertFalse(Osame.contains(fifteen));
    assertTrue(Osame.contains(fourteen));

    assertTrue(COsame.includes(Osame));
    assertTrue(Csame.includes(Osame));
    assertTrue(OCsame.includes(Osame));
    assertTrue(Osame.includes(Osame));

    assertFalse(COsame.includes(OCsame));
    assertTrue(Csame.includes(OCsame));
    assertTrue(OCsame.includes(OCsame));
    assertFalse(Osame.includes(OCsame));

    assertFalse(COsame.includes(Csame));
    assertTrue(Csame.includes(Csame));
    assertFalse(OCsame.includes(Csame));
    assertFalse(Osame.includes(Csame));

    assertTrue(COsame.includes(COsame));
    assertTrue(Csame.includes(COsame));
    assertFalse(OCsame.includes(COsame));
    assertFalse(Osame.includes(COsame));

    assertFalse(Csame.includes(OC));
  }

  @Test
  public void testStringEdges() throws Exception {
    assertTrue(doubleCnotsame.contains(.3));
    assertFalse(doubleCnotsame.contains(1.3));


    assertFalse(stringOCsame.contains(aardvark));

    assertTrue(stringOCsame.contains(animal));
    assertTrue(stringCOsame.contains(aardvark));
    assertTrue(stringCsame.contains(aardvark));
    assertFalse(stringOsame.contains(aardvark));
    assertTrue(stringOsame.contains(animal));

    assertEquals(stringOCsame.lowerBound(),aardvark);

    assertTrue(stringOCsame.contains(cat));
    assertFalse(stringCOsame.contains(cat));
    assertTrue(stringCOsame.contains(bird));
    assertTrue(stringCsame.contains(cat));
    assertTrue(stringOsame.contains(bird));
    assertFalse(stringOsame.contains(cat));

    assertTrue(stringCOsame.includes(stringOsame));
    assertTrue(stringCsame.includes(stringOsame));
    assertTrue(stringOCsame.includes(stringOsame));
    assertTrue(stringOsame.includes(stringOsame));

    assertFalse(stringCOsame.includes(stringOCsame));
    assertTrue(stringCsame.includes(stringOCsame));
    assertTrue(stringOCsame.includes(stringOCsame));
    assertFalse(stringOsame.includes(stringOCsame));

    assertFalse(stringCOsame.includes(stringCsame));
    assertTrue(stringCsame.includes(stringCsame));
    assertFalse(stringOCsame.includes(stringCsame));
    assertFalse(stringOsame.includes(stringCsame));

    assertTrue(stringCOsame.includes(stringCOsame));
    assertTrue(stringCsame.includes(stringCOsame));
    assertFalse(stringOCsame.includes(stringCOsame));
    assertFalse(stringOsame.includes(stringCOsame));

    assertFalse(stringCsame.includes(stringCnotsame));
  }

  @Test
  public void testSingleton() throws Exception {
    ClosedInterval c = new ClosedInterval<Integer>(3,3);
    assertEquals(c,Intervals.singleton(3));
  }

  @Test
  public void testToString() throws Exception {
    assertEquals("Interval is [7,15)",intersectionClosedOpen.toString());
  }
}