import java.util.Comparator;

public class OpenInterval<T> extends AbstractInterval{

  public OpenInterval(T lower, T upper){
    super(lower,upper);

    if (comparator.compare(lowerBound,upperBound) == 0) {
      throw new IllegalArgumentException("Undefined Interval");
    }
  }

  public OpenInterval(T lower, T upper, Comparator<T> comp){
    super(lower,upper,comp);

    if (comparator.compare(lowerBound,upperBound) == 0) {
      throw new IndexOutOfBoundsException("Undefined Interval");
    }
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Open;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Open;
  }
}
