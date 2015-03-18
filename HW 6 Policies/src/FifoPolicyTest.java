import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FifoPolicyTest {

  ReplacementPolicy<Integer> policy = new FifoPolicy<>(5);

  @Before
  public void setUp() {
    policy.require(1);    // 1 _  _  _  _
    policy.require(2);    // 1 2 _  _  _
    policy.require(3);    // 1 2 3 _  _
    policy.require(4);    // 1 2 3 4 _
  }

  @Test
  public void testCapacity() {
    assertEquals(5, policy.capacity());
  }

  @Test
  public void testSize() {
    assertEquals(4, policy.size());
  }

  @Test
  public void testSizeAfterReqInArray() {
    policy.require(1);                  // 1 2 3 4 _
    assertEquals(4, policy.size());
  }

  @Test
  public void testSizeAfterAdd() {
    assertNull(policy.require(5));      // 1 2 3 4 5
    assertEquals(5, policy.size());
  }

  @Test
  public void testSizeAfterReqInAndOut() {
    assertNull(policy.require(5));          // 1 2 3 4 5
    Integer evicted = policy.require(6);    // 2 3 4 5 6
    assertEquals(evicted.intValue(), 1);
    assertEquals(5, policy.size());
  }

  @Test
  public void testRequireAlreadyAdded() {
    assertNull(policy.require(2));                 // 1 2 3 4 _
    assertNull(policy.require(6));                 // 1 2 3 4 6
    assertEquals(policy.require(7).intValue(), 1); // 2 3 4 6 7
  }

  @Test
  public void testRequireAddTwice() {
    policy.require(5);
    assertEquals((Integer) 1, policy.require(6)); // 2 3 4 5 6
    assertEquals((Integer) 2, policy.require(7)); // 3 4 5 6 7
    assertEquals(5, policy.size());
  }

  @Test
  public void testEvictionAfterOrderChangeTwoDiffTypes() {
    ReplacementPolicy<String> policy2 = new FifoPolicy<>(5);
    assertNull(policy2.require("one"));       // one _ _ _ _
    assertNull(policy2.require("two"));       // one two _ _ _
    assertNull(policy2.require("three"));     // one two three _ _
    assertNull(policy2.require("four"));      // one two three four _
    assertNull(policy2.require("five"));      // one two three four five
    assertNull(policy2.require("one"));       // one two three four five
    String evicted = policy2.require("six");  // two three four five six
    assertEquals(evicted, "one");
  }

  @Test
  public void testRequireLong() {
    assertNull(policy.require(5));                // 1 2 3 4 5
    assertEquals((Integer) 1, policy.require(9)); // 2 3 4 5 9
    assertNull(policy.require(5));                // 2 3 4 5 9
    assertEquals((Integer) 2, policy.require(7)); // 3 4 5 9 7
    assertNull(policy.require(3));                // 3 4 5 9 7
    assertNull(policy.require(7));                // 3 4 5 9 7
    assertEquals((Integer) 3, policy.require(6)); // 4 5 9 7 6
    assertNull(policy.require(4));                // 4 5 9 7 6
    assertEquals((Integer) 4, policy.require(2)); // 5 9 7 6 2
    assertNull(policy.require(9));                // 5 9 7 6 2
    assertNull(policy.require(6));                // 5 9 7 6 2
    assertEquals((Integer) 5, policy.require(4)); // 9 7 6 2 4
    assertEquals((Integer) 9, policy.require(8)); // 7 6 2 4 8
  }

  @Test
  public void testInvalidConstructorInput() {
    try {
      ReplacementPolicy<Integer> policy = new FifoPolicy<>(0);
    } catch (IllegalArgumentException ex) {
      assertEquals(ex.getMessage(), "capacity must be at least 1");
    }
  }

  @Test
  public void extendedExample() {
    ReplacementPolicy<Integer> policy = new FifoPolicy<>(5);

    assertEquals(5, policy.capacity());
    assertEquals(0, policy.size());

    // Requiring items starts to fill the cache.

    policy.require(1);                            // 1 _ _ _ _
    assertEquals(1, policy.size());

    policy.require(2);                            // 1 2 _ _ _
    assertEquals(2, policy.size());

    policy.require(1);                            // 1 2 _ _ _
    assertEquals(2, policy.size());

    policy.require(3);                            // 1 2 3 _ _
    policy.require(4);                            // 1 2 3 4 _
    assertEquals(4, policy.size());

    policy.require(1);                            // 1 2 3 4 _
    assertEquals(4, policy.size());

    policy.require(5);                            // 1 2 3 4 5
    assertEquals(5, policy.size());

    // Once the cache is full, requiring an item that is absent causes the
    // oldest item to be evicted

    assertEquals((Integer) 1, policy.require(6)); // 2 3 4 5 6
    assertEquals(5, policy.size());

    assertNull(policy.require(5));                // 2 3 4 5 6
    assertEquals((Integer) 2, policy.require(7)); // 3 4 5 6 7
    assertNull(policy.require(4));                // 3 4 5 6 7
    assertNull(policy.require(5));                // 3 4 5 6 7
    assertEquals((Integer) 3, policy.require(8)); // 4 5 6 7 8
    assertNull(policy.require(5));                // 4 5 6 7 8
    assertEquals((Integer) 4, policy.require(3)); // 5 6 7 8 3
    assertEquals((Integer) 5, policy.require(9)); // 6 7 8 3 9
    assertEquals((Integer) 6, policy.require(0)); // 7 8 3 9 0
    assertEquals((Integer) 7, policy.require(4)); // 8 3 9 0 4
    assertEquals((Integer) 8, policy.require(7)); // 3 9 0 4 7

    assertEquals(5, policy.capacity());
    assertEquals(5, policy.size());
  }
}