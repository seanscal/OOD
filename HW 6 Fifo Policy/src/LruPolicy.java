import java.util.ArrayList;

/**
 * A cache policy implementing the <a
 * href="http://en.wikipedia.org/wiki/Page_replacement_algorithm#Clock">clock
 * algorithm</a>.
 */
public final class LruPolicy<K> implements ReplacementPolicy<K> {

  private final int capacity;
  private int size = 0;
  private ArrayList<K> books;

  /*
   * CLASS INVARIANTS:
   *
   *  - books.length == capacity
   *  - size <= capacity
   *  - items in buffer are unique
   */

  /**
   * Creates a new queue with capacity {@code capacity}.
   *
   * @param cap the capacity of the queue.
   * @throws IllegalArgumentException {@code cap < 1}
   */
  public LruPolicy(int cap) {
    if (cap < 1) {
      throw new IllegalArgumentException("capacity must be at least 1");
    }

    ArrayList<K> temp = new ArrayList<K>();

    books = temp;
    capacity = cap;
  }

  @Override
  public int capacity() {
    return capacity;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public K require(K item) {

    for(int i = 0; i < size; i++) {
      if (books.get(i).equals(item)) {
        books.remove(i);
        books.add(item);
        return null;
      }
    }

    K evicted = null;

    if (size == capacity) {
        evicted = books.get(0);
        books.remove(0);
        size--;
    }

    books.add(item);
    size++;

    return evicted;
  }
}
