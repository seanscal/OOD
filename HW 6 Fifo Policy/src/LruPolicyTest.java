import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LruPolicyTest {
  /**
   * Here's an example of using the LRU policy. The comments on the right
   * show the state of the cache/queue after each {@code require} operation.
   */
  @Test
  public void extendedExample() {
    ReplacementPolicy<Integer> policy = new LruPolicy<>(5);
    assertEquals(5, policy.capacity());
    assertEquals(0, policy.size());

    // Requiring items starts to fill the cache.

    policy.require(1);                            // 1 _ _ _ _
    assertEquals(1, policy.size());

    policy.require(2);                            // 1 2 _ _ _
    assertEquals(2, policy.size());

    // Requiring an item that's already in the cache moves it to the back:
    policy.require(1);                            // 2 1 _ _ _
    assertEquals(2, policy.size());

    policy.require(3);                            // 2 1 3 _ _
    policy.require(4);                            // 2 1 3 4 _
    assertEquals(4, policy.size());

    policy.require(1);                            // 2 3 4 1 _
    assertEquals(4, policy.size());

    policy.require(5);                            // 2 3 4 1 5
    assertEquals(5, policy.size());

    // Once the cache is full, requiring an item that is absent causes the
    // oldest item to be evicted; requiring an item that is present still
    // moves it to the back.

    assertEquals((Integer) 2, policy.require(6)); // 3 4 1 5 6
    assertEquals(5, policy.size());

    assertNull(policy.require(5));                // 3 4 1 6 5
    assertEquals((Integer) 3, policy.require(7)); // 4 1 6 5 7
    assertNull(policy.require(4));                // 1 6 5 7 4
    assertNull(policy.require(5));                // 1 6 7 4 5
    assertEquals((Integer) 1, policy.require(8)); // 6 7 4 5 8
    assertNull(policy.require(5));                // 6 7 4 8 5
    assertEquals((Integer) 6, policy.require(3)); // 7 4 8 5 3
    assertEquals((Integer) 7, policy.require(9)); // 4 8 5 3 9
    assertEquals((Integer) 4, policy.require(0)); // 8 5 3 9 0
    assertEquals((Integer) 8, policy.require(1)); // 5 3 9 0 1

    assertEquals(5, policy.capacity());
    assertEquals(5, policy.size());
  }
}
