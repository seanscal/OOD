import java.util.Comparator;

public class OpenClosedInterval<T> extends AbstractInterval{

//  public OpenClosedInterval(){
//
//    lowerBound = (T) new Integer(0);
//    upperBound = (T) new Integer(0);
//    comparator = (Comparator<T>) Comparator.naturalOrder();
//  }

//  public OpenClosedInterval(Comparator<T> comp){
//    lowerBound = (T) new Integer(0);
//    upperBound = (T) new Integer(0);
//    comparator = comp;
//  }

  public OpenClosedInterval(T lower, T upper){
    super(lower,upper);

//    if (comparator.compare(lowerBound,upperBound) == 0){
//    }
  }

  public OpenClosedInterval(T lower, T upper, Comparator<T> comp){
    super(lower,upper,comp);

//    if (comparator.compare(lowerBound,upperBound) == 0){
//    }
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
