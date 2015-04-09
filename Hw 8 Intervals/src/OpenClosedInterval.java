import java.util.Comparator;

public class OpenClosedInterval<T> extends AbstractInterval{


  public OpenClosedInterval(T lower, T upper){
    super(lower,upper);
  }

  public OpenClosedInterval(T lower, T upper, Comparator<T> comp){
    super(lower,upper,comp);
  }

  @Override
  public boolean isEmpty() {
    return comparator.compare(lowerBound, upperBound) == 0;
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Open;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Closed;
  }
}
