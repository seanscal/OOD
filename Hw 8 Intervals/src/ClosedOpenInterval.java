import java.util.Comparator;

public class ClosedOpenInterval<T> extends AbstractInterval{

  public ClosedOpenInterval(T lower, T upper){
    super(lower,upper);
  }

  public ClosedOpenInterval(T lower, T upper, Comparator<T> comp){
    super(lower,upper,comp);
  }

  @Override
  public boolean isEmpty() {
    return comparator.compare(lowerBound, upperBound) == 0;
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Closed;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Open;
  }
}
