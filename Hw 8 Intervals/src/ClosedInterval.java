import java.util.Comparator;

public class ClosedInterval<T> extends AbstractInterval{

  public ClosedInterval(T value) {
    super(value);
  }

  public ClosedInterval(T value, Comparator<T> comp) {
    super(value,comp);
  }

  public ClosedInterval(T lower, T upper) {
    super(lower,upper);
  }

  public ClosedInterval(T lower, T upper, Comparator<T> comp) {
    super(lower,upper,comp);
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Closed;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Closed;
  }
}
